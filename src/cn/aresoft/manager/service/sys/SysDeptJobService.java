package cn.aresoft.manager.service.sys;

import java.util.List;

import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysDeptJob;

import com.puff.jdbc.core.Record;
/**
 * 系统部门岗位service
 * @author yyj
 *
 */
public interface SysDeptJobService extends BaseService<SysDeptJob> {

	/**
	 * 新增岗位拥有的角色
	 * @param id
	 * @param res
	 */
	void insertDeptJobRoles(String id, List<String> roles);

	/**
	 * 查询部门岗位拥有的角色
	 * @param id
	 * @return
	 */
	List<String> queryDeptJobRoles(String id);

	/**
	 * 删除部门岗位拥有的角色
	 * @param id
	 */
	void deleteDeptJobRoles(String id);

	/**
	 * 保存部门岗位
	 * @param vo
	 * @param roles
	 */
	void saveDeptJob(SysDeptJob vo, List<String> roles);

	/**
	 * 删除部门岗位
	 * @param ids
	 */
	void deleteDeptJob(List<String> ids);

	/**
	 * 修改部门岗位
	 * @param role
	 * @param res
	 */
	void updateDept(SysDeptJob vo, List<String> roles);
	
	/**
	 * 查询当前部门下所有的岗位
	 * @param deptId
	 * @return
	 */
	List<SysDeptJob> queryCurrentDeptJobs(String deptId);
	
	/**
	 * 查询组织架构以及对应的岗位
	 * @param deptId 部门id
	 * @param deptType  部门类型[1:部门,2;组,3:特殊小组]
	 * @return
	 */
	List<Record> queryDeptJobOrg(String deptId,String deptType);
	
	/**
	 * 查询当前组织或者部门下的部门与岗位下拉option列表
	 * @param deptId 部门id
	 * @param deptType 部门类型[1:部门,2;组,3:特殊小组]
	 * @return
	 */
	List<Record> queryDeptJobOptionList(String deptId,String deptType);

}
