package cn.aresoft.cms.common.model;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("cms_file")
public class CmsFile extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4697392494866051891L;

	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String file_name;// 文件名

	@Column
	private String file_type;// 文件类型

	@Column
	private String file_path;// 文件路径

	@Column
	private String upload_toftp;// 上传到FTP

	@Column
	private String file_url;// 文件url

	@Column
	private String size;// 文件大小

	@Column
	private String upload_time;// 上传时间

	@Column
	private int download_count;// 下载次数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getFile_type() {
		return file_type;
	}

	public void setFile_type(String file_type) {
		this.file_type = file_type;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getUpload_toftp() {
		return upload_toftp;
	}

	public void setUpload_toftp(String upload_toftp) {
		this.upload_toftp = upload_toftp;
	}

	public String getFile_url() {
		return file_url;
	}

	public void setFile_url(String file_url) {
		this.file_url = file_url;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(String upload_time) {
		this.upload_time = upload_time;
	}

	public int getDownload_count() {
		return download_count;
	}

	public void setDownload_count(int download_count) {
		this.download_count = download_count;
	}

}
