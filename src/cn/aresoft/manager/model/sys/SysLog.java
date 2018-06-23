package cn.aresoft.manager.model.sys;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;
/***
 * 系统日志表
 * @author yyj
 *
 */
@Table("SYS_LOG")
public class SysLog extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7405525075332696600L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String user_id;

	@Column
	private String loglevel;

	@Column
	private String log_time;
	
	@Column
	private String opt_type;

	@Column
	private String opt_content;
	
	@Column
	private String ip;

	@Column
	private String browser;
	
	@Column
	private String req_url;
	
	//辅助查询字段
	private String begin_time;
	
	private String end_time;
	
	@Column(alias=true)
	private String login_name;//用户登录名
	
	@Column(alias=true)
	private String rname;//请求资源名
	
	@Column(alias=true)
	private String rparent_name;//请求资源父节点名
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getLoglevel() {
		return loglevel;
	}

	public void setLoglevel(String loglevel) {
		this.loglevel = loglevel;
	}

	public String getLog_time() {
		return log_time;
	}

	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}

	public String getOpt_type() {
		return opt_type;
	}

	public void setOpt_type(String opt_type) {
		this.opt_type = opt_type;
	}

	public String getOpt_content() {
		return opt_content;
	}

	public void setOpt_content(String opt_content) {
		this.opt_content = opt_content;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getBegin_time() {
		return begin_time;
	}

	public void setBegin_time(String begin_time) {
		this.begin_time = begin_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public String getReq_url() {
		return req_url;
	}

	public void setReq_url(String req_url) {
		this.req_url = req_url;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getRname() {
		return rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getRparent_name() {
		return rparent_name;
	}

	public void setRparent_name(String rparent_name) {
		this.rparent_name = rparent_name;
	}

	

}
