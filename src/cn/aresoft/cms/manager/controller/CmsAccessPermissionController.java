package cn.aresoft.cms.manager.controller;

import java.util.List;

import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsAccessPermissionCache;
import cn.aresoft.cms.common.model.CmsAccessPermission;
import cn.aresoft.cms.common.service.CmsAccessPermissionService;
import cn.aresoft.cms.manager.validator.CmsAccessPermissionValidator;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;

/**
 * cms 访问权限控制
 * 
 * @author dongchao
 *
 */

@Controller("/admin/cms/access/permission")
public class CmsAccessPermissionController extends CommonController {

	@Inject
	private CmsAccessPermissionService cmsAccessPermissionService;

	@Inject
	private CmsAccessPermissionCache cmsAccessPermissionCache;

	public View index() {
		return new MyBeetlView("/cms/accesspermission/accesspermission_list.html");
	}

	public View json() {
		PageRecord<CmsAccessPermission> data = cmsAccessPermissionService.paging(PuffContext.getModel(CmsAccessPermission.class), getCommonParam());
		return ViewFactory.json(data);
	}

	public View add() {
		return new MyBeetlView("/cms/accesspermission/accesspermission_add.html");
	}

	@Validate(CmsAccessPermissionValidator.class)
	public View insert() {
		CmsAccessPermission model = PuffContext.getModel(CmsAccessPermission.class);
		cmsAccessPermissionService.insert(model);
		cmsAccessPermissionCache.add(model);
		return ViewFactory.json(RetMsg.success("新增成功！"));
	}

	public View edit(String code) {
		CmsAccessPermission model = cmsAccessPermissionService.query(code);
		MyBeetlView view = new MyBeetlView("/cms/accesspermission/accesspermission_edit.html", "ap", model);
		return view;
	}

	@Validate(CmsAccessPermissionValidator.class)
	public View update() {
		CmsAccessPermission model = PuffContext.getModel(CmsAccessPermission.class);
		cmsAccessPermissionService.update(model);
		cmsAccessPermissionCache.update(model.getId(), model);
		return ViewFactory.json(RetMsg.success("修改成功！"));
	}

	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		cmsAccessPermissionService.deleteIn(ids);
		cmsAccessPermissionCache.batchRemove(ids);
		return ViewFactory.json(RetMsg.success("删除成功！"));
	}

}
