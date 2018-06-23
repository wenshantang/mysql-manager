package cn.aresoft.cms.manager.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.model.CmsModel;
import cn.aresoft.cms.common.model.CmsModelAttr;
import cn.aresoft.cms.common.model.CmsTemplate;
import cn.aresoft.cms.common.service.CmsModelService;
import cn.aresoft.cms.common.service.CmsTemplateService;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;

/**
 * cms 模型
 * 
 * @author dongchao
 *
 */

@Controller(value = "/admin/cms/model", scope = BeanScope.SINGLETON)
public class CmsModelController extends CommonController {

	@Inject
	private CmsModelService cmsModelService;

	@Inject
	private CmsTemplateService cmsTemplateService;

	public View index() {
		return new MyBeetlView("/cms/model/model_list.html");
	}

	public View json() {
		PageRecord<CmsModel> data = cmsModelService.paging(PuffContext.getModel(CmsModel.class), getCommonParam());
		return ViewFactory.json(data);
	}

	public View add() {
		List<CmsTemplate> templates = cmsTemplateService.findHtmlTemplate();
		MyBeetlView view = new MyBeetlView("/cms/model/model_add.html");
		view.put("templates", templates);
		return view;
	}

	public View insert() {
		CmsModel model = PuffContext.getModel(CmsModel.class);
		model.setStatus(PuffContext.getIntParam("status", 0));
		String code = model.getCode();
		CmsModel cmsModel = cmsModelService.query(code);
		if (cmsModel != null) {
			return ViewFactory.json(RetMsg.error("模型代码已存在！"));
		}
		HttpServletRequest request = PuffContext.getRequest();
		Enumeration<String> parameterNames = request.getParameterNames();
		List<CmsModelAttr> attrs = new ArrayList<CmsModelAttr>();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			if (name.startsWith("other_attr_field_")) {
				String field = request.getParameter(name);
				String id = name.replace("other_attr_field_", "");
				String fieldName = request.getParameter("other_attr_name_" + id);
				String fieldType = request.getParameter("other_attr_type_" + id);
				CmsModelAttr ma = new CmsModelAttr(code, field, fieldName, fieldType);
				attrs.add(ma);
			}
		}
		cmsModelService.addModel(model, attrs);
		return ViewFactory.json(RetMsg.success("新增成功！"));
	}

	public View edit(String code) {
		CmsModel model = cmsModelService.query(code);
		MyBeetlView view = new MyBeetlView("/cms/model/model_edit.html", "model", model);
		List<CmsModelAttr> modelAttrs = cmsModelService.queryModelAttr(code);
		view.put("modelAttrs", modelAttrs);
		List<CmsTemplate> templates = cmsTemplateService.findHtmlTemplate();
		view.put("templates", templates);
		return view;
	}

	public View update() {
		CmsModel model = PuffContext.getModel(CmsModel.class);
		model.setStatus(PuffContext.getIntParam("status", 0));
		HttpServletRequest request = PuffContext.getRequest();
		Enumeration<String> parameterNames = request.getParameterNames();
		List<CmsModelAttr> attrs = new ArrayList<CmsModelAttr>();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			if (name.startsWith("other_attr_field_")) {
				String field = request.getParameter(name);
				String id = name.replace("other_attr_field_", "");
				String fieldName = request.getParameter("other_attr_name_" + id);
				String fieldType = request.getParameter("other_attr_type_" + id);
				CmsModelAttr ma = new CmsModelAttr(model.getCode(), field, fieldName, fieldType);
				attrs.add(ma);
			}
		}
		cmsModelService.updateModel(model, attrs);
		return ViewFactory.json(RetMsg.success("修改成功！"));
	}

	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		cmsModelService.deleteIn(ids);
		cmsModelService.deleteModelAttr(ids);
		return ViewFactory.json(RetMsg.success("删除成功！"));
	}

}
