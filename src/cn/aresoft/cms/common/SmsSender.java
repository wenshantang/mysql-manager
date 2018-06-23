package cn.aresoft.cms.common;

/**
 * 短信发送
 * 
 * @author dongchao
 *
 */
public interface SmsSender {

	public boolean sendSms(String target, String content);

}
