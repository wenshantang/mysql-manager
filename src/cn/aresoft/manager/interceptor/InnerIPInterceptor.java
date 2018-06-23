package cn.aresoft.manager.interceptor;

import com.puff.web.interceptor.Interceptor;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.ViewFactory;

/**
 * 
 * 判断用户是否内网IP
 * 
 * @author dongchao
 * 
 */
public class InnerIPInterceptor implements Interceptor {

	@Override
	public void intercept(DispatcherExecutor executor) {
		if (!PuffContext.isInnerIP()) {
			executor.setResult(ViewFactory.redirect("/"));
			return;
		}
		executor.execute();
	}
}