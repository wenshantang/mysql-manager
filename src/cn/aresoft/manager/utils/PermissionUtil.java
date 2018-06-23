package cn.aresoft.manager.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.service.sys.SysUserService;
import com.puff.ioc.BeanFactory;
import com.puff.web.mvc.PuffContext;

public class PermissionUtil {
	private static final Map<String, List<String>> userRes = new HashMap<String, List<String>>();

	public static void chearPer() {
		userRes.clear();
	}

	public static boolean canInvoke(SysUser user, String url) {
		if ("superAdmin".equals(user.getName())) {
			return true;
		}
		List<String> userRes = getUserRes(String.valueOf(user.getId()));
		return userRes.contains(url);
	}

	public static boolean hasPerm(String url) {
		SysUser user = PuffContext.getSessionAttribute("user");
		if (user == null) {
			return false;
		}
		return canInvoke(user, url);
	}

	public static List<String> getUserRes(String userId) {
		List<String> urls = userRes.get(userId);
		if (urls == null) {
			SysUserService service = BeanFactory.getBean("sysUserService");
			urls = service.queryUserResource(userId);
			userRes.put(userId, urls);
		}
		return urls;
	}

}
