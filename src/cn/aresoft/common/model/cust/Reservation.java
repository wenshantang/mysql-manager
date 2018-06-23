package cn.aresoft.common.model.cust;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("t_req")
public class Reservation extends BaseModel{
	/**
	 * 客户预约管理表
	 * yyl
	 */
	private static final long serialVersionUID = 1L;
	@Column
	@PrimaryKey.IDWORKER
	private String id;//主键Id
	@Column
	private String req_time;//预约时间;
	@Column
	private String cust_name;//客户名称;
	@Column
	private String pro_id;//产品id;
	@Column
	private String req_amts;//预约金额; 份额金额至少有1个
	@Column
	private String mobile;//手机号码
	@Column
	private String email;//邮箱
	@Column
	private String memo;//备注
	@Column
	private String status;//处理状态状态： 0： 未处理  1：已处理
	@Column
	private String saas_id;//租户id
	@Column(alias = true)
	private String begin_date;//开始时间
	@Column(alias = true)
	private String end_date;//结束时间

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReq_time() {
		return req_time;
	}
	public void setReq_time(String req_time) {
		this.req_time = req_time;
	}
	public String getCust_name() {
		return cust_name;
	}
	public void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public String getReq_amts() {
		return req_amts;
	}
	public void setReq_amts(String req_amts) {
		this.req_amts = req_amts;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSaas_id() {
		return saas_id;
	}
	public void setSaas_id(String saas_id) {
		this.saas_id = saas_id;
	}
	public String getBegin_date() {
		return begin_date;
	}
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	
}
