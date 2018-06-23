package cn.aresoft.manager.web.validate;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class QuestionnaireValidate extends Validator {

	@Override
	protected boolean isShort() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		// TODO Auto-generated method stub
		required("wname","问卷名称不能为空");
		required("result","风险等级不能为空");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		// TODO Auto-generated method stub
		return ViewFactory.json();
	}

}
