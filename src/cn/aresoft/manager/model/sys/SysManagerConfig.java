package cn.aresoft.manager.model.sys;

import com.alibaba.fastjson.JSON;
import com.puff.core.Puff;
import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
import com.puff.framework.utils.StringUtil;

import cn.aresoft.common.model.BaseModel;

/**
 * 管理系统设置
 * 
 * @author dongchao
 *
 */

@Table("SYS_MANAGERCONFIG")
public class SysManagerConfig extends BaseModel {

	private static final long serialVersionUID = -7467462923861523165L;

	@Column
	@PrimaryKey.CUSTOM
	private String company_name;

	@Column
	private String logo;
	private String full_logo;

	@Column
	private String icon;
	private String full_icon;

	@Column
	private String index_url;
	private String full_index_url;

	@Column
	private String login_filter;

	@Column
	private String authcode_format = "2";

	@Column
	private String username;

	@Column
	private String pwd;

	@Column
	private String super_user;

	@Column
	private String super_pwd;

	@Column
	private String upload_config;

	private UploadConfig uploadConfig;

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
		if (StringUtil.notBlank(logo) && !logo.startsWith("http")) {
			this.full_logo = Puff.getContextPath() + logo;
		} else {
			this.full_logo = logo;
		}

	}

	public String getFull_logo() {
		return full_logo;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
		if (StringUtil.notBlank(icon) && !icon.startsWith("http")) {
			this.full_icon = Puff.getContextPath() + icon;
		} else {
			this.full_icon = icon;
		}
	}

	public String getFull_icon() {
		return full_icon;
	}

	public String getIndex_url() {
		return index_url;
	}

	public void setIndex_url(String index_url) {
		this.index_url = index_url;
		if (StringUtil.notBlank(index_url) && !index_url.startsWith("http")) {
			this.full_index_url = Puff.getContextPath() + index_url;
		} else {
			this.full_index_url = index_url;
		}
	}

	public String getFull_index_url() {
		return full_index_url;
	}

	public String getLogin_filter() {
		return login_filter;
	}

	public void setLogin_filter(String login_filter) {
		this.login_filter = login_filter;
	}

	public void setFull_logo(String full_logo) {
		this.full_logo = full_logo;
	}

	public void setFull_icon(String full_icon) {
		this.full_icon = full_icon;
	}

	public void setFull_index_url(String full_index_url) {
		this.full_index_url = full_index_url;
	}

	public String getAuthcode_format() {
		return authcode_format;
	}

	public void setAuthcode_format(String authcode_format) {
		this.authcode_format = authcode_format;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getSuper_user() {
		return super_user;
	}

	public void setSuper_user(String super_user) {
		this.super_user = super_user;
	}

	public String getSuper_pwd() {
		return super_pwd;
	}

	public void setSuper_pwd(String super_pwd) {
		this.super_pwd = super_pwd;
	}

	public String getUpload_config() {
		return upload_config;
	}

	public void setUpload_config(String upload_config) {
		this.upload_config = StringUtil.empty(upload_config, "{}");
		this.uploadConfig = JSON.parseObject(upload_config, UploadConfig.class);
	}

	public UploadConfig getUploadConfig() {
		return uploadConfig;
	}

	public void setUploadConfig(UploadConfig uploadConfig) {
		this.uploadConfig = uploadConfig;
		if (uploadConfig != null) {
			this.upload_config = uploadConfig.toJson();
		}
	}

}
