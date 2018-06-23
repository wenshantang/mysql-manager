package cn.aresoft.manager.web.validate;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class Product_RateValidate extends Validator {
	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("fundid", "基金不能为空");
		required("rate_type", "费率类型不能为空");
		required("interval_type", "区间类型不能为空");
		required("valuepre", "区间前缀不能为空");
		required("valuebehind", "区间后缀不能为空");
//		required("showvalue", "显示值不能为空");
		required("valuetype", "值类型不能为空");
		required("rate_value", "费率不能为空");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
}
