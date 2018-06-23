package cn.aresoft.manager.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.puff.framework.utils.StringUtil;
import com.puff.ioc.BeanFactory;

import cn.aresoft.manager.model.sys.SysLog;
import cn.aresoft.manager.service.sys.SysLogService;

/**
 * 日志工具类
 * @author yyj
 *
 */
public class LogUtils {
	private static final SysLogService sysLogService = BeanFactory.getBean("sysLogService");
	
	/**
	 * 写系统日志
	 * 
	 * @param user
	 *            登录用户名
	 * @param type
	 *            业务类型 : 1 营销活动信息,2 产品信息,3 文章信息（审核算变更）,4 用户权限,5 通用类，default:5
	 * @param req_url 
	 *            请求url
	 * @param opt
	 *            操作种类： 1:增加 2：修改 3：删除,4:通用，default:4
	 * @param paramJson
	 *            json格式请求参数
	 * @param level 
	 *           日志级别 默认传空【1:info;2:debug;3:warm;4:error;default:1】
	 *            
	 */
	public static void insertSsysLog(String user, String type, String req_url,String opt,String paramJson, String level,HttpServletRequest request) {
		SysLog syslog = new SysLog();
		String content = "";
		syslog.setUser_id(user);
		if(StringUtil.blank(type)){
			level="5";
		}
		syslog.setOpt_type(type);
		if(StringUtil.blank(level)){
			level="1";
		}
		syslog.setLoglevel(level);
		String userAgent = request.getHeader("user-agent");
		syslog.setReq_url(req_url);
		syslog.setBrowser(userAgent == null ? "" : userAgent);
		syslog.setIp(getIpAddr(request));
		syslog.setLog_time(DateUtils.date2Str6(new Date(), 6));//DateTime.currentTimeStamp()

		if ("1".equals(opt)) { // 增加
			opt = "增加 ";
		} else if ("2".equals(opt)) { // 修改
			opt = "修改 ";
		} else if ("3".equals(opt)) { // 删除
			opt = "删除 ";
		}else{
			opt = "";//default
		}

		if ("1".equals(type)) { // 营销活动信息
			type = "营销活动信息";
		} else if ("2".equals(type)) { // 产品信息
			type = "产品信息 ";
		} else if ("3".equals(type)) { // 文章信息
			type = "文章信息 ";
		} else if ("4".equals(type)) { // 用户
			type = "用户 ";
		}

		content = user + "于" + syslog.getLog_time() + "完成" + opt + type + "操作,参数为："+paramJson+" ,IP: " + syslog.getIp() + ". ";
		syslog.setOpt_content(content);
		sysLogService.insert(syslog);
	}
	
	
	public static String getIpAddr(HttpServletRequest request) {
		String ips = request.getHeader("x-forwarded-for");
		if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
			ips = request.getHeader("Proxy-Client-IP");
		}
		if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
			ips = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ips == null || ips.length() == 0 || "unknown".equalsIgnoreCase(ips)) {
			ips = request.getRemoteAddr();
		}

		String[] ipArray = ips.split(",");
		String clientIP = "";
		for (String ip : ipArray) {
			if (!("unknown".equalsIgnoreCase(ip))) {
				clientIP = ip;
				break;
			}
		}
		return clientIP;
	}
}
