package cn.aresoft.manager.interceptor;

import java.util.List;
import java.util.Map;

import com.puff.core.Puff;
import com.puff.framework.utils.JsonUtil;
import com.puff.ioc.BeanFactory;
import com.puff.web.interceptor.Interceptor;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsPropertiesCache;
import cn.aresoft.cms.common.model.CmsProperties;
import cn.aresoft.common.model.RetCode;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.model.sys.SysDict;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.service.sys.SysDictService;

/**
 * 
 * 判断用户是否登录
 * 
 * @author dongchao
 * 
 */
public class LoginInterceptor implements Interceptor {
	private SysDictService sysDictService = BeanFactory.getBean("sysDictService");
	private CmsPropertiesCache cmsPropertiesCache = BeanFactory.getBean("cmsPropertiesCache");

	@Override
	public void intercept(DispatcherExecutor executor) {
		SysUser user = PuffContext.getSessionAttribute("user");
		if (user != null) {
			if (!PuffContext.ajax()) {
				Map<String, List<SysDict>> map = sysDictService.queryAllDict();
				PuffContext.setAttribute("dict", JsonUtil.toJson(map));
				Map<String, List<CmsProperties>> map2 = cmsPropertiesCache.queryAllProperties();
				PuffContext.setAttribute("dict", JsonUtil.toJson(map));
				PuffContext.setAttribute("properties", JsonUtil.toJson(map2));
			}
			executor.execute();
		} else {
			if (PuffContext.ajax()) {
				RetMsg msg = RetMsg.error(RetCode.SESSION_TIMEOUT, "登陆超时");
				PuffContext.getResponse().setHeader("sessionstatus", "timeout");
				PuffContext.getResponse().setHeader("ctx", Puff.getContextPath());
				executor.setResult(ViewFactory.json(msg));
			} else {
				executor.setResult(ViewFactory.redirect("/admin/console"));
			}
		}
	}
}