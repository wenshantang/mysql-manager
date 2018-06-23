package cn.aresoft.cms.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Transaction;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;

import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.model.CmsArticlePreview;
import cn.aresoft.cms.common.model.CmsArticleTxtPreview;
import cn.aresoft.cms.common.service.CmsArticlePreviewService;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "cmsArticlePreviewService")
public class CmsArticlePreviewServiceImpl extends CenterServiceImpl<CmsArticlePreview> implements CmsArticlePreviewService {

	@Override
	public PageRecord<CmsArticlePreview> queryTopicArticle(String topicId, CmsArticlePreview article, CommonParam param) {
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
		if (article.getStatus()>=0) {
			sql.append("and status=? ");
			condition.add(article.getStatus());
		}
		if (StringUtil.notEmpty(article.getPublish_time_begin())) {
			sql.append("and publish_time >= ? ");
			condition.add(article.getPublish_time_begin());
		}	
		if (StringUtil.notEmpty(article.getPublish_time_end())) {
			sql.append("and publish_time <= ? ");
			condition.add(article.getPublish_time_end());
		}			
		if (StringUtil.notEmpty(article.getCreate_user())) {
			sql.append("and create_user = ? ");
			condition.add(article.getCreate_user());
		}	
		if (StringUtil.notEmpty(article.getLast_modify_user())) {
			sql.append("and last_modify_user = ? ");
			condition.add(article.getLast_modify_user());
		}			
		if (StringUtil.notEmpty(article.getAudit_user())) {
			sql.append("and audit_user = ? ");
			condition.add(article.getAudit_user());
		}
		if (StringUtil.notEmpty(article.getEndtime_begin())) {
			sql.append("and endtime >= ? ");
			condition.add(article.getEndtime_begin());
		}	
		if (StringUtil.notEmpty(article.getEndtime_end())) {
			sql.append("and endtime <= ? ");
			condition.add(article.getEndtime_end());
		}	
		if(StringUtil.blank(param.getSort())){//添加默认排序
			sql.append(" order by status,publish_time desc ");
		}
		return super.paging(sql.toString(), condition, param);
	}

	@Override
	@Transaction
	public void saveArticleContent(String id, String content) {
		deleteBySql("delete from cms_article_txt_preview where id=?", id);
		CmsArticleTxtPreview articleTxtPreview = new CmsArticleTxtPreview();
		articleTxtPreview.setContent(content);
		articleTxtPreview.setId(id);
		executor.save(articleTxtPreview);
	}

	@Override
	public String queryArticleContent(String id) {
		return executor.queryString("select content from cms_article_txt_preview where id=?", id);
	}

	@Override
	public void auditArticle(String id, String result, String name) {
		String sql = "update cms_article_preview set status=?,audit_user=? where id=?";
		update(sql, result, name, id);
	}

	@Override
	public void updateArticleUrl(String id, String old_location, String location) {
		String sql = "update cms_article_preview set article_location=? where id=?";
		executor.updateBySql(sql, location + "/" + id, id);
	}

	@Override
	public CmsArticle queryCmsArticleById(String id) {
		String sql = "select * from cms_article_preview t where t.id=? ";
		return executor.query(CmsArticle.class, sql, id);
	}

	@Override
	public PageRecord<CmsArticlePreview> queryTopicLimitArticle(String userId,String topicId, CmsArticlePreview article,
			CommonParam param) {
		StringBuffer sql = new StringBuffer(buildQuerySql());
		sql.append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (!"888888888888888888".equals(topicId)) {
			sql.append(" and topic_id in ( select id from cms_topic where parent_id=? or id=? ) ");
			condition.add(topicId);
			condition.add(topicId);
		}else{
			sql.append("and TOPIC_ID in("
					+ "  select DISTINCT TOPIC_ID from SYS_ROLE_TOPIC rt,SYS_USER_ROLE ur "
					+ "  where RT.ROLE_ID=UR.ROLE_ID "
					+ "	 and UR.USER_ID=?)");
			condition.add(userId);
		}
		if (StringUtil.notEmpty(article.getTitle())) {
			sql.append("and title like ? ");
			condition.add("%" + article.getTitle() + "%");
		}
		if (article.getStatus()>=0) {
			sql.append("and status=? ");
			condition.add(article.getStatus());
		}
		if (StringUtil.notEmpty(article.getPublish_time_begin())) {
			sql.append("and publish_time >= ? ");
			condition.add(article.getPublish_time_begin());
		}	
		if (StringUtil.notEmpty(article.getPublish_time_end())) {
			sql.append("and publish_time <= ? ");
			condition.add(article.getPublish_time_end());
		}			
		if (StringUtil.notEmpty(article.getCreate_user())) {
			sql.append("and create_user = ? ");
			condition.add(article.getCreate_user());
		}	
		if (StringUtil.notEmpty(article.getLast_modify_user())) {
			sql.append("and last_modify_user = ? ");
			condition.add(article.getLast_modify_user());
		}			
		if (StringUtil.notEmpty(article.getAudit_user())) {
			sql.append("and audit_user = ? ");
			condition.add(article.getAudit_user());
		}
		if (StringUtil.notEmpty(article.getEndtime_begin())) {
			sql.append("and endtime >= ? ");
			condition.add(article.getEndtime_begin());
		}	
		if (StringUtil.notEmpty(article.getEndtime_end())) {
			sql.append("and endtime <= ? ");
			condition.add(article.getEndtime_end());
		}			
		if(StringUtil.blank(param.getSort())){//添加默认排序
			sql.append(" order by status,publish_time desc ");
		}
		return super.paging(sql.toString(), condition, param);
	}

	
}
