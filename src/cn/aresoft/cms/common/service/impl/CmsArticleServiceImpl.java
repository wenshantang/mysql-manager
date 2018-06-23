package cn.aresoft.cms.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Transaction;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;

import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.model.CmsArticleTxt;
import cn.aresoft.cms.common.service.CmsArticleService;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "cmsArticleService")
public class CmsArticleServiceImpl extends CenterServiceImpl<CmsArticle> implements CmsArticleService {

	@Override
	public PageRecord<CmsArticle> queryTopicArticle(String topicId, CmsArticle article, CommonParam param) {
		StringBuffer sql = new StringBuffer(buildQuerySql());
		sql.append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (!"888888888888888888".equals(topicId)) {
			sql.append("and topic_id in ( select id from cms_topic where parent_id=? or id=? ) ");
			condition.add(topicId);
			condition.add(topicId);
		}
		if (StringUtil.notEmpty(article.getTitle())) {
			sql.append("and title like ? ");
			condition.add("%" + article.getTitle() + "%");
		}
		sql.append(" order by status,publish_time desc ");
		return super.paging(sql.toString(), condition, param);
	}

	@Override
	@Transaction
	public void saveArticleContent(String id, String content) {
		deleteBySql("delete from cms_article_txt where id=?", id);
		CmsArticleTxt articleTxt = new CmsArticleTxt();
		articleTxt.setContent(content);
		articleTxt.setId(id);
		executor.save(articleTxt);
	}

	@Override
	public String queryArticleContent(String id) {
		return executor.queryString("select content from cms_article_txt where id=?", id);
	}

	@Override
	public void auditArticle(String id, String result, String name) {
		String sql = "update cms_article set status=?,audit_user=? where id=?";
		update(sql, result, name, id);
	}

	@Override
	public void updateArticleUrl(String id, String old_location, String location) {
		String sql = "update cms_article set article_location=? where id=?";
		executor.updateBySql(sql, location + "/" + id, id);
	}

}
