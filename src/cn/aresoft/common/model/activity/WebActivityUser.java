package cn.aresoft.common.model.activity;

import java.util.Date;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("WEB_ACTIVITY_USER")
public class WebActivityUser  extends BaseModel {

	
	private static final long serialVersionUID = 1L;

	@Column
	private String openid;
	@Column
	private String name;
	@Column
	private String sex;
	@Column
	private String age;
	@Column
	private String mobile;
	@Column
	private String undergraduate_major;
	@Column
	private String present_major;
	@Column
	private String interested_industry;
	@Column
	private String undergraduate_school;
	@Column
	private String remarks;
	@Column
	private String source;
	@Column
	private String source_description;
	@Column
	private Date create_date;
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUndergraduate_major() {
		return undergraduate_major;
	}
	public void setUndergraduate_major(String undergraduate_major) {
		this.undergraduate_major = undergraduate_major;
	}
	public String getPresent_major() {
		return present_major;
	}
	public void setPresent_major(String present_major) {
		this.present_major = present_major;
	}
	public String getInterested_industry() {
		return interested_industry;
	}
	public void setInterested_industry(String interested_industry) {
		this.interested_industry = interested_industry;
	}
	public String getUndergraduate_school() {
		return undergraduate_school;
	}
	public void setUndergraduate_school(String undergraduate_school) {
		this.undergraduate_school = undergraduate_school;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSource_description() {
		return source_description;
	}
	public void setSource_description(String source_description) {
		this.source_description = source_description;
	}
	public Date getCreate_date() {
		return create_date;
	}
	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}
	
	
	
	
}
