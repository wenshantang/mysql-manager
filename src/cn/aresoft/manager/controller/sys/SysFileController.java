package cn.aresoft.manager.controller.sys;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Skip;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.DateTime;
import com.puff.framework.utils.FileUtil;
import com.puff.framework.utils.IdentityUtil;
import com.puff.framework.utils.ListUtil;
import com.puff.framework.utils.PathUtil;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.Record;
import com.puff.log.Log;
import com.puff.log.LogFactory;
import com.puff.web.fileupload.FileUpload;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.service.record.RecordService;
import cn.aresoft.ftp.FtpClient;
import cn.aresoft.ftp.SFtpClient;
import cn.aresoft.ftp.model.FtpServer;
import cn.aresoft.ftp.service.FtpServerService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.interceptor.UserPermissionInterceptor;
import cn.aresoft.manager.model.sys.SysFile;
import cn.aresoft.manager.model.sys.SysManagerConfig;
import cn.aresoft.manager.model.sys.Upload;
import cn.aresoft.manager.model.sys.UploadConfig;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysFileService;
import cn.aresoft.manager.service.sys.SysManagerConfigService;
import cn.aresoft.manager.validator.SysFileValidate;

/**
 * 文件管理
 * 
 * @author dongchao
 *
 */

@Controller(value = "/admin/sys/file", scope = BeanScope.SINGLETON)
public class SysFileController extends CommonController {

	private static final Log log = LogFactory.get();

	@Inject
	private SysFileService sysFileService;

	@Inject
	private FtpServerService ftpServerService;

	@Inject
	private SysManagerConfigService sysManagerConfigService;
	
	@Inject
	private  RecordService recordService;

	public View index() {
		return new MyBeetlView("/admin/file/file_list.html");
	}

	public View json() throws Exception {
		PageRecord<SysFile> data = sysFileService.paging(PuffContext.getModel(SysFile.class), getCommonParam());
		Collection<SysFile> collection = data.getDataList();
		if (!ListUtil.empty(collection)) {
			for (SysFile sysFile : collection) {
				//add 远程文件获取不到文件大小时获取file_size异常修复start
				String fileSize=sysFile.getFile_size();
				if(StringUtil.blank(fileSize)){
					sysFile.setFile_size("--");
				}else{
					sysFile.setFile_size(convertFileSize(Long.valueOf(fileSize)));
				}
				//add 远程文件获取不到文件大小时获取file_size异常修复end
				//sysFile.setFile_size(convertFileSize(Long.valueOf(fileSize)));
			}
		}
		return ViewFactory.json(data);
	}

	@Skip.ONE(UserPermissionInterceptor.class)
	public View list() {

		String dirName = PuffContext.getParameter("dir");
		int page = PuffContext.getIntParam("page", 1);
		int rows = PuffContext.getIntParam("rows", 20);
		String order = StringUtil.empty(PuffContext.getParameter("order"), "time");

		List<SysFile> files = sysFileService.queryFile(dirName, page, rows, order);

		// 遍历目录取的文件信息
		List<Hashtable<String, Object>> fileList = new ArrayList<Hashtable<String, Object>>();
		if (files != null) {
			for (SysFile file : files) {
				Hashtable<String, Object> hash = new Hashtable<String, Object>();
				String fileName = file.getName();
				String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
				hash.put("is_dir", false);
				hash.put("has_file", false);
				hash.put("filesize", file.getFile_size());
				hash.put("is_photo", "image".equals(file.getType()));
				hash.put("filetype", fileExt);
				hash.put("filename", fileName);
				hash.put("fileUrl", file.getHttp_url());
				hash.put("datetime", file.getUpload_time());
				fileList.add(hash);
			}
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("moveup_dir_path", "");
		result.put("current_dir_path", "");
		result.put("current_url", "xxxxx");
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
		return ViewFactory.json(result);
	}

	private Map<String, Object> getError(String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", 1);
		map.put("message", message);
		return map;
	}

	public View upload() {
		FileUpload file = PuffContext.getFile("imgFile");
		if (file == null)
			return ViewFactory.json(getError("请选择文件。"));
		SysManagerConfig managerConfig = sysManagerConfigService.querySysManagerConfig();
		UploadConfig uploadConfig = managerConfig.getUploadConfig();
		String dirName = PuffContext.getParameter("dir");
		if (StringUtil.empty(dirName)) {
			String suffixName = file.getSuffixName();
			Map<String, Upload> upload_setting = uploadConfig.getUpload_setting();
			if (upload_setting != null) {
				for (Entry<String, Upload> entry : upload_setting.entrySet()) {
					Upload upload = entry.getValue();
					if (upload != null) {
						List<String> suffix = upload.getSuffix();
						if (!ListUtil.empty(suffix)) {
							if (suffix.contains(suffixName)) {
								dirName = entry.getKey();
								break;
							}
						}
					}
				}
			}
		}
		if (!uploadConfig.getUpload_setting().containsKey(dirName)) {
			return ViewFactory.json(getError("不允许上传该类型文件!"));
		}
		Upload upload = uploadConfig.getUpload_setting().get(dirName);
		// 最大文件大小
		Long maxSize = upload.getMaxSize();
		// 检查文件大小
		if (maxSize != null && maxSize != 0L && file.getFile().length() > maxSize * 1024)
			return ViewFactory.json(getError("上传文件大小超过限制。"));

		if (!upload.getSuffix().contains(file.getSuffixName()))
			return ViewFactory.json(getError("上传文件扩展名是不允许的扩展名。\n只允许" + ListUtil.list2Str(upload.getSuffix()) + "格式。"));

		String savePath = PathUtil.getWebRootPath() + "/" + uploadConfig.getSave_dir();
		String filePath = "";
		// 文件保存方式 1/yyyMMdd/file 2yyyyMM/dd/file
		String save_type = uploadConfig.getSave_type();
		if ("1".equals(save_type)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String ymd = sdf.format(new Date());
			filePath = "/" + ymd;
		} else if ("2".equals(save_type)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM/dd");
			String ymd = sdf.format(new Date());
			filePath = "/" + ymd;
		}

		String ftpFileUrl = (uploadConfig.getSave_dir() + "/" + dirName + "/" + filePath).replace("//", "/");
		String ftpFilePath = (uploadConfig.getSave_dir() + "/" + dirName + "/" + filePath).replace("//", "/").replace("/", String.valueOf(File.separatorChar));

		String fullPath = savePath + "/" + dirName + "/" + filePath;
		File saveDirFile = new File(fullPath);
		if (!saveDirFile.exists())
			saveDirFile.mkdirs();
		String newFileName = IdentityUtil.uuid16() + "." + file.getSuffixName();
		fullPath = (fullPath + "/" + newFileName).replace("//", "/").replace("/", String.valueOf(File.separatorChar));
		String http_url = "";
		try {
			File uploadedFile = new File(fullPath);
			file.save(uploadedFile);
			List<String> servers = upload.getFtpServers();
			if (!ListUtil.empty(servers)) {
				for (String ftpId : servers) {
					FtpServer ftpServer = ftpServerService.query(ftpId);
					//判断服务器类型[0:ftp服务器；1：sftp服务器]
					if("1".equals(ftpServer.getServer_type())){
						SFtpClient sFtpClient=new SFtpClient(ftpServer);
						sFtpClient.upload(uploadedFile, ftpFilePath, newFileName);
					}else{
						FtpClient client = new FtpClient(ftpServer);
						client.upload(uploadedFile, ftpFilePath, newFileName);
					}
					if (StringUtil.empty(http_url)) {
						http_url = ftpServer.getVisit_url() + ("/" + ftpFileUrl + "/" + newFileName).replace("//", "/");
					}
				}
			}
		} catch (Exception e) {
			log.error("上传文件失败！！！", e);
			return ViewFactory.json(getError("上传文件失败。"));
		}
		if (StringUtil.empty(http_url)) {
			http_url = ("/" + ftpFileUrl + "/" + newFileName).replace("//", "/");
		}
		SysFile f = new SysFile();
		f.setHttp_url(http_url);
		f.setName(file.getFileName());
		f.setPath("/" + ftpFileUrl);
		f.setFile_size(file.getSize() + "");
		f.setUpload_time(DateTime.currentTimeStamp());
		f.setUpload_user(getSysUser().getName());
		f.setType(dirName);
		f.setSave_name(newFileName);
		sysFileService.insert(f);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", 0);
//		map.put("url", http_url);
		//-----update by lxy ---
//		map.put("fid", f.getId());
		map.put("name", file.getFileName());
		map.put("save_name", newFileName);
		map.put("type", dirName);
		map.put("path", "/" + ftpFileUrl);
		map.put("http_url", http_url);
		map.put("file_size", file.getSize() + "");
		map.put("url", http_url);
		map.put("id", f.getId());
		//-----end -------
		return ViewFactory.json(map);
	}

	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		String webRootPath = PathUtil.getWebRootPath();
		for (String id : ids) {
			SysFile sysFile = sysFileService.query(id);
			String path = sysFile.getPath();
			String file = webRootPath + ("/" + path + "/" + sysFile.getSave_name()).replace("//", "/").replace("/", String.valueOf(File.separatorChar));
			FileUtil.deleteQuietly(new File(file));
		}
		sysFileService.deleteIn(ids);
		return ViewFactory.json(RetMsg.success("删除成功！！！"));
	}

	/**
	 * 新增页面
	 * @return
	 */
	public View add() {
		MyBeetlView view =new  MyBeetlView("/admin/file/file_add.html");
		//查询出已经有的菜单栏目分类
		List<SysFile> flist = sysFileService.queryFileMemu();
		view.put("flist", flist);
		//查询产品表
		List<Record> plist = recordService.queryProInfoList("");
		view.put("plist", plist);
		return view;
	}
	
	/**
	 * 新增文件资源
	 * @return
	 */
	@Validate(SysFileValidate.class)
	public View insert() {
		SysFile model = PuffContext.getModel(SysFile.class);
		String[] pro_idsArr = PuffContext.getParameterValues("pro_idsarr");
		String pro_ids = "";
		//处理产品id
		if(pro_idsArr != null && pro_idsArr.length>0){
			for(int i=0;i<pro_idsArr.length;i++){
				pro_ids += pro_idsArr[i]+"|";
			}
			if(StringUtil.notEmpty(pro_ids)){
				pro_ids = pro_ids.substring(0,pro_ids.length()-1);
			}
		}
		model.setPro_ids(pro_ids);
		model.setUpload_time(DateTime.currentTimeStamp());
		model.setUpload_user(getSysUser().getName());
		sysFileService.insert(model);
		return ViewFactory.json(RetMsg.success("新增成功！"));
	}
	
	
	/**
	 * 编辑文件资源页面
	 * @param id
	 * @return
	 */
	public View edit(String id) {
		SysFile model = sysFileService.query(id);
		if(model.getPro_ids() == null){
			model.setPro_ids("");
		}
		MyBeetlView view =new  MyBeetlView("/admin/file/file_edit.html", "ap", model);
		//查询出已经有的菜单栏目分类
		List<SysFile> flist = sysFileService.queryFileMemu();
		view.put("flist", flist);
		//查询产品表
		List<Record> plist = recordService.queryProInfoList("");
		view.put("plist", plist);
		return view;
	}
	/**
	 * 修改文件资源
	 * @return
	 */
	@Validate(SysFileValidate.class)
	public View update() {
		SysFile model = PuffContext.getModel(SysFile.class);
		String[] pro_idsArr = PuffContext.getParameterValues("pro_idsarr");
		String pro_ids = "";
		//处理产品id
		if(pro_idsArr != null && pro_idsArr.length>0){
			for(int i=0;i<pro_idsArr.length;i++){
				pro_ids += pro_idsArr[i]+"|";
			}
			if(StringUtil.notEmpty(pro_ids)){
				pro_ids = pro_ids.substring(0,pro_ids.length()-1);
			}
		}
		model.setPro_ids(pro_ids);
		model.setUpload_time(DateTime.currentTimeStamp());
		model.setUpload_user(getSysUser().getName());
		sysFileService.update(model);
		return ViewFactory.json(RetMsg.success("更新成功！"));
	}

	public String convertFileSize(long size) {
		long kb = 1000;
		long mb = kb * 1000;
		long gb = mb * 1000;
		if (size >= gb) {
			return String.format("%.1f GB", (float) size / gb);
		} else if (size >= mb) {
			float f = (float) size / mb;
			return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
		} else if (size >= kb) {
			float f = (float) size / kb;
			return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
		} else
			return String.format("%d B", size);
	}

}