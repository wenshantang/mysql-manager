package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * 模板信息
 * 
 * @author dongchao
 *
 */
@Table("cms_template")
public class CmsTemplate extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3316378319630354210L;

	public static final String DEFAULT_TEMPLATE_DIR = "template";

	@PrimaryKey.IDWORKER
	@Column
	private String id;

	@Column
	private String name;// 模板名称

	@Column
	private String path;// 模板路径

	@Column
	private String dir;// 模板目录

	@Column
	private String parent_id;// 上一级模板ID

	@Column
	private String type;// 模板类型

	@Column
	private String content;// 模板内容

	@Column
	private String create_time;// 创建时间

	@Column
	private String create_user;// 创建人

	@Column
	private String last_modify_time;// 最后修改时间

	@Column
	private String last_modify_user;// 最后修改人

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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

	public String getLast_modify_time() {
		return last_modify_time;
	}

	public void setLast_modify_time(String last_modify_time) {
		this.last_modify_time = last_modify_time;
	}

	public String getLast_modify_user() {
		return last_modify_user;
	}

	public void setLast_modify_user(String last_modify_user) {
		this.last_modify_user = last_modify_user;
	}

}
