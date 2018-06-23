package cn.aresoft.manager.service.impl.sys;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysLog;
import cn.aresoft.manager.service.sys.SysLogService;
/***
 * 系统日志service实现
 * @author yyj
 *
 */
@Bean(id="sysLogService")
public class SysLogServiceImpl extends CenterServiceImpl<SysLog> implements SysLogService {
	
	@Override
	public PageRecord<SysLog> paging(SysLog vo, CommonParam param) {
		/*String sql = SqlBuilder.buildQuerySQL(SysLog.class);*/
		String sql="select l.*,u.login_name,r.name rname,(select name from sys_resource where id=r.parent_id) rparent_name "
				+ " from sys_log l,sys_user u,sys_resource r"
				+ " where l.user_id=u.id "
				+ " and l.req_url=r.url ";
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(vo.getUser_id())) {
			sb.append("and l.user_id = ? ");
			condition.add(vo.getUser_id());
		}
		if (StringUtil.notEmpty(vo.getLogin_name())) {
			sb.append("and u.login_name like ? ");
			condition.add("%" +vo.getLogin_name()+ "%");
		}
		if (StringUtil.notEmpty(vo.getReq_url())) {
			sb.append("and l.req_url=? ");
			condition.add(vo.getReq_url());
		}
		if(StringUtil.notBlank(vo.getBegin_time())){
			sb.append("and l.log_time >= ? ");
			condition.add(vo.getBegin_time());
		}
		if(StringUtil.notBlank(vo.getEnd_time())){
			sb.append("and l.log_time <= ? ");
			condition.add(vo.getEnd_time());
		}
		//添加默认排序
		if(StringUtil.blank(param.getSort())){
			sb.append(" order by  log_time  desc ");
		}
		return paging(sb.toString(), condition, param);
	}
}
