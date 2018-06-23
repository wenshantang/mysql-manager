package cn.aresoft.common.model.recommend;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
import cn.aresoft.common.model.BaseModel;
/**
 * Sun Sep 18 16:29:26 CST 2016
 */
@Table("WEB_RECOMMENDFUND")
public class Webrecommendfund extends BaseModel {
	private static final long serialVersionUID = 1L;

	@PrimaryKey.IDWORKER
	@Column
	private String id;

	
	@Column
	private String fundcode;
	
	
	@Column
	private String fundName;
	
	@Column
	private long indexing;
	
	@Column
	private String recommented_flag;
	
	@Column
	private String create_time;

	
	@Column
	private String pcorapp;

	
	@Column
	private String introduce;

	
	@Column
	private String introduceimg;

	
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
	
	public String getFundcode() {
		return this.fundcode;
	}

	public void setFundcode(String Fundcode) {
		this.fundcode = Fundcode;
	}
	
	public long getIndexing() {
		return this.indexing;
	}

	public void setIndexing(long Indexing) {
		this.indexing = Indexing;
	}
	
	public String getCreate_time() {
		return this.create_time;
	}

	public void setCreate_time(String Create_time) {
		this.create_time = Create_time;
	}
	
	
	
	public String getPcorapp() {
		return pcorapp;
	}

	public void setPcorapp(String pcorapp) {
		this.pcorapp = pcorapp;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getIntroduceimg() {
		return introduceimg;
	}

	public void setIntroduceimg(String introduceimg) {
		this.introduceimg = introduceimg;
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

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getRecommented_flag() {
		return recommented_flag;
	}

	public void setRecommented_flag(String recommented_flag) {
		this.recommented_flag = recommented_flag;
	}
	
	
	
}