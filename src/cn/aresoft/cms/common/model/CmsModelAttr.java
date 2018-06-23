package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * cms 模型附加字段
 * 
 * @author dongchao
 *
 */

@Table("cms_model_attr")
public class CmsModelAttr extends BaseModel {

	private static final long serialVersionUID = 1035250193127055493L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String model_code;

	@Column
	private String field;

	@Column
	private String name;

	@Column
	private String type;

	public CmsModelAttr() {
		super();
	}

	public CmsModelAttr(String model_code, String field, String name, String type) {
		super();
		this.model_code = model_code;
		this.field = field;
		this.name = name;
		this.type = type;
	}

	public CmsModelAttr(String id, String model_code, String field, String name, String type) {
		this(model_code, field, name, type);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModel_code() {
		return model_code;
	}

	public void setModel_code(String model_code) {
		this.model_code = model_code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
