package cn.aresoft.manager.service.sys;

import java.util.List;

import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysUserJob;
/**
 * 人员岗位/小组service
 * @author yyj
 *
 */
public interface SysUserJobService extends BaseService<SysUserJob> {

	/**
	 * 设定人员的部门岗位或者小组岗位
	 * @param users 需要设定的人员id集合
	 * @param job_id 需要分配的部门岗位id或者小组岗位Id
	 * @param jobType 岗位类型[1:部门,2;组,3:特殊小组]
	 */
	void setUsersJobOrTeam(List<String> users,String job_id,String jobType);

	/**
	 * 给选定人员批量设定岗位
	 * @param userId 用户id
	 * @param jobids 要分配的部门岗位id或者小组岗位Id集合
	 * @param jobType 岗位类型[1:部门,2;组,3:特殊小组]
	 */
	void addUserDeptJobs(String userId,List<String> jobids,String jobType);
	
	/**
	 * 查询用户分配的岗位
	 * @param userId 用户id
	 * @param deptType 部门类型[1:部门,2;组,3:特殊小组]
	 * @return
	 */
	List<SysUserJob> queryUserJobs(String userId,String deptType);
}
