package cn.aresoft.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.manager.task.TaskJob;

public class SysTaskValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("scheduleName", "任务名称不能为空");
		string("scheduleName", true, 1, 50, "任务名称长度不得大于50");

		required("startTime", "startTimeTip", "启动时间不能为空");

		required("endTime", "endTimeTip", "中止时间不能为空");

		integer("retryCount", 0, 99999, "重试次数为0-99999");

		integer("retryTime", 0, 99999, "重试间隔为0-99999");

		String periodsType = PuffContext.getParameter("periodsType", "0");
		if ("1".equals(periodsType)) {
			required("workDays", "workDayTip", "请选择每周时间");
		}
		if ("2".equals(periodsType)) {
			integer("monthDay", 1, 31, "每月输入值为1-31");
		}
		if ("3".equals(periodsType)) {
			required("specialDate", "specialDateTip", "特定日期不能为空");
			date("specialDate", "yyyy-MM-dd", "specialDateTip", "特定日期不合法");
		}
		required("taskClass", "运行任务Class不能为空");
		custom(new CustomValidate() {
			@Override
			public boolean error(String value) {
				try {
					Class<?> klass = Class.forName(value);
					if (!TaskJob.class.isAssignableFrom(klass)) {
						return true;
					}
				} catch (ClassNotFoundException e) {
					return true;
				}
				return false;
			}
		}, "taskClass", "运行任务Class必须继承" + TaskJob.class.getName());

		string("memo", false, 0, 200, "任务描述长度不得大于200");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
