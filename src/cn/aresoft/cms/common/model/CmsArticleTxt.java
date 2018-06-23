package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * 文章内容(文本)
 * 
 * @author dongchao
 *
 */

@Table("cms_article_txt")
public class CmsArticleTxt extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4421754958703028443L;

	@PrimaryKey.CUSTOM
	@Column
	private String id;

	@Column
	private String content;// 文章内容

	@Column
	private String txt1;// 备用字段1

	@Column
	private String txt2;// 备用字段2

	@Column
	private String txt3;// 备用字段3

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTxt1() {
		return txt1;
	}

	public void setTxt1(String txt1) {
		this.txt1 = txt1;
	}

	public String getTxt2() {
		return txt2;
	}

	public void setTxt2(String txt2) {
		this.txt2 = txt2;
	}

	public String getTxt3() {
		return txt3;
	}

	public void setTxt3(String txt3) {
		this.txt3 = txt3;
	}

}
