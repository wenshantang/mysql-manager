package cn.aresoft.common.model.webbottomtitle;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
import cn.aresoft.common.model.BaseModel;
/**
 * Mon Sep 05 16:20:36 CST 2016
 */
@Table("WEB_BOTTOMTITLE")
public class Webbottomtitle extends BaseModel {
	private static final long serialVersionUID = 1L;

	@PrimaryKey.IDWORKER
	@Column
	private String id;

	
	@Column
	private String title;

	
	@Column
	private String img;

	
	@Column
	private String url;

	
	@Column
	private long indexing;

	
	@Column
	private String show_flag;

	
	@Column
	private String create_time;

	
	@Column
	private String update_time;

	
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
		return this.id;
	}

	public void setId(String Id) {
		this.id = Id;
	}
	
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String Title) {
		this.title = Title;
	}
	
	public String getImg() {
		return this.img;
	}

	public void setImg(String Img) {
		this.img = Img;
	}
	
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String Url) {
		this.url = Url;
	}
	
	public long getIndexing() {
		return this.indexing;
	}

	public void setIndexing(long Indexing) {
		this.indexing = Indexing;
	}
	
	public String getShow_flag() {
		return this.show_flag;
	}

	public void setShow_flag(String Show_flag) {
		this.show_flag = Show_flag;
	}
	
	public String getCreate_time() {
		return this.create_time;
	}

	public void setCreate_time(String Create_time) {
		this.create_time = Create_time;
	}
	
	public String getUpdate_time() {
		return this.update_time;
	}

	public void setUpdate_time(String Update_time) {
		this.update_time = Update_time;
	}
	
	public String getRemark1() {
		return this.remark1;
	}

	public void setRemark1(String Remark1) {
		this.remark1 = Remark1;
	}
	
	public String getRemark2() {
		return this.remark2;
	}

	public void setRemark2(String Remark2) {
		this.remark2 = Remark2;
	}
	
	public String getRemark3() {
		return this.remark3;
	}

	public void setRemark3(String Remark3) {
		this.remark3 = Remark3;
	}
	
	public String getRemark4() {
		return this.remark4;
	}

	public void setRemark4(String Remark4) {
		this.remark4 = Remark4;
	}
	
	public String getRemark5() {
		return this.remark5;
	}

	public void setRemark5(String Remark5) {
		this.remark5 = Remark5;
	}
}