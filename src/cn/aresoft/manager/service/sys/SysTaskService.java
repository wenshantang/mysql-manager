package cn.aresoft.manager.service.sys;

import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysTask;
import cn.aresoft.manager.model.sys.WorkDay;

public interface SysTaskService extends BaseService<SysTask> {

	WorkDay getWorkDay(String date);

}
