package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * cms 访问权限
 * 
 * @author dongchao
 *
 */

@Table("cms_access_permission")
public class CmsAccessPermission extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1953790518381067898L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String name;

	@Column
	private String class_name;

	@Column
	private String status;

	@Column
	private String memo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClass_name() {
		return class_name;
	}

	public void setClass_name(String class_name) {
		this.class_name = class_name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
