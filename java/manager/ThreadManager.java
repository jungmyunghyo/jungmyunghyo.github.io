import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Component
@Lazy
@Getter
@Slf4j
public class ThreadManager {
	@Autowired
	private Executor nameExecutor;
	@Autowired
	private Semaphore nameSemaphore;
	
	public final void handleBySemaphoreWithExecutor(Runnable procedure, Semaphore semaphore, Executor executor) {
		Util.handleByExecutor(() -> Util.handleBySemaphore(procedure, semaphore), executor, true);
	}
	private static final class Util {
		private static final int DELAY_MS_DEFAULT = 3_000;
		
		private static final void handleByExecutor(Runnable procedure, Executor executor, boolean retryRequired) {
			try {
				executor.execute(procedure);
			} catch (RejectedExecutionException e) {
				log.error("RejectedExecutionException in execute [handleByExecutor] data.", e);
				if (retryRequired) {
					handleByDelay(DELAY_MS_DEFAULT);
					handleByExecutor(procedure, executor, false);
				}
			}
		}
		private static final void handleBySemaphore(Runnable procedure, Semaphore semaphore) {
			try {
				semaphore.acquire();
				try {
					procedure.run();
				} catch (Exception e) {
					log.error("Exception in run [handleBySemaphore] data.", e);
				} finally {
					semaphore.release();
				}
			} catch (InterruptedException e) {
				log.error("InterruptedException in acquire [handleBySemaphore] data.", e);
				Thread.currentThread().interrupt();
			}
		}
		private static final void handleByDelay(int delay) {
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				log.error("InterruptedException in sleep [handleByDelay] data.", e);
				Thread.currentThread().interrupt();
			}
		}
	}
}