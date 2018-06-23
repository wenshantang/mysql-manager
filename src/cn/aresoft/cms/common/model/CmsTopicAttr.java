package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * cms 栏目附加信息
 * 
 * @author dongchao
 *
 */

@Table("cms_topic_attr")
public class CmsTopicAttr extends BaseModel {

	private static final long serialVersionUID = -31158317770015396L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String topic_id;

	@Column
	private String field;

	@Column
	private String name;

	@Column
	private String value;

	@Column
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTopic_id() {
		return topic_id;
	}

	public void setTopic_id(String topic_id) {
		this.topic_id = topic_id;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
