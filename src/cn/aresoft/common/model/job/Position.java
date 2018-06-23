package cn.aresoft.common.model.job;

import cn.aresoft.common.model.BaseModel;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

@Table("FUN_POSITION")
public class Position extends BaseModel {
    /**
     * 招聘管理
     */
    private static final long serialVersionUID = 1L;
    @Column
    @PrimaryKey.IDWORKER
    private String id;
    @Column
    private String post_name;
    @Column
    private String job_name;
    @Column
    private String post_city;
    @Column
    private String degree;
    @Column
    private String experience;
    @Column
    private String recruit_type;
    @Column
    private String recruit_title;
    @Column
    private String dep_type;
    @Column
    private String req_num;
    @Column
    private String description;
    @Column
    private String post_request;
    @Column
    private String show_index;
    @Column
    private String del_flag;
    @Column
    private String create_date;
    @Column
    private String create_by;
    @Column
    private String update_date;
    @Column
    private String update_by;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getPost_name() {
	return post_name;
    }

    public void setPost_name(String post_name) {
	this.post_name = post_name;
    }

    public String getJob_name() {
	return job_name;
    }

    public void setJob_name(String job_name) {
	this.job_name = job_name;
    }

    public String getPost_city() {
	return post_city;
    }

    public void setPost_city(String post_city) {
	this.post_city = post_city;
    }

    public String getDegree() {
	return degree;
    }

    public void setDegree(String degree) {
	this.degree = degree;
    }

    public String getExperience() {
	return experience;
    }

    public void setExperience(String experience) {
	this.experience = experience;
    }

    public String getRecruit_type() {
	return recruit_type;
    }

    public void setRecruit_type(String recruit_type) {
	this.recruit_type = recruit_type;
    }

    public String getRecruit_title() {
	return recruit_title;
    }

    public void setRecruit_title(String recruit_title) {
	this.recruit_title = recruit_title;
    }

    public String getDep_type() {
	return dep_type;
    }

    public void setDep_type(String dep_type) {
	this.dep_type = dep_type;
    }

    public String getReq_num() {
	return req_num;
    }

    public void setReq_num(String req_num) {
	this.req_num = req_num;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getPost_request() {
	return post_request;
    }

    public void setPost_request(String post_request) {
	this.post_request = post_request;
    }

    public String getShow_index() {
	return show_index;
    }

    public void setShow_index(String show_index) {
	this.show_index = show_index;
    }

    public String getDel_flag() {
	return del_flag;
    }

    public void setDel_flag(String del_flag) {
	this.del_flag = del_flag;
    }

    public String getCreate_date() {
	return create_date;
    }

    public void setCreate_date(String create_date) {
	this.create_date = create_date;
    }

    public String getCreate_by() {
	return create_by;
    }

    public void setCreate_by(String create_by) {
	this.create_by = create_by;
    }

    public String getUpdate_date() {
	return update_date;
    }

    public void setUpdate_date(String update_date) {
	this.update_date = update_date;
    }

    public String getUpdate_by() {
	return update_by;
    }

    public void setUpdate_by(String update_by) {
	this.update_by = update_by;
    }

}
