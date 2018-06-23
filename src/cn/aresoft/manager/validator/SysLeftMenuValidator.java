package cn.aresoft.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class SysLeftMenuValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("name", "菜单名称不能为空");
		string("name", true, 1, 50, "菜单名称长度不得大于50");
		required("idx", "排序不能为空");
		integer("idx", 1, 9999, "排序为1-9999");
		String type = PuffContext.getParameter("type");
		if ("2".equals(type)) {
			required("resourceId", "rssource_id", "请选择导航资源");
		}
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
