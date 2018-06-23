package cn.aresoft.manager.interceptor;

import com.puff.log.Log;
import com.puff.log.LogFactory;
import com.puff.web.interceptor.Interceptor;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetCode;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.utils.PermissionUtil;

/**
 * 
 * 判断用户是否有调用权限
 * 
 * @author dongchao
 * 
 */
public class UserPermissionInterceptor implements Interceptor {
	private static final Log LOG = LogFactory.get(UserPermissionInterceptor.class);

	@Override
	public void intercept(DispatcherExecutor executor) {
		SysUser user = PuffContext.getSessionAttribute("user");
		boolean canInvoke = PermissionUtil.canInvoke(user, executor.getExecutorKey());
		if (canInvoke) {
			executor.execute();
		} else {
			LOG.error("The user '" + user.getName() + "' ip:" + PuffContext.getIpAddr() + " invoke target " + executor.getExecutorKey() + " don't have permission ");
			if (PuffContext.ajax()) {
				RetMsg msg = RetMsg.error(RetCode.INVOKE_ALLOW, "您没有权限");
				executor.setResult(ViewFactory.json(msg));
			} else {
				executor.setResult(new MyBeetlView("/common/message.html", "msg", "抱歉，您没有权限访问该页面！！！"));
			}
		}
	}
}