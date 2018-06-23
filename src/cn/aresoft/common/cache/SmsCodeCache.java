package cn.aresoft.common.cache;

import com.puff.framework.annotation.Bean;

import cn.aresoft.cache.AbstractCache;

@Bean(id = "smsCodeCache")
public class SmsCodeCache extends AbstractCache {

	@Override
	public String region() {
		return "SMS_CODE";
	}

}
