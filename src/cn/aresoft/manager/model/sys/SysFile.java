package cn.aresoft.manager.model.sys;

import com.puff.framework.annotation.Column;
import com.puff.framework.annotation.PrimaryKey;
import com.puff.framework.annotation.Table;

import cn.aresoft.common.model.BaseModel;

@Table("SYS_FILE")
public class SysFile extends BaseModel {

	private static final long serialVersionUID = -3390261978672431712L;
	@Column
	@PrimaryKey.IDWORKER
	private String id;

	@Column
	private String name;// 文件原始�?
	@Column
	private String save_name;// 保存文件�?
	@Column
	private String type;// 文件类型

	@Column
	private String path;// 文件路径

	@Column
	private String http_url;// 文件访问url

	@Column
	private String file_size;// 文件大小

	@Column
	private String upload_user;// 上传�?
	@Column
	private String upload_time;// 上传时间

	@Column
	private long download_count;// 下载次数
	
	@Column
	private String file_menu;// 资源目录分类
	@Column
	private String file_describe;// 资源说明
	@Column
	private String pro_ids;// 关联产品id 
	private String[] pro_idsarr;// 关联产品id String[]
	@Column
	private String file_auth;// 资源权限 1-�?��人可�?2-会员可见,3-持有人可�?   �?�?��人可�?
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

	public String getSave_name() {
		return save_name;
	}

	public void setSave_name(String save_name) {
		this.save_name = save_name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getHttp_url() {
		return http_url;
	}

	public void setHttp_url(String http_url) {
		this.http_url = http_url;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public String getUpload_user() {
		return upload_user;
	}

	public void setUpload_user(String upload_user) {
		this.upload_user = upload_user;
	}

	public String getUpload_time() {
		return upload_time;
	}

	public void setUpload_time(String upload_time) {
		this.upload_time = upload_time;
	}

	public long getDownload_count() {
		return download_count;
	}

	public void setDownload_count(long download_count) {
		this.download_count = download_count;
	}

	public String getFile_menu() {
		return file_menu;
	}

	public void setFile_menu(String file_menu) {
		this.file_menu = file_menu;
	}

	public String getFile_describe() {
		return file_describe;
	}

	public void setFile_describe(String file_describe) {
		this.file_describe = file_describe;
	}

	public String getPro_ids() {
		return pro_ids;
	}

	public void setPro_ids(String pro_ids) {
		this.pro_ids = pro_ids;
	}

	public String getFile_auth() {
		return file_auth;
	}

	public void setFile_auth(String file_auth) {
		this.file_auth = file_auth;
	}

	public String[] getPro_idsarr() {
		return pro_idsarr;
	}

	public void setPro_idsarr(String[] pro_idsarr) {
		this.pro_idsarr = pro_idsarr;
	}
	
}
