package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

/**
 * cms模型
 * 
 * @author dongchao
 *
 */

@Table("cms_model")
public class CmsModel extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6435161860552830031L;

	@PrimaryKey.CUSTOM
	@Column
	private String code;

	@Column
	private String name;// 模型名称

	@Column
	private int title_img_width;// 标题图片宽度

	@Column
	private int title_img_height;// 标题图片高度

	@Column
	private int content_img_width;// 内容图片宽度

	@Column
	private int content_img_height;// 内容图片高度

	@Column
	private int idx;// 排序

	@Column
	private int status;// 状态 1启用 0停用

	@Column
	private String create_time;// 创建时间

	@Column
	private String create_user;// 创建人

	@Column
	private String memo;// 模型描述

	@Column
	private String pc_template_id;// PC端模板ID

	@Column
	private String mobile_template_id;// 手机端模板ID

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTitle_img_width() {
		return title_img_width;
	}

	public void setTitle_img_width(int title_img_width) {
		this.title_img_width = title_img_width;
	}

	public int getTitle_img_height() {
		return title_img_height;
	}

	public void setTitle_img_height(int title_img_height) {
		this.title_img_height = title_img_height;
	}

	public int getContent_img_width() {
		return content_img_width;
	}

	public void setContent_img_width(int content_img_width) {
		this.content_img_width = content_img_width;
	}

	public int getContent_img_height() {
		return content_img_height;
	}

	public void setContent_img_height(int content_img_height) {
		this.content_img_height = content_img_height;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPc_template_id() {
		return pc_template_id;
	}

	public void setPc_template_id(String pc_template_id) {
		this.pc_template_id = pc_template_id;
	}

	public String getMobile_template_id() {
		return mobile_template_id;
	}

	public void setMobile_template_id(String mobile_template_id) {
		this.mobile_template_id = mobile_template_id;
	}

}
