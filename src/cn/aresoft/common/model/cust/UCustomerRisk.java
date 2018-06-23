package cn.aresoft.common.model.cust;
import java.util.List;

import cn.aresoft.common.model.BaseModel;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
import com.puff.jdbc.core.Record;
@Table("u_customer_risk")
public class UCustomerRisk  extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	@PrimaryKey.IDWORKER
	private String id;
	@Column
	private String cust_id;
	@Column
	private String answers;
	@Column
	private Integer totalscore;
	@Column
	private String risk_type;
	@Column
	private String risk_level;
	@Column
	private String create_time;
	@Column
	private String update_time;
	@Column
	private String valid_date;
	
	private List<Record> listRecord;
	
	@Column(alias=true)
	private String short_name;//客户简称
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getAnswers() {
		return answers;
	}
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	public Integer getTotalscore() {
		return totalscore;
	}
	public void setTotalscore(Integer totalscore) {
		this.totalscore = totalscore;
	}
	public String getRisk_type() {
		return risk_type;
	}
	public void setRisk_type(String risk_type) {
		this.risk_type = risk_type;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getValid_date() {
		return valid_date;
	}
	public void setValid_date(String valid_date) {
		this.valid_date = valid_date;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getShort_name() {
		return short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}
	public List<Record> getListRecord() {
		return listRecord;
	}
	public void setListRecord(List<Record> listRecord) {
		this.listRecord = listRecord;
	}
	public String getRisk_level() {
		return risk_level;
	}
	public void setRisk_level(String risk_level) {
		this.risk_level = risk_level;
	}
	
}
