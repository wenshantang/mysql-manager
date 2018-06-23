package cn.aresoft.manager.service.impl.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysDeptJob;
import cn.aresoft.manager.service.sys.SysDeptJobService;
import cn.aresoft.manager.utils.DateUtils;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.Procedure;
import com.puff.jdbc.core.Record;
import com.puff.jdbc.core.SqlBuilder;
import com.puff.jdbc.executor.Atom;
/**
 * 部门service实现
 * @author yyj
 *
 */
@Bean(id = "sysDeptJobService")
public class SysDeptJobServiceImpl extends CenterServiceImpl<SysDeptJob>implements SysDeptJobService {

	@Override
	public PageRecord<SysDeptJob> paging(SysDeptJob vo, CommonParam param) {
		//String sql = SqlBuilder.buildQuerySQL(SysDeptJob.class);
		String sql="select a.*,b.job_name as administrative_sup_name,c.dept_name as dept_name from sys_dept_job a left join sys_dept_job b on a.administrative_sup=b.id "
				+ "left join sys_dept c on a.dept_id=c.id ";
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(vo.getJob_name())) {
			sb.append("and a.job_name like ? ");
			condition.add("%" +vo.getJob_name()+ "%");
		}
		if (StringUtil.notEmpty(vo.getAdministrative_sup_name())) {
			sb.append("and b.job_name like ? ");
			condition.add("%" +vo.getAdministrative_sup_name()+ "%");
		}
		if (StringUtil.notEmpty(vo.getDept_name())) {
			sb.append("and c.dept_name like ? ");
			condition.add("%" +vo.getDept_name()+ "%");
		}
		return paging(sb.toString(), condition, param);
	}
	
	

	@Override
	public void insertDeptJobRoles(String id, List<String> roles) {
		if (roles == null || roles.size() == 0)
			return;
		String current_time=DateUtils.date2Str6(new Date(), 6);
		String sql = "insert into sys_job_role(job_id, role_id,create_time) values (?,?,'"+current_time+"') ";
		for (String role_id :roles) {
			insert(sql, id, role_id);
		}
	}



	@Override
	public List<String> queryDeptJobRoles(String id) {
		String sql = "select role_id from sys_job_role where job_id=? ";
		return executor.querySimpleList(sql, id);
	}



	@Override
	public void deleteDeptJobRoles(String id) {
		executor.delete("delete from sys_job_role where job_id=?", id);
		
	}



	@Override
	public void saveDeptJob(final SysDeptJob vo, final List<String> roles) {
		executor.transaction(new Atom() {
			@Override
			public boolean execute() throws Exception {
				insert(vo);
				insertDeptJobRoles(vo.getId(), roles);
				return true;
			}
		});
	}



	@Override
	public void deleteDeptJob(final List<String> ids) {
		executor.transaction(new Atom() {
			@Override
			public boolean execute() throws Exception {
				deleteIn("id", ids);
				deleteInSql("delete from sys_job_role  where job_id ", ids);
				return true;
			}
		});
	}



	@Override
	public void updateDept(final SysDeptJob vo, final List<String> roles) {
		executor.transaction(new Atom() {
			@Override
			public boolean execute() throws Exception {
				update(vo);
				deleteDeptJobRoles(vo.getId());
				insertDeptJobRoles(vo.getId(), roles);
				return true;
			}
		});
		
	}



	@Override
	public List<SysDeptJob> queryCurrentDeptJobs(String deptId) {
		String sql = SqlBuilder.buildQuerySQL(SysDeptJob.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notBlank(deptId)) {
			sb.append("and dept_id = ? ");
			condition.add(deptId);
		}
		return queryList(sb.toString(), condition);
	}



	@Override
	public List<Record> queryDeptJobOrg(String deptId,String deptType) {
		List<Record> resultList=new ArrayList<Record>();
		try{
			if(StringUtil.blank(deptId)){
				deptId="1";//默认查询公司下面的所有层级
			}
			Procedure procedure=new Procedure();
			procedure.setName("pro_dept_job_org");
			procedure.setIn(deptId);
			procedure.setIn(deptType);
			executor.call(procedure);
			StringBuilder sb=new StringBuilder();
			sb.append("select a.*,b.id job_id,concat(SPACE((nLevel+1)*2),'--',b.job_name) job_name ")
			  .append(" from(")
			  .append(" SELECT a.id,concat(SPACE(B.nLevel*2),'+--',A.dept_name) dept_name,B.sCort,B.nLevel")
			  .append(" FROM sys_dept A,tmp_deptList B")
			  .append(" WHERE A.ID=B.ID and a.dept_type=? ORDER BY B.sCort)")
			  .append("a left join sys_dept_job b on a.id=b.dept_id ");
		    resultList=executor.queryListRecord(sb.toString(), deptType);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return resultList;
	}



	@Override
	public List<Record> queryDeptJobOptionList(String deptId,String deptType) {
		List<Record> queryList=queryDeptJobOrg(deptId,deptType);
		List<Record> recordDeptOrgList=new ArrayList<Record>();
		List<String> deptIdList=new ArrayList<String>();
		for(Record record:queryList){
			Record deptRecord=new Record();
			Record deptJobRecord=new Record();
			String dept_id=record.getString("id");//部门Id
			String dept_name=record.getString("dept_name");//部门name
			String job_id=record.getString("job_id");//岗位id
			String job_name=record.getString("job_name");//岗位name
			deptRecord.set("isJob", "0");//非岗位
			deptRecord.set("id","gw_"+dept_id);//重组非岗位id，避免id与岗位id重复
			deptRecord.set("name",dept_name);
			deptJobRecord.set("isJob", "1");//岗位
			deptJobRecord.set("id",job_id);//id
			deptJobRecord.set("name",job_name);
			if(!deptIdList.contains(dept_id)){//添加部门
				recordDeptOrgList.add(deptRecord);
			}
			recordDeptOrgList.add(deptJobRecord);//添加岗位
			deptIdList.add(dept_id);
		}
		return recordDeptOrgList;
	}

	
}
