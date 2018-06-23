package cn.aresoft.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class SysRoleValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("code", "角色代码不能为空");
		string("code", true, 1, 50, "角色代码长度不得大于50");
		required("name", "角色名不能为空");
		string("name", true, 1, 50, "角色名长度不得大于50");
		string("memo", false, 0, 300, "角色描述长度不得大于300");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
