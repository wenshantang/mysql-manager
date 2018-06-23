package cn.aresoft.manager.service.impl.sys;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.Security;
import com.puff.framework.utils.StringUtil;

import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysManagerConfig;
import cn.aresoft.manager.model.sys.UploadConfig;
import cn.aresoft.manager.service.sys.SysManagerConfigService;

@Bean(id = "sysManagerConfigService")
public class SysManagerConfigServiceImpl extends CenterServiceImpl<SysManagerConfig> implements SysManagerConfigService {

	@Override
	public SysManagerConfig querySysManagerConfig() {
		SysManagerConfig config = executor.query(getClazz(), buildQuerySql());
		if (config == null) {
			config = new SysManagerConfig();
			config.setCompany_name("Aresoft");
			config.setIcon("/resource-injar/image?t=/favicon.ico");
			config.setIndex_url("/admin/center");
			config.setLogin_filter("1");
			config.setLogo("/resource-injar/image?t=logo.png");
			config.setUploadConfig(new UploadConfig());
			executor.save(config);
		}
		if (StringUtil.notBlank(config.getPwd())) {
			config.setPwd(Security.aesDecrypt(config.getPwd()));
		}
		return config;
	}

	@Override
	public void updateUploadConfig(UploadConfig config, String company_name) {
		executor.updateBySql("update sys_managerconfig set upload_config=? where company_name=? ", config.toJson(), company_name);
	}
}