package cn.aresoft.cms.common.cache;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.ioc.BeanFactory;

import cn.aresoft.cache.AbstractCache;
import cn.aresoft.cms.common.service.CmsArticleService;

@Bean(id = "cmsArticleContentCache")
public class CmsArticleContentCache extends AbstractCache {

	public String find(String id) {
		String content = get(id);
		if (StringUtil.empty(content)) {
			CmsArticleService cmsArticleService = BeanFactory.getBean("cmsArticleService");
			content = cmsArticleService.queryArticleContent(id);
			cache(id, content);
		}
		return content;
	}

	@Override
	public String region() {
		return "CMS_ARTICLE_CONTENT";
	}

}
