package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * cms审核
 * 
 * @author dongchao
 *
 */

@Table("cms_audit")
public class CmsAudit extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -161501804796744670L;

	@PrimaryKey.IDWORKER
	@Column
	private String id;

	@Column
	private String audit_type;

	private String dict_type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAudit_type() {
		return audit_type;
	}

	public void setAudit_type(String audit_type) {
		this.audit_type = audit_type;
	}

	public String getDict_type() {
		return dict_type;
	}

	public void setDict_type(String dict_type) {
		this.dict_type = dict_type;
	}

}
