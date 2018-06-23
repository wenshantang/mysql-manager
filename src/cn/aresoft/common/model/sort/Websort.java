package cn.aresoft.common.model.sort;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;
/**
 * Tue Sep 06 12:53:30 CST 2016
 */
@Table("WEB_SORT")
public class Websort extends BaseModel {
	private static final long serialVersionUID = 1L;

	@PrimaryKey.IDWORKER
	@Column
	private String id;

	
	@Column
	private String sort_type;

	
	@Column
	private String sort_way;

	
	@Column
	private String sort_value;

	
	@Column
	private String sort_update_value;
	
	@Column
	private String update_author;
	
	@Column
	private String shenghe_author;

	
	@Column
	private String shenghenum;

	@Column
	private String update_time;

	
	@Column
	private String shenghe_time;
	
	@Column
	private String isdelete;

	
	@Column
	private String remark1;

	
	@Column
	private String remark2;

	
	@Column
	private String remark3;

	
	@Column
	private String remark4;

	
	@Column
	private String remark5;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSort_type() {
		return sort_type;
	}


	public void setSort_type(String sort_type) {
		this.sort_type = sort_type;
	}


	public String getSort_way() {
		return sort_way;
	}


	public void setSort_way(String sort_way) {
		this.sort_way = sort_way;
	}


	public String getSort_value() {
		return sort_value;
	}


	public void setSort_value(String sort_value) {
		this.sort_value = sort_value;
	}


	public String getSort_update_value() {
		return sort_update_value;
	}


	public void setSort_update_value(String sort_update_value) {
		this.sort_update_value = sort_update_value;
	}


	public String getUpdate_author() {
		return update_author;
	}


	public void setUpdate_author(String update_author) {
		this.update_author = update_author;
	}


	public String getShenghe_author() {
		return shenghe_author;
	}


	public void setShenghe_author(String shenghe_author) {
		this.shenghe_author = shenghe_author;
	}


	public String getShenghenum() {
		return shenghenum;
	}


	public void setShenghenum(String shenghenum) {
		this.shenghenum = shenghenum;
	}


	public String getUpdate_time() {
		return update_time;
	}


	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}


	public String getShenghe_time() {
		return shenghe_time;
	}


	public void setShenghe_time(String shenghe_time) {
		this.shenghe_time = shenghe_time;
	}


	public String getIsdelete() {
		return isdelete;
	}


	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}


	public String getRemark1() {
		return remark1;
	}


	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}


	public String getRemark2() {
		return remark2;
	}


	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}


	public String getRemark3() {
		return remark3;
	}


	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}


	public String getRemark4() {
		return remark4;
	}


	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}


	public String getRemark5() {
		return remark5;
	}


	public void setRemark5(String remark5) {
		this.remark5 = remark5;
	}

	
	
	
}