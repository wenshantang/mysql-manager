package cn.aresoft.cms.common.cache;

import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.ListUtil;
import com.puff.framework.utils.StringUtil;
import com.puff.ioc.BeanFactory;

import cn.aresoft.cache.AbstractCache;
import cn.aresoft.cms.common.model.CmsTemplate;
import cn.aresoft.cms.common.service.CmsArticleService;
import cn.aresoft.cms.common.service.CmsTemplateService;

@Bean(id = "cmsTemplateCache")
public class CmsTemplateCache extends AbstractCache {

	@Inject
	private CmsTemplateService cmsTemplateService;

	@Override
	public String region() {
		return "CMS_TEMPLATE";
	}

	/**
	 * 初始化模板缓存
	 */
	public void init() {
		List<CmsTemplate> templates = cmsTemplateService.queryList();
		if (ListUtil.notEmpty(templates)) {
			for (CmsTemplate cmsTemplate : templates) {
				cache(cmsTemplate.getId(), cmsTemplate);
				cache(cmsTemplate.getPath(), cmsTemplate);
			}
		}
	}

	/***
	 * 删除模板缓存
	 * 
	 * @param cmsTemplate
	 */
	public void delete(String templateId) {
		CmsTemplate template = find(templateId);
		remove(templateId);
		if (template != null) {
			remove(template.getPath());
		}
	}

	/**
	 * 从缓存中查找模板,没有则查询数据库，然后存放入缓存
	 * 
	 * @param id
	 * @return
	 */
	public CmsTemplate find(String id) {
		CmsTemplate cmsTemplate=get(id);
		if (cmsTemplate==null) {
			cmsTemplate = cmsTemplateService.query(id);
			cache(id, cmsTemplate);
		}
		return cmsTemplate;
		//return get(id);
	}

}
