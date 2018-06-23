package cn.aresoft.manager.task;

import com.puff.framework.utils.StringUtil;

import cn.aresoft.manager.model.sys.SysTask;
import cn.aresoft.manager.model.sys.Week;

public class SchedulerUtil {

	private SchedulerUtil() {
	}

	public static Scheduler getScheduler(SysTask sysTask) {
		TimeCreator time = new TimeCreator(sysTask);
		RunTaskScheduler runTask = new RunTaskScheduler(sysTask);
		Scheduler scheduler = new Scheduler();
		scheduler.schedule(runTask, time);
		return scheduler;
	}

	public static SysTask refresh(SysTask task) {
		if ("0".equals(task.getPeriodsType())) {// 每天
			task.setWorkDay("1111111");
			task.setSpecialDate("");
		} else if ("1".equals(task.getPeriodsType())) {// 每周
			task.setWorkDay(initWorkDay(task.getWorkDay()));
			task.setSpecialDate("");
		} else if ("2".equals(task.getPeriodsType())) {// 每月
			task.setWorkDay(task.getMonthDay());
			task.setSpecialDate("");
		} else if ("3".equals(task.getPeriodsType())) {// 特殊日期
			task.setWorkDay("");
		}
		return task;
	}

	/**
	 * 计算工作日
	 */
	private static String initWorkDay(String workDay) {
		String retrunValue = "";
		if (StringUtil.notEmpty(workDay)) {
			String[] item = workDay.split(", ");
			String[] result = { "0", "0", "0", "0", "0", "0", "0" };
			for (int i = 0; i < item[0].length(); i++) {
				int a = Integer.parseInt(result[i]);
				for (int j = 0; j < item.length; j++) {
					int b = Integer.parseInt(String.valueOf(item[j].charAt(i)));
					a = a | b;
					if (a == 1)
						break;
				}
				result[i] = String.valueOf(a);
			}
			String tmp = "";
			for (int i = 0; i < result.length; i++)
				tmp += result[i];
			retrunValue = tmp;
		} else
			retrunValue = "0000000";
		return retrunValue;
	}

	public static Week getWeek(String workDay) {
		Week week = new Week();
		for (int i = 0; i < 7; i++) {
			if ("1".equals(String.valueOf(workDay.charAt(i)))) {
				switch (i) {
				case 0:// 周日
					week.setSunday("1");
					break;
				case 1:// 周一
					week.setMonday("1");
					break;
				case 2:// 周二
					week.setTuesday("1");
					break;
				case 3:// 周三
					week.setWendsday("1");
					break;
				case 4:// 周四
					week.setThursday("1");
					break;
				case 5:
					week.setFriday("1");
					break;
				case 6:
					week.setSaturday("1");
					break;
				}
			}
		}
		return week;
	}
}
