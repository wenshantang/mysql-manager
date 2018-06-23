package cn.aresoft.manager.model.sys;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.Table;
/**
 * 人员岗位表
 * @author yyj
 *
 */
@Table("sys_user_job")
public class SysUserJob extends SysBaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7405525075332696600L;

	@Column
	private String user_id;

	@Column
	private String job_id;
	
	@Column
	private String job_type;

	@Column(alias=true)
	private String job_name;

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getJob_id() {
		return job_id;
	}

	public void setJob_id(String job_id) {
		this.job_id = job_id;
	}

	public String getJob_name() {
		return job_name;
	}

	public void setJob_name(String job_name) {
		this.job_name = job_name;
	}

	public String getJob_type() {
		return job_type;
	}

	public void setJob_type(String job_type) {
		this.job_type = job_type;
	}



}
