package cn.aresoft.manager.controller;

import com.puff.framework.annotation.Before;
import com.puff.framework.annotation.InterceptorChain;
import com.puff.web.mvc.PuffContext;

import cn.aresoft.manager.interceptor.LoginInterceptor;
import cn.aresoft.manager.interceptor.UserPermissionInterceptor;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.common.model.CommonParam;

@InterceptorChain({ @Before(LoginInterceptor.class), @Before(UserPermissionInterceptor.class) })
public class CommonController {

	public CommonParam getCommonParam() {
		CommonParam p = new CommonParam();
		p.setPage(PuffContext.getIntParam("page", 1));
		p.setRows(PuffContext.getIntParam("rows", 10));
		p.setOrder(PuffContext.getParameter("order"));
		p.setSort(PuffContext.getParameter("sort"));
		return p;
	}

	public SysUser getSysUser() {
		return PuffContext.getSessionAttribute("user");
	}
}
