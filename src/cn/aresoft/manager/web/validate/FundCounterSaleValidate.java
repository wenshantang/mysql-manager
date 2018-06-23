package cn.aresoft.manager.web.validate;

import com.puff.framework.utils.StringUtil;
import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

/** 
* @author yyl
* @version 创建时间：2016年3月15日 下午17:56:36 
* 数据校验 
*/
public class FundCounterSaleValidate extends Validator{

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("vc_name","名称不能为空!");
		required("vc_tel","联系电话不能为空!");
		tel("vc_tel", "联系电话格式不正确");
	    email("vc_email", "email格式不正确");
	    required("vc_address", "地址不能为空");
	    string("vc_address", 0, 200, "地址长度超出范围");
	    String vc_fax=PuffContext.getParameter("vc_fax");
	    if(StringUtil.notBlank(vc_fax)){
	    	string("vc_fax",0, 20, "传真号码长度超出范围");
	    }
		required("vc_post", "邮编不能为空");
		integer("vc_post", 0, 10000000, "邮编格式不正确");
		
		integer("vc_pub0", 0, 200000, "索引格式不正确,请输入数字");
		
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
	

}
