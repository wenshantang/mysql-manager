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
public class ProductConfValidate extends Validator{

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("cust_id","cust_id_tip","请选择客户");
		required("pro_id","pro_id_tip","请选择产品	");
		required("busi_code","busi_code_tip","请选择交易类型");
		required("req_date","req_date_tip","申请日期不能为空");
		required("open_date","open_date_tip","开放日不能为空");
		required("conf_date","conf_date_tip","确认日期不能为空");
		
		required("net_value","净值不能为空");
		//required("conf_num","确认编号不能为空");
		required("trade_tate","交易费率不能为空");
		required("trade_fee","交易费不能为空");
		required("conf_amts","确认金额不能为空");
		required("conf_shares","确认份额不能为空");
		
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
	

}
