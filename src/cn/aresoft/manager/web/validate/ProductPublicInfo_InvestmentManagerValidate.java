package cn.aresoft.manager.web.validate;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class ProductPublicInfo_InvestmentManagerValidate extends Validator {
	@Override
	protected boolean isShort() {
		return false;
	}
	
	@Override
	protected void validate(DispatcherExecutor excutor) {
//		required("id","主键id不能为空");
		required("managerid","基金经理不能为空");		
		required("job","职务不能为空");	
		required("starttime", "starttime_tip","开始时间不能为空");
//		required("endtime", "结束时间不能为空");
		required("i_index","索引不能为空");
	}
	
	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
}
