package cn.aresoft.manager.validator;

import com.puff.framework.utils.StringUtil;
import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class SysDeptValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("dept_name", "部门名称不能为空");
		string("dept_name", true, 1, 32, "部门名称长度不得大于32");
		required("dept_type", "dept_type_hidden","部门类型不能为空");
		string("dept_type", true, 1, 2, "部门类型选取不合法");
		String dept_description=PuffContext.getParameter("dept_description");
		if(StringUtil.notBlank(dept_description)&&dept_description.length()>2000){
			addError("dept_description", "部门描述超出长度范围");
		}
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
