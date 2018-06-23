package cn.aresoft.manager.controller.sys;

import java.util.List;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.model.sys.SysResource;
import cn.aresoft.manager.model.sys.SysRole;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysResourceService;
import cn.aresoft.manager.service.sys.SysRoleService;
import cn.aresoft.manager.utils.PermissionUtil;
import cn.aresoft.manager.validator.SysRoleValidator;

/**
 * 角色管理
 * 
 * @author dongchao
 *
 */

@Controller(value = "/admin/sys/role", scope = BeanScope.SINGLETON)
public class SysRoleController extends CommonController {

	@Inject
	private SysRoleService sysRoleService;

	@Inject
	private SysResourceService sysResourceService;

	public View index() {
		return new MyBeetlView("/admin/role/role_list.html");
	}

	public View json() throws Exception {
		PageRecord<SysRole> data = sysRoleService.paging(PuffContext.getModel(SysRole.class), getCommonParam());
		return ViewFactory.json(data);
	}

	public View edit(String id) {
		SysRole sysrole = sysRoleService.query(id);
		MyBeetlView view = new MyBeetlView("/admin/role/role_edit.html", "role", sysrole);
		List<SysResource> resources = sysResourceService.querySysResourceList(getSysUser().getId());
		view.put("resources", resources);
		List<String> resIds = sysRoleService.queryRoleRes(id);
		StringBuffer sb = new StringBuffer();
		if (resIds != null) {
			for (String rid : resIds) {
				sb.append(rid).append("-");
			}
		}
		view.put("resIds", sb.toString());
		return view;
	}

	@Validate(SysRoleValidator.class)
	public View update() {
		SysRole role = PuffContext.getModel(SysRole.class);
		List<String> res = PuffContext.getParameterList("res");
		sysRoleService.updateRole(role, res);
		PermissionUtil.chearPer();
		return ViewFactory.json(RetMsg.success("更新成功！！！"));
	}

	public View add() {
		List<SysRole> roleList = sysRoleService.queryList();
		List<SysResource> resources = sysResourceService.querySysResourceList(getSysUser().getId());
		MyBeetlView view = new MyBeetlView("/admin/role/role_add.html", "roleList", roleList);
		view.put("resources", resources);
		return view;
	}

	@Validate(SysRoleValidator.class)
	public View insert() {
		SysRole role = PuffContext.getModel(SysRole.class);
		List<String> res = PuffContext.getParameterList("res");
		sysRoleService.saveRole(role, res);
		PermissionUtil.chearPer();
		return ViewFactory.json(RetMsg.success("添加成功！！！"));
	}

	public View delete() {
		sysRoleService.deleteRole(PuffContext.getParameterList("ids", ","));
		PermissionUtil.chearPer();
		return ViewFactory.json(RetMsg.success("删除成功！！！"));
	}
}
