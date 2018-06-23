package cn.aresoft.cms.common.model.config;

import java.io.Serializable;
import java.util.List;

public class Upload implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4047452114296137477L;

	private String fileType;// 文件类型

	private List<String> suffix;// 后缀

	private long maxSize;// 最大

	private List<String> ftpServers;// ftp服务器;

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public List<String> getSuffix() {
		return suffix;
	}

	public void setSuffix(List<String> suffix) {
		this.suffix = suffix;
	}

	public long getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	public List<String> getFtpServers() {
		return ftpServers;
	}

	public void setFtpServers(List<String> ftpServers) {
		this.ftpServers = ftpServers;
	}

}
