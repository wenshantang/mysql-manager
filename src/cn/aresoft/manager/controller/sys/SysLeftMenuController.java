package cn.aresoft.manager.controller.sys;

import java.util.List;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysLeftMenu;
import cn.aresoft.manager.model.sys.SysResource;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysMenuService;
import cn.aresoft.manager.service.sys.SysResourceService;
import cn.aresoft.manager.validator.SysLeftMenuValidator;

/**
 * 后台导航管理
 * 
 * @author dongchao
 *
 */
@Controller(value = "/admin/sys/leftmenu", scope = BeanScope.SINGLETON)
public class SysLeftMenuController extends CommonController {

	@Inject
	private SysResourceService sysResourceService;

	@Inject
	private SysMenuService sysMenuService;

	public View index() {
		List<SysLeftMenu> menus = sysMenuService.getLeftMenu();
		String leve1_open = PuffContext.getParameter("leve1_open");
		String leve2_open = PuffContext.getParameter("leve2_open");
		MyBeetlView view = new MyBeetlView("/admin/leftmenu/leftmenu_list.html", "menus", menus);
		view.put("leve1_open", leve1_open);
		view.put("leve2_open", leve2_open);
		return view;
	}

	public View add(String pid, String level) {
		MyBeetlView view = new MyBeetlView("/admin/leftmenu/leftmenu_add.html");
		if ("2".equals(level)) {
			SysLeftMenu menu = sysMenuService.queryTopMenu(pid);
			view.put("menu", menu);
		}
		if ("3".equals(level)) {
			SysLeftMenu menu = sysMenuService.findLeftMenu(pid);
			view.put("menu", menu);
		}
		List<SysResource> resources = sysResourceService.querySysResource();
		view.put("resources", resources);
		view.put("level", level);
		view.put("pid", pid);
		return view;
	}

	@Validate(SysLeftMenuValidator.class)
	public View insert() {
		sysMenuService.clearMenu();
		String level = PuffContext.getParameter("level");
		if ("1".equals(level)) {
			SysLeftMenu menu = new SysLeftMenu();
			menu.setParentId("888888888888888888");
			menu.setName(PuffContext.getParameter("name"));
			String type = PuffContext.getParameter("type");
			menu.setType(type);
			if ("2".equals(type)) {
				menu.setResourceId(PuffContext.getParameter("resourceId"));
			}
			menu.setUrl(PuffContext.getParameter("url"));
			menu.setIdx(PuffContext.getIntParam("idx", 1));
			sysMenuService.insert(menu);
			return ViewFactory.json(RetMsg.success());
		} else {
			SysLeftMenu menu = new SysLeftMenu();
			menu.setName(PuffContext.getParameter("name"));
			menu.setIdx(PuffContext.getIntParam("idx", 1));
			menu.setParentId(PuffContext.getParameter("parentId"));
			if ("3".equals(level)) {
				menu.setResourceId(PuffContext.getParameter("resourceId"));
			}
			menu.setUrl(PuffContext.getParameter("url"));
			sysMenuService.saveLeftMenu(menu);
			return ViewFactory.json(RetMsg.success());
		}
	}

	public View edit(String pid, String level) {
		List<SysResource> resources = sysResourceService.querySysResource();
		MyBeetlView view = new MyBeetlView("/admin/leftmenu/leftmenu_edit.html");
		view.put("resources", resources);
		if ("1".equals(level)) {
			SysLeftMenu menu = sysMenuService.queryTopMenu(pid);
			view.put("menu", menu);
		} else {
			SysLeftMenu menu = sysMenuService.findLeftMenu(pid);
			view.put("menu", menu);
		}
		view.put("level", level);
		view.put("pid", pid);
		return view;
	}

	@Validate(SysLeftMenuValidator.class)
	public View update() {
		sysMenuService.clearMenu();
		String level = PuffContext.getParameter("level");
		if ("1".equals(level)) {
			SysLeftMenu menu = new SysLeftMenu();
			menu.setParentId("888888888888888888");
			menu.setId(PuffContext.getParameter("id"));
			menu.setName(PuffContext.getParameter("name"));
			String type = PuffContext.getParameter("type");
			menu.setType(type);
			if ("2".equals(type)) {
				menu.setResourceId(PuffContext.getParameter("resourceId"));
			}
			menu.setUrl(PuffContext.getParameter("url"));
			menu.setIdx(PuffContext.getIntParam("idx", 1));
			sysMenuService.update(menu);
			return ViewFactory.json(RetMsg.success());
		} else {
			SysLeftMenu menu = new SysLeftMenu();
			menu.setId(PuffContext.getParameter("id"));
			menu.setName(PuffContext.getParameter("name"));
			menu.setIdx(PuffContext.getIntParam("idx", 1));
			menu.setParentId(PuffContext.getParameter("parentId"));
			if ("3".equals(level)) {
				menu.setResourceId(PuffContext.getParameter("resourceId"));
			}
			menu.setUrl(PuffContext.getParameter("url"));
			sysMenuService.updateLeftMenu(menu);
			return ViewFactory.json(RetMsg.success());
		}
	}

	public View delete(String id, String level) {
		sysMenuService.clearMenu();
		if ("1".equals(level)) {
			sysMenuService.deleteTopMenu(id);
		} else {
			sysMenuService.deleteMenu(id);
		}
		return ViewFactory.json(RetMsg.success());
	}

}
