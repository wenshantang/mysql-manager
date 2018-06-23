package cn.aresoft.manager.service.impl.sys;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysTask;
import cn.aresoft.manager.model.sys.WorkDay;
import cn.aresoft.manager.service.sys.SysTaskService;

@Bean(id = "sysTaskService")
public class SysTaskServiceImpl extends CenterServiceImpl<SysTask>implements SysTaskService {

	@Override
	public PageRecord<SysTask> paging(SysTask task, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(SysTask.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(task.getScheduleName())) {
			sb.append("and schedule_name like ? ");
			condition.add("%" + task.getScheduleName() + "%");
		}
		return paging(sb.toString(), condition, param);
	}

	@Override
	public WorkDay getWorkDay(String date) {
		return executor.query(WorkDay.class, "select day,flag from sys_workday where day = ? and flag ='1'", date);
	}

}