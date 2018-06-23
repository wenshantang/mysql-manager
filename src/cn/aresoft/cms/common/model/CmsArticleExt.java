package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * 文章扩展信息
 * 
 * @author dongchao
 *
 */

@Table("cms_article_ext")
public class CmsArticleExt extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2648468853367890797L;
	@PrimaryKey.CUSTOM
	@Column
	private long id;
	
	
	

}
