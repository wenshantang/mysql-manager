package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * cms 访问统计
 * 
 * @author dongchao
 *
 */

@Table("cms_access_count")
public class CmsAccessCount extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1774952071440758564L;

	@Column
	@PrimaryKey.CUSTOM
	private long id;

	@Column
	private int type;

	@Column
	private long view_count;// 点击次数

	@Column
	private long up_count;// 点赞次数

	@Column
	private long down_count;// 被踩次数

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getView_count() {
		return view_count;
	}

	public void setView_count(long view_count) {
		this.view_count = view_count;
	}

	public long getUp_count() {
		return up_count;
	}

	public void setUp_count(long up_count) {
		this.up_count = up_count;
	}

	public long getDown_count() {
		return down_count;
	}

	public void setDown_count(long down_count) {
		this.down_count = down_count;
	}

}
