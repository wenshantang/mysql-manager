package cn.aresoft.common.service.impl.sys;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.cust.Reservation;
import cn.aresoft.common.model.sys.LogCount;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.common.service.sys.LogCountService;


/*
 * 日志发布统计实现
 */
@Bean(id = "logCountService")
public class LogCountServiceImpl extends CenterServiceImpl<LogCount> implements LogCountService{

	public PageRecord<LogCount> paging(LogCount vo,CommonParam param){
		String sql="select count(*) logCount,t.logtime, t.user_id,t.loginname "
				+" from (select l.user_id,substr ( log_time, 0, 10 ) logtime,u.login_name loginname "
				+" from sys_log l,sys_user u "
				+" where l.user_id=u.id "
				+" and req_url like '/admin/cms/article/audit%' "
				+" group by user_id,u.login_name,l.log_time "
				+" order by user_id) t where 1=1 ";
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		List<Object> condition = new ArrayList<Object>();
		
		if (StringUtil.notEmpty(vo.getUser_id())) {
			sb.append(" and t.user_id = ? ");
			condition.add(vo.getUser_id());
		}
		if (StringUtil.notEmpty(vo.getLogtime())) {
			sb.append(" and t.logtime like ? ");
			condition.add("%" +vo.getLogtime()+ "%");
		}
		if (StringUtil.notEmpty(vo.getLoginname())) {
			sb.append(" and t.loginname = ? ");
			condition.add(vo.getLoginname());
		}
		if(StringUtil.notBlank(vo.getBegin_time())){
			sb.append(" and t.logtime >= ? ");
			condition.add(vo.getBegin_time());
		}
		if(StringUtil.notBlank(vo.getEnd_time())){
			sb.append(" and t.logtime <= ? ");
			condition.add(vo.getEnd_time());
		}
		
		sb.append(" group by t.logtime,t.user_id , t.loginname ");
		
		if(StringUtil.blank(param.getSort())){
			sb.append(" order by t.user_id ");
		}

		return paging(sb.toString(), condition, param);
		
		
	}


	

}
