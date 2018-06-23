package cn.aresoft.cms.common.service;

import com.puff.jdbc.core.PageRecord;

import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.BaseService;

public interface CmsArticleService extends BaseService<CmsArticle> {

	PageRecord<CmsArticle> queryTopicArticle(String topicId, CmsArticle article, CommonParam param);

	void saveArticleContent(String id, String content);

	String queryArticleContent(String id);

	void auditArticle(String id, String result, String name);

	void updateArticleUrl(String id, String old_location, String location);

}
