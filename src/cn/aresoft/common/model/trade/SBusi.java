package cn.aresoft.common.model.trade;
import cn.aresoft.common.model.BaseModel;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
@Table("s_busi")
public class SBusi extends BaseModel {
	/**
	 * 业务代码表
	 */
	private static final long serialVersionUID = 1492747389321659021L;
	@Column
	@PrimaryKey.CUSTOM
	private String busi_code;
	@Column
	private String busi_type;
	@Column
	private String busi_name;
	@Column
	private Integer idx;
	@Column
	private Integer c_inout;//可用资金标志位
	@Column
	private Integer s_inout;//可用份额标志位
	@Column
	private Integer c_desi;//可取金额标志位
	@Column
	private Integer s_desi;//可赎份额标志位
	@Column
	private String memo;
	@Column
	private String busi_label;
	
	public String getBusi_code() {
		return busi_code;
	}
	public void setBusi_code(String busi_code) {
		this.busi_code = busi_code;
	}
	public String getBusi_type() {
		return busi_type;
	}
	public void setBusi_type(String busi_type) {
		this.busi_type = busi_type;
	}
	public String getBusi_name() {
		return busi_name;
	}
	public void setBusi_name(String busi_name) {
		this.busi_name = busi_name;
	}
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public Integer getC_inout() {
		return c_inout;
	}
	public void setC_inout(Integer c_inout) {
		this.c_inout = c_inout;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getBusi_label() {
		return busi_label;
	}
	public void setBusi_label(String busi_label) {
		this.busi_label = busi_label;
	}
	public Integer getS_inout() {
		return s_inout;
	}
	public void setS_inout(Integer s_inout) {
		this.s_inout = s_inout;
	}
	public Integer getC_desi() {
		return c_desi;
	}
	public void setC_desi(Integer c_desi) {
		this.c_desi = c_desi;
	}
	public Integer getS_desi() {
		return s_desi;
	}
	public void setS_desi(Integer s_desi) {
		this.s_desi = s_desi;
	}
	
	
}
