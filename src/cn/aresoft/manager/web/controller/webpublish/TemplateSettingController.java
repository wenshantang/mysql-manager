package cn.aresoft.manager.web.controller.webpublish;


import java.util.List;

import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.DateTime;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsTopicCache;
import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.service.CmsArticleService;
import cn.aresoft.cms.common.service.CmsTopicService;
import cn.aresoft.common.cache.TemplateSettingCache;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.model.webpublish.TemplateParam;
import cn.aresoft.common.model.webpublish.TemplateSetting;
import cn.aresoft.common.service.webpublish.TemplateParamService;
import cn.aresoft.common.service.webpublish.TemplateSettingService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.web.validate.TemplateSettingValidate;
/**
 * 网站前台菜单管理
 * @author lxy
 *
 */
@Controller(value = "/admin/webpublish/templatesetting")
public class TemplateSettingController extends CommonController{
	@Inject
	private TemplateSettingService templateSettingService;
	@Inject
	private TemplateParamService templateParamService;
	@Inject
	private TemplateSettingCache templateSettingCache;
	
	@Inject
	private CmsTopicService cmsTopicService;

	@Inject
	private CmsArticleService cmsArticleService;

	@Inject
	private CmsTopicCache cmsTopicCache;


	public View index() {
		List<TemplateSetting> menus = templateSettingService.getFrontMenu();
		String leve1_open = PuffContext.getParameter("leve1_open");
		MyBeetlView view = new MyBeetlView("/webpublish/temSetting/temsetting_list.html", "menus", menus);
		view.put("leve1_open", leve1_open);
		return view;
	}
	
	/**
	 * 新增
	 * @param pid
	 * @param level
	 * @return
	 */
	public View add(String pid, String level) {
		MyBeetlView view = new MyBeetlView("/webpublish/temSetting/temsetting_add.html");
		if ("2".equals(level)) {
			TemplateSetting menu = templateSettingService.queryTopMenu(pid);
			view.put("menu", menu);
		}
		view.put("level", level);
		view.put("pid", pid);
		return view;
	}
	
	/**
	 * 新增
	 * @return
	 */
	@Validate(TemplateSettingValidate.class)
	public View insert() {
		String level = PuffContext.getParameter("level");
		TemplateSetting menu = new TemplateSetting();
		if ("1".equals(level)) {
			menu.setParent_id("888888888888888888");
		} else {
			menu.setParent_id(PuffContext.getParameter("parent_Id"));
		}
		menu.setTemplate_name(PuffContext.getParameter("template_name"));
		menu.setShow_name(PuffContext.getParameter("show_name"));
		menu.setMenu_link(PuffContext.getParameter("menu_link"));
		menu.setMenu_level(level);
		menu.setIdx(PuffContext.getIntParam("idx", 1));
		menu.setIsused(PuffContext.getParameter("isused"));
		menu.setCreate_time(DateTime.currentTimeStamp());
		menu.setCreate_user(getSysUser().getName());
		templateSettingService.insert(menu);
		return ViewFactory.json(RetMsg.success());
	}
	
	public View edit(String pid, String level) {
		MyBeetlView view = new MyBeetlView("/webpublish/temSetting/temsetting_edit.html");
		TemplateSetting menu = templateSettingService.query(pid);
		view.put("menu", menu);
		view.put("level", level);
		view.put("pid", pid);
		return view;
	}

	@Validate(TemplateSettingValidate.class)
	public View update() {
		String level = PuffContext.getParameter("level");
		TemplateSetting menu = new TemplateSetting();
		menu.setId(PuffContext.getParameter("id"));
		if ("1".equals(level)) {
			menu.setParent_id("888888888888888888");
		} else {
			menu.setParent_id(PuffContext.getParameter("parent_Id"));
		}
		menu.setTemplate_name(PuffContext.getParameter("template_name"));
		menu.setShow_name(PuffContext.getParameter("show_name"));
		menu.setMenu_link(PuffContext.getParameter("menu_link"));
		menu.setMenu_level(level);
		menu.setIdx(PuffContext.getIntParam("idx", 1));
		menu.setIsused(PuffContext.getParameter("isused"));
		menu.setUpdate_time(DateTime.currentTimeStamp());
		menu.setUpdate_user(getSysUser().getName());
		templateSettingService.update(menu);
		return ViewFactory.json(RetMsg.success());
		
	}
	/**
	 * 
	 * @param id
	 * @param type
	 * @param value
	 * @return
	 */
	public View updateSet(String id, String type,String value) {
		TemplateSetting menu = templateSettingService.query(id);
		if("1".equals(type)){
			menu.setShow_name(value);
		}else if("2".equals(type)){
			menu.setIdx(Integer.valueOf(value));
		}if("3".equals(type)){
			menu.setIsused(value);
		}
		templateSettingService.update(menu);
		templateSettingCache.cache(menu.getTemplate_name(), menu);
		templateSettingCache.cache(menu.getId(), menu);
		if("1".equals(type)){
//			CmsTopic topic = cmsTopicService.queryTopicByCode(menu.getTemplate_name());
//			if(topic != null){
//				topic.setMenu_name(value);
//				topic.setName(value);
//				topic.setMeta_title(value);
//				cmsTopicService.update(topic);
//				cmsTopicCache.addTopic(topic);
//			}
		}
		return ViewFactory.json(RetMsg.success("修改成功"));
	}

	public View delete(String id) {
		templateSettingService.deleteMenu(id);
		return ViewFactory.json(RetMsg.success());
	}
	
	/**
	 * 展示页面 图片 内容信息
	 * @param template_name
	 * @return
	 */
	public View showPageInfo(String template_name,String show_name) {
		MyBeetlView view =new  MyBeetlView("/webpublish/webpage_edit.html");
		List<TemplateParam> listParams = templateParamService.queryParamsByTemplateName(template_name);
		for(int i=0;i<listParams.size();i++){
			TemplateParam tem = listParams.get(i);
			if("article".equals(tem.getParam_type())){
				CmsArticle article = cmsArticleService.query(tem.getParam_value());
				if(article != null){
					String article_title = article.getTitle();
					tem.setArticle_title(article_title);
				}
			}
		}
		view.put("listParams", listParams);
		view.put("template_name", template_name);
		view.put("show_name", show_name);
		return view;
	}
	
	/**
	 * 修改页面内容 图片 信息
	 * @return
	 */
	public View updatePageInfo(String template_name) {
		List<TemplateParam> listParams = templateParamService.queryParamsByTemplateName(template_name);
		if(listParams != null && listParams.size()>0){
			for(int i=0;i<listParams.size();i++){
				TemplateParam param = listParams.get(i);
				String param_value_old = PuffContext.getParameter(param.getParam_key()+"_old");
				String param_value_new = PuffContext.getParameter(param.getParam_key());
				String param_link = PuffContext.getParameter(param.getParam_key()+"_link");
				if(param_link == null){
					param_link ="";
				}
				if(!param_value_old.equals(param_value_new) || !param_link.equals(param.getParam_link())){
					templateParamService.updateTemplateParam(template_name,param.getParam_key(),param_value_new,param_link);
				}
			}
		}
		return ViewFactory.json(RetMsg.success("修改成功！"));
	}
	
	/**
	 * 更新模板参数
	 * @param templateName
	 * @param paramkey
	 * @param paramvalue
	 */
	private void updateTemplateParam(String templateName,String paramkey,String paramvalue){
		TemplateParam model = templateParamService.queryByKey(templateName,paramkey);
		if (model != null) {
			model.setParam_value(paramvalue);
			model.setUpdate_time(DateTime.currentTimeStamp());
			model.setUpdate_user(getSysUser().getName());
			templateParamService.update(model);
		} else {
			model = new TemplateParam();
			model.setTemplate_name(templateName);
			model.setParam_key(paramkey);
			model.setParam_value(paramvalue);
			model.setCreate_time(DateTime.currentTimeStamp());
			model.setCreate_user(getSysUser().getName());
			model.setIsdelete("0");
			templateParamService.insert(model);
		}
	}
}

