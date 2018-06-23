package cn.aresoft.manager.service.sys;

import java.util.List;

import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.common.service.BaseService;

public interface SysUserService extends BaseService<SysUser> {

	SysUser queryUserByLoginName(String login_name);

	void deleteUserRole(String id);

	void addUserRole(String id, List<String> roles);

	List<String> queryUserRole(String userId);

	List<String> queryUserResource(String userId);

	void updateRole(SysUser user, List<String> roles);

	void deleteUser(List<String> ids);

	void updatePwd(String id, String new_pwd);

}
