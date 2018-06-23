package cn.aresoft.manager.model.sys;

import java.io.Serializable;

import com.puff.framework.annotation.Column;
/**
 * 系统相关新表继承model
 * <br/>包含如下公共字段：
 * <br/>create_time/create_user/update_time/update_user
 * @author yyj
 *
 */
public class SysBaseModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column
	private String create_time;

	@Column
	private String create_user;
	
	@Column
	private String update_time;

	@Column
	private String update_user;

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCreate_user() {
		return create_user;
	}

	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getUpdate_user() {
		return update_user;
	}

	public void setUpdate_user(String update_user) {
		this.update_user = update_user;
	}
	
	
}
