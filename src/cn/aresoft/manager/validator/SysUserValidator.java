package cn.aresoft.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class SysUserValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("login_name", "用户名不能为空");
		string("login_name", true, 1, 20, "用户名长度不得大于20");
		if ("/admin/sys/user/insert".equals(excutor.getExecutorKey())) {
			required("pwd", "密码不能为空");
			string("pwd", true, 1, 30, "密码长度不得大于30");
			required("pwd1", "确认密码不能为空");
			string("pwd1", true, 1, 30, "确认密码长度不得大于30");
			equalFiled("pwd", "pwd1", "pwd1", "2次密码不一致");
		}
		required("name", "姓名不能为空");
		string("name", true, 1, 20, "姓名长度不得大于20");
		required("phone", "手机号码不能为空");
		mobile("phone", "手机号码不合法");
		required("email", "Email不能为空");
		email("email", "Email不合法");
		string("email", true, 1, 100, "Email长度不得大于100");
		required("roles", "请选择角色！");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
