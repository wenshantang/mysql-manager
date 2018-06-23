package cn.aresoft.manager.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.puff.framework.utils.DateUtil;
import com.puff.log.Log;
import com.puff.log.LogFactory;

import cn.aresoft.manager.model.sys.SysTask;

public class TimeCreator {
	private final Calendar calendar = Calendar.getInstance();
	private Log logger = LogFactory.get(TimeCreator.class);
	private SysTask task;
	private Calendar lastDate;

	public TimeCreator(SysTask task) {
		this.task = task;
		init();
	}

	public Date getCurrDate() {
		return calendar.getTime();
	}

	private void init() {
		calendar.set(Calendar.HOUR_OF_DAY, task.getStartHour());
		calendar.set(Calendar.MINUTE, task.getStartMinute());

		// 时间未到，就往前退一天
		Calendar tmp = Calendar.getInstance();
		if (tmp.before(calendar))
			calendar.add(Calendar.DATE, -1);
		lastDate = (Calendar) calendar.clone();
	}

	public Date getRunTime() {
		if (lastDate == null || !lastDate.equals(calendar))
			lastDate = (Calendar) calendar.clone();
		else
			lastDate = null;

		if ("3".equals(task.getPeriodsType())) { // 特定日期
			try {
				if (expireDate()) {// 判断是否过期
					logger.error(task.getId() + "---" + task.getScheduleName() + "——>指定的特殊日期已经过期：" + task.getSpecialDate());
					return null;
				} else {
					SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd");

					Date date = sbf.parse(task.getSpecialDate());
					Calendar calc = Calendar.getInstance();
					calc.setTime(date);

					calendar.set(Calendar.YEAR, calc.get(Calendar.YEAR));
					calendar.set(Calendar.MONTH, calc.get(Calendar.MONTH));
					calendar.set(Calendar.DATE, calc.get(Calendar.DATE));

					logger.info(task.getId() + "---" + task.getScheduleName() + "——>运行时间:" + DateUtil.long2Time(calendar.getTimeInMillis()));
					return calendar.getTime();
				}
			} catch (ParseException e) {
				logger.error("特殊日期设置错误", e);
				return null;
			}
		}
		int step = 1;
		calendar.add(Calendar.DATE, step);

		while (!task.isWorkDay(calendar)) {
			calendar.add(Calendar.DATE, step);
		}

		logger.info(task.getId() + "---" + task.getScheduleName() + "——>运行时间:" + DateUtil.long2Time(calendar.getTimeInMillis()));
		return calendar.getTime();
	}

	private boolean expireDate() throws ParseException {
		SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sbf.parse(task.getSpecialDate());
		Calendar calc = Calendar.getInstance();
		calc.setTime(date);
		calc.set(Calendar.HOUR_OF_DAY, task.getStartHour());
		calc.set(Calendar.MINUTE, task.getStartMinute());

		return calc.before(Calendar.getInstance());
	}

	public Date getLastDate() {
		if (lastDate != null) {
			logger.info("上次运行时间:" + DateUtil.long2Time(lastDate.getTimeInMillis()));
			return lastDate.getTime();
		} else {
			logger.error("无法获取上次运行时间");
			return null;
		}
	}

}
