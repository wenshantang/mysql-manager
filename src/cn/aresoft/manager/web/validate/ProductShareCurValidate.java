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
public class ProductShareCurValidate extends Validator{

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("cust_id","cust_id_tip","请选择客户");
		required("pro_id","pro_id_tip","请选择产品	");
		required("shares","当前份额不能为空");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
	

}
