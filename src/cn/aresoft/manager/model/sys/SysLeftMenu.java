package cn.aresoft.manager.model.sys;

import java.util.List;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("SYS_LEFT_MENU")
public class SysLeftMenu extends BaseModel {

	private static final long serialVersionUID = 2994586957091936242L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String name;

	@Column("parent_id")
	private String parentId;

	@Column("resource_id")
	private String resourceId;

	@Column
	private String type;

	@Column
	private int idx;

	@Column
	private String url;

	private List<SysLeftMenu> sons;

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<SysLeftMenu> getSons() {
		return sons;
	}

	public void setSons(List<SysLeftMenu> sons) {
		this.sons = sons;
	}

}
