package cn.aresoft.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class SysDictValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("type", "字典项分类不能为空");
		string("type", true, 1, 50, "字典项分类长度不得大于50");
		required("name", "字典名称不能为空");
		string("name", true, 1, 50, "字典名称长度不得大于50");
		required("value", "字典值不能为空");
		string("value", true, 1, 200, "字典值长度不得大于200");
		string("memo", false, 0, 200, "角色描述长度不得大于200");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
