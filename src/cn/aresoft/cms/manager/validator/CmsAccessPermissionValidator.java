package cn.aresoft.cms.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class CmsAccessPermissionValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("name", "名称不能为空");
		string("name", 1, 50, "名称长度为1-50");
		string("memo", false, 0, 50, "描述长度为不能超过50");
		required("class_name", "class_name不能为空");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
