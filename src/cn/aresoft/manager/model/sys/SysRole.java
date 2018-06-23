package cn.aresoft.manager.model.sys;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("SYS_ROLE")
public class SysRole extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7405525075332696600L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String code;

	@Column
	private String name;

	@Column
	private String memo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
