package cn.aresoft.cms.common.model.config;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.aresoft.cms.common.StringTemplate;

/**
 * 注册设置
 * 
 * @author dongchao
 *
 */
public class RegisterConfig implements Config {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6879865955728307323L;

	private boolean allow_reg;// 是否允许注册

	private int min_username;// 用户名最小长度

	private int max_username;// 用户名最大长度

	private boolean send_smscode;// 是否发送短信验证码

	private int smscode_format;// 验证码格式1.数字2.字母3.字母加数字

	private String sms_content;// 短信内容

	private boolean email_activate;// 邮件激活

	private String email_subject;// 邮件主题

	private String email_content;// 邮件内容

	public boolean isAllow_reg() {
		return allow_reg;
	}

	public void setAllow_reg(boolean allow_reg) {
		this.allow_reg = allow_reg;
	}

	public int getMin_username() {
		return min_username;
	}

	public void setMin_username(int min_username) {
		this.min_username = min_username;
	}

	public int getMax_username() {
		return max_username;
	}

	public void setMax_username(int max_username) {
		this.max_username = max_username;
	}

	public boolean isSend_smscode() {
		return send_smscode;
	}

	public void setSend_smscode(boolean send_smscode) {
		this.send_smscode = send_smscode;
	}

	public int getSmscode_format() {
		return smscode_format;
	}

	public void setSmscode_format(int smscode_format) {
		this.smscode_format = smscode_format;
	}

	public String getSms_content() {
		return sms_content;
	}

	public void setSms_content(String sms_content) {
		this.sms_content = sms_content;
	}

	public boolean isEmail_activate() {
		return email_activate;
	}

	public void setEmail_activate(boolean email_activate) {
		this.email_activate = email_activate;
	}

	public String getEmail_subject() {
		return email_subject;
	}

	public void setEmail_subject(String email_subject) {
		this.email_subject = email_subject;
	}

	public String getEmail_content() {
		return email_content;
	}

	public void setEmail_content(String email_content) {
		this.email_content = email_content;
	}

	@Override
	public String toJson() {
		return JSON.toJSONString(this);
	}

	/**
	 * 解析短信内容
	 * 
	 * @param param
	 * @return
	 */
	public String parseSmsContent(Map<String, Object> param) {
		return StringTemplate.parser(sms_content, param);
	}

	/**
	 * 解析邮件主题
	 * 
	 * @param param
	 * @return
	 */
	public String parseEmailSubject(Map<String, Object> param) {
		return StringTemplate.parser(email_subject, param);
	}

	/**
	 * 解析邮件内容
	 * 
	 * @param param
	 * @return
	 */
	public String parseEmailContent(Map<String, Object> param) {
		return StringTemplate.parser(email_content, param);
	}

}
