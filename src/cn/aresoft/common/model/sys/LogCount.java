package cn.aresoft.common.model.sys;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;
/*
 * 日志统计表
 * 与jar包内不同,少了 几个辅助查询字段,多了一个现实日志数量的展示
 */

@Table("SYS_LOG")
public class LogCount extends BaseModel{

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
	
	@Column(alias=true)
	private int logCount;
	
	//辅助查询字段
    private String begin_time;
		
    private String end_time;
    
    @Column(alias=true)
	private String loginname;
    
    @Column(alias=true)
    private String logtime;


	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getLogtime() {
		return logtime;
	}

	public void setLogtime(String logtime) {
		this.logtime = logtime;
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

	public int getLogCount() {
		return logCount;
	}

	public void setLogCount(int logCount) {
		this.logCount = logCount;
	}

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

	public String getReq_url() {
		return req_url;
	}

	public void setReq_url(String req_url) {
		this.req_url = req_url;
	}
	
	
	
}
