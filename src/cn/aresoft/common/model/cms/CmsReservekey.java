package cn.aresoft.common.model.cms;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * CMS保留字功能
 * @author ylj
 *
 */
@Table("CMS_RESERVEKEY")
public class CmsReservekey extends BaseModel {

	private static final long serialVersionUID = 1L;

	@Column
	@PrimaryKey.IDWORKER
	private String i_id;//主键Id
	@Column
	private String vc_keyword;//关键字
	@Column
	private String vc_pub0;//备用字段1
	@Column
	private String vc_pub1;//备用字段2
	@Column
	private String vc_pub2;//备用字段3
	@Column
	private String d_createtime;//创建时间
	@Column
	private String i_createuser;//创建人id
	@Column
	private String d_lastmodtime;//修改时间
	@Column
	private String i_moduser;//修改人id
	@Column
	private String c_deleted;//是否删除[0:否；1：是]
	
	
	public String getI_id() {
		return i_id;
	}
	public void setI_id(String i_id) {
		this.i_id = i_id;
	}
	public String getVc_keyword() {
		return vc_keyword;
	}
	public void setVc_keyword(String vc_keyword) {
		this.vc_keyword = vc_keyword;
	}
	public String getVc_pub0() {
		return vc_pub0;
	}
	public void setVc_pub0(String vc_pub0) {
		this.vc_pub0 = vc_pub0;
	}
	public String getVc_pub1() {
		return vc_pub1;
	}
	public void setVc_pub1(String vc_pub1) {
		this.vc_pub1 = vc_pub1;
	}
	public String getVc_pub2() {
		return vc_pub2;
	}
	public void setVc_pub2(String vc_pub2) {
		this.vc_pub2 = vc_pub2;
	}
	public String getD_createtime() {
		return d_createtime;
	}
	public void setD_createtime(String d_createtime) {
		this.d_createtime = d_createtime;
	}
	public String getI_createuser() {
		return i_createuser;
	}
	public void setI_createuser(String i_createuser) {
		this.i_createuser = i_createuser;
	}
	public String getD_lastmodtime() {
		return d_lastmodtime;
	}
	public void setD_lastmodtime(String d_lastmodtime) {
		this.d_lastmodtime = d_lastmodtime;
	}
	public String getI_moduser() {
		return i_moduser;
	}
	public void setI_moduser(String i_moduser) {
		this.i_moduser = i_moduser;
	}
	public String getC_deleted() {
		return c_deleted;
	}
	public void setC_deleted(String c_deleted) {
		this.c_deleted = c_deleted;
	}




}
