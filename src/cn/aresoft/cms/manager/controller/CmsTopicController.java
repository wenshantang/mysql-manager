package cn.aresoft.cms.manager.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.ListUtil;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsArticleCache;
import cn.aresoft.cms.common.cache.CmsTopicCache;
import cn.aresoft.cms.common.model.CmsAccessPermission;
import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.model.CmsModel;
import cn.aresoft.cms.common.model.CmsTemplate;
import cn.aresoft.cms.common.model.CmsTopic;
import cn.aresoft.cms.common.model.CmsTopicAttr;
import cn.aresoft.cms.common.service.CmsAccessPermissionService;
import cn.aresoft.cms.common.service.CmsArticleService;
import cn.aresoft.cms.common.service.CmsModelService;
import cn.aresoft.cms.common.service.CmsTemplateService;
import cn.aresoft.cms.common.service.CmsTopicService;
import cn.aresoft.cms.manager.validator.CmsTopicValidator;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysUserService;

@Controller(value = "/admin/cms/topic", scope = BeanScope.SINGLETON)
public class CmsTopicController extends CommonController {

	private final String TOP_PARENTID = "888888888888888888";
	@Inject
	private CmsTopicService cmsTopicService;

	@Inject
	private CmsTemplateService cmsTemplateService;

	@Inject
	private CmsModelService cmsModelService;

	@Inject
	private CmsTopicCache cmsTopicCache;

	@Inject
	private CmsAccessPermissionService cmsAccessPermissionService;

	@Inject
	private CmsArticleService cmsArticleService;

	@Inject
	private CmsArticleCache cmsArticleCache;
	
	@Inject
	private SysUserService sysUserService;

	public View index() {
		return new MyBeetlView("/cms/topic/topic_index.html");
	}

	public View left() {
		return new MyBeetlView("/cms/topic/topic_left.html");
	}

	/***
	 * 加载右侧列表
	 * update by yyj 2016-11-09加载右侧列表权限添加
	 * @return
	 */
	public View list(String parent_id) {
		//---update by yyj 2016-11-09栏目右侧列表权限添加start
		//---如果是系统管理员角色,加载所有栏目；如果不是系统管理员角色，查询当前角色下的栏目列表
		//----598735761148997632为默认系统管理员的角色id
		SysUser sysUser=getSysUser();
		List<CmsTopic> topics=new ArrayList<CmsTopic>();
		//查询用户所拥有的的角色
		List<String> roleIds=PuffContext.getSessionAttribute("user_roleIds");
		if(roleIds==null||roleIds.size()<1){
			roleIds=sysUserService.queryUserRole(sysUser.getId());
		}
		if(roleIds.contains("598735761148997632")||!parent_id.equals(TOP_PARENTID)){//【系统管理员|非系统管理员点击子目录】
			topics = cmsTopicService.findByParentId(parent_id);
		}else{
			topics=cmsTopicService.queryTopicByUserId(sysUser.getId(),2);
			parent_id="";//无总节点
		}
		//---update by yyj 2016-11-09栏目右侧列表权限添加end
		//List<CmsTopic> topics = cmsTopicService.findByParentId(parent_id);
		MyBeetlView view = new MyBeetlView("/cms/topic/topic_list.html");
		view.put("topics", topics);
		view.put("parent_id", parent_id);
		return view;
	}

	/***
	 * 加载左侧树状结构
	 * update by yyj 2016-11-09栏目左侧树状结构权限添加
	 * @return
	 */
	/*public View tree() {
		String root = PuffContext.getPara("root");
		String parent_id = "source".equals(root) ? TOP_PARENTID : root;
		//---update by yyj 2016-11-09栏目左侧树状结构权限添加start
		//---如果是系统管理员角色,加载所有栏目；如果不是系统管理员角色，查询当前角色下的栏目列表
		//----598735761148997632为默认系统管理员的角色id
		SysUser sysUser=getSysUser();
		List<CmsTopic> topics=new ArrayList<CmsTopic>();
		//查询用户所拥有的的角色
		List<String> roleIds=PuffContext.getSessionAttribute("user_roleIds");
		if(roleIds==null||roleIds.size()<1){
			roleIds=sysUserService.queryUserRole(sysUser.getId());
		}
		if(roleIds.contains("598735761148997632")||!"source".equals(root)){//【系统管理员|非系统管理员查询当前节点的子目录】
			topics = cmsTopicService.findByParentId(parent_id);
		}else{
			topics=cmsTopicService.queryTopicByUserId(sysUser.getId(),1);
			parent_id="";//无总节点
		}
		//---update by yyj 2016-11-09栏目左侧树状结构权限添加end
		//List<CmsTopic> topics = cmsTopicService.findByParentId(parent_id);
		MyBeetlView view = new MyBeetlView("/cms/topic/topic_tree.html");
		view.put("topics", topics);
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
		
		SysUser sysUser=getSysUser();
		//查询用户所拥有的的角色
		List<String> roleIds=PuffContext.getSessionAttribute("user_roleIds");
		List<Map<String,Object>> topics = new ArrayList<Map<String,Object>>();
		
		if(roleIds==null||roleIds.size()<1){
			roleIds=sysUserService.queryUserRole(sysUser.getId());
		}
		if(roleIds.contains("598735761148997632")||!"source".equals(root)){//【系统管理员|非系统管理员查询当前节点的子目录】
			//topics = cmsTopicService.findByParentId(parent_id);
			topics = cmsTopicService.findTemplateByParentId(parent_id);
		}else{
			topics=cmsTopicService.findTemplateByUserId(sysUser.getId(),1);
			parent_id="";//无总节点		
		}

		Map<String,Object> map = new HashMap<String,Object>();
		JSONArray jsonObject = (JSONArray)JSONObject.toJSON(topics);
		map.put("code", "0000");
		map.put("topics", jsonObject.toJSONString());
		return ViewFactory.json(map);
	}
	
	public CmsTopic buildTopTopic() {
		CmsTopic parent = new CmsTopic();
		parent.setId(TOP_PARENTID);
		parent.setName("顶级栏目");
		parent.setLocation("/");
		return parent;
	}

	public CmsTopic buildLimitTopic() {
		CmsTopic parent = new CmsTopic();
		parent.setId("");
		parent.setName("--请选择--");
		parent.setLocation("");
		return parent;
	}
	
	/***
	 * 
	 * update by yyj 2016-11-10上级栏目下拉框根据用户所拥有的角色栏目权限动态展示
	 * @return
	 */
	public View add(String parent_id) {
		MyBeetlView view = new MyBeetlView("/cms/topic/topic_add.html");
		//---update by yyj 2016-11-10上级栏目下拉框根据用户所拥有的角色栏目权限动态展示start
		//---如果是系统管理员角色,加载所有栏目；如果不是系统管理员角色，查询当前角色下的栏目列表
		//----598735761148997632为默认系统管理员的角色id
		SysUser sysUser=getSysUser();
		CmsTopic parent =null;
		List<CmsTopic> topics =new ArrayList<CmsTopic>();
		//查询用户所拥有的的角色
		List<String> roleIds=PuffContext.getSessionAttribute("user_roleIds");
		if(roleIds==null||roleIds.size()<1){
			roleIds=sysUserService.queryUserRole(sysUser.getId());
		}
		if(roleIds.contains("598735761148997632")){//【系统管理员】
			view.put("topTopic", buildTopTopic());//顶级栏目
			parent = cmsTopicService.query(parent_id);
			if (parent == null) {
				parent = buildTopTopic();
			}
			topics = cmsTopicService.queryList();
		}else{
			view.put("topTopic", buildLimitTopic());////顶级栏目
			parent=buildLimitTopic();//无总节点
			topics = cmsTopicService.queryTopicByUserId(sysUser.getId(),2);
		}
		//---update by yyj 2016-11-10上级栏目下拉框根据用户所拥有的角色栏目权限动态展示end
		/*CmsTopic parent = cmsTopicService.query(parent_id);
		if (parent == null) {
			parent = buildTopTopic();
		}*/
		view.put("parent", parent);
		//List<CmsTopic> topics = cmsTopicService.queryList();
		//view.put("topTopic", buildTopTopic());
		view.put("topics", topics);
		List<CmsTemplate> templates = cmsTemplateService.findHtmlTemplate();
		view.put("templates", templates);

		List<CmsAccessPermission> list = cmsAccessPermissionService.queryList();
		for (Iterator<CmsAccessPermission> iterator = list.iterator(); iterator.hasNext();) {
			CmsAccessPermission cmsAccessPermission = iterator.next();
			if ("2".equals(cmsAccessPermission.getStatus())) {
				iterator.remove();
			}
		}
		view.put("permissions", list);
		return view;
	}

	public View add_attr(String topic_id) {
		return new MyBeetlView("/cms/topic/add_attr.html", "topic_id", topic_id);
	}

	public View insert_attr() {
		CmsTopicAttr topicAttr = PuffContext.getModel(CmsTopicAttr.class);
		cmsTopicService.saveTopicAttr(topicAttr);
		return new MyBeetlView("/cms/topic/add_attr.html");
	}

	@Validate(CmsTopicValidator.class)
	public View insert() {
		CmsTopic topic = PuffContext.getModel(CmsTopic.class);
		String[] access_permission = PuffContext.getParameterValues("permissions");
		if (access_permission != null && access_permission.length > 0) {
			Map<Integer, String> map = new TreeMap<Integer, String>(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o1.compareTo(o2);
				}
			});
			for (String permissionId : access_permission) {
				int idx = PuffContext.getIntParam("idx_" + permissionId, 0);
				map.put(idx, permissionId);
			}
			Collection<String> values = map.values();
			access_permission = values.toArray(new String[] {});
			topic.setAccess_permission(access_permission);
		}
		topic.setCode(topic.getCode().replace("-", "_"));
		cmsTopicService.insert(topic);
		if ("1".equals(topic.getIs_show())) {
			cmsTopicCache.addTopic(topic);
		}
		return ViewFactory.json(RetMsg.success("新增成功!"));
	}

	/**
	 * update by yyj 2016-11-10上级栏目下拉框根据用户所拥有的角色栏目权限动态展示
	 * @param id
	 * @return
	 */
	public View edit(String id) {
		MyBeetlView view = new MyBeetlView("/cms/topic/topic_edit.html");
		CmsTopic topic = cmsTopicService.query(id);
		view.put("topic", topic);
		List<CmsTemplate> templates = cmsTemplateService.findHtmlTemplate();
		view.put("templates", templates);
		//---update by yyj 2016-11-10上级栏目下拉框根据用户所拥有的角色栏目权限动态展示start
		//---如果是系统管理员角色,加载所有栏目；如果不是系统管理员角色，查询当前角色下的栏目列表
		//----598735761148997632为默认系统管理员的角色id
		SysUser sysUser=getSysUser();
		List<CmsTopic> topics =new ArrayList<CmsTopic>();
		//查询用户所拥有的的角色
		List<String> roleIds=PuffContext.getSessionAttribute("user_roleIds");
		if(roleIds==null||roleIds.size()<1){
			roleIds=sysUserService.queryUserRole(sysUser.getId());
		}
		if(roleIds.contains("598735761148997632")){//【系统管理员】
			view.put("topTopic", buildTopTopic());//顶级栏目
			topics = cmsTopicService.queryList();
		}else{
			view.put("topTopic", cmsTopicService.query(topic.getParent_id()));////顶级栏目为当前节点的父节点
			topics = cmsTopicService.queryTopicByUserId(sysUser.getId(),2);
		}
		//---update by yyj 2016-11-10上级栏目下拉框根据用户所拥有的角色栏目权限动态展示end		
		//List<CmsTopic> topics = cmsTopicService.queryList();
		view.put("topics", topics);
		//view.put("topTopic", buildTopTopic());
		List<CmsModel> models = cmsModelService.queryList();
		view.put("models", models);

		List<CmsAccessPermission> list = cmsAccessPermissionService.queryList();
		for (Iterator<CmsAccessPermission> iterator = list.iterator(); iterator.hasNext();) {
			CmsAccessPermission cmsAccessPermission = iterator.next();
			if ("2".equals(cmsAccessPermission.getStatus())) {
				iterator.remove();
			}
		}
		view.put("permissions", list);

		String[] access_permission = topic.getAccess_permission();
		if (access_permission != null && access_permission.length > 0) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (int i = 0; i < access_permission.length; i++) {
				map.put(access_permission[i], i + 1);
			}
			view.put("access_permission", map);
		}

		List<CmsTopicAttr> attrs = cmsTopicService.queryTopicAttr(id);
		view.put("attrs", attrs);
		return view;
	}

	@Validate(CmsTopicValidator.class)
	public View update() {
		CmsTopic topic = PuffContext.getModel(CmsTopic.class);
		String old_location = PuffContext.getParameter("old_location");
		if (!old_location.equals(topic.getLocation())) {
			boolean exist = cmsTopicService.locationExist(topic.getLocation());
			if (exist) {
				ViewFactory.json(RetMsg.error("访问路径已经存在!"));
			}
		}
		String[] access_permission = PuffContext.getParameterValues("permissions");
		if (access_permission != null && access_permission.length > 0) {
			Map<Integer, String> map = new TreeMap<Integer, String>(new Comparator<Integer>() {
				@Override
				public int compare(Integer o1, Integer o2) {
					return o1.compareTo(o2);
				}
			});
			for (String permissionId : access_permission) {
				int idx = PuffContext.getIntParam("idx_" + permissionId, 0);
				map.put(idx, permissionId);
			}
			Collection<String> values = map.values();
			access_permission = values.toArray(new String[] {});
			topic.setAccess_permission(access_permission);
		}
		cmsTopicService.update(topic);
		// 改变了访问路径
		if (!old_location.equals(topic.getLocation())) {
			cmsTopicCache.deleteTopic(old_location);
			List<String> articleIds = cmsTopicService.queryTopicArticle(topic.getCode());
			if (ListUtil.notEmpty(articleIds)) {
				for (String id : articleIds) {
					cmsArticleService.updateArticleUrl(id, old_location, topic.getLocation());
					CmsArticle ca = cmsArticleCache.get(id);
					ca.setArticle_location(topic.getLocation() + "/" + id);
					cmsArticleCache.update(id, ca);
				}
			}
		}
		// 禁止访问
		if (!"1".equals(topic.getIs_show())) {
			cmsTopicCache.deleteTopic(topic.getLocation());
		} else {
			cmsTopicCache.addTopic(topic);
		}
		return ViewFactory.json(RetMsg.success("修改成功!"));
	}

	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		for (String id : ids) {
			CmsTopic topic = cmsTopicService.query(id);
			cmsTopicService.deleteTopic(id);
			cmsTopicCache.deleteTopic(topic.getLocation());
		}
		return ViewFactory.json(RetMsg.success("删除成功!"));
	}

	public View save_idx() {
		String idxs = PuffContext.getParameter("idxs");
		String[] arr = idxs.split("\\|");
		cmsTopicService.updateIdx(arr);
		return ViewFactory.json(RetMsg.success("排列顺序更新成功!"));
	}
}
