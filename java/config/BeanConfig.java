import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;

@Configuration
public class BeanConfig implements BeanDefinitionRegistryPostProcessor {
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
	}
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		if (beanFactory instanceof DefaultListableBeanFactory) {
			DefaultListableBeanFactory defaultBeanFactory = (DefaultListableBeanFactory) beanFactory;
			
			for (OkHttp.Task task : OkHttp.TASKS) {
				OkHttpClient okHttpClient = OkHttp.getClient(task.getConnectTimeOut(), task.getWriteTimeOut(), task.getReadTimeOut());
				defaultBeanFactory.registerSingleton(task.getClientKey(), okHttpClient);
				defaultBeanFactory.registerDisposableBean(task.getClientKey(), OkHttp.getDisposableBean(okHttpClient));
			}
			for (ThreadPool.Task task : ThreadPool.TASKS) {
				ThreadPoolTaskExecutor executor = ThreadPool.getExecutor(task.getExecutorKey(), task.getSize());
				defaultBeanFactory.registerSingleton(task.getExecutorKey(), executor);
				defaultBeanFactory.registerDisposableBean(task.getExecutorKey(), ThreadPool.getDisposableBean(executor));
				
				Semaphore semaphore = ThreadPool.getSemaphore(task.getSize());
				defaultBeanFactory.registerSingleton(task.getSemaphoreKey(), semaphore);
			}
		}
	}
	private static final class OkHttp {
		private static final String OK_HTTP_CLIENT_SUFFIX = "OkHttpClient";
		
		private static final int CONNECT_TIMEOUT_MS = 1_500;
		private static final int WRITE_TIMEOUT_MS = 2_500;
		
		private static final int MAX_REQUESTS = 200;
		
		private static final List<Protocol> PROTOCOLS = Collections.singletonList(Protocol.HTTP_1_1);
		
		private static final List<Task> TASKS = Arrays.asList(
			new Task("name", 300_000)
		);
		private static final OkHttpClient getClient(int connectTimeOut, int writeTimeOut, int readTimeOut) {
			return new OkHttpClient.Builder()
					.connectTimeout(connectTimeOut, TimeUnit.MILLISECONDS)
					.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS)
					.readTimeout(readTimeOut, TimeUnit.MILLISECONDS)
					.callTimeout(connectTimeOut + writeTimeOut + readTimeOut, TimeUnit.MILLISECONDS)
					.retryOnConnectionFailure(false)
					.followRedirects(false)
					.followSslRedirects(false)
					.protocols(PROTOCOLS)
					.dispatcher(getDispatcher())
					.connectionPool(getConnectionPool())
					.build();
		}
		private static final Dispatcher getDispatcher() {
			Dispatcher dispatcher = new Dispatcher();
			dispatcher.setMaxRequests(MAX_REQUESTS);
			dispatcher.setMaxRequestsPerHost(dispatcher.getMaxRequests() / 2);
			return dispatcher;
		}
		private static final ConnectionPool getConnectionPool() {
			return new ConnectionPool(0, 1, TimeUnit.SECONDS);
		}
		private static final DisposableBean getDisposableBean(OkHttpClient okHttpClient) {
			return new DisposableBean() {
				@Override
				public void destroy() throws Exception {
					okHttpClient.dispatcher().executorService().shutdown();
					okHttpClient.connectionPool().evictAll();
				}
			};
		}
		@AllArgsConstructor(access=AccessLevel.PRIVATE)
		private static final class Task {
			private String name;
			private int readTimeOut;
			
			private String getClientKey() {return this.name + OK_HTTP_CLIENT_SUFFIX;};
			private int getConnectTimeOut() {return CONNECT_TIMEOUT_MS;};
			private int getWriteTimeOut() {return WRITE_TIMEOUT_MS;};
			private int getReadTimeOut() {return this.readTimeOut;}
		}
	}
	private static final class ThreadPool {
		private static final String EXECUTOR_SUFFIX = "Executor";
		private static final String SEMAPHORE_SUFFIX = "Semaphore";
		
		private static final String THREAD_NAME_DELIMITER = "-";
		
		private static final int SEMAPHORE_BASE_UNIT = 10;
		
		private static final double EXECUTOR_MAX_RATIO = 2.5;
		private static final double EXECUTOR_QUEUE_RATIO = EXECUTOR_MAX_RATIO * 10.0;
		
		private static final int AWAIT_SECONDS = 60;
		
		private static final AbortPolicy REJECTED_EXECUTION_POLICY = new ThreadPoolExecutor.AbortPolicy();
		
		private static final List<Task> TASKS = Arrays.asList(
			new Task("name", 1)
		);
		private static final ThreadPoolTaskExecutor getExecutor(String key, int size) {
			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
			executor.setCorePoolSize(size);
			executor.setMaxPoolSize((int) (executor.getCorePoolSize() * EXECUTOR_MAX_RATIO));
			executor.setQueueCapacity((int) (executor.getCorePoolSize() * EXECUTOR_QUEUE_RATIO));
			executor.setThreadNamePrefix(key + THREAD_NAME_DELIMITER);
			executor.setWaitForTasksToCompleteOnShutdown(false);
			executor.setAwaitTerminationSeconds(AWAIT_SECONDS);
			executor.setRejectedExecutionHandler(REJECTED_EXECUTION_POLICY);
			executor.initialize();
			return executor;
		}
		private static final DisposableBean getDisposableBean(ThreadPoolTaskExecutor executor) {
			return new DisposableBean() {
				@Override
				public void destroy() throws Exception {
					executor.shutdown();
					if (!executor.getThreadPoolExecutor().awaitTermination(AWAIT_SECONDS / 2, TimeUnit.SECONDS)) {
						executor.getThreadPoolExecutor().shutdownNow();
					}
				}
			};
		}
		private static final Semaphore getSemaphore(int size) {
			return new Semaphore(size);
		}
		@AllArgsConstructor(access=AccessLevel.PRIVATE)
		private static final class Task {
			private String name;
			private int unit;
			
			private String getExecutorKey() {return this.name + EXECUTOR_SUFFIX;}
			private String getSemaphoreKey() {return this.name + SEMAPHORE_SUFFIX;}
			private int getSize() {return this.unit * SEMAPHORE_BASE_UNIT;}
		}
	}
}