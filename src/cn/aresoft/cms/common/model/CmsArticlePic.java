package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * 文章内容(图片)
 * 
 * @author dongchao
 *
 */

@Table("cms_article_pic")
public class CmsArticlePic extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4088208469407915050L;

	@PrimaryKey.CUSTOM
	@Column
	private String id;

	@Column
	private int idx;// 排序

	@Column
	private String img_path;// 图片路径

	@Column
	private String img_desc;// 图片描述

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getImg_desc() {
		return img_desc;
	}

	public void setImg_desc(String img_desc) {
		this.img_desc = img_desc;
	}

}
