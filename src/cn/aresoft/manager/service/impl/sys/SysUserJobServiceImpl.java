package cn.aresoft.manager.service.impl.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysUserJob;
import cn.aresoft.manager.service.sys.SysUserJobService;
import cn.aresoft.manager.utils.DateUtils;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Transaction;
import com.puff.framework.utils.StringUtil;
/**
 * 人员岗位/小组service实现
 * @author yyj
 *
 */
@Bean(id = "sysUserJobService")
public class SysUserJobServiceImpl extends CenterServiceImpl<SysUserJob>implements SysUserJobService {

	@Transaction
	@Override
	public void setUsersJobOrTeam(List<String> users,String job_id,String jobType) {
		if (users == null || users.size() == 0)
			return;
		if(StringUtil.blank(job_id)){
			return;
		}
		String current_time=DateUtils.date2Str6(new Date(), 6);
		String sql = "insert into sys_user_job(user_id, job_id,job_type,create_time) values (?,?,'"+jobType+"','"+current_time+"') ";
		for (String user_id :users) {
			String deleteSql="delete from sys_user_job where user_id=? and job_id=? ";
			executor.delete(deleteSql, user_id,job_id);
			insert(sql, user_id, job_id);
		}
		
	}

	@Transaction
	@Override
	public void addUserDeptJobs(String userId, List<String> jobids,String jobType) {
		if(StringUtil.blank(userId)){
			return;
		}
		String deleteSql="delete from sys_user_job where user_id=? and job_type=? ";
		executor.delete(deleteSql, userId,jobType);
        if (jobids == null || jobids.size() == 0){
        	return;
		}else{
			String current_time=DateUtils.date2Str6(new Date(), 6);
			String sql = "insert into sys_user_job(user_id, job_id,job_type,create_time) values (?,?,'"+jobType+"','"+current_time+"') ";
			for (String job_id :jobids) {
				insert(sql, userId, job_id);
			}
		}
	
		
	}

	@Override
	public List<SysUserJob> queryUserJobs(String userId,String deptType) {
		List<SysUserJob> jobList=new ArrayList<SysUserJob>();
		if(StringUtil.blank(userId)){
			return jobList;
		}
		StringBuilder sql=new StringBuilder("select uj.*,dj.job_name from sys_user_job uj,sys_dept_job dj,sys_dept sd ");
		sql.append(" where uj.user_id= ? ");
		sql.append(" and uj.job_id=dj.id ");
		sql.append(" and dj.dept_id=sd.id ");
		sql.append(" and sd.dept_type=? ");
		return queryList(sql.toString(), userId,deptType);
	}


	
}
