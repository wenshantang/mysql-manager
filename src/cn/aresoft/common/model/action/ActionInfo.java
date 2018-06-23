package cn.aresoft.common.model.action;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 *
 *
 * @author  林小钧2016年4月7日 下午1:37:53
 *
 */
@Table("fun_act_info")
public class ActionInfo extends BaseModel{
 
	private static final long serialVersionUID = 1L;
	
	@PrimaryKey.UUID
	@Column
	private String act_id;//活动id
	@Column
	private String act_code;//活动代码
	@Column
	private String act_name;//活动名称
	@Column
	private String act_type;//活动类型
	@Column
	private String act_desc;//活动描述
	@Column
	private String act_mode;//预约描述
	@Column
	private String act_remark;//预约备注
	@Column
	private String act_enddate;//预约截止日
	@Column
	private String act_scope;//活动范围
	@Column
	private String pro_code;//产品代码
	@Column
	private String pro_name;//产品名称
	@Column
	private String valid_begin_time;//开始时间
	@Column
	private String valid_end_time;//结束时间
	@Column
	private String end_flg;//结束标志
	@Column
	private String creat_date;//建立时间
	@Column
	private String creat_by;//建立人
	@Column
	private String update_date;//最后修改时间
	@Column
	private String update_by;//修改人
	public String getAct_id() {
		return act_id;
	}
	public void setAct_id(String act_id) {
		this.act_id = act_id;
	}
	public String getAct_code() {
		return act_code;
	}
	public void setAct_code(String act_code) {
		this.act_code = act_code;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getAct_type() {
		return act_type;
	}
	public void setAct_type(String act_type) {
		this.act_type = act_type;
	}
	public String getAct_desc() {
		return act_desc;
	}
	public void setAct_desc(String act_desc) {
		this.act_desc = act_desc;
	}
	public String getAct_mode() {
		return act_mode;
	}
	public void setAct_mode(String act_mode) {
		this.act_mode = act_mode;
	}
	public String getAct_remark() {
		return act_remark;
	}
	public void setAct_remark(String act_remark) {
		this.act_remark = act_remark;
	}
	public String getAct_enddate() {
		return act_enddate;
	}
	public void setAct_enddate(String act_enddate) {
		this.act_enddate = act_enddate;
	}
	public String getAct_scope() {
		return act_scope;
	}
	public void setAct_scope(String act_scope) {
		this.act_scope = act_scope;
	}
	public String getPro_code() {
		return pro_code;
	}
	public void setPro_code(String pro_code) {
		this.pro_code = pro_code;
	}
	public String getPro_name() {
		return pro_name;
	}
	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}
	public String getValid_begin_time() {
		return valid_begin_time;
	}
	public void setValid_begin_time(String valid_begin_time) {
		this.valid_begin_time = valid_begin_time;
	}
	public String getValid_end_time() {
		return valid_end_time;
	}
	public void setValid_end_time(String valid_end_time) {
		this.valid_end_time = valid_end_time;
	}
	public String getEnd_flg() {
		return end_flg;
	}
	public void setEnd_flg(String end_flg) {
		this.end_flg = end_flg;
	}
	public String getCreat_date() {
		return creat_date;
	}
	public void setCreat_date(String creat_date) {
		this.creat_date = creat_date;
	}
	public String getCreat_by() {
		return creat_by;
	}
	public void setCreat_by(String creat_by) {
		this.creat_by = creat_by;
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
	
	
    }

