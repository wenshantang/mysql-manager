package cn.aresoft.manager.validator;

import com.puff.framework.utils.StringUtil;
import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class SysDeptJobValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("job_name", "岗位名称不能为空");
		string("job_name", true, 1, 32, "岗位名称长度不得大于32");
		required("dept_id", "dept_id_hidden","所属部门不能为空");
		String job_description=PuffContext.getParameter("job_description");
		if(StringUtil.notBlank(job_description)&&job_description.length()>2000){
			addError("dept_description", "岗位描述超出长度范围");
		}
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
