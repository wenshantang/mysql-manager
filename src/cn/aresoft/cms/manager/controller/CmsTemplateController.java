package cn.aresoft.cms.manager.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.puff.core.Puff;
import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.DateTime;
import com.puff.framework.utils.FileUtil;
import com.puff.framework.utils.ListUtil;
import com.puff.framework.utils.PathUtil;
import com.puff.framework.utils.StringUtil;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsTemplateCache;
import cn.aresoft.cms.common.model.CmsTemplate;
import cn.aresoft.cms.common.service.CmsTemplateService;
import cn.aresoft.cms.manager.synctemplate.TemplateContentSync;
import cn.aresoft.cms.manager.synctemplate.TemplateSyncPlugin;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.plugin.MyBeetlView;

@Controller(value = "/admin/cms/template", scope = BeanScope.SINGLETON)
public class CmsTemplateController extends CommonController {

	private final String TOP_PARENTID = "888888888888888888";

	@Inject
	private CmsTemplateService cmsTemplateService;

	@Inject
	private CmsTemplateCache cmsTemplateCache;

	private String getTemplatePath() {
		TemplateSyncPlugin plugin = Puff.getPlugin(TemplateSyncPlugin.class);
		if (plugin != null) {
			String templatePath = plugin.getTemplatePath();
			return templatePath;
		} else {
			return (PathUtil.getWebRootPath() + "/WEB-INF/template/").replace("/", String.valueOf(File.separatorChar));
		}
	}

	public View index() {
		return new MyBeetlView("/cms/template/index.html");
	}

	public View left() {
		return new MyBeetlView("/cms/template/left.html");
	}

	public View list(String parent_id) {
		List<CmsTemplate> templates = cmsTemplateService.findByParentId(parent_id);
		MyBeetlView view = new MyBeetlView("/cms/template/list.html");
		view.put("templates", templates);
		view.put("parent_id", parent_id);
		return view;
	}

	/*public View tree() {
		String root = PuffContext.getPara("root");
		String parent_id = "source".equals(root) ? TOP_PARENTID : root;
		List<CmsTemplate> templates = cmsTemplateService.findByParentId(parent_id);
		MyBeetlView view = new MyBeetlView("/cms/template/tree.html");
		view.put("templates", templates);
		view.put("parent_id", parent_id);
		return view;
	}*/
	/***
	 * 加载左侧树状结构
	 * update by luoyg 2016-12-15栏目左侧树新方法+权限
	 * 如果是系统管理员角色,加载所有栏目；如果不是系统管理员角色，查询当前角色下的栏目列表
	 * @return
	 */
	public View tree() {
		String root = PuffContext.getPara("root");
		String parent_id = "source".equals(root) ? TOP_PARENTID : root;	
		List<Map<String,Object>> topics = cmsTemplateService.findTemplateByParentId(parent_id);
		Map<String,Object> map = new HashMap<String,Object>();
		JSONArray jsonObject = (JSONArray)JSONObject.toJSON(topics);
		map.put("code", "0000");
		map.put("topics", jsonObject.toJSONString());
		return ViewFactory.json(map);
	}

	/**
	 * 新增文件夹页面
	 * 
	 * @param parent_id
	 * @return
	 */
	public View add_dir(String parent_id) {
		MyBeetlView view = new MyBeetlView("/cms/template/add_dir.html");
		String parentDir = cmsTemplateService.findPathById(parent_id);
		view.put("parent_id", parent_id);
		view.put("parentDir", StringUtil.empty(parentDir, "/"));
		return view;
	}

	/**
	 * 新增文件夹
	 * 
	 * @param parent_id
	 * @return
	 */
	public View insert_dir(String parent_id) {
		String dir = PuffContext.getParameter("dir");
		if (StringUtil.empty(dir)) {
			return ViewFactory.json(RetMsg.error("文件夹名称不能为空！"));
		}
		String path = cmsTemplateService.findPathById(parent_id);
		String parentDir = StringUtil.empty(path, "/");
		CmsTemplate tmplate = cmsTemplateService.findByDirAndParentId(dir, parent_id);
		File f = new File(getTemplatePath() + parentDir, dir);
		boolean file_exists = f.exists();// 文件夹是否已存在
		boolean database_exists = tmplate != null;// 数据库是否有记录
		if (database_exists && file_exists) {
			return ViewFactory.json(RetMsg.error("文件夹已经存在！"));
		}
		if (!file_exists) {
			f.mkdirs();
		}
		if (!database_exists) {
			CmsTemplate template = new CmsTemplate();
			template.setCreate_user(getSysUser().getName());
			template.setCreate_time(DateTime.currentTimeStamp());
			template.setDir(dir);
			template.setParent_id(parent_id);
			if (parentDir.endsWith("/")) {
				template.setPath(parentDir + dir);
			} else {
				template.setPath(parentDir + "/" + dir);
			}
			cmsTemplateService.insert(template);
		}
		return ViewFactory.json(RetMsg.success("文件夹创建成功！"));
	}

	/**
	 * 删除文件夹
	 * 
	 * @param id
	 * @return
	 */
	public View delete_dir(String id) {
		String path = cmsTemplateService.findPathById(id);
		File f = new File(getTemplatePath() + path);
		try {
			FileUtil.deleteDirectory(f);
			cmsTemplateService.deleteDir(id);
			List<CmsTemplate> list = cmsTemplateService.findByParentId(id);
			if (ListUtil.notEmpty(list)) {
				for (CmsTemplate ct : list) {
					cmsTemplateCache.remove(ct.getId());
					cmsTemplateCache.remove(ct.getPath());
				}
			}
		} catch (IOException e) {
			return ViewFactory.json(RetMsg.error("文件夹删除失败！"));
		}
		return ViewFactory.json(RetMsg.success("文件夹删除成功！"));
	}

	/**
	 * 新增模板
	 * 
	 * @param parent_id
	 * @return
	 */
	public View add(String parent_id) {
		MyBeetlView view = new MyBeetlView("/cms/template/add.html");
		String path = cmsTemplateService.findPathById(parent_id);
		String parentDir = StringUtil.empty(path, "/");
		view.put("parent_id", parent_id);
		view.put("parentDir", parentDir);
		return view;
	}

	/**
	 * 新增模板
	 * 
	 * @param parent_id
	 * @return
	 */
	public View insert(String parent_id) {
		String name = PuffContext.getParameter("name");
		if (StringUtil.empty(name)) {
			return ViewFactory.json(RetMsg.error("模板名称不能为空！"));
		}
		String content = PuffContext.getParameter("content");
		if (StringUtil.empty(content)) {
			return ViewFactory.json(RetMsg.error("模板内容不能为空！"));
		}
		String type = PuffContext.getParameter("type");
		if (StringUtil.empty(content)) {
			return ViewFactory.json(RetMsg.error("请选择模板类型！"));
		}
		String parentDir = PuffContext.getParameter("parentDir");
		CmsTemplate tmplate = cmsTemplateService.findByNameAndParentId(name, parent_id);
		File f = new File(getTemplatePath() + parentDir, name + ".html");
		File parentFile = f.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		boolean file_exists = f.exists();// 文件是否已存在
		boolean database_exists = tmplate != null;// 数据库是否有记录
		if (database_exists && file_exists) {
			return ViewFactory.json(RetMsg.error("模板已经存在！"));
		}
		FileUtil.saveFile(f, content, "UTF-8");
		if (!database_exists) {
			CmsTemplate template = new CmsTemplate();
			template.setCreate_user(getSysUser().getName());
			template.setCreate_time(DateTime.currentTimeStamp());
			template.setName(name);
			template.setParent_id(parent_id);
			template.setContent(PuffContext.getParameter("content"));
			if (parentDir.endsWith("/")) {
				template.setPath(parentDir + name + ".html");
			} else {
				template.setPath(parentDir + "/" + name + ".html");
			}
			template.setType(type);
			cmsTemplateService.insert(template);
			cmsTemplateCache.cache(template.getId(), template);
			cmsTemplateCache.cache(template.getPath(), template);

		}
		return ViewFactory.json(RetMsg.success("模板创建成功！"));
	}

	/**
	 * 修改模板
	 * 
	 * @param parent_id
	 * @return
	 */
	public View edit(String id) {
		MyBeetlView view = new MyBeetlView("/cms/template/edit.html");
		CmsTemplate template = cmsTemplateService.query(id);
		String content = template.getContent();
		if (StringUtil.notBlank(content)) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(content);
			content = m.replaceAll("");
			p = Pattern.compile("(<%include[^>]*%>)");
			m = p.matcher(content);
			List<CmsTemplate> includeTemplate = new ArrayList<CmsTemplate>();
			while (m.find()) {
				String group = m.group();
				String[] arr = group.split("\\,");
				group = arr[0];
				group = group.replace("<", "")//
						.replace(">", "")//
						.replace("%include", "")//
						.replace("%", "")//
						.replace("\"", "")//
						.replace("'", "")//
						.replace("\"", "")//
						.replace("{", "")//
						.replace("}", "")//
						.replace("(", "")//
						.replace(")", "");
				CmsTemplate t = cmsTemplateService.findByPath(group);
				if (t != null) {
					includeTemplate.add(t);
				}
			}
			view.put("includeTemplate", includeTemplate);
		}
		view.put("template", template);
		return view;
	}

	/**
	 * 修改模板
	 * 
	 * @param parent_id
	 * @return
	 */
	public View update() {
		String name = PuffContext.getParameter("name");
		String content = PuffContext.getParameter("content");
		String path = PuffContext.getParameter("path");
		String id = PuffContext.getParameter("id");
		String type = PuffContext.getParameter("type");
		if (StringUtil.empty(name)) {
			return ViewFactory.json(RetMsg.error("模板名称不能为空！"));
		}
		if (!name.equals(PuffContext.getParameter("old_name"))) {
			return ViewFactory.json(RetMsg.error("模板名称不能修改！"));
		}
		if (StringUtil.empty(content)) {
			return ViewFactory.json(RetMsg.error("模板内容不能为空！"));
		}
		if (StringUtil.empty(type)) {
			return ViewFactory.json(RetMsg.error("请选择模板类型！"));
		}
		File f = new File(getTemplatePath() + path);
		File parentFile = f.getParentFile();
		if (!parentFile.exists()) {
			parentFile.mkdirs();
		}
		FileUtil.saveFile(f, content, "UTF-8");
		CmsTemplate template = cmsTemplateService.query(id);
		template.setName(name);
		template.setContent(content);
		template.setType(type);
		template.setLast_modify_user(getSysUser().getName());
		template.setLast_modify_time(DateTime.currentTimeStamp());
		cmsTemplateService.update(template);
		cmsTemplateCache.update(template.getId(), template);
		cmsTemplateCache.update(template.getPath(), template);
		TemplateContentSync channel = TemplateContentSync.getInstance();
		channel.notifyContentUpdate(template.getPath());
		return ViewFactory.json(RetMsg.success("模板修改成功！"));
	}

	/**
	 * 删除模板
	 * 
	 * @param id
	 * @return
	 */
	public View delete(String id) {
		String path = cmsTemplateService.findPathById(id);
		File f = new File(getTemplatePath() + path);
		try {
			FileUtil.deleteQuietly(f);
			cmsTemplateService.delete(id);
			cmsTemplateCache.delete(id);
		} catch (Exception e) {
			return ViewFactory.json(RetMsg.error("模板删除失败！"));
		}
		return ViewFactory.json(RetMsg.success("模板删除成功！"));
	}
}
