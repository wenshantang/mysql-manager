package cn.aresoft.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

/**
 * 数据校验 
 * @author Administrator
 *
 */
public class SysFileValidate extends Validator{

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("file_menu","资源目录不能为空!");
		required("file_describe","资源说明不能为空!");
		required("http_url","请上传资源!");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
	

}
