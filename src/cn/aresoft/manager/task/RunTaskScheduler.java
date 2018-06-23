package cn.aresoft.manager.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.puff.framework.utils.DateTime;
import com.puff.ioc.BeanFactory;
import com.puff.log.Log;
import com.puff.log.LogFactory;

import cn.aresoft.manager.model.sys.SysTask;
import cn.aresoft.manager.model.sys.SysTaskLog;
import cn.aresoft.manager.service.sys.SysTaskLogService;

/**
 * 
 * @ClassName: RunTaskScheduler
 * @Description: 自动任务入口
 * @author 董超 dc.sh@aresoft.cn
 * @date 2012-11-24 下午01:53:09
 */
public final class RunTaskScheduler extends TaskScheduler {

	private Log logger = LogFactory.get(RunTaskScheduler.class);
	private SysTask task;
	private int runCount = 0;// 任务运行次数
	private String scheduleLogId = "0";// 设置全局任务日志ID
										// 原因是防止任务在非第一次运行的时候出错而无法正确记录日志

	private SysTaskLogService sysTaskLogService = BeanFactory.getBean("sysTaskLogService");

	public RunTaskScheduler(SysTask task) {
		this.task = task;
	}

	public SysTask getScheduleInfo() {
		return task;
	}

	public void setScheduleInfo(SysTask task) {
		this.task = task;
	}

	/**
	 * 判断是否运行任务
	 * 
	 * @param count
	 * @return
	 */
	private boolean isRun(int count) {
		String endTime = task.getEndTime();
		String nowTime = getTimeString("HH:mm");
		if (nowTime.compareTo(endTime) > 0) {
			logger.debug("任务结束时间到了，不再运行");
			return false;
		}
		if (count > task.getRetryCount()) {
			logger.debug("任务重试次数已试完，不再运行");
			if (!"0".equals(scheduleLogId)) {
				SysTaskLog taskLog = new SysTaskLog();
				// 更新任务运行日志 任务运行状态为运行失败
				taskLog.setStatus("2");
				taskLog.setLogId(scheduleLogId);
				taskLog.setMemo("任务重试次数已试完，不再运行");
				sysTaskLogService.updateTaskLog(taskLog);
			}
			return false;
		}
		return true;
	}

	private String getTimeString(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern(format);
		return sdf.format(cal.getTime());
	}

	public void run() {
		long start = System.currentTimeMillis();
		setRunningState();
		while (isRun(runCount)) {
			runCount++;// 增加任务运行次数
			String result = execute();// 任务执行结果。1：成功；2：等待重试；3：失败
			if (result.equals("2")) {
				try {
					logger.debug("任务——" + task.getScheduleName() + " 等待重试");
					Thread.sleep(60 * task.getRetryTime());
				} catch (Exception e) {
					logger.error("任务——" + task.getScheduleName() + " 无法重试" + e);
				}
			}
			if (result.equals("1"))
				break;
		}
		long end = System.currentTimeMillis();
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(end - start);
		logger.info(task.getScheduleName() + "成功运行完成—耗时: " + c.get(Calendar.MINUTE) + "分 " + c.get(Calendar.SECOND) + "秒 ");
		setWaitingState();
	}

	private String execute() {
		String reason = "1";
		logger.info("尝试运行任务————>" + task.getScheduleName() + ",第" + runCount + "次运行.");
		SysTaskLog taskLog = new SysTaskLog();
		if (1 == runCount) {
			// 记录任务运行日志 运行状态为运行中
			taskLog.setTaskId(task.getId());
			taskLog.setTaskName(task.getScheduleName());
			taskLog.setStartTime(DateTime.currentTimeStamp());
			taskLog.setStatus("0");
			sysTaskLogService.insert(taskLog);
			scheduleLogId = taskLog.getLogId();
		}
		TaskJob job = task.getJob();
		boolean wait = job.waitting(task);
		if (wait) {
			return "2";
		}
		try {
			task.getJob().execute(task);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 更新任务运行日志 任务运行状态为正常结束
		taskLog.setStatus("1");
		taskLog.setLogId(scheduleLogId);
		sysTaskLogService.updateTaskLog(taskLog);
		return reason;
	}

}
