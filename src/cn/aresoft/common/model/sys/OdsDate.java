package cn.aresoft.common.model.sys;

import java.util.Date;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("ODS_DATE")
public class OdsDate extends BaseModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	@PrimaryKey.IDWORKER
	private Integer odsid;//主键Id
	
	@Column
	private String date_id;//日期
	@Column
	private String is_work_qd;//工作日;(1:是;0:不是)
	@Column
	private String year_id;//年份
	@Column
	private String mon_id;//月份;(年+月)
	@Column
	private String day_id;// 当月日号;(01~31)
	@Column
	private String qter_id;// 季度;(1~4)
	@Column
	private Integer week_day;// 星期几;(1~7);7表示周日
	@Column
	private Integer idx_day;// 日序号;(1~366)
	@Column
	private Integer idx_mon;// 月序号;(1~12)
	@Column
	private Integer idx_week;// 周序号;(1~53)
	@Column
	private Integer idx_iweek;//实际周序号;(IW)
	@Column
	private Integer idx_mon_week;// 当月周序号;(1~5)
	@Column
	private Integer date_type_id;// 交易日类型（1国内、2香港）
	@Column
	private String date_type_name;// 季度;(1~4)
	@Column
	private Integer createid;// 创建人ID(员工表)
	@Column
	private String createname;// 创建人
	@Column
	private Date createtime;// 创建时间
	@Column
	private Integer updateid;//修改人ID(员工表) 
	@Column
	private String updatename;// 修改人
	@Column
	private Date updatetime;// 修改时间
	@Column
	private String remark;//备注
	@Column
	private String is_work;// is_work
	public Integer getOdsid() {
		return odsid;
	}
	public void setOdsid(Integer odsid) {
		this.odsid = odsid;
	}
	public String getDate_id() {
		return date_id;
	}
	public void setDate_id(String date_id) {
		this.date_id = date_id;
	}
	public String getIs_work_qd() {
		return is_work_qd;
	}
	public void setIs_work_qd(String is_work_qd) {
		this.is_work_qd = is_work_qd;
	}
	public String getYear_id() {
		return year_id;
	}
	public void setYear_id(String year_id) {
		this.year_id = year_id;
	}
	public String getMon_id() {
		return mon_id;
	}
	public void setMon_id(String mon_id) {
		this.mon_id = mon_id;
	}
	public String getDay_id() {
		return day_id;
	}
	public void setDay_id(String day_id) {
		this.day_id = day_id;
	}
	public String getQter_id() {
		return qter_id;
	}
	public void setQter_id(String qter_id) {
		this.qter_id = qter_id;
	}
	public Integer getWeek_day() {
		return week_day;
	}
	public void setWeek_day(Integer week_day) {
		this.week_day = week_day;
	}
	public Integer getIdx_day() {
		return idx_day;
	}
	public void setIdx_day(Integer idx_day) {
		this.idx_day = idx_day;
	}
	public Integer getIdx_mon() {
		return idx_mon;
	}
	public void setIdx_mon(Integer idx_mon) {
		this.idx_mon = idx_mon;
	}
	public Integer getIdx_week() {
		return idx_week;
	}
	public void setIdx_week(Integer idx_week) {
		this.idx_week = idx_week;
	}
	public Integer getIdx_iweek() {
		return idx_iweek;
	}
	public void setIdx_iweek(Integer idx_iweek) {
		this.idx_iweek = idx_iweek;
	}
	public Integer getIdx_mon_week() {
		return idx_mon_week;
	}
	public void setIdx_mon_week(Integer idx_mon_week) {
		this.idx_mon_week = idx_mon_week;
	}
	public Integer getDate_type_id() {
		return date_type_id;
	}
	public void setDate_type_id(Integer date_type_id) {
		this.date_type_id = date_type_id;
	}
	public String getDate_type_name() {
		return date_type_name;
	}
	public void setDate_type_name(String date_type_name) {
		this.date_type_name = date_type_name;
	}
	public String getCreatename() {
		return createname;
	}
	public void setCreatename(String createname) {
		this.createname = createname;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getUpdateid() {
		return updateid;
	}
	public void setUpdateid(Integer updateid) {
		this.updateid = updateid;
	}
	public String getUpdatename() {
		return updatename;
	}
	public void setUpdatename(String updatename) {
		this.updatename = updatename;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIs_work() {
		return is_work;
	}
	public void setIs_work(String is_work) {
		this.is_work = is_work;
	}
	public Integer getCreateid() {
		return createid;
	}
	public void setCreateid(Integer createid) {
		this.createid = createid;
	}
	
	
	
}
