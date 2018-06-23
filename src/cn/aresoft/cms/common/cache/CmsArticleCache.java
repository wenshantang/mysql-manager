package cn.aresoft.cms.common.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.ListUtil;
import com.puff.ioc.BeanFactory;

import cn.aresoft.cache.AbstractCache;
import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.service.CmsArticleService;

@Bean(id = "cmsArticleCache")
public class CmsArticleCache extends AbstractCache {
    CmsArticleService cmsArticleService=BeanFactory.getBean("cmsArticleService");
    
	public CmsArticle find(String id) {
		//update by yyj 2016-10-25 start
		//修改获取策略[先从缓存里获取，获取不到再从数据库里查询，然后存放到缓存中]
		CmsArticle cacheObj=get(id);
		if(cacheObj==null){
			cacheObj=cmsArticleService.query(id);
			cache(id, cacheObj);
		}
		//update by yyj 2016-10-25 end
		return get(id);
	}

	@Override
	public String region() {
		return "CMS_ARTICLE";
	}

	public List<CmsArticle> getList(List<String> articles) {
		if (ListUtil.empty(articles)) {
			return Collections.emptyList();
		}
		List<CmsArticle> list = new ArrayList<CmsArticle>();
		for (String id : articles) {
			list.add((CmsArticle) get(id));
		}
		return list;
	}

}
