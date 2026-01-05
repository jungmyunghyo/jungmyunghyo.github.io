import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.FixedDelayTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Component
public class Scheduler implements SchedulingConfigurer {
	private static final int SCHEDULE_NOT_REQUIRED_THRESHOLD_MS = 300_000;
	private static final int FIXED_DELAY_MS = 3_000;
	
	@Autowired
	private ThreadManager threadManager;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar registry) {
//		List<Task> taskList = Arrays.asList(
//			new Task("0 0 0  * * *",	() -> mnsProcService.processByBid(MnsHelper.Type.Target.RX_MY_CSCP),				true)
//		);
//		this.setScheduledTaskRegistrar(registry, taskList, threadManager.getMnsSchedulerSemaphore(), threadManager.getMnsSchedulerExecutor());
	}
	private final void setScheduledTaskRegistrar(ScheduledTaskRegistrar registry, List<Task> taskList, Semaphore semaphore, Executor executor) {
		AtomicInteger idx = new AtomicInteger(0);
		for (Task task : taskList) {
			String cron = task.getCron();
			Runnable procedure = () -> threadManager.handleBySemaphoreWithExecutor(task.getProcedure(), semaphore, executor);
			boolean runOnStart = task.getRunOnStart();
			if (runOnStart && isRequiredRunOnStart(cron)) {
				registry.addFixedDelayTask(getFixedDelayTask(procedure, idx));
			}
			registry.addCronTask(getCronTask(procedure, cron));
		}
	}
	private static final boolean isRequiredRunOnStart(String cron) {
		Date now = new Date();
		return new CronSequenceGenerator(cron).next(now).getTime() - now.getTime() > SCHEDULE_NOT_REQUIRED_THRESHOLD_MS;
	}
	private static final FixedDelayTask getFixedDelayTask(Runnable procedure, AtomicInteger idx) {
		return new FixedDelayTask(procedure, Long.MAX_VALUE, idx.getAndIncrement() * FIXED_DELAY_MS);
	}
	private static final CronTask getCronTask(Runnable procedure, String cron) {
		return new CronTask(procedure, cron);
	}
	@Getter
	@AllArgsConstructor(access=AccessLevel.PRIVATE)
	private static final class Task {
		private String cron;
		private Runnable procedure;
		private Boolean runOnStart;
	}
}