package cn.aresoft.common.model.webpublish;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("cms_web_template")
public class WebTemplate extends BaseModel{
	/**
	 * 网站发布模板信息
	 * lxy
	 */
	private static final long serialVersionUID = 1L;
	@Column
	@PrimaryKey.IDWORKER
	private String id;//主键Id
	@Column
	private String template_name;//模板名称
	@Column
	private String status;//模板状态
	@Column
	private String style_path;//模板风格路径
	@Column
	private String pc_simage_path;//pc端模板小图路径
	@Column
	private String pc_bimage_path;//pc端模板大图路径
	@Column
	private String html5_simage_path;//html5端模板小图路径
	@Column
	private String html5_bimage_path;//html5端模板大图路径
	@Column
	private String MEMO;//模板介绍
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStyle_path() {
		return style_path;
	}
	public void setStyle_path(String style_path) {
		this.style_path = style_path;
	}
	public String getPc_simage_path() {
		return pc_simage_path;
	}
	public void setPc_simage_path(String pc_simage_path) {
		this.pc_simage_path = pc_simage_path;
	}
	public String getPc_bimage_path() {
		return pc_bimage_path;
	}
	public void setPc_bimage_path(String pc_bimage_path) {
		this.pc_bimage_path = pc_bimage_path;
	}
	public String getHtml5_simage_path() {
		return html5_simage_path;
	}
	public void setHtml5_simage_path(String html5_simage_path) {
		this.html5_simage_path = html5_simage_path;
	}
	public String getHtml5_bimage_path() {
		return html5_bimage_path;
	}
	public void setHtml5_bimage_path(String html5_bimage_path) {
		this.html5_bimage_path = html5_bimage_path;
	}
	public String getMEMO() {
		return MEMO;
	}
	public void setMEMO(String mEMO) {
		MEMO = mEMO;
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
	
}
