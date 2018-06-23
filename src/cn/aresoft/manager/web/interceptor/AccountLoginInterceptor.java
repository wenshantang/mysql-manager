package cn.aresoft.manager.web.interceptor;

import com.puff.log.Log;
import com.puff.log.LogFactory;
import com.puff.web.interceptor.Interceptor;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;

/**
 * 
 * 判断用户是否登录 controller拦截
 * 
 */
public class AccountLoginInterceptor implements Interceptor {
	Log log=LogFactory.get(getClass());
	
	@Override
	public void intercept(DispatcherExecutor executor) {
		String login_sign = PuffContext.findCookieValue("login_sign");
		//String openid = (String) PuffContext.getSession().getAttribute("openId");
		log.info("invoke AccountLoginInterceptor>>>");
	}
}