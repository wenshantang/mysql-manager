package cn.aresoft.manager.model.sys;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

/**
 * 上传设置
 * 
 * @author dongchao
 *
 */
public class UploadConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4784588150242444054L;

	private int allow_upload;// 允许上传文件 1允许 0不允许,仅仅针对前台

	private String save_dir;// 文件保存目录

	private String save_type;// 文件保存方式（按年月日每天一个目录、按年月/日/存入不同目录

	private Map<String, Upload> upload_setting = new HashMap<String, Upload>();

	public int getAllow_upload() {
		return allow_upload;
	}

	public void setAllow_upload(int allow_upload) {
		this.allow_upload = allow_upload;
	}

	public String getSave_dir() {
		return save_dir;
	}

	public void setSave_dir(String save_dir) {
		this.save_dir = save_dir;
	}

	public String getSave_type() {
		return save_type;
	}

	public void setSave_type(String save_type) {
		this.save_type = save_type;
	}

	public Map<String, Upload> getUpload_setting() {
		return upload_setting;
	}

	public void setUpload_setting(Map<String, Upload> upload_setting) {
		this.upload_setting = upload_setting;
	}

	public String toJson() {
		return JSON.toJSONString(this);
	}

}
