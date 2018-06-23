package cn.aresoft.cms.common.model.config;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.aresoft.cms.common.StringTemplate;

/**
 * 登陆配置
 * 
 * @author dongchao
 *
 */
public class LoginConfig implements Config {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2229421693471529919L;

	private int fail_show_authcode = 3;// 达到错误次数后显示验证码

	private int fail_time = 10; // 登录错误时间 (分钟)。超过时间重计次数

	private int fail_lock;// 达到错误次数后锁定

	private int lock_time = 30;// 锁定时间（分钟）

	private boolean send_sms;// 是否发送提醒短信

	private String sms_content;// 提醒短信内容

	public int getFail_show_authcode() {
		return fail_show_authcode;
	}

	public void setFail_show_authcode(int fail_show_authcode) {
		this.fail_show_authcode = fail_show_authcode;
	}

	public int getFail_time() {
		return fail_time;
	}

	public void setFail_time(int fail_time) {
		this.fail_time = fail_time;
	}

	public int getFail_lock() {
		return fail_lock;
	}

	public void setFail_lock(int fail_lock) {
		this.fail_lock = fail_lock;
	}

	public int getLock_time() {
		return lock_time;
	}

	public void setLock_time(int lock_time) {
		this.lock_time = lock_time;
	}

	public boolean isSend_sms() {
		return send_sms;
	}

	public void setSend_sms(boolean send_sms) {
		this.send_sms = send_sms;
	}

	public String getSms_content() {
		return sms_content;
	}

	public void setSms_content(String sms_content) {
		this.sms_content = sms_content;
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

}
