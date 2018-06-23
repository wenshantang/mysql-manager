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
public class ProductNavInfoValidate extends Validator{

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("pro_name","pro_name_tip","请选择产品");
		required("net_value","单位净值不能为空");
		required("total_value","累计净值不能为空");
		required("net_date","net_date_tip","净值日期不能为空");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
	

}
