package cn.aresoft.manager.model.sys;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
import com.puff.framework.utils.ClassUtil;
import com.puff.framework.utils.StringUtil;
import com.puff.ioc.BeanFactory;

import cn.aresoft.common.model.BaseModel;
import cn.aresoft.manager.service.sys.SysTaskService;
import cn.aresoft.manager.task.TaskJob;

@Table("SYS_TASK")
public class SysTask extends BaseModel {

	private static final long serialVersionUID = 8183612230825949542L;
	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column("SCHEDULE_NAME")
	private String scheduleName;// 任务名称

	@Column("START_TIME")
	private String startTime;// 启动时间

	@Column("END_TIME")
	private String endTime;// 结束时间

	@Column("WORK_DAY")
	private String workDay;// 普通工作日

	@Column("MONTH_DAY")
	private String monthDay;// 每月工作日期

	@Column("IS_FUNDDAY")
	private String isFundday = "0";// 是否基金工作日

	@Column("SPECIAL_DATE")
	private String specialDate;// 特殊启动时间

	@Column("RETRY_COUNT")
	private Integer retryCount;// 重试次数

	@Column("RETRY_TIME")
	private Integer retryTime;// 重试时间

	@Column("IS_RUN")
	private String isRun = "1";// 是否运行

	@Column("STATUS")
	private String status;// 任务状态

	@Column("PERIODS_TYPE")
	private String periodsType;// 0：每日，1：每周，2：每月，3:特定日期

	@Column("TASK_CLASS")
	private String taskClass;// 任务运行class

	@Column("CREATE_TIME")
	private String createTime;

	@Column
	private String memo;

	private TaskJob job;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScheduleName() {
		return scheduleName;
	}

	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getWorkDay() {
		return workDay;
	}

	public void setWorkDay(String workDay) {
		this.workDay = workDay;
	}

	public String getMonthDay() {
		return monthDay;
	}

	public void setMonthDay(String monthDay) {
		this.monthDay = monthDay;
	}

	public String getIsFundday() {
		return isFundday;
	}

	public void setIsFundday(String isFundday) {
		this.isFundday = isFundday;
	}

	public String getSpecialDate() {
		return specialDate;
	}

	public void setSpecialDate(String specialDate) {
		this.specialDate = specialDate;
	}

	public Integer getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}

	public Integer getRetryTime() {
		return retryTime;
	}

	public void setRetryTime(Integer retryTime) {
		this.retryTime = retryTime;
	}

	public String getIsRun() {
		return isRun;
	}

	public void setIsRun(String isRun) {
		this.isRun = isRun;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPeriodsType() {
		return periodsType;
	}

	public void setPeriodsType(String periodsType) {
		this.periodsType = periodsType;
	}

	public String getTaskClass() {
		return taskClass;
	}

	public void setTaskClass(String taskClass) {
		this.taskClass = taskClass;
		this.job = (TaskJob) ClassUtil.newInstance(taskClass);
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public TaskJob getJob() {
		return job;
	}

	public int getStartHour() {
		if (startTime != null) {
			try {
				return Integer.parseInt(startTime.substring(0, startTime.indexOf(":")));
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	public int getStartMinute() {
		if (startTime != null) {
			try {
				return Integer.parseInt(startTime.substring(startTime.indexOf(":") + 1));
			} catch (Exception e) {
				return 0;
			}
		}
		return 0;
	}

	public boolean isWorkDay(Calendar calendar) {
		if ("0".equals(periodsType))
			return isFundDay(calendar);
		else if ("1".equals(periodsType)) {
			if (workDay.charAt(calendar.get(Calendar.DAY_OF_WEEK) - 1) == '1')
				return isFundDay(calendar);
			else
				return false;
		} else {
			if (workDay.charAt(0) == '0' && workDay.length() > 1)
				workDay = workDay.substring(1);
			if (String.valueOf(calendar.get(Calendar.DATE)).equals(workDay))
				return isFundDay(calendar);
			else
				return false;
		}
	}

	/**
	 * 判断当天是否为基金工作日
	 * 
	 * @param calendar
	 * @param service
	 * @return
	 */
	private boolean isFundDay(Calendar calendar) {
		if (StringUtil.notEmpty(getIsFundday()) && getIsFundday().equals("1")) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String date = format.format(calendar.getTime());
			SysTaskService service = BeanFactory.getBean("sysTaskService");
			WorkDay day = service.getWorkDay(date);
			return day != null;
		}
		return true;
	}
}
