package cn.aresoft.manager.web.validate;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class ProductPublicInfoValidate extends Validator {
	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		/*required("pro_id", "基金id不能为空");*/
		required("pro_name", "基金名称不能为空");
		required("pro_shortname", "基金简称不能为空");
//		required("pro_englishname", "英文名称不能为空");
//		required("pro_fundmanager", "基金管理人不能为空");
//		required("pro_deposit", "基金托管人不能为空");
		required("fundcode", "基金代码不能为空");
		required("pro_legalname", "法定名称不能为空");
//		required("funcode_fee", "后端收费基金代码不能为空");
//		required("pro_hyplink", "基金超链接不能为空");
		required("pro_fundmanager", "基金管理人不能为空");
		required("pro_deposit", "基金托管人不能为空");
//		required("decimalnumber", "小数位数不能为空");
//		required("setup_date", "成立日期不能为空");
//		required("index2", "索引不能为空");
//		required("minimum_purchase_amount", "最低申购金额不能为空");
//		required("fundsize", "基金规模不能为空");
//		required("currency", "币种不能为空");
//		required("min_purchase_rate", "最低申购费率不能为空");
//		required("per_eva_cri", "业绩评从标准不能为空");
//		required("fund_management_fees", "基金管理费不能为空");
//		required("fund_custody_fees", "基金托管费不能为空");
//		required("fund_category", "基金类别不能为空");
//		required("opening_period", "开放周期不能为空");
//		required("investment", "投资目标不能为空");
//		required("investment_strategy", "投资策略不能为空");
//		required("investment_range", "投资组合范围不能为空");
//		required("next_open_day", "下次开放日不能为空");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
