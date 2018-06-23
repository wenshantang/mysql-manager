package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * 文章基本信息(预览)
 * 
 * @author dongchao
 *
 */

@Table("cms_article_preview")
public class CmsArticlePreview extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6393270729595891798L;

	private String[] topicIds;
	

	public String[] getTopicIds() {
		return topicIds;
	}

	public void setTopicIds(String[] topicIds) {
		this.topicIds = topicIds;
	}
	@Column
	private String article_resource_id;
	
	public String getArticle_resource_id() {
		return article_resource_id;
	}

	public void setArticle_resource_id(String article_resource_id) {
		this.article_resource_id = article_resource_id;
	}

	@PrimaryKey.AUTO_SEQUENCE(name = "seq_article")
	@Column
	private String id;

	@Column
	private int i_top;// 文章置顶

	@Column
	private String topic_id;// 文章主栏目ID

	@Column
	private String article_location;// 文章访问路径

	@Column
	private String big_picurl;// 文章大图路径

	@Column
	private String small_picurl;// 文章小图路径

	@Column
	private String title;// 文章标题

	@Column
	private String short_title;// 文章小标题

	@Column
	private String title_style;// 标题样式

	@Column
	private String tag;// 文章标签

	@Column
	private String meta_title;// html meta title

	@Column
	private String meta_keywords;// html meta keywords

	@Column
	private String meta_desc;// html meta description

	@Column
	private String digest;// 简短摘要

	@Column
	private String centre_tip;// 核心提示

	@Column
	private int original;// 是否原创

	@Column
	private String author;// 文章作者

	@Column
	private String excerpts;// 摘编

	@Column
	private String source;// 文章来源

	@Column
	private String source_url;// 文章来源url

	@Column
	private String type_id;// 文章类型

	@Column
	private String model_id;// 文章模型ID

	@Column
	private int status;// 文章状态 0待审核 1审核通过 2审核不通过

	@Column
	private String create_time;// 创建时间

	@Column
	private String create_user;// 创建人

	@Column
	private String last_modify_time;// 最后修改时间

	@Column
	private String last_modify_user;// 最后修改人

	@Column
	private String audit_user;// 审核人

	@Column
	private String audit_time;// 审核时间

	@Column
	private String publish_time;// 发布时间

	@Column
	private int allow_review;// 是否允许评论
	
	@Column
	private String article_auth;//权限
	
	@Column
	private String pro_ids;//关联产品
	
	@Column
	private String allpro_ids;//选择所有关联产品

	@Column(processor = ArrayFieldProcessor.class)
	private String[] access_permission;// 浏览权限
	
	@Column
	private String is_index="0";//是否首页展示[默认0:非首页展示]
	
	@Column
	private String endtime;//到期时间
	
	private String endtime_flag;//到期标记辅助字段（1月后、3月后、1年后等）
	
	private String endtime_begin;//到期时间查询辅助字段
	
	private String endtime_end;//到期时间查询辅助字段
	
	private String publish_time_begin;//发布时间查询辅助字段
	
	private String publish_time_end;//发布时间查询辅助字段
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getI_top() {
		return i_top;
	}

	public void setI_top(int i_top) {
		this.i_top = i_top;
	}

	public String getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(String topic_id) {
		this.topic_id = topic_id;
	}

	public String getArticle_location() {
		return article_location;
	}

	public void setArticle_location(String article_location) {
		this.article_location = article_location;
	}

	public String getBig_picurl() {
		return big_picurl;
	}

	public void setBig_picurl(String big_picurl) {
		this.big_picurl = big_picurl;
	}

	public String getSmall_picurl() {
		return small_picurl;
	}

	public void setSmall_picurl(String small_picurl) {
		this.small_picurl = small_picurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShort_title() {
		return short_title;
	}

	public void setShort_title(String short_title) {
		this.short_title = short_title;
	}

	public String getTitle_style() {
		return title_style;
	}

	public void setTitle_style(String title_style) {
		this.title_style = title_style;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMeta_title() {
		return meta_title;
	}

	public void setMeta_title(String meta_title) {
		this.meta_title = meta_title;
	}

	public String getMeta_keywords() {
		return meta_keywords;
	}

	public void setMeta_keywords(String meta_keywords) {
		this.meta_keywords = meta_keywords;
	}

	public String getMeta_desc() {
		return meta_desc;
	}

	public void setMeta_desc(String meta_desc) {
		this.meta_desc = meta_desc;
	}

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getCentre_tip() {
		return centre_tip;
	}

	public void setCentre_tip(String centre_tip) {
		this.centre_tip = centre_tip;
	}

	public int getOriginal() {
		return original;
	}

	public void setOriginal(int original) {
		this.original = original;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getExcerpts() {
		return excerpts;
	}

	public void setExcerpts(String excerpts) {
		this.excerpts = excerpts;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getLast_modify_time() {
		return last_modify_time;
	}

	public void setLast_modify_time(String last_modify_time) {
		this.last_modify_time = last_modify_time;
	}

	public String getLast_modify_user() {
		return last_modify_user;
	}

	public void setLast_modify_user(String last_modify_user) {
		this.last_modify_user = last_modify_user;
	}

	public String getAudit_user() {
		return audit_user;
	}

	public void setAudit_user(String audit_user) {
		this.audit_user = audit_user;
	}

	public String getAudit_time() {
		return audit_time;
	}

	public void setAudit_time(String audit_time) {
		this.audit_time = audit_time;
	}

	public String getPublish_time() {
		return publish_time;
	}

	public void setPublish_time(String publish_time) {
		this.publish_time = publish_time;
	}

	public int getAllow_review() {
		return allow_review;
	}

	public void setAllow_review(int allow_review) {
		this.allow_review = allow_review;
	}

	public String[] getAccess_permission() {
		return access_permission;
	}

	public void setAccess_permission(String[] access_permission) {
		this.access_permission = access_permission;
	}

	public String getArticle_auth() {
		return article_auth;
	}

	public void setArticle_auth(String article_auth) {
		this.article_auth = article_auth;
	}

	public String getPro_ids() {
		return pro_ids;
	}

	public void setPro_ids(String pro_ids) {
		this.pro_ids = pro_ids;
	}

	public String getAllpro_ids() {
		return allpro_ids;
	}

	public void setAllpro_ids(String allpro_ids) {
		this.allpro_ids = allpro_ids;
	}

	public String getIs_index() {
		return is_index;
	}

	public void setIs_index(String is_index) {
		this.is_index = is_index;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getEndtime_flag() {
		return endtime_flag;
	}

	public void setEndtime_flag(String endtime_flag) {
		this.endtime_flag = endtime_flag;
	}

	public String getEndtime_begin() {
		return endtime_begin;
	}

	public void setEndtime_begin(String endtime_begin) {
		this.endtime_begin = endtime_begin;
	}

	public String getEndtime_end() {
		return endtime_end;
	}

	public void setEndtime_end(String endtime_end) {
		this.endtime_end = endtime_end;
	}

	public String getPublish_time_begin() {
		return publish_time_begin;
	}

	public void setPublish_time_begin(String publish_time_begin) {
		this.publish_time_begin = publish_time_begin;
	}

	public String getPublish_time_end() {
		return publish_time_end;
	}

	public void setPublish_time_end(String publish_time_end) {
		this.publish_time_end = publish_time_end;
	}
	
	

}
