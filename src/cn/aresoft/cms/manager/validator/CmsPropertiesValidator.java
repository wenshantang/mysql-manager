package cn.aresoft.cms.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class CmsPropertiesValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("type", "配置分类不能为空");
		string("type", true, 1, 50, "配置分类长度不得大于50");
		required("name", "配置名称不能为空");
		string("name", true, 1, 50, "配置名称长度不得大于50");
		required("value", "配置值不能为空");
		string("value", true, 1, 200, "配置值长度不得大于200");
		string("memo", false, 0, 200, "描述长度不得大于200");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
