package cn.aresoft.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class SysResourceValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("name", "资源名称不能为空");
		string("name", true, 1, 100, "资源名称长度不得大于100");
		string("url", false, 0, 500, "URL长度不得大于500");
		string("memo", false, 0, 400, "说明长度不得大于400");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
