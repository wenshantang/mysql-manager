package cn.aresoft.common.model.trade;
import cn.aresoft.common.model.BaseModel;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
@Table("t_share_cur")
public class TShareCur  extends BaseModel {
	/**
	 * 产品份额表
	 */
	private static final long serialVersionUID = 5616136945308660212L;
	@Column
	@PrimaryKey.IDWORKER
	private String i_id;
	@Column
	private String cust_id;
	@Column
	private String pro_id;
	@Column
	private String share_type;
	@Column
	private Double shares;
	@Column
	private Double frz_shares;
	@Column
	private Double unpay_income;
	@Column
	private String share_lev;
	@Column
	private String last_date;
	@Column
	private String avail_date;
	@Column
	private Double desi_shares;
	@Column(alias=true)
	private String pro_name;//产品名称
	@Column(alias=true)
	private String net_date;//净值日期
	@Column(alias=true)
	private String net_value;//单位净值
	@Column(alias=true)
	private String total_value;//累计净值
	@Column(alias=true)
	private Double balance;//当前市值
	
	
	public String getI_id() {
		return i_id;
	}
	public void setI_id(String i_id) {
		this.i_id = i_id;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public String getShare_type() {
		return share_type;
	}
	public void setShare_type(String share_type) {
		this.share_type = share_type;
	}
	public Double getShares() {
		return shares;
	}
	public void setShares(Double shares) {
		this.shares = shares;
	}
	public Double getFrz_shares() {
		return frz_shares;
	}
	public void setFrz_shares(Double frz_shares) {
		this.frz_shares = frz_shares;
	}
	public Double getUnpay_income() {
		return unpay_income;
	}
	public void setUnpay_income(Double unpay_income) {
		this.unpay_income = unpay_income;
	}
	public String getShare_lev() {
		return share_lev;
	}
	public void setShare_lev(String share_lev) {
		this.share_lev = share_lev;
	}
	public String getLast_date() {
		return last_date;
	}
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
	public String getAvail_date() {
		return avail_date;
	}
	public void setAvail_date(String avail_date) {
		this.avail_date = avail_date;
	}
	public Double getDesi_shares() {
		return desi_shares;
	}
	public void setDesi_shares(Double desi_shares) {
		this.desi_shares = desi_shares;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getNet_date() {
		return net_date;
	}
	public void setNet_date(String net_date) {
		this.net_date = net_date;
	}
	public String getNet_value() {
		return net_value;
	}
	public void setNet_value(String net_value) {
		this.net_value = net_value;
	}
	public String getTotal_value() {
		return total_value;
	}
	public void setTotal_value(String total_value) {
		this.total_value = total_value;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	
}
