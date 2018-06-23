package cn.aresoft.manager.model.sys;

import java.util.List;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("SYS_USER")
public class SysUser extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1919234474987540768L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String login_name;

	@Column
	private String name;

	@Column
	private String pwd;

	@Column
	private String phone;

	@Column
	private String email;

	@Column
	private String status;

	private List<String> roleIds;

	private List<String> resIds;
	
	@Column
	private int errors;//累计密码错误次数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public List<String> getResIds() {
		return resIds;
	}

	public void setResIds(List<String> resIds) {
		this.resIds = resIds;
	}

	public int getErrors() {
		return errors;
	}

	public void setErrors(int errors) {
		this.errors = errors;
	}

}
