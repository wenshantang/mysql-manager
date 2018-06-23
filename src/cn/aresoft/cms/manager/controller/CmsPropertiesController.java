package cn.aresoft.cms.manager.controller;

import java.util.List;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsPropertiesCache;
import cn.aresoft.cms.common.model.CmsProperties;
import cn.aresoft.cms.common.service.CmsPropertiesService;
import cn.aresoft.cms.manager.validator.CmsPropertiesValidator;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;

/**
 * CMS 配置管理
 * 
 * @author dongchao
 *
 */

@Controller(value = "/admin/cms/properties", scope = BeanScope.SINGLETON)
public class CmsPropertiesController extends CommonController {

	@Inject
	private CmsPropertiesCache cmsPropertiesCache;

	@Inject
	private CmsPropertiesService cmsPropertiesService;

	public View index() {
		List<String> types = cmsPropertiesService.queryAllType();
		return new MyBeetlView("/cms/properties/properties_list.html", "dictTypes", types);
	}

	public View json() throws Exception {
		PageRecord<CmsProperties> data = cmsPropertiesService.paging(PuffContext.getModel(CmsProperties.class), getCommonParam());
		return ViewFactory.json(data);
	}

	public View edit(String id) {
		CmsProperties dict = cmsPropertiesService.query(id);
		MyBeetlView jsp = new MyBeetlView("/cms/properties/properties_edit.html", "dict", dict);
		List<String> dictTypes = cmsPropertiesService.queryAllType();
		jsp.put("dictTypes", dictTypes);
		return jsp;
	}

	@Validate(CmsPropertiesValidator.class)
	public View update() {
		CmsProperties cp = PuffContext.getModel(CmsProperties.class);
		cmsPropertiesService.update(cp);
		cmsPropertiesCache.clear(cp.getType(), cp.getName());
		cmsPropertiesCache.remove("propertiesAllType");
		return ViewFactory.json(RetMsg.success("更新成功！！！"));
	}

	public View add() {
		List<String> dictTypes = cmsPropertiesService.queryAllType();
		return new MyBeetlView("/cms/properties/properties_add.html", "dictTypes", dictTypes);
	}

	@Validate(CmsPropertiesValidator.class)
	public View insert() {
		CmsProperties cp = PuffContext.getModel(CmsProperties.class);
		cmsPropertiesService.insert(cp);
		cmsPropertiesCache.clear(cp.getType(), cp.getName());
		cmsPropertiesCache.remove("propertiesAllType");
		return ViewFactory.json(RetMsg.success("添加成功！！！"));
	}

	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		for (String id : ids) {
			CmsProperties cp = cmsPropertiesService.query(id);
			cmsPropertiesCache.clear(cp.getType(), cp.getName());
		}
		cmsPropertiesService.deleteIn(ids);
		cmsPropertiesCache.remove("propertiesAllType");
		return ViewFactory.json(RetMsg.success("删除成功！！！"));
	}

}
