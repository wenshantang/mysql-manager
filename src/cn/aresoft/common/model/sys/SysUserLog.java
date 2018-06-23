package cn.aresoft.common.model.sys;

import cn.aresoft.common.model.BaseModel;
/*
 * 日志统计表
 * 
 */

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

@Table("SYS_USER_LOG")
public class SysUserLog extends BaseModel{
	private static final long serialVersionUID = 1L;
	@Column
	@PrimaryKey.IDWORKER
	private String id;//
	@Column
	private String loglevel;//日志级别;0000 ：操作成功   其他的操作失败
	@Column
	private String log_time;//日志发生时间 到毫秒
	@Column
	private String opt_type;//操作类型
	@Column
	private String opt_content;//操作内容
	@Column
	private String ip;//用户IP
	@Column
	private String id_no;//开户用户必传、证件号
	@Column
    private String id_kind_gb;//证件类别
	@Column
    private String old_mobile_tel;//旧的手机号
	@Column
    private String mobile_tel;//新的手机号
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getId_no() {
		return id_no;
	}
	public void setId_no(String id_no) {
		this.id_no = id_no;
	}
	public String getId_kind_gb() {
		return id_kind_gb;
	}
	public void setId_kind_gb(String id_kind_gb) {
		this.id_kind_gb = id_kind_gb;
	}
	public String getOld_mobile_tel() {
		return old_mobile_tel;
	}
	public void setOld_mobile_tel(String old_mobile_tel) {
		this.old_mobile_tel = old_mobile_tel;
	}
	public String getMobile_tel() {
		return mobile_tel;
	}
	public void setMobile_tel(String mobile_tel) {
		this.mobile_tel = mobile_tel;
	}

}
