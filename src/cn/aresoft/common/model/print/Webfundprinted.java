package cn.aresoft.common.model.print;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;
/**
 * Thu Sep 08 10:39:01 CST 2016
 */
@Table("WEB_FUND_PRINTED")
public class Webfundprinted extends BaseModel {
	private static final long serialVersionUID = 1L;

	@PrimaryKey.IDWORKER
	@Column
	private String id;

	
	@Column
	private String printed_name;

	
	@Column
	private String i_dividendamount;

	
	@Column
	private String sevenday;

	
	@Column
	private String rate;

	
	@Column
	private String nav;

	
	@Column
	private String sumnav;

	
	@Column
	private String daily_growth_rate;

	
	@Column
	private String onemonth;

	
	@Column
	private String threemonth;

	
	@Column
	private String sixmonth;

	
	@Column
	private String oneyear;

	
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
	
	public String getI_dividendamount() {
		return this.i_dividendamount;
	}

	public void setI_dividendamount(String I_dividendamount) {
		this.i_dividendamount = I_dividendamount;
	}
	
	public String getSevenday() {
		return this.sevenday;
	}

	public void setSevenday(String Sevenday) {
		this.sevenday = Sevenday;
	}
	
	public String getRate() {
		return this.rate;
	}

	public void setRate(String Rate) {
		this.rate = Rate;
	}
	
	public String getNav() {
		return this.nav;
	}

	public void setNav(String Nav) {
		this.nav = Nav;
	}
	
	public String getSumnav() {
		return this.sumnav;
	}

	public void setSumnav(String Sumnav) {
		this.sumnav = Sumnav;
	}
	
	public String getDaily_growth_rate() {
		return this.daily_growth_rate;
	}

	public void setDaily_growth_rate(String Daily_growth_rate) {
		this.daily_growth_rate = Daily_growth_rate;
	}
	
	public String getOnemonth() {
		return this.onemonth;
	}

	public void setOnemonth(String Onemonth) {
		this.onemonth = Onemonth;
	}
	
	public String getThreemonth() {
		return this.threemonth;
	}

	public void setThreemonth(String Threemonth) {
		this.threemonth = Threemonth;
	}
	
	public String getSixmonth() {
		return this.sixmonth;
	}

	public void setSixmonth(String Sixmonth) {
		this.sixmonth = Sixmonth;
	}
	
	public String getOneyear() {
		return this.oneyear;
	}

	public void setOneyear(String Oneyear) {
		this.oneyear = Oneyear;
	}
	
	public String getIsdelete() {
		return this.isdelete;
	}

	public void setIsdelete(String Isdelete) {
		this.isdelete = Isdelete;
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

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
	
}