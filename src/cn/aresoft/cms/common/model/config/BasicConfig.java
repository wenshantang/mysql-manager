package cn.aresoft.cms.common.model.config;

import com.alibaba.fastjson.JSON;

/**
 * 基本配置
 * 
 * @author dongchao
 *
 */
public class BasicConfig implements Config {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2056267635982858534L;

	private String site_name;// 站点名称

	private String simple_name;// 站点简称

	private String site_domain;// 站点域名

	private String visit_protocol;// 访问方式

	private String location;// 访问路径

	private String html_meta_title;// html meta 标题

	private String html_meta_keywords;// html meta 关键字

	private String html_meta_description;// html meta 描述

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}

	public String getSimple_name() {
		return simple_name;
	}

	public void setSimple_name(String simple_name) {
		this.simple_name = simple_name;
	}

	public String getSite_domain() {
		return site_domain;
	}

	public void setSite_domain(String site_domain) {
		this.site_domain = site_domain;
	}

	public String getVisit_protocol() {
		return visit_protocol;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setVisit_protocol(String visit_protocol) {
		this.visit_protocol = visit_protocol;
	}

	public String getHtml_meta_title() {
		return html_meta_title;
	}

	public void setHtml_meta_title(String html_meta_title) {
		this.html_meta_title = html_meta_title;
	}

	public String getHtml_meta_keywords() {
		return html_meta_keywords;
	}

	public void setHtml_meta_keywords(String html_meta_keywords) {
		this.html_meta_keywords = html_meta_keywords;
	}

	public String getHtml_meta_description() {
		return html_meta_description;
	}

	public void setHtml_meta_description(String html_meta_description) {
		this.html_meta_description = html_meta_description;
	}

	@Override
	public String toJson() {
		return JSON.toJSONString(this);
	}

}
