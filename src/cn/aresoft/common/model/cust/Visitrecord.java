package cn.aresoft.common.model.cust;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("u_visitrecord")
public class Visitrecord extends BaseModel{
	/**
	 * 拜访记录信息表
	 * yyl
	 */
	private static final long serialVersionUID = 1L;
	@Column
	@PrimaryKey.IDWORKER
	private String id;//主键Id
	@Column
	private String visitsubject;//拜访主题
	@Column
	private String cust_id;//CUST_ID
	@Column
	private String cust_name;//客户名称
	@Column
	private String visitobject;//拜访对象
	@Column
	private String visitmethods;//拜访方式
	@Column
	private String visitaddress;//拜访地点
	@Column
	private String visitcontent;//拜访内容
	@Column
	private String memo;//备注
	@Column
	private String startdate;//开始时间
	@Column
	private String enddate;//结束时间
	@Column
	private String create_user;//创建人
	@Column
	private String create_time;//创建时间
	@Column
	private String update_user;//修改人
	@Column
	private String update_time;//修改时间
	@Column
	private String isdelete;//是否删除（0-保留；1-删除）
	@Column
	private String visit_attachment;//附件
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getVisitsubject() {
		return visitsubject;
	}
	public void setVisitsubject(String visitsubject) {
		this.visitsubject = visitsubject;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getVisitobject() {
		return visitobject;
	}
	public void setVisitobject(String visitobject) {
		this.visitobject = visitobject;
	}
	public String getVisitmethods() {
		return visitmethods;
	}
	public void setVisitmethods(String visitmethods) {
		this.visitmethods = visitmethods;
	}
	public String getVisitaddress() {
		return visitaddress;
	}
	public void setVisitaddress(String visitaddress) {
		this.visitaddress = visitaddress;
	}
	public String getVisitcontent() {
		return visitcontent;
	}
	public void setVisitcontent(String visitcontent) {
		this.visitcontent = visitcontent;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getUpdate_user() {
		return update_user;
	}
	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getIsdelete() {
		return isdelete;
	}
	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}
	public String getVisit_attachment() {
		return visit_attachment;
	}
	public void setVisit_attachment(String visit_attachment) {
		this.visit_attachment = visit_attachment;
	}
	
}
