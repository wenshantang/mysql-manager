package cn.aresoft.common.model.webpublish;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("cms_template_param")
public class TemplateParam extends BaseModel{
	/**
	 * 模板参数信息
	 * lxy
	 */
	private static final long serialVersionUID = 1L;
	@Column
	@PrimaryKey.IDWORKER
	private String id;//主键Id
	@Column
	private String template_name;//模板名称
	@Column
	private String display_name;//显示名字
	@Column
	private String param_type;//显示：lable : 文字     textarea: 文本框    image  : 图片
	@Column
	private String param_key;
	@Column
	private String param_value;
	@Column
	private String param_link;
	@Column
	private String memo;//备注
	@Column
	private String saas_id;//客户saas平台id
	@Column
	private String create_user;//创建人
	@Column
	private String create_time;//创建时间
	@Column
	private String update_user;//修改人
	@Column
	private String update_time;//修改时间
	@Column
	private String isdelete;//是否删除（0-保留；1-删除）
	
	private String article_title;//文章栏目id
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTemplate_name() {
		return template_name;
	}
	public void setTemplate_name(String template_name) {
		this.template_name = template_name;
	}
	public String getParam_key() {
		return param_key;
	}
	public void setParam_key(String param_key) {
		this.param_key = param_key;
	}
	public String getParam_value() {
		return param_value;
	}
	public void setParam_value(String param_value) {
		this.param_value = param_value;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getSaas_id() {
		return saas_id;
	}
	public void setSaas_id(String saas_id) {
		this.saas_id = saas_id;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getParam_type() {
		return param_type;
	}
	public void setParam_type(String param_type) {
		this.param_type = param_type;
	}
	public String getParam_link() {
		return param_link;
	}
	public void setParam_link(String param_link) {
		this.param_link = param_link;
	}
	public String getArticle_title() {
		return article_title;
	}
	public void setArticle_title(String article_title) {
		this.article_title = article_title;
	}
	

}
