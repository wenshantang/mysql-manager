package cn.aresoft.manager.controller.sys;

import java.util.Arrays;
import java.util.List;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Mapping;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysResource;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysMenuService;
import cn.aresoft.manager.service.sys.SysResourceService;
import cn.aresoft.manager.utils.PermissionUtil;
import cn.aresoft.manager.validator.SysResourceValidator;

/**
 * 系统资源管理
 * 
 * @author dongchao
 *
 */

@Controller(value = "/admin/sys/resource", scope = BeanScope.SINGLETON)
public class SysResourceController extends CommonController {

	@Inject
	private SysResourceService sysResourceService;
	@Inject
	private SysMenuService sysMenuService;

	public View index() {
		SysResource res = PuffContext.getModel(SysResource.class);
		CommonParam param = getCommonParam();
		param.setRows(PuffContext.getIntParam("rows", 10));
		PageRecord<SysResource> pg = sysResourceService.querySysResource(param, res);
		String open = PuffContext.getParameter("open");
		if (StringUtil.empty(open)) {
			open = res.getId();
		}
		View view = new MyBeetlView("/admin/resource/resource_list.html", "pg", pg).put("open", open);
		List<SysResource> topMenu = sysResourceService.queryTopMenu();
		view.put("topMenu", topMenu);
		view.put("res", res);
		return view;

	}

	public View add(String pid) {
		SysResource parent = sysResourceService.query(pid);
		return new MyBeetlView("/admin/resource/resource_add.html", "parent", parent);
	}

	@Mapping("/add/quick")
	public View quickAdd(String pid) {
		SysResource parent = sysResourceService.query(pid);
		return new MyBeetlView("/admin/resource/resource_quickadd.html", "parent", parent);
	}

	@Validate(SysResourceValidator.class)
	public View insert() {
		SysResource resource = PuffContext.getModel(SysResource.class);
		List<String> urls = PuffContext.getParameterList("dep_urls");
		StringBuilder sb = new StringBuilder();
		if (urls != null)
			for (String url : urls) {
				sb.append(url).append(",");
			}
		resource.setDep_url(sb.toString());
		sysResourceService.insert(resource);
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success());
	}

	@Mapping("/insert/quick")
	public View quickInsert() {
		String name = PuffContext.getParameter("name");
		if (StringUtil.empty(name)) {
			return ViewFactory.json(RetMsg.error("资源名称不能为空"));
		}
		String url = PuffContext.getParameter("url");
		if (StringUtil.empty(url)) {
			return ViewFactory.json(RetMsg.error("URL不能为空"));
		}
		SysResource resource = new SysResource();
		resource.setName(name + "管理");
		resource.setParent_id("888888888888888888");
		resource.setMemo(name + "管理");
		sysResourceService.insert(resource);
		String parentId = resource.getId();

		resource = new SysResource();
		resource.setName(name + "列表");
		resource.setParent_id(parentId);
		resource.setUrl(url);
		resource.setDep_url(url + "/json");
		resource.setMemo(name + "列表");
		sysResourceService.insert(resource);

		resource = new SysResource();
		resource.setName("新增" + name);
		resource.setParent_id(parentId);
		resource.setUrl(url + "/insert");
		resource.setDep_url(url + "/add");
		resource.setMemo("新增" + name);
		sysResourceService.insert(resource);

		resource = new SysResource();
		resource.setName("查看" + name);
		resource.setParent_id(parentId);
		resource.setUrl(url + "/edit");
		resource.setMemo("查看" + name);
		sysResourceService.insert(resource);

		resource = new SysResource();
		resource.setName("修改" + name);
		resource.setParent_id(parentId);
		resource.setUrl(url + "/update");
		resource.setMemo("修改" + name);
		sysResourceService.insert(resource);

		resource = new SysResource();
		resource.setName("删除" + name);
		resource.setParent_id(parentId);
		resource.setUrl(url + "/delete");
		resource.setMemo("删除" + name);
		sysResourceService.insert(resource);

		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success());
	}

	public View edit(String id) {
		SysResource resource = sysResourceService.querySysResource(id);
		MyBeetlView view = new MyBeetlView("/admin/resource/resource_edit.html", "res", resource);
		String url = resource.getDep_url();
		if (StringUtil.notEmpty(url)) {
			String[] arr = url.split(",");
			List<String> urls = Arrays.asList(arr);
			view.put("urls", urls);
		}
		return view;
	}

	@Validate(SysResourceValidator.class)
	public View update() {
		SysResource resource = PuffContext.getModel(SysResource.class);
		List<String> urls = PuffContext.getParameterList("dep_urls");
		StringBuilder sb = new StringBuilder();
		if (urls != null)
			for (String url : urls) {
				sb.append(url).append(",");
			}
		resource.setDep_url(sb.toString());
		sysResourceService.update(resource);
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success());
	}

	public View delete(String id) {
		sysResourceService.deleteRes(id);
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success());
	}

}
