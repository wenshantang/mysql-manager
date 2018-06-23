package cn.aresoft.manager.service.sys;

import java.util.List;

import cn.aresoft.manager.model.sys.SysRole;
import cn.aresoft.common.service.BaseService;

public interface SysRoleService extends BaseService<SysRole> {

	void insertRoleRes(String id, List<String> res);

	List<String> queryRoleRes(String id);

	void deleteRoleRes(String id);

	void saveRole(SysRole role, List<String> res);

	void deleteRole(List<String> ids);

	void updateRole(SysRole role, List<String> res);

}
