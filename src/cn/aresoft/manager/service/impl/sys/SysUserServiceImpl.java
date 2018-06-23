package cn.aresoft.manager.service.impl.sys;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Transaction;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.Record;
import com.puff.jdbc.core.SqlBuilder;
import com.puff.jdbc.executor.Atom;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.service.sys.SysUserService;

@Bean(id = "sysUserService")
public class SysUserServiceImpl extends CenterServiceImpl<SysUser>implements SysUserService {

	@Override
	public SysUser queryUserByLoginName(String login_name) {
		String sql = SqlBuilder.buildQuerySQL(getClazz()) + " where login_name = ?";
		SysUser user = query(sql, login_name);
		if (user == null) {
			user = query(SqlBuilder.buildQuerySQL(getClazz()) + " where phone = ?", login_name);
		}
		if (user == null) {
			user = query(SqlBuilder.buildQuerySQL(getClazz()) + " where email = ?", login_name);
		}
		if (user == null) {
			user = query(SqlBuilder.buildQuerySQL(getClazz()) + " where name = ?", login_name);
		}
		return user;
	}

	@Override
	public PageRecord<SysUser> paging(SysUser user, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(SysUser.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(user.getLogin_name())) {
			sb.append("and login_name like ? ");
			condition.add("%" + user.getLogin_name() + "%");
		}
		if (StringUtil.notEmpty(user.getName())) {
			sb.append("and name like ? ");
			condition.add("%" + user.getName() + "%");
		}
		return paging(sb.toString(), condition, param);
	}

	@Override
	public void deleteUserRole(String id) {
		executor.delete("delete from sys_user_role where user_id=?", id);
	}

	@Override
	public void addUserRole(String userId, List<String> roles) {
		for (String roleId : roles) {
			insert("insert into sys_user_role (user_id, role_id) values (?,?)", userId, roleId);
		}
	}

	@Override
	public List<String> queryUserRole(String userId) {
		return executor.querySimpleList("select role_id from sys_user_role where user_id=? ", userId);
	}

	@Override
	public List<String> queryUserResource(String userId) {
		List<Record> listRecord = executor.queryListRecord(
				"select url,dep_url from sys_resource s,sys_user_role u,SYS_ROLE_RESOURCE r where u.user_id=? and u.role_id = r.role_id and r.resource_id=s.id ", userId);
		List<String> userRes = new ArrayList<String>();
		if (listRecord != null) {
			for (Record record : listRecord) {
				userRes.add(record.getString("url").trim());
				String dep_url = record.getString("dep_url");
				if (StringUtil.notEmpty(dep_url)) {
					for (String url : dep_url.split(",")) {
						userRes.add(url.trim());
					}
				}
			}
		}
		return userRes;
	}

	@Override
	public void updateRole(final SysUser user, final List<String> roles) {
		executor.transaction(new Atom() {
			@Override
			public boolean execute() throws Exception {
				executor.updateBySql("update sys_user t set t.login_name=?,t.name=?,t.phone=?,t.email=?,t.status=? where t.id=? ", user.getLogin_name(), user.getName(),
						user.getPhone(), user.getEmail(), user.getStatus(), user.getId());
				deleteUserRole(user.getId());
				if (roles != null && roles.size() > 0) {
					addUserRole(user.getId(), roles);
				}
				return true;
			}
		});
	}

	@Override
	@Transaction
	public void deleteUser(final List<String> ids) {
		deleteIn("id", ids);
		deleteInSql("delete from sys_user_role where user_id ", ids);
	}

	@Override
	public void updatePwd(String id, String new_pwd) {
		executor.updateBySql("update sys_user t set t.pwd=? where t.id=?", new_pwd, id);
	}

}
