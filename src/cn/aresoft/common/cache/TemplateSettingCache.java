package cn.aresoft.common.cache;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Inject;

import cn.aresoft.cache.AbstractCache;
import cn.aresoft.common.model.webpublish.TemplateSetting;
import cn.aresoft.common.service.webpublish.TemplateSettingService;
@Bean(id = "templateSettingCache")
public class TemplateSettingCache extends AbstractCache {
	@Inject
	private TemplateSettingService templateSettingService;

	@Override
	public String region() {
		return "cms_template_setting";
	}
	
	public TemplateSetting find(String id) {
		TemplateSetting set = get(id);
		if (set == null) {
			set = templateSettingService.query(id);
			cache(id, set);
		}
		return set;
	}
	
	public TemplateSetting findByTemplateName(String templateName) {
		TemplateSetting set = get(templateName);
		if (set == null) {
			set = templateSettingService.querySetByTemplateName(templateName);
			cache(templateName, set);
		}
		return set;
	}

}
