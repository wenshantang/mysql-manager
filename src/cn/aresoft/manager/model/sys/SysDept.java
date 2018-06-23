package cn.aresoft.manager.model.sys;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;
/**
 * 部门表
 * @author yyj
 *
 */
@Table("sys_dept")
public class SysDept extends SysBaseModel {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 7405525075332696600L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String dept_name;

	@Column
	private String sup_dept_id;
	
	@Column(alias=true)
	private String sup_dept_name;

	@Column
	private String dept_description;
	
	@Column
	private String dept_type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getSup_dept_id() {
		return sup_dept_id;
	}

	public void setSup_dept_id(String sup_dept_id) {
		this.sup_dept_id = sup_dept_id;
	}

	public String getDept_description() {
		return dept_description;
	}

	public void setDept_description(String dept_description) {
		this.dept_description = dept_description;
	}

	public String getDept_type() {
		return dept_type;
	}

	public void setDept_type(String dept_type) {
		this.dept_type = dept_type;
	}

	public String getSup_dept_name() {
		return sup_dept_name;
	}

	public void setSup_dept_name(String sup_dept_name) {
		this.sup_dept_name = sup_dept_name;
	}

	
}
