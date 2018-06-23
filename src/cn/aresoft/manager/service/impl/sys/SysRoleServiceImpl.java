package cn.aresoft.manager.service.impl.sys;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;
import com.puff.jdbc.executor.Atom;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.manager.model.sys.SysRole;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.service.sys.SysRoleService;

@Bean(id = "sysRoleService")
public class SysRoleServiceImpl extends CenterServiceImpl<SysRole>implements SysRoleService {

	@Override
	public PageRecord<SysRole> paging(SysRole role, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(SysRole.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(role.getCode())) {
			sb.append("and code =? ");
			condition.add(role.getCode());
		}
		if (StringUtil.notEmpty(role.getName())) {
			sb.append("and name like ? ");
			condition.add("%" + role.getName() + "%");
		}
		return paging(sb.toString(), condition, param);
	}

	@Override
	public void insertRoleRes(String role_id, List<String> res) {
		if (res == null || res.size() == 0)
			return;
		String sql = "insert into sys_role_resource (role_id, resource_id) values (?,?) ";
		for (String resource_id : res) {
			insert(sql, role_id, resource_id);
		}
	}

	@Override
	public List<String> queryRoleRes(String id) {
		String sql = "select resource_id from sys_role_resource where role_id=? ";
		return executor.querySimpleList(sql, id);
	}

	@Override
	public void deleteRoleRes(String id) {
		executor.delete("delete from sys_role_resource where role_id=?", id);
	}

	@Override
	public void saveRole(final SysRole role, final List<String> res) {
		executor.transaction(new Atom() {
			@Override
			public boolean execute() throws Exception {
				insert(role);
				insertRoleRes(role.getId(), res);
				return true;
			}
		});
	}

	@Override
	public void deleteRole(final List<String> ids) {
		executor.transaction(new Atom() {
			@Override
			public boolean execute() throws Exception {
				deleteIn("id", ids);
				deleteInSql("delete from sys_role_resource t where t.role_id ", ids);
				return true;
			}
		});
	}

	@Override
	public void updateRole(final SysRole role, final List<String> res) {
		executor.transaction(new Atom() {
			@Override
			public boolean execute() throws Exception {
				update(role);
				deleteRoleRes(role.getId());
				insertRoleRes(role.getId(), res);
				return true;
			}
		});
	}

}
