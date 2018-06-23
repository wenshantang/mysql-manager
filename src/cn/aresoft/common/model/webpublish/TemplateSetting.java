package cn.aresoft.common.model.webpublish;

import java.util.List;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("cms_template_setting")
public class TemplateSetting extends BaseModel{
	/**
	 * 菜单设置表
	 * lxy
	 */
	private static final long serialVersionUID = 1L;
	@Column
	@PrimaryKey.IDWORKER
	private String id;//主键Id
	@Column
	private String template_name;//模板名称
	@Column
	private String menu_link;//菜单链接地址
	@Column
	private String menu_level;//菜单级别
	@Column
	private String parent_id;//备注
	@Column
	private String isused;//是否使用（0-使用；1-不使用）
	@Column
	private int idx;//排序
	@Column
	private String show_name;//菜单显示名字
	@Column
	private String create_user;//创建人
	@Column
	private String create_time;//创建时间
	@Column
	private String update_user;//修改人
	@Column
	private String update_time;//修改时间
	@Column
	private String saas_id;//客户saas平台id
	
	private List<TemplateSetting> sons;
	
	private int param_count;//参数个数
	
	
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
	public String getMenu_link() {
		return menu_link;
	}
	public void setMenu_link(String menu_link) {
		this.menu_link = menu_link;
	}
	public String getMenu_level() {
		return menu_level;
	}
	public void setMenu_level(String menu_level) {
		this.menu_level = menu_level;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}
	public String getIsused() {
		return isused;
	}
	public void setIsused(String isused) {
		this.isused = isused;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getShow_name() {
		return show_name;
	}
	public void setShow_name(String show_name) {
		this.show_name = show_name;
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
	public String getSaas_id() {
		return saas_id;
	}
	public void setSaas_id(String saas_id) {
		this.saas_id = saas_id;
	}
	public List<TemplateSetting> getSons() {
		return sons;
	}
	public void setSons(List<TemplateSetting> sons) {
		this.sons = sons;
	}
	public int getParam_count() {
		return param_count;
	}
	public void setParam_count(int param_count) {
		this.param_count = param_count;
	}

}
