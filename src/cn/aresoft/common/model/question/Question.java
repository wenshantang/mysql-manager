package cn.aresoft.common.model.question;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("fun_quertion")
public class Question extends BaseModel{

	/**
	 * 问题答案表
	 */
	private static final long serialVersionUID = 1L;
	@Column
	@PrimaryKey.UUID
	private String id;
	@Column
	private String wid;
	@Column
	private int qidx;
	@Column
	private String aidx;
	@Column
	private String content;
	@Column
	private int score;
	@Column
	private String creat_date;
	@Column
	private String creat_by;
	@Column
	private String update_date;
	@Column
	private String update_by;
	@Column
	private String SAAS_ID;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWid() {
		return wid;
	}
	public void setWid(String wid) {
		this.wid = wid;
	}
	public int getQidx() {
		return qidx;
	}
	public void setQidx(int qidx) {
		this.qidx = qidx;
	}
	public String getAidx() {
		return aidx;
	}
	public void setAidx(String aidx) {
		this.aidx = aidx;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
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
	public String getSAAS_ID() {
		return SAAS_ID;
	}
	public void setSAAS_ID(String sAAS_ID) {
		SAAS_ID = sAAS_ID;
	}
	
	
	

}
