package cn.aresoft.manager.service.sys;

import java.util.List;

import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysDept;
/**
 * 系统部门service
 * @author yyj
 *
 */
public interface SysDeptService extends BaseService<SysDept> {

	/**
	 * 新增部门拥有的角色
	 * @param id
	 * @param res
	 */
	void insertDeptRoles(String id, List<String> roles);

	/**
	 * 查询部门拥有的角色
	 * @param id
	 * @return
	 */
	List<String> queryDeptRoles(String id);

	/**
	 * 删除部门拥有的角色
	 * @param id
	 */
	void deleteDeptRoles(String id);

	/**
	 * 保存部门
	 * @param vo
	 * @param roles
	 */
	void saveDept(SysDept vo, List<String> roles);

	/**
	 * 删除部门
	 * @param ids
	 */
	void deleteDept(List<String> ids);

	/**
	 * 修改部门
	 * @param role
	 * @param res
	 */
	void updateDept(SysDept vo, List<String> roles);
	
	/**
	 * 查询不包含根节点（总公司或者集团）的所有部门或者子部门的节点
	 * @return
	 */
	List<SysDept> queryNoRootList();

}
