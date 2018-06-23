package cn.aresoft.manager.web.validate;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

/** 
* @author yyl
* @version 创建时间：2016年3月15日 下午17:56:36 
* 数据校验 
*/
public class ProductPayWaysValidate extends Validator{

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("name","名称不能为空");
		required("code","编码不能为空");
		required("site","站点不能为空");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
	

}
