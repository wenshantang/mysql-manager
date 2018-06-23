package cn.aresoft.common.model.appfundtype;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
import cn.aresoft.common.model.BaseModel;
/**
 * Tue Oct 11 10:53:09 CST 2016
 */
@Table("WEB_APPFUNDTYPE_PRINTED")
public class Webappfundtypeprinted extends BaseModel {
	private static final long serialVersionUID = 1L;

	@PrimaryKey.IDWORKER
	@Column
	private String id;

	
	@Column
	private String printed_name;

	
	@Column
	private String zhishu;

	
	@Column
	private String hunhe;

	
	@Column
	private String zhaiquan;

	
	@Column
	private String huobi;

	
	@Column
	private String etf;

	
	@Column
	private String gupiao;

	
	@Column
	private String qdll;

	
	@Column
	private String isdelete;

	
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

	
	public String getId() {
		return this.id;
	}

	public void setId(String Id) {
		this.id = Id;
	}
	
	public String getPrinted_name() {
		return this.printed_name;
	}

	public void setPrinted_name(String Printed_name) {
		this.printed_name = Printed_name;
	}
	
	public String getZhishu() {
		return this.zhishu;
	}

	public void setZhishu(String Zhishu) {
		this.zhishu = Zhishu;
	}
	
	public String getHunhe() {
		return this.hunhe;
	}

	public void setHunhe(String Hunhe) {
		this.hunhe = Hunhe;
	}
	
	public String getZhaiquan() {
		return this.zhaiquan;
	}

	public void setZhaiquan(String Zhaiquan) {
		this.zhaiquan = Zhaiquan;
	}
	
	public String getHuobi() {
		return this.huobi;
	}

	public void setHuobi(String Huobi) {
		this.huobi = Huobi;
	}
	
	public String getEtf() {
		return this.etf;
	}

	public void setEtf(String Etf) {
		this.etf = Etf;
	}
	
	public String getGupiao() {
		return this.gupiao;
	}

	public void setGupiao(String Gupiao) {
		this.gupiao = Gupiao;
	}
	
	public String getQdll() {
		return this.qdll;
	}

	public void setQdll(String Qdll) {
		this.qdll = Qdll;
	}
	
	public String getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(String Isdelete) {
		this.isdelete = Isdelete;
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
}