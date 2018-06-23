package cn.aresoft.manager.web.controller.webpublish;


import java.util.List;

import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.DateTime;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.model.webpublish.TemplateParam;
import cn.aresoft.common.model.webpublish.TemplateSetting;
import cn.aresoft.common.service.webpublish.TemplateParamService;
import cn.aresoft.common.service.webpublish.TemplateSettingService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.web.validate.TemplateParamValidate;
/**
 * 页面参数配置管理
 *
 */
@Controller(value = "/admin/webpublish/template_param")
public class TemplateParamController extends CommonController{
	@Inject
	private TemplateParamService templateParamService;
	@Inject
	private TemplateSettingService templateSettingService;


	public View index() {
		MyBeetlView view =new  MyBeetlView("/webpublish/template_param/template_param_list.html");
//		List<TemplateParam> flist = templateParamService.queryParams();
		List<TemplateSetting> flist = templateSettingService.getFrontMenu();
		view.put("flist", flist);
		return view;
	}
	public View json() {
		TemplateParam param = PuffContext.getModel(TemplateParam.class);
		PageRecord<TemplateParam> data = templateParamService.paging(param, getCommonParam());
		return ViewFactory.json(data);
	}
	
	/**
	 * 新增
	 * @return
	 */
	public View add() {
		MyBeetlView view = new MyBeetlView("/webpublish/template_param/template_param_add.html");
//		List<TemplateParam> flist = templateParamService.queryParams();
		List<TemplateSetting> flist = templateSettingService.getFrontMenu();
		view.put("flist", flist);
		return view;
	}
	
	/**
	 * 新增
	 * @return
	 */
	@Validate(TemplateParamValidate.class)
	public View insert() {
		TemplateParam model = PuffContext.getModel(TemplateParam.class);
		model.setCreate_time(DateTime.currentTimeStamp());
		model.setCreate_user(getSysUser().getName());
		model.setIsdelete("0");
		templateParamService.insert(model);
		return ViewFactory.json(RetMsg.success("新增成功！"));
	}

	public View edit(String id) {
		TemplateParam model = templateParamService.query(id);
		MyBeetlView view = new MyBeetlView("/webpublish/template_param/template_param_edit.html","ap", model);
//		List<TemplateParam> flist = templateParamService.queryParams();
		List<TemplateSetting> flist = templateSettingService.getFrontMenu();
		view.put("flist", flist);
		return view;
	}

	@Validate(TemplateParamValidate.class)
	public View update() {
		TemplateParam model = PuffContext.getModel(TemplateParam.class);
		model.setUpdate_time(DateTime.currentTimeStamp());
		model.setUpdate_user(getSysUser().getName());
		templateParamService.update(model);
		return ViewFactory.json(RetMsg.success("修改成功！"));
		
	}

	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		for (String id : ids) {
			templateParamService.delete(id);
		}
		return ViewFactory.json(RetMsg.success("删除成功！"));
	}
	
}

