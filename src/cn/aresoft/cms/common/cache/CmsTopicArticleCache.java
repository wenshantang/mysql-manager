package cn.aresoft.cms.common.cache;

import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.ListUtil;
import com.puff.jdbc.core.PageRecord;

import cn.aresoft.cache.AbstractCache;
import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.service.CmsTopicService;

@Bean(id = "cmsTopicArticleCache")
public class CmsTopicArticleCache extends AbstractCache {

	@Inject
	private CmsTopicService cmsTopicService;

	@Inject
	private CmsArticleCache cmsArticleCache;

	@Override
	public String region() {
		return "CMS_TOPIC_ARTICLE";
	}

	public List<String> getArticleIdsByTopic(String topicCode) {
		List<String> articles = get(topicCode);
		if (ListUtil.empty(articles)) {
			articles = cmsTopicService.queryTopicArticle(topicCode);
			if (articles.size() > 0) {
				cache(topicCode, articles);
			}
		}
		return articles;
	}

	public PageRecord<CmsArticle> getArticleByTopic(String topicCode, int page, int rows) {
		PageRecord<CmsArticle> pg = new PageRecord<CmsArticle>();
		pg.setPage(page);
		pg.setPageSize(rows);
		List<String> articles = getArticleIdsByTopic(topicCode);
		if (ListUtil.empty(articles)) {
			pg.setTotalCount(0);
		} else {
			pg.setTotalCount(articles.size());
			page = pg.getPage();
			int start = (page - 1) * rows;
			int end = page * rows;
			if (start >= articles.size()) {
				start = articles.size();
			}
			if (end >= articles.size()) {
				end = articles.size();
			}
			articles = articles.subList(start, end);
			List<CmsArticle> data = cmsArticleCache.getList(articles);
			pg.setDataList(data);
		}
		return pg;
	}

	public void restTopicArticle(String topicCode) {
		List<String> articles = cmsTopicService.queryTopicArticle(topicCode);
		cache(topicCode, articles);
	}

}
