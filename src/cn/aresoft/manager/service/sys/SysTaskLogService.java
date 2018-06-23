package cn.aresoft.manager.service.sys;

import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysTaskLog;

public interface SysTaskLogService extends BaseService<SysTaskLog> {

	void updateTaskLog(SysTaskLog taskLog);

}
