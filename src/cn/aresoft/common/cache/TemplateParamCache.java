package cn.aresoft.common.cache;

import com.puff.framework.annotation.Bean;

import cn.aresoft.cache.AbstractCache;
@Bean(id = "templateParamCache")
public class TemplateParamCache extends AbstractCache {

	@Override
	public String region() {
		return "cms_template_setting";
	}

}
