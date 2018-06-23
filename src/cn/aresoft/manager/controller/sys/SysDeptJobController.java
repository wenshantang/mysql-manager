package cn.aresoft.manager.controller.sys;

import java.util.ArrayList;
import java.util.List;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysDept;
import cn.aresoft.manager.model.sys.SysDeptJob;
import cn.aresoft.manager.model.sys.SysRole;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysDeptJobService;
import cn.aresoft.manager.service.sys.SysDeptService;
import cn.aresoft.manager.service.sys.SysMenuService;
import cn.aresoft.manager.service.sys.SysRoleService;
import cn.aresoft.manager.utils.PermissionUtil;
import cn.aresoft.manager.validator.SysDeptJobValidator;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

/***
 * 部门岗位管理
 * @author yyj
 *
 */

@Controller(value = "/admin/sys/job", scope = BeanScope.SINGLETON)
public class SysDeptJobController extends CommonController {

	@Inject
	private SysDeptService sysDeptService;
	@Inject
	private SysRoleService sysRoleService;
	
	@Inject
	private SysMenuService sysMenuService;

	@Inject
	private SysDeptJobService sysDeptJobService;
	
	//基础信息
	List<SysRole> roleList;//角色list
	List<SysDept> deptList;//部门list
	List<SysDeptJob> deptJobList;//部门岗位list
	
	
	public View index() {
		return new MyBeetlView("/admin/job/job_list.html");
	}

	public View json() throws Exception {
		PageRecord<SysDeptJob> data = sysDeptJobService.paging(PuffContext.getModel(SysDeptJob.class), getCommonParam());
		return ViewFactory.json(data);
	}


	public View edit(String id) {
		SysDeptJob sysDeptJob = sysDeptJobService.query(id);
		List<String> roleIds = sysDeptJobService.queryDeptJobRoles(id);
		commonService();
		MyBeetlView view = new MyBeetlView("/admin/job/job_edit.html", "vo", sysDeptJob);
		view.put("roleList", roleList);
		view.put("deptList", deptList);
		view.put("deptJobList", deptJobList);
		StringBuffer sb = new StringBuffer();
		if (roleIds != null) {
			for (String rId : roleIds) {
				sb.append(rId).append("-");
			}
		}
		view.put("roleIds", sb.toString());
		return view;
	}

	@Validate(SysDeptJobValidator.class)
	public View update() {
		SysDeptJob sysDeptJob = PuffContext.getModel(SysDeptJob.class);
		List<String> roles = PuffContext.getParameterList("roles");
		sysDeptJobService.updateDept(sysDeptJob, roles);
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success("更新成功！！！"));
	}

	public View add() {
		commonService();
		MyBeetlView view = new MyBeetlView("/admin/job/job_add.html", "roleList", roleList);
		view.put("deptList", deptList);
		view.put("deptJobList", deptJobList);
		return view;
	}

	@Validate(SysDeptJobValidator.class)
	public View insert() {
		SysDeptJob sysDeptJob = PuffContext.getModel(SysDeptJob.class);
		sysDeptJobService.insert(sysDeptJob);
		List<String> roles = PuffContext.getParameterList("roles");
		if (roles != null && roles.size() > 0) {
			sysDeptJobService.insertDeptJobRoles(sysDeptJob.getId(), roles);
		}
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success("添加成功！！！"));
	}

	public View delete() {
		sysDeptJobService.deleteDeptJob(PuffContext.getParameterList("ids", ","));
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success("删除成功！！！"));
	}
	
	/**
	 * 公共查询service
	 */
	private void commonService(){
		 roleList= sysRoleService.queryList();
		 deptList=sysDeptService.queryNoRootList();
	}
	
	/**
	 * 查询部门下的岗位<br/>
	 * deptId为空，则查询所有的岗位
	 * @param deptId
	 * @return
	 */
	public View queryCurrentDeptJobs(){
		String deptId=PuffContext.getParameter("dept_id");
		if(StringUtil.notBlank(deptId)){
			deptJobList=sysDeptJobService.queryCurrentDeptJobs(deptId);
		}else{
			deptJobList=new ArrayList<SysDeptJob>();
		}
	    return ViewFactory.json(deptJobList);
	}
}
