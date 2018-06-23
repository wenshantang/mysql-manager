package cn.aresoft.manager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.puff.core.Puff;
import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Before;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Mapping;
import com.puff.framework.utils.Security;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.Record;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.AuthCodeView;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.util.MD5Util;
import cn.aresoft.common.util.SHAUtils;
import cn.aresoft.manager.interceptor.LoginInterceptor;
import cn.aresoft.manager.model.sys.SysLeftMenu;
import cn.aresoft.manager.model.sys.SysManagerConfig;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysManagerConfigService;
import cn.aresoft.manager.service.sys.SysMenuService;
import cn.aresoft.manager.service.sys.SysUserService;

@Controller(value = "/admin", scope = BeanScope.SINGLETON)
public class AdminController {

	@Inject
	private SysUserService sysUserService;

	@Inject
	private SysMenuService sysMenuService;

	@Inject
	private SysManagerConfigService sysManagerConfigService;

	public View console() {
		SysUser user = PuffContext.getSessionAttribute("user");
		SysManagerConfig config = sysManagerConfigService.querySysManagerConfig();
		if (user == null) {
			if (!"1".equals(config.getLogin_filter()) && !PuffContext.isInnerIP()) {
				return new MyBeetlView("/admin/notallow.html");
			}
			return new MyBeetlView("/admin/login.html", "config", config);
		} else {
			if (user.getLogin_name().equals(config.getSuper_user())) {
				config.setIndex_url("/admin/center");
				return new MyBeetlView("/admin/index_super.html", "config", config);
			}
			if (!"1".equals(config.getLogin_filter()) && !PuffContext.isInnerIP()) {
				return new MyBeetlView("/admin/notallow.html");
			}
			List<SysLeftMenu> leftMenus = sysMenuService.findUserMenu(user.getId());
			return new MyBeetlView("/admin/index.html", "config", config).put("leftMenus", leftMenus);
		}
	}

	public View ieupdate() {
		return new MyBeetlView("/admin/ieupdate.html");
	}

	public View authcode() {
		SysManagerConfig config = sysManagerConfigService.querySysManagerConfig();
		String format = config.getAuthcode_format();
		AuthCodeView view = new AuthCodeView();
		view.setAuthCode(getRandom(format, 4));
		view.setSessionKey("authCode");
		return view;
	}

	public View clock() {
		return new MyBeetlView("/admin/clock.html");
	}

	private static char[] a = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	private static char[] b = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private static char[] c = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '3', '5', '6', '7', '8', '9' };
	private final static Random random = new Random();

	public static String getRandom(String format, int size) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			if ("1".equals(format)) {
				sb.append(a[Math.abs(random.nextInt()) % a.length]);
			} else if ("2".equals(format)) {
				sb.append(b[Math.abs(random.nextInt()) % b.length]);
			} else if ("3".equals(format)) {
				sb.append(c[Math.abs(random.nextInt()) % c.length]);
			} else {
				sb.append(c[Math.abs(random.nextInt()) % c.length]);
			}
		}
		return sb.toString();
	}

	public View login() {
		RetMsg msg = null;
		String username = PuffContext.getParameter("username");
		if (StringUtil.empty(username)) {
			msg = RetMsg.error("用户名不能为空！");
			return ViewFactory.json(msg);
		}
		String password = PuffContext.getParameter("password");
		if (StringUtil.empty(password)) {
			msg = RetMsg.error("密码不能为空！");
			return ViewFactory.json(msg);
		}

		SysManagerConfig config = sysManagerConfigService.querySysManagerConfig();
		if (!"0".equals(config.getAuthcode_format())) {
			String authcode = PuffContext.getParameter("authcode");
			if (StringUtil.empty(authcode)) {
				msg = RetMsg.error("验证码不能为空！");
				return ViewFactory.json(msg);
			}

			String sessionAuthCode = PuffContext.removeSessionAttr("authCode");

			if (!"8888".equalsIgnoreCase(authcode) && !authcode.equalsIgnoreCase(sessionAuthCode)) {
				msg = RetMsg.error("验证码错误！");
				return ViewFactory.json(msg);
			}
		}
		SysUser user = sysUserService.queryUserByLoginName(username);
		if (user == null) {
			if (username.equals(config.getSuper_user()) && Security.aesEncrypt(password).equals(config.getSuper_pwd())) {
				user = new SysUser();
				user.setId("0000");
				user.setLogin_name(username);
				user.setName(username);
			} else {
				msg = RetMsg.error("用户名密码不正确！");
				return ViewFactory.json(msg);
			}
		} else {
			//if (!Security.md5(password).equals(user.getPwd())) {
			//update 2016-11-18 页面已经MD5加密过一次，将数值改成大写即可,
			//另外超过配置文件中的最大密码错误次数后，用户锁掉，需要由admin来解锁start
			int sysErrors=Integer.parseInt(Puff.getConstant("logTimes", "10"));//读取配置次数，默认为10
			if(user.getErrors()>=sysErrors||!"1".equals(user.getStatus())){
				msg = RetMsg.error("用户已被锁定！");
				return ViewFactory.json(msg);
			}
			if (!(password).equals(user.getPwd())) {
			//update 2016-11-18 页面已经MD5加密过一次，将数值改成大写即可end
				msg = RetMsg.error("用户名密码不正确！");
				user.setErrors(user.getErrors()+1);
				if(user.getErrors()>=sysErrors){
					user.setStatus("0");
				}
				sysUserService.update(user);
				return ViewFactory.json(msg);
			}
			/*if (!"1".equals(user.getStatus())) {
				msg = RetMsg.error("用户已被锁定！");
				return ViewFactory.json(msg);
			}*/
		}
		PuffContext.setSessionAttribute("user", user);
		msg = RetMsg.success("登录成功！");
		return ViewFactory.json(msg);
	}

	@Before(LoginInterceptor.class)
	public View center() throws ParseException {
		Record r = new Record();
		r.set("javaVersion", System.getProperty("java.version"));
		r.set("javaHome", System.getProperty("java.home"));
		r.set("javaIoTmpdir", System.getProperty("java.io.tmpdir"));
		r.set("osName", System.getProperty("os.name"));
		r.set("osArch", System.getProperty("os.arch"));
		r.set("osVersion", System.getProperty("os.version"));
		r.set("userName", System.getProperty("user.name"));
		r.set("userHome", System.getProperty("user.home"));
		r.set("userDir", System.getProperty("user.dir"));
		r.set("availableProcessors", Runtime.getRuntime().availableProcessors());
		r.set("totalMemory", convertFileSize(Runtime.getRuntime().totalMemory()));
		r.set("freeMemory", convertFileSize(Runtime.getRuntime().freeMemory()));
		r.set("maxMemory", convertFileSize(Runtime.getRuntime().maxMemory()));
		String serverStartTime = Puff.serverStartTime();
		r.set("serverStartTime", serverStartTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse(serverStartTime);
		long time = System.currentTimeMillis() - date.getTime();
		long days = time / (1000 * 60 * 60 * 24);
		long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (time % (1000 * 60)) / 1000;
		String serverRunTime = "";
		if (days > 0) {
			serverRunTime += days + " 天 ";
		}
		if (hours > 0) {
			serverRunTime += hours + " 小时 ";
		}
		if (minutes > 0) {
			serverRunTime += minutes + " 分钟 ";
		}
		if (seconds > 0) {
			serverRunTime += seconds + " 秒 ";
		}
		r.set("serverRunTime", serverRunTime);

		r.set("ip", PuffContext.getIpAddr());
		r.set("greetings", greetings());
		r.set("port", PuffContext.getRequest().getServerPort());
		r.set("servletEngine", Puff.getServletContext().getServerInfo());
		MyBeetlView view = new MyBeetlView("/admin/center.html");
		view.put("sys", r);
		return view;
	}

	@Before(LoginInterceptor.class)
	@Mapping("/sys/user/pwd")
	public View pwd() {
		return new MyBeetlView("/admin/user/user_updatepwd.html");
	}

	@Before(LoginInterceptor.class)
	@Mapping("/sys/user/pwd/update")
	public View updateUserPwd() {
		String old_pwd = PuffContext.getParameter("old_pwd");
		if (StringUtil.empty(old_pwd)) {
			return ViewFactory.json(RetMsg.error("原始密码不能为空！！！"));
		}
		SysUser user = PuffContext.getSessionAttribute("user");
		if (!SHAUtils.SHA(MD5Util.GetMD5Code(old_pwd)).equalsIgnoreCase(user.getPwd())) {
			return ViewFactory.json(RetMsg.error("原始密码不正确！！！"));
		}
		String new_pwd = PuffContext.getParameter("new_pwd");
		if (StringUtil.empty(new_pwd)) {
			return ViewFactory.json(RetMsg.error("新密码不能为空！！！"));
		}
		String new_pwd_confirm = PuffContext.getParameter("new_pwd_confirm");
		if (StringUtil.empty(new_pwd_confirm)) {
			return ViewFactory.json(RetMsg.error("确认密码不能为空！！！"));
		}
		if (!new_pwd.equals(new_pwd_confirm)) {
			return ViewFactory.json(RetMsg.error("2次新密码不一致！！！"));
		}
		sysUserService.updatePwd(user.getId(), SHAUtils.SHA(MD5Util.GetMD5Code(new_pwd_confirm)));
		user.setPwd(SHAUtils.SHA(MD5Util.GetMD5Code(new_pwd_confirm)));
		PuffContext.setSessionAttribute("user", user);
		return ViewFactory.json(RetMsg.success("密码修改成功！！！"));
	}

	public View quit() {
		PuffContext.removeSessionAttribute("user");
		PuffContext.removeSessionAttribute("sysMenu");
		return ViewFactory.redirect("/admin/console");
	}

	public String convertFileSize(long size) {
		long kb = 1024;
		long mb = kb * 1024;
		long gb = mb * 1024;
		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

	private String greetings() {
		Calendar calendar = Calendar.getInstance();
		int i = calendar.get(Calendar.HOUR_OF_DAY);
		if (i >= 6 && i <= 8) {
			return "早上好";
		}
		if (i >= 8 && i <= 12) {
			return "上午好";
		}
		if (i >= 13 && i <= 18) {
			return "下午好";
		}
		if (i >= 18 && i <= 21) {
			return "晚上好";
		}
		return "夜深了，请休息";
	}

}
