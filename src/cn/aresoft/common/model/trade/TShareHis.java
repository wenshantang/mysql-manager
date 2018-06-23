package cn.aresoft.common.model.trade;
import cn.aresoft.common.model.BaseModel;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
@Table("t_share_his")
public class TShareHis  extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3875393573935193762L;
	
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
	private String valid_begin_date;
	@Column
	private String valid_end_date;
	@Column
	private String busi_code;
	@Column
	private String last_date;
	@Column
	private String vali_date;
	@Column
	private String conf_num;
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
	public String getValid_begin_date() {
		return valid_begin_date;
	}
	public void setValid_begin_date(String valid_begin_date) {
		this.valid_begin_date = valid_begin_date;
	}
	public String getValid_end_date() {
		return valid_end_date;
	}
	public void setValid_end_date(String valid_end_date) {
		this.valid_end_date = valid_end_date;
	}
	public String getBusi_code() {
		return busi_code;
	}
	public void setBusi_code(String busi_code) {
		this.busi_code = busi_code;
	}
	public String getLast_date() {
		return last_date;
	}
	public void setLast_date(String last_date) {
		this.last_date = last_date;
	}
	public String getVali_date() {
		return vali_date;
	}
	public void setVali_date(String vali_date) {
		this.vali_date = vali_date;
	}
	public String getConf_num() {
		return conf_num;
	}
	public void setConf_num(String conf_num) {
		this.conf_num = conf_num;
	}

}
