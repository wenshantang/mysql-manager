package cn.aresoft.manager.web.controller.webpublish;

import java.util.List;

import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Mapping;
import com.puff.framework.utils.DateTime;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.model.CmsTopic;
import cn.aresoft.cms.common.service.CmsArticleService;
import cn.aresoft.cms.common.service.CmsTopicService;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.model.webpublish.TemplateParam;
import cn.aresoft.common.model.webpublish.WebTemplate;
import cn.aresoft.common.service.webpublish.TemplateParamService;
import cn.aresoft.common.service.webpublish.WebTemplateService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;
/**
 * 网站基础参数管理
 * @author lxy
 *
 */
@Controller(value = "/admin/webpublish/baseconfig")
public class BaseConfigController extends CommonController{
	@Inject
	private TemplateParamService templateParamService;
	@Inject
	private WebTemplateService webTemplateService;
	@Inject
	private CmsArticleService cmsArticleService;
	@Inject
	private CmsTopicService cmsTopicService;
	
	/**
	 * 展示基本信息
	 * @return
	 */
	public View index() {
		String template_name = "base";
		MyBeetlView view =new  MyBeetlView("/webpublish/webpage_edit.html");
		List<TemplateParam> listParams = templateParamService.queryParamsByTemplateName(template_name);
		for(int i=0;i<listParams.size();i++){
			TemplateParam tem = listParams.get(i);
			if("webstyle".equals(tem.getParam_key())){
				listParams.remove(i);
				i--;
			}
			if("article".equals(tem.getParam_type())){
				CmsArticle article = cmsArticleService.query(tem.getParam_value());
				String article_title = article.getTitle();
				tem.setArticle_title(article_title);
			}
		}
		view.put("listParams", listParams);
		view.put("template_name", template_name);
		view.put("show_name", "基本信息");
		return view;
	}
	
	/**
	 * 修改基本信息
	 * @return
	 */
	public View updateBase(String template_name) {
		List<TemplateParam> listParams = templateParamService.queryParamsByTemplateName(template_name);
		if(listParams != null && listParams.size()>0){
			for(int i=0;i<listParams.size();i++){
				TemplateParam param = listParams.get(i);
				if("webstyle".equals(param.getParam_key())){
					continue;
				}
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

//	private void updateBaseSite(String templateName,String paramkey,String paramvalue){
//		TemplateParam model = templateParamService.queryByKey(templateName,paramkey);
//		if (model != null) {
//			model.setParam_value(paramvalue);
//			model.setUpdate_time(DateTime.currentTimeStamp());
//			model.setUpdate_user(getSysUser().getName());
//			templateParamService.update(model);
//		} else {
//			model = new TemplateParam();
//			model.setTemplate_name(templateName);
//			model.setParam_key(paramkey);
//			model.setParam_value(paramvalue);
//			model.setCreate_time(DateTime.currentTimeStamp());
//			model.setCreate_user(getSysUser().getName());
//			model.setIsdelete("0");
//			templateParamService.insert(model);
//		}
//	}
	
	
	public View showTemplate() {
		MyBeetlView view =new  MyBeetlView("/webpublish/template_select.html");
		TemplateParam model = templateParamService.queryByKey("base","webstyle");
		view.put("ap", model);
		List<WebTemplate> templateList = webTemplateService.queryList();
		view.put("templateList", templateList);
		return view;
	}
	/**
	 * 选择模板（色系）
	 * 
	 * @return
	 */
	@Mapping("/update")
	public View selectTemplate() {
		String templateid = PuffContext.getParameter("templateid");
		TemplateParam model = templateParamService.queryByKey("base","webstyle");
		WebTemplate webTemplate = webTemplateService.query(templateid);
		if (model != null) {
			model.setParam_value(webTemplate.getStyle_path());
			model.setMemo(webTemplate.getId());
			model.setUpdate_time(DateTime.currentTimeStamp());
			model.setUpdate_user(getSysUser().getName());
			templateParamService.update(model);
		} else {
			model = new TemplateParam();
			model.setTemplate_name("base");
			model.setParam_key("webstyle");
			model.setParam_value(webTemplate.getStyle_path());
			model.setMemo(webTemplate.getId());
			model.setCreate_time(DateTime.currentTimeStamp());
			model.setCreate_user(getSysUser().getName());
			model.setIsdelete("0");
			templateParamService.insert(model);
		}
		return ViewFactory.json(RetMsg.success("模板设置成功！"));
	}
	
	
	/**
	 * 选择文章
	 * @param templateName
	 * @param paramkey
	 * @return
	 */
	public View selectArticle(String paramKey, String paramValue) {
		MyBeetlView view = new MyBeetlView("/webpublish/article_select.html");
		List<CmsTopic> topics = cmsTopicService.queryArticleColumn();
		view.put("topics", topics);
		view.put("paramkey", paramKey);
		CmsArticle article  = cmsArticleService.query(paramValue);
		if(article == null){
			article = new CmsArticle();
		}
		view.put("article", article);
		return view;
	}
	
	public View selectArticleList() {
		CmsArticle article = PuffContext.getModel(CmsArticle.class);
		String topicid = "888888888888888888";
		if(article != null && StringUtil.notEmpty(article.getTopic_id())){
			topicid = article.getTopic_id();
		}
		PageRecord<CmsArticle> data = cmsArticleService.queryTopicArticle(topicid, article, getCommonParam());
		return ViewFactory.json(data);
	}
	
	
}










