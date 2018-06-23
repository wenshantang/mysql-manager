package cn.aresoft.common.model.honor;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("fun_honer")
public class HonorManager extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@PrimaryKey.UUID
	@Column
	private String id;
	
	@Column
	private String honor_year;
	
	@Column 
	private String honor_month;
	
	@Column
	private String honor_type;

	@Column
	private String honor_name;
	
	@Column
	private String honor_from;
	
	@Column
	private String url;
	
	@Column
	private String show_index;
	
	@Column
	private String remark;
	
	@Column
	private String del_flag;

	@Column
	private String create_date;
	
	@Column
	private String create_by;

	@Column
	private String update_date;
	
	@Column
	private String update_by;
	
	@Column(alias=true)
	private String from_date;
	
	@Column(alias=true)
	private String to_date;
	
	//is_h5_flag 0、pc端，1h5端
	@Column
	private String is_h5_flag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHonor_year() {
		return honor_year;
	}

	public void setHonor_year(String honor_year) {
		this.honor_year = honor_year;
	}

	public String getHonor_month() {
		return honor_month;
	}

	public void setHonor_month(String honor_month) {
		this.honor_month = honor_month;
	}

	public String getHonor_type() {
		return honor_type;
	}

	public void setHonor_type(String honor_type) {
		this.honor_type = honor_type;
	}

	public String getHonor_name() {
		return honor_name;
	}

	public void setHonor_name(String honor_name) {
		this.honor_name = honor_name;
	}

	public String getHonor_from() {
		return honor_from;
	}

	public void setHonor_from(String honor_from) {
		this.honor_from = honor_from;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShow_index() {
		return show_index;
	}

	public void setShow_index(String show_index) {
		this.show_index = show_index;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(String del_flag) {
		this.del_flag = del_flag;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCreate_by() {
		return create_by;
	}

	public void setCreate_by(String create_by) {
		this.create_by = create_by;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getIs_h5_flag() {
		return is_h5_flag;
	}

	public void setIs_h5_flag(String is_h5_flag) {
		this.is_h5_flag = is_h5_flag;
	}
	
	
	

	
}
