package cn.aresoft.manager.controller.sys;

import java.util.List;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysDept;
import cn.aresoft.manager.model.sys.SysRole;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysDeptService;
import cn.aresoft.manager.service.sys.SysMenuService;
import cn.aresoft.manager.service.sys.SysRoleService;
import cn.aresoft.manager.utils.PermissionUtil;
import cn.aresoft.manager.validator.SysDeptValidator;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

/***
 * 部门管理
 * @author yyj
 *
 */

@Controller(value = "/admin/sys/dept", scope = BeanScope.SINGLETON)
public class SysDeptController extends CommonController {

	@Inject
	private SysDeptService sysDeptService;
	@Inject
	private SysRoleService sysRoleService;
	
	@Inject
	private SysMenuService sysMenuService;
	
	//基础信息
	List<SysRole> roleList;//角色list
	List<SysDept> deptList;//部门list
	
	
	public View index() {
		return new MyBeetlView("/admin/dept/dept_list.html");
	}

	public View json() throws Exception {
		PageRecord<SysDept> data = sysDeptService.paging(PuffContext.getModel(SysDept.class), getCommonParam());
		return ViewFactory.json(data);
	}


	public View edit(String id) {
		SysDept sysDept = sysDeptService.query(id);
		List<String> roleIds = sysDeptService.queryDeptRoles(id);
		commonService();
		MyBeetlView view = new MyBeetlView("/admin/dept/dept_edit.html", "vo", sysDept);
		view.put("roleList", roleList);
		view.put("deptList", deptList);
		StringBuffer sb = new StringBuffer();
		if (roleIds != null) {
			for (String rId : roleIds) {
				sb.append(rId).append("-");
			}
		}
		view.put("roleIds", sb.toString());
		return view;
	}

	@Validate(SysDeptValidator.class)
	public View update() {
		SysDept sysDept = PuffContext.getModel(SysDept.class);
		List<String> roles = PuffContext.getParameterList("roles");
		sysDeptService.updateDept(sysDept, roles);
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success("更新成功！！！"));
	}

	public View add() {
		commonService();
		MyBeetlView view = new MyBeetlView("/admin/dept/dept_add.html", "roleList", roleList);
		view.put("deptList", deptList);
		return view;
	}

	@Validate(SysDeptValidator.class)
	public View insert() {
		SysDept sysDept = PuffContext.getModel(SysDept.class);
		sysDeptService.insert(sysDept);
		List<String> roles = PuffContext.getParameterList("roles");
		if (roles != null && roles.size() > 0) {
			sysDeptService.insertDeptRoles(sysDept.getId(), roles);
		}
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success("添加成功！！！"));
	}

	public View delete() {
		sysDeptService.deleteDept(PuffContext.getParameterList("ids", ","));
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
}
