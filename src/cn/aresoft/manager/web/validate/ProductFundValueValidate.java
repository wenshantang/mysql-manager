package cn.aresoft.manager.web.validate;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class ProductFundValueValidate extends Validator {
	@Override
	protected boolean isShort() {
		return false;
	}
	
	@Override
	protected void validate(DispatcherExecutor excutor) {
		String pro_type = PuffContext.getParameter("pro_type");
		required("vc_fundcode", "基金名称不能为空");
		required("c_funddate", "净值日期不能为空");
		if("4".equals(pro_type)){
			required("i_dividendamount", "万份收益不能为空");
			required("i_incomdepercent", "7日年化收益不能为空");
		}else{
			required("i_fundvalue", "净值不能为空");
			required("i_fundtotalnetval", "累计净值不能为空");
		}
	}
	
	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
}
