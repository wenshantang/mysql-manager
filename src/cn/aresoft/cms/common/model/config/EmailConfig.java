package cn.aresoft.cms.common.model.config;

import com.alibaba.fastjson.JSON;
import com.puff.framework.utils.Security;

/**
 * 邮件配置
 * 
 * @author dongchao
 *
 */
public class EmailConfig implements Config {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7749816106443434418L;

	private String smtp_server;// 邮件服务器地址

	private String smtp_port;// 端口

	private String originator;// 发件人

	private String email;// 邮箱账号

	private String password;// 邮箱密码

	private String nick_name;// 发件人昵称

	private String encoding;// 编码

	private String memo;// 描述

	public String getSmtp_server() {
		return smtp_server;
	}

	public void setSmtp_server(String smtp_server) {
		this.smtp_server = smtp_server;
	}

	public String getSmtp_port() {
		return smtp_port;
	}

	public void setSmtp_port(String smtp_port) {
		this.smtp_port = smtp_port;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return Security.aesDecrypt(password);
	}

	public void setPassword(String password) {
		this.password = Security.aesEncrypt(password);
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Override
	public String toJson() {
		return JSON.toJSONString(this);
	}

}
