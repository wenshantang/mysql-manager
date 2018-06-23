package cn.aresoft.cms.manager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Disable;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Mapping;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.ListUtil;
import com.puff.jdbc.core.Record;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsSiteConfigCache;
import cn.aresoft.cms.common.model.config.BasicConfig;
import cn.aresoft.cms.common.model.config.CmsSiteConfig;
import cn.aresoft.cms.common.model.config.EmailConfig;
import cn.aresoft.cms.common.model.config.LoginConfig;
import cn.aresoft.cms.common.model.config.RegisterConfig;
import cn.aresoft.cms.common.model.config.SmsConfig;
import cn.aresoft.cms.common.model.config.Upload;
import cn.aresoft.cms.common.model.config.UploadConfig;
import cn.aresoft.cms.common.service.CmsSiteConfigService;
import cn.aresoft.cms.manager.validator.CmsSiteConfigValidator;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;

@Controller(value = "/admin/cms/siteconfig", scope = BeanScope.SINGLETON)
public class CmsSiteConfigController extends CommonController {

	@Inject
	private CmsSiteConfigService cmsSiteConfigService;

	@Inject
	private CmsSiteConfigCache cmsSiteConfigCache;

	private static final String siteId = "888888888";

	public View index() {
		CmsSiteConfig config = cmsSiteConfigService.query(siteId);
		if (config == null) {
			config = new CmsSiteConfig();
			config.setSite_id(siteId);
			config.setBasic_config(new BasicConfig());
			config.setEmail_config(new EmailConfig());
			config.setLogin_config(new LoginConfig());
			config.setRegister_config(new RegisterConfig());
			config.setSms_config(new SmsConfig());
			config.setUpload_config(new UploadConfig());
			cmsSiteConfigService.insert(config);
		}
		String target = PuffContext.getParameter("target", "basic");
		MyBeetlView view = new MyBeetlView("/cms/site/site_info.html");
		view.put("site", config).put("target", target);
		/**
		 * 探测是否加载了FTP插件
		 */
		try {
			Class.forName("cn.aresoft.ftp.FtpClient");
			view.put("ftp_support", true);
			List<Record> ftpServers = cmsSiteConfigService.queryFtpServer();
			view.put("ftpServers", ftpServers);
		} catch (Exception e) {
			view.put("ftp_support", false);
		}

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
		}

		return view;
	}

	/**
	 * 修改基本信息
	 * 
	 * @return
	 */
	@Mapping("/update/basic")
	@Validate(CmsSiteConfigValidator.class)
	public View updateBasicConfig() {
		CmsSiteConfig site = cmsSiteConfigService.query(siteId);
		BasicConfig config = PuffContext.getModel(BasicConfig.class);
		if (site != null) {
			site.setBasic_config(config);
			cmsSiteConfigService.update(site);
			cmsSiteConfigCache.update(siteId, site);
		} else {
			site = new CmsSiteConfig();
			site.setSite_id(siteId);
			site.setBasic_config(config);
			cmsSiteConfigService.insert(site);
			cmsSiteConfigCache.cache(siteId, site);
		}
		return ViewFactory.json(RetMsg.success("基本信息设置成功！！！", "basic"));
	}

	/**
	 * 修改邮件设置
	 * 
	 * @return
	 */
	@Mapping("/update/email")
	@Validate(CmsSiteConfigValidator.class)
	public View updateEmailConfig() {
		CmsSiteConfig site = cmsSiteConfigService.query(siteId);
		EmailConfig config = PuffContext.getModel(EmailConfig.class);
		if (site != null) {
			site.setEmail_config(config);
			cmsSiteConfigService.update(site);
			cmsSiteConfigCache.update(siteId, site);
		} else {
			site = new CmsSiteConfig();
			site.setSite_id(siteId);
			site.setEmail_config(config);
			cmsSiteConfigService.insert(site);
			cmsSiteConfigCache.cache(siteId, site);
		}
		return ViewFactory.json(RetMsg.success("邮件信息设置成功！！！", "email"));
	}

	/**
	 * 修改短信设置
	 * 
	 * @return
	 */
	@Mapping("/update/sms")
	@Validate(CmsSiteConfigValidator.class)
	public View updateSmsConfig() {
		CmsSiteConfig site = cmsSiteConfigService.query(siteId);
		SmsConfig config = PuffContext.getModel(SmsConfig.class);
		if (site != null) {
			site.setSms_config(config);
			cmsSiteConfigService.update(site);
			cmsSiteConfigCache.update(siteId, site);
		} else {
			site = new CmsSiteConfig();
			site.setSite_id(siteId);
			site.setSms_config(config);
			cmsSiteConfigService.insert(site);
			cmsSiteConfigCache.cache(siteId, site);
		}
		return ViewFactory.json(RetMsg.success("短信信息设置成功！！！", "sms"));
	}

	/**
	 * 修改登录设置
	 * 
	 * @return
	 */
	@Mapping("/update/login")
	@Validate(CmsSiteConfigValidator.class)
	public View updateLoginConfig() {
		CmsSiteConfig site = cmsSiteConfigService.query(siteId);
		LoginConfig config = PuffContext.getModel(LoginConfig.class);
		if (site != null) {
			site.setLogin_config(config);
			cmsSiteConfigService.update(site);
			cmsSiteConfigCache.update(siteId, site);
		} else {
			site = new CmsSiteConfig();
			site.setSite_id(siteId);
			site.setLogin_config(config);
			cmsSiteConfigService.insert(site);
			cmsSiteConfigCache.cache(siteId, site);
		}
		return ViewFactory.json(RetMsg.success("登录信息设置成功！！！", "login"));
	}

	/**
	 * 修改注册设置
	 * 
	 * @return
	 */
	@Mapping("/update/register")
	@Validate(CmsSiteConfigValidator.class)
	public View updateRegisterConfig() {
		CmsSiteConfig site = cmsSiteConfigService.query(siteId);
		RegisterConfig config = PuffContext.getModel(RegisterConfig.class);
		if (site != null) {
			site.setRegister_config(config);
			cmsSiteConfigService.update(site);
			cmsSiteConfigCache.update(siteId, site);
		} else {
			site = new CmsSiteConfig();
			site.setSite_id(siteId);
			site.setRegister_config(config);
			cmsSiteConfigService.insert(site);
			cmsSiteConfigCache.cache(siteId, site);
		}
		return ViewFactory.json(RetMsg.success("注册信息设置成功！！！", "register"));
	}

	/**
	 * 修改文件上传设置
	 * 
	 * @return
	 */
	@Disable
	@Mapping("/update/upload")
	@Validate(CmsSiteConfigValidator.class)
	public View updateUploadConfig() {
		CmsSiteConfig site = cmsSiteConfigService.query(siteId);
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
		if (site != null) {
			site.setUpload_config(config);
			cmsSiteConfigService.update(site);
			cmsSiteConfigCache.update(siteId, site);
		} else {
			site = new CmsSiteConfig();
			site.setSite_id(siteId);
			site.setUpload_config(config);
			cmsSiteConfigService.insert(site);
			cmsSiteConfigCache.cache(siteId, site);
		}
		return ViewFactory.json(RetMsg.success("文件上传信息设置成功！！！", "upload"));
	}

}
