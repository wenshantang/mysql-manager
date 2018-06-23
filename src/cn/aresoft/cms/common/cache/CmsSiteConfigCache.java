package cn.aresoft.cms.common.cache;

import com.puff.framework.annotation.Bean;

import cn.aresoft.cache.AbstractCache;

@Bean(id = "cmsSiteConfigCache")
public class CmsSiteConfigCache extends AbstractCache {

	@Override
	public String region() {
		return "CMS_SITECONFIG";
	}

}
