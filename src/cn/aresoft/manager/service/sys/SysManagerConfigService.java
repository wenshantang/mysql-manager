package cn.aresoft.manager.service.sys;

import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysManagerConfig;
import cn.aresoft.manager.model.sys.UploadConfig;

public interface SysManagerConfigService extends BaseService<SysManagerConfig> {

	public SysManagerConfig querySysManagerConfig();

	/**
	 * 更新上传设置
	 * @param config
	 */
	public void updateUploadConfig(UploadConfig config, String company_name);

}
