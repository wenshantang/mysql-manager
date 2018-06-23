package cn.aresoft.manager.web.validate;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

/**
 * 数据校验 
 * @author lxy
 *
 */
public class TemplateSettingValidate extends Validator{

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("template_name","菜单名称不能为空!");
		required("show_name","菜单显示名称不能为空!");
		required("menu_link","菜单链接地址不能为空!");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
	

}
