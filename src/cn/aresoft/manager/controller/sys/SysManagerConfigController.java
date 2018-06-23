package cn.aresoft.manager.controller.sys;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Mapping;
import com.puff.framework.annotation.Skip;
import com.puff.framework.utils.ListUtil;
import com.puff.framework.utils.PathUtil;
import com.puff.framework.utils.Security;
import com.puff.framework.utils.StringUtil;
import com.puff.web.fileupload.FileUpload;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.util.MD5Util;
import cn.aresoft.common.util.SHAUtils;
import cn.aresoft.ftp.model.FtpServer;
import cn.aresoft.ftp.service.FtpServerService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.interceptor.UserPermissionInterceptor;
import cn.aresoft.manager.model.sys.SysManagerConfig;
import cn.aresoft.manager.model.sys.Upload;
import cn.aresoft.manager.model.sys.UploadConfig;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysManagerConfigService;

@Controller(value = "/admin/sys/manager/config", scope = BeanScope.SINGLETON)
public class SysManagerConfigController extends CommonController {
	// 定义允许上传的文件扩展名
	private static HashMap<String, String> extMap = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;

		{
			put("image", "gif,jpg,jpeg,png,bmp,ico");
		}
	};
	@Inject
	private SysManagerConfigService sysManagerConfigService;

	@Inject
	private FtpServerService ftpServerService;

	public View index() {
		SysManagerConfig config = sysManagerConfigService.querySysManagerConfig();
		MyBeetlView view = new MyBeetlView("/admin/managerconfig/managerconfig.html");
		view.put("config", config);
		view.put("notRemLogin", StringUtil.allEmpty(config.getUsername(), config.getPwd()));
		return view;
	}

	@Skip.ONE(UserPermissionInterceptor.class)
	public View upload() {
		// 最大文件大小
		long maxSize = 10240;
		FileUpload file = PuffContext.getFile("imgFile");
		if (file == null)
			return ViewFactory.json(getError("请选择文件。"));
		String dirName = StringUtil.empty(PuffContext.getParameter("dir"), "image");
		// 检查文件大小
		if (file.getFile().length() > maxSize)
			return ViewFactory.json(getError("上传文件大小超过限制。"));

		if (!Arrays.<String> asList(extMap.get(dirName).split(",")).contains(file.getSuffixName()))
			return ViewFactory.json(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));

		if (!extMap.containsKey(dirName))
			return ViewFactory.json(getError("目录名不正确。"));

		// 文件保存目录URL
		String saveUrl = "/static/upload/";
		String savePath = PathUtil.getWebRootPath() + "/static/upload/";
		// 创建文件夹
		savePath += dirName + "/";
		saveUrl += dirName + "/";
		File saveDirFile = new File(savePath);
		if (!saveDirFile.exists())
			saveDirFile.mkdirs();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String ymd = sdf.format(new Date());
		savePath += ymd + "/";
		saveUrl += ymd + "/";
		File dirFile = new File(savePath);
		if (!dirFile.exists())
			dirFile.mkdirs();

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + file.getSuffixName();
		try {
			File uploadedFile = new File(savePath, newFileName);
			file.save(uploadedFile);
		} catch (Exception e) {
			return ViewFactory.json(getError("上传文件失败。"));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", 0);
		map.put("url", saveUrl + newFileName);
		return ViewFactory.json(map);
	}

	private Map<String, Object> getError(String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", 1);
		map.put("message", message);
		return map;
	}

	/**
	 * 修改基本信息
	 * 
	 * @return
	 */

	public View update() {
		SysManagerConfig config = PuffContext.getModel(SysManagerConfig.class);
		if (StringUtil.empty(PuffContext.getParameter("rem_pwd"))) {
			config.setUsername("");
			config.setPwd("");
		} else if (StringUtil.notBlank(config.getPwd())) {
			config.setPwd(Security.aesEncrypt(config.getPwd()));
		}
		if (StringUtil.notBlank(config.getSuper_user()) && StringUtil.notBlank(config.getSuper_pwd())) {
			//config.setSuper_pwd(Security.aesEncrypt(config.getSuper_pwd()));
			//update 2016-11-22在AES之前再进行一步MD5加密与sha加密start
			config.setSuper_pwd(Security.aesEncrypt(SHAUtils.SHA(MD5Util.GetMD5Code(config.getSuper_pwd()))));
			//update 2016-11-22在AES之前再进行一步加密与sha加密end
		}
		SysManagerConfig managerConfig = sysManagerConfigService.querySysManagerConfig();
		String upload_config = managerConfig.getUpload_config();
		config.setUpload_config(upload_config);
		sysManagerConfigService.update(config);
		return ViewFactory.json(RetMsg.success("设置成功！！！"));
	}

	/**
	 * 文件上传设置
	 * @return
	 */
	@Mapping("/upload/setting")
	public View uploadSetting() {
		MyBeetlView view = new MyBeetlView("/admin/managerconfig/upload_setting.html");
		/**
		 * 探测是否加载了FTP插件
		 */
		try {
			Class.forName("cn.aresoft.ftp.FtpClient");
			view.put("ftp_support", true);
			List<FtpServer> ftpServers = ftpServerService.queryList();
			view.put("ftpServers", ftpServers);
		} catch (Exception e) {
			view.put("ftp_support", false);
		}
		SysManagerConfig config = sysManagerConfigService.querySysManagerConfig();
		UploadConfig uploadConfig = config.getUploadConfig();
		Map<String, Upload> setting = uploadConfig.getUpload_setting();
		if (setting != null && !setting.isEmpty()) {
			for (Entry<String, Upload> entry : setting.entrySet()) {
				String fileTye = entry.getKey();
				Upload upload = entry.getValue();
				view.put(fileTye + "_suffix", ListUtil.list2Str(upload.getSuffix()));
				view.put(fileTye + "_ftp", ListUtil.list2Str(upload.getFtpServers()));
				view.put(fileTye + "_max", upload.getMaxSize());
			}
		} else {
			String[] arr = "image,music,media,file".split(",");
			for (String fileTye : arr) {
				view.put(fileTye + "_suffix", "");
				view.put(fileTye + "_ftp", "");
				view.put(fileTye + "_max", "");
			}
		}
		view.put("company_name", config.getCompany_name());
		view.put("uploadConfig", uploadConfig);
		return view;
	}

	/**
	 * 修改文件上传设置
	 * 
	 * @return
	 */
	@Mapping("/update/upload")
	public View updateUploadConfig() {
		UploadConfig config = PuffContext.getModel(UploadConfig.class);
		config.setAllow_upload(PuffContext.getIntParam("allow_upload", 0));
		String[] arr = "image,music,media,file".split(",");
		Map<String, Upload> map = new HashMap<String, Upload>();
		for (String fileType : arr) {
			List<String> suffix = PuffContext.getParameterList(fileType + "_suffix", ",");
			String maxSize = PuffContext.getParameter(fileType + "_max");
			List<String> ftpServers = PuffContext.getParameterList(fileType + "_ftp");
			Upload u = new Upload();
			u.setFileType(fileType);
			try {
				u.setMaxSize(Long.valueOf(maxSize));
			} catch (NumberFormatException e) {
				u.setMaxSize(0);
			}
			u.setSuffix(suffix);
			u.setFtpServers(ftpServers);
			map.put(fileType, u);
		}
		config.setUpload_setting(map);
		sysManagerConfigService.updateUploadConfig(config, PuffContext.getParameter("company_name"));
		return ViewFactory.json(RetMsg.success("文件上传信息设置成功！！！", "upload"));
	}

}
