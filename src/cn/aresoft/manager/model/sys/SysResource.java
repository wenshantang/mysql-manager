package cn.aresoft.manager.model.sys;

import java.util.List;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("SYS_RESOURCE")
public class SysResource extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2393222764922282916L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String name;

	@Column
	private String url;

	@Column
	private String memo;

	@Column
	private String parent_id;

	@Column
	private String dep_url;

	private List<SysResource> sons;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getDep_url() {
		return dep_url;
	}

	public void setDep_url(String dep_url) {
		this.dep_url = dep_url;
	}

	public List<SysResource> getSons() {
		return sons;
	}

	public void setSons(List<SysResource> sons) {
		this.sons = sons;
	}

}
