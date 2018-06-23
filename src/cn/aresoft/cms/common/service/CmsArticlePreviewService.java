package cn.aresoft.cms.common.service;

import com.puff.jdbc.core.PageRecord;

import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.model.CmsArticlePreview;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.BaseService;

public interface CmsArticlePreviewService extends BaseService<CmsArticlePreview> {

	PageRecord<CmsArticlePreview> queryTopicArticle(String topicId, CmsArticlePreview article, CommonParam param);

	void saveArticleContent(String id, String content);

	String queryArticleContent(String id);

	void auditArticle(String id, String result, String name);

	void updateArticleUrl(String id, String old_location, String location);
	
	CmsArticle queryCmsArticleById(String id);
	
	/**
	 * 查询有权限的栏目文章列表
	 * @param userId
	 * @param topicId
	 * @param article
	 * @param param
	 * @return
	 */
	PageRecord<CmsArticlePreview> queryTopicLimitArticle(String userId,String topicId, CmsArticlePreview article, CommonParam param);

}
