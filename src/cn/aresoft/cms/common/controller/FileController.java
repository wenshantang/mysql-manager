package cn.aresoft.cms.common.controller;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

@Controller(value = "/file", scope = BeanScope.SINGLETON)
public class FileController {

	/**
	 * 文件上传
	 * @return
	 */
	public View upload() {
		return ViewFactory.json("msg", "上传成功!");
	}

	/**
	 * 文件下载
	 * @return
	 */
	public View download() {
		return ViewFactory.file("");
	}

}
