package cn.aresoft.manager.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.puff.log.Log;
import com.puff.log.LogFactory;

public class Scheduler {

	private final Timer timer = new Timer();
	private Log logger = LogFactory.get(Scheduler.class);
	private final Lock lock = new ReentrantLock();

	/**
	 * 退出任务KILL掉线程
	 */
	public void cancel() {
		timer.cancel();
	}

	/**
	 * 设置任务运行时间
	 * 
	 * @param schedulerTask
	 * @param iterator
	 */
	public void schedule(TaskScheduler schedulerTask, TimeCreator timeCreator) {
		Date time = timeCreator.getRunTime();
		if (time == null) // 没有启动时间 就KILL创建的线程
			cancel();
		else {
			lock.lock();
			try {
				if (schedulerTask.getState() != TaskScheduler.INIT_RUN)
					throw new IllegalStateException("任务尚未完成初始化");
				schedulerTask.setState(TaskScheduler.WAIT_RUN);
				timer.schedule(new SchedulerTimerTask(schedulerTask, timeCreator), time);
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * 任务运行完成 设置任务下一次启动时间
	 * 
	 * @param schedulerTask
	 * @param iterator
	 */
	private void reschedule(TaskScheduler schedulerTask, TimeCreator timeCreator) {
		Date time = timeCreator.getRunTime();
		if (time == null) // 没有启动时间 就KILL创建的线程
			cancel();
		else {
			lock.lock();
			try {
				if (schedulerTask.getState() != TaskScheduler.KILL_RUN) {
					schedulerTask.setState(TaskScheduler.WAIT_RUN);
					timer.schedule(new SchedulerTimerTask(schedulerTask, timeCreator), time);
				}
			} finally {
				lock.unlock();
			}
		}
	}

	private class SchedulerTimerTask extends TimerTask {
		private TaskScheduler schedulerTask;
		private TimeCreator timeCreator;

		public SchedulerTimerTask(TaskScheduler schedulerTask, TimeCreator timeCreator) {
			this.schedulerTask = schedulerTask;
			this.timeCreator = timeCreator;
		}

		public void run() {
			try {
				schedulerTask.run();
				logger.info("任务运行完成，设置新的运行时间");
			} catch (Exception e) {
				logger.error("任务运行出错:" + e);
			} finally {
				reschedule(schedulerTask, timeCreator);
			}
		}
	}
}