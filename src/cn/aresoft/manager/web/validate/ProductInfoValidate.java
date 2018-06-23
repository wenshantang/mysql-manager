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
public class ProductInfoValidate extends Validator{

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("pro_id","产品代码不能为空");
		required("pro_name","产品名称不能为空");		
		required("pro_shortname","产品简称不能为空");	
		required("investmentmanagerid", "investmentmanagerid_tip", "投资经理不能为空");
		required("setup_date", "setup_date_tip", "成立日期不能为空");
		required("pro_type","产品类型不能为空");	
		
		required("subscription_point","认购起点不能为空");
		required("close_date","封闭期不能为空");	
		required("open_date","开放日不能为空");	
		
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
	

}
