package cn.aresoft.cms.common.model.config;

import java.io.Serializable;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
import com.puff.framework.utils.StringUtil;

import cn.aresoft.cms.common.SmsSender;

/**
 * website 配置
 * 
 * @author dongchao
 *
 */

@Table("cms_site_config")
public class CmsSiteConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4204313651292288663L;

	@Column
	@PrimaryKey.CUSTOM
	private String site_id;// 站点ID

	@Column
	private String basic_config;// 基本配置

	private BasicConfig basicConfig;

	@Column
	private String login_config;// 登陆配置

	private LoginConfig loginConfig;

	@Column
	private String register_config;// 注册配置

	private RegisterConfig registerConfig;

	@Column
	private String email_config;// 邮件配置

	private EmailConfig emailConfig;

	@Column
	private String sms_config;// 短信配置

	private SmsConfig smsConfig;

	@Column
	private String upload_config;// 文件上传设置

	private UploadConfig uploadConfig;

	public String getSite_id() {
		return site_id;
	}

	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}

	public String getBasic_config() {
		return basic_config;
	}

	public void setBasic_config(String basic_config) {
		this.basic_config = StringUtil.empty(basic_config, "{}");
		this.basicConfig = JSON.parseObject(basic_config, BasicConfig.class);
	}

	public void setBasic_config(BasicConfig basicConfig) {
		this.basic_config = basicConfig.toJson();
	}

	public BasicConfig getBasicConfig() {
		if (StringUtil.notEmpty(this.basic_config)) {
			return JSON.parseObject(basic_config, BasicConfig.class);
		}
		return basicConfig;
	}

	public String getLogin_config() {
		return login_config;
	}

	public void setLogin_config(String login_config) {
		this.login_config = StringUtil.empty(login_config, "{}");
		this.loginConfig = JSON.parseObject(login_config, LoginConfig.class);
	}

	public void setLogin_config(LoginConfig login_config) {
		this.login_config = login_config.toJson();
	}

	public LoginConfig getLoginConfig() {
		if (StringUtil.notEmpty(this.login_config)) {
			return JSON.parseObject(login_config, LoginConfig.class);
		}
		return loginConfig;
	}

	public String getRegister_config() {
		return register_config;
	}

	public void setRegister_config(String register_config) {
		this.register_config = StringUtil.empty(register_config, "{}");
		this.registerConfig = JSON.parseObject(register_config, RegisterConfig.class);
	}

	public void setRegister_config(RegisterConfig registerConfig) {
		this.register_config = registerConfig.toJson();
	}

	public RegisterConfig getRegisterConfig() {
		if (StringUtil.notEmpty(this.register_config)) {
			return JSON.parseObject(register_config, RegisterConfig.class);
		}
		return registerConfig;
	}

	public String getEmail_config() {
		return email_config;
	}

	public void setEmail_config(String email_config) {
		this.email_config = StringUtil.empty(email_config, "{}");
		this.emailConfig = JSON.parseObject(email_config, EmailConfig.class);
	}

	public void setEmail_config(EmailConfig email_config) {
		this.email_config = email_config.toJson();
	}

	public EmailConfig getEmailConfig() {
		if (StringUtil.notEmpty(this.email_config)) {
			return JSON.parseObject(email_config, EmailConfig.class);
		}
		return emailConfig;
	}

	public String getSms_config() {
		return sms_config;
	}

	public void setSms_config(String sms_config) {
		this.sms_config = StringUtil.empty(sms_config, "{}");
		this.smsConfig = JSON.parseObject(sms_config, SmsConfig.class);
	}

	public void setSms_config(SmsConfig sms_config) {
		this.sms_config = sms_config.toJson();
	}

	public SmsConfig getSmsConfig() {
		if (StringUtil.notEmpty(this.sms_config)) {
			return JSON.parseObject(sms_config, SmsConfig.class);
		}
		return smsConfig;
	}

	public String getUpload_config() {
		return upload_config;
	}

	public void setUpload_config(String upload_config) {
		this.upload_config = StringUtil.empty(upload_config, "{}");
		this.uploadConfig = JSON.parseObject(upload_config, UploadConfig.class);
	}

	public UploadConfig getUploadConfig() {
		if (StringUtil.notEmpty(this.upload_config)) {
			return JSON.parseObject(upload_config, UploadConfig.class);
		}
		return uploadConfig;
	}

	public void setUpload_config(UploadConfig uploadConfig) {
		this.upload_config = uploadConfig.toJson();
	}

	/**
	 * 发送连续登录错误提醒短信
	 */
	public void sendLoginFailSms(String mobile, Map<String, Object> param) {
		String content = getLoginConfig().parseSmsContent(param);
		SmsSender sender = getSmsConfig().getSmsSender();
		sender.sendSms(mobile, content);
	}

	/**
	 * 发送注册短信
	 */
	public void sendRegisterSms(String mobile, Map<String, Object> param) {
		String content = getRegisterConfig().parseSmsContent(param);
		SmsSender sender = getSmsConfig().getSmsSender();
		sender.sendSms(mobile, content);
	}

}
