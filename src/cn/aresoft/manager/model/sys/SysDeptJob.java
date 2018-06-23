package cn.aresoft.manager.model.sys;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
/**
 * 岗位表
 * @author yyj
 *
 */
@Table("sys_dept_job")
public class SysDeptJob extends SysBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7405525075332696600L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String dept_id;

	@Column(alias=true)
	private String dept_name;

	@Column
	private String job_name;

	@Column
	private String administrative_sup;//行政上级[岗位id]

	@Column(alias=true)
	private String administrative_sup_name;//行政上级[岗位名称]
	
	@Column
	private String business_sup;//业务上级id
	
	@Column(alias=true)
	private String business_sup_name;//业务上级name

	@Column
	private String job_description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDept_id() {
		return dept_id;
	}

	public void setDept_id(String dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public String getAdministrative_sup() {
		return administrative_sup;
	}

	public void setAdministrative_sup(String administrative_sup) {
		this.administrative_sup = administrative_sup;
	}

	public String getBusiness_sup() {
		return business_sup;
	}

	public void setBusiness_sup(String business_sup) {
		this.business_sup = business_sup;
	}

	public String getJob_description() {
		return job_description;
	}

	public void setJob_description(String job_description) {
		this.job_description = job_description;
	}

	public String getAdministrative_sup_name() {
		return administrative_sup_name;
	}

	public void setAdministrative_sup_name(String administrative_sup_name) {
		this.administrative_sup_name = administrative_sup_name;
	}

	public String getBusiness_sup_name() {
		return business_sup_name;
	}

	public void setBusiness_sup_name(String business_sup_name) {
		this.business_sup_name = business_sup_name;
	}

}
