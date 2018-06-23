package cn.aresoft.manager.service.impl.sys;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.DateTime;

import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysTaskLog;
import cn.aresoft.manager.service.sys.SysTaskLogService;

@Bean(id = "sysTaskLogService")
public class SysTaskLogServiceImpl extends CenterServiceImpl<SysTaskLog>implements SysTaskLogService {

	@Override
	public void updateTaskLog(SysTaskLog taskLog) {
		String sql = "update sys_tasklog set status=?,endtime=?,memo=? where logid=? ";
		executor.updateBySql(sql, taskLog.getStatus(), DateTime.currentTimeStamp(), taskLog.getMemo(), taskLog.getLogId());
	}

}