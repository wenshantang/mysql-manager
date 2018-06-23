package cn.aresoft.manager.service.impl.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysDept;
import cn.aresoft.manager.service.sys.SysDeptService;
import cn.aresoft.manager.utils.DateUtils;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;
import com.puff.jdbc.executor.Atom;
/**
 * 部门service实现
 * @author yyj
 *
 */
@Bean(id = "sysDeptService")
public class SysDeptServiceImpl extends CenterServiceImpl<SysDept>implements SysDeptService {

	@Override
	public PageRecord<SysDept> paging(SysDept vo, CommonParam param) {
		//String sql = SqlBuilder.buildQuerySQL(SysDept.class);
		String sql="select a.*,b.dept_name as sup_dept_name from sys_dept a left join sys_dept b on a.sup_dept_id=b.id ";
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 and a.id!='1' ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(vo.getDept_name())) {
			sb.append("and a.dept_name like ? ");
			condition.add("%" +vo.getDept_name()+ "%");
		}
		if (StringUtil.notEmpty(vo.getDept_type())) {
			sb.append("and a.dept_type =? ");
			condition.add(vo.getDept_type());
		}
		return paging(sb.toString(), condition, param);
	}

	@Override
	public void insertDeptRoles(String id, List<String> roles) {
		if (roles == null || roles.size() == 0)
			return;
		String current_time=DateUtils.date2Str6(new Date(), 6);
		String sql = "insert into sys_dept_role(id, role_id,create_time) values (?,?,'"+current_time+"') ";
		for (String role_id :roles) {
			insert(sql, id, role_id);
		}

	}

	@Override
	public List<String> queryDeptRoles(String id) {
		String sql = "select role_id from sys_dept_role where id=? ";
		return executor.querySimpleList(sql, id);
	}

	@Override
	public void deleteDeptRoles(String id) {
		executor.delete("delete from sys_dept_role where id=?", id);

	}

	@Override
	public void saveDept(final SysDept vo, final List<String> roles) {
		executor.transaction(new Atom() {
			@Override
			public boolean execute() throws Exception {
				insert(vo);
				insertDeptRoles(vo.getId(), roles);
				return true;
			}
		});

	}

	@Override
	public void deleteDept(final List<String> ids) {
		executor.transaction(new Atom() {
			@Override
			public boolean execute() throws Exception {
				deleteIn("id", ids);
				deleteInSql("delete from sys_dept_role  where id ", ids);
				return true;
			}
		});
	}

	@Override
	public void updateDept(final SysDept vo, final List<String> roles) {
		executor.transaction(new Atom() {
			@Override
			public boolean execute() throws Exception {
				update(vo);
				deleteDeptRoles(vo.getId());
				insertDeptRoles(vo.getId(), roles);
				return true;
			}
		});
	}

	@Override
	public List<SysDept> queryNoRootList() {
		String sql = SqlBuilder.buildQuerySQL(SysDept.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 and id!='1' ");
		return queryList(sb.toString(), new Object[]{});
	}


}
