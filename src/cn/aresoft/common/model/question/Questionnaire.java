package cn.aresoft.common.model.question;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("fun_questionnaire")
public class Questionnaire extends BaseModel {

	/**
	 * 问卷表
	 */
	private static final long serialVersionUID = 1L;
	@Column
	@PrimaryKey.UUID
	private String id;
	@Column
	private String wname;
	@Column
	private String q_type;
	@Column
	private String isuse;
	@Column
	private String pre_remark;
	@Column
	private String last_remark;
	@Column
	private String creat_date;
	@Column
	private String creat_by;
	@Column
	private String update_date;
	@Column
	private String update_by;
	@Column
	private String result;
	@Column
	private String SAAS_ID;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWname() {
		return wname;
	}
	public void setWname(String wname) {
		this.wname = wname;
	}
	public String getQ_type() {
		return q_type;
	}
	public void setQ_type(String q_type) {
		this.q_type = q_type;
	}
	public String getIsuse() {
		return isuse;
	}
	public void setIsuse(String isuse) {
		this.isuse = isuse;
	}
	
	public String getPre_remark() {
		return pre_remark;
	}
	public void setPre_remark(String pre_remark) {
		this.pre_remark = pre_remark;
	}
	public String getLast_remark() {
		return last_remark;
	}
	public void setLast_remark(String last_remark) {
		this.last_remark = last_remark;
	}
	public String getCreat_date() {
		return creat_date;
	}
	public void setCreat_date(String creat_date) {
		this.creat_date = creat_date;
	}
	public String getCreat_by() {
		return creat_by;
	}
	public void setCreat_by(String creat_by) {
		this.creat_by = creat_by;
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
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getSAAS_ID() {
		return SAAS_ID;
	}
	public void setSAAS_ID(String sAAS_ID) {
		SAAS_ID = sAAS_ID;
	}
	
	
}
