package cn.aresoft.cms.manager.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.DateTime;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.Record;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsArticleCache;
import cn.aresoft.cms.common.cache.CmsArticleContentCache;
import cn.aresoft.cms.common.cache.CmsPropertiesCache;
import cn.aresoft.cms.common.cache.CmsTopicArticleCache;
import cn.aresoft.cms.common.model.CmsAccessPermission;
import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.model.CmsArticlePreview;
import cn.aresoft.cms.common.model.CmsModel;
import cn.aresoft.cms.common.model.CmsTopic;
import cn.aresoft.cms.common.service.CmsAccessPermissionService;
import cn.aresoft.cms.common.service.CmsArticlePreviewService;
import cn.aresoft.cms.common.service.CmsArticleService;
import cn.aresoft.cms.common.service.CmsModelService;
import cn.aresoft.cms.common.service.CmsTopicService;
import cn.aresoft.cms.manager.validator.CmsArticleValidator;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.service.record.RecordService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.admin.CmsReservekeyService;
import cn.aresoft.manager.service.sys.SysUserService;


@Controller(value = "/admin/cms/article", scope = BeanScope.SINGLETON)
public class CmsArticleController extends CommonController {

	private final String TOP_PARENTID = "888888888888888888";
	@Inject
	private CmsReservekeyService cmsReservekeyService;//保留字管理service
	@Inject
	private CmsTopicService cmsTopicService;

	@Inject
	private CmsModelService cmsModelService;

	@Inject
	private CmsAccessPermissionService cmsAccessPermissionService;

	@Inject
	private CmsArticleService cmsArticleService;
	
	@Inject
	private CmsArticlePreviewService cmsArticlePreviewService;

	@Inject
	private CmsArticleCache cmsArticleCache;

	@Inject
	private CmsArticleContentCache cmsArticleContentCache;

	@Inject
	private CmsTopicArticleCache cmsTopicArticleCache;
	
	@Inject
	private  RecordService recordService;
	
	@Inject
	private CmsPropertiesCache cmsPropertiesCache;
	
	@Inject
	private SysUserService sysUserService;

	public View index() {
		return new MyBeetlView("/cms/article/article_index.html");
	}

	public View left() {
		return new MyBeetlView("/cms/article/article_left.html");
	}

	
	public View list(String topicId) {
		String preview_ctx = cmsPropertiesCache.findValueFormCache("portal", "preview_ctx");
		MyBeetlView view = new MyBeetlView("/cms/article/article_list.html");
		view.put("topicId", topicId);
		view.put("preview_ctx", preview_ctx);
		return view;
	}

	/***
	 * 加载右侧列表
	 * update by yyj 2016-11-09加载右侧列表权限添加
	 * @return
	 */
	public View json(String topicId) {
		CommonParam param = getCommonParam();
		CmsArticlePreview article = PuffContext.getModel(CmsArticlePreview.class);
		//---update by yyj 2016-11-09栏目右侧列表权限添加start
		//---如果是系统管理员角色,加载所有栏目；如果不是系统管理员角色，查询当前角色下的栏目列表
		//----598735761148997632为默认系统管理员的角色id
		SysUser sysUser=getSysUser();
		PageRecord<CmsArticlePreview> articles=new PageRecord<CmsArticlePreview>();
		//查询用户所拥有的的角色
		List<String> roleIds=PuffContext.getSessionAttribute("user_roleIds");
		if(roleIds==null||roleIds.size()<1){
			roleIds=sysUserService.queryUserRole(sysUser.getId());
		}
		if(roleIds.contains("598735761148997632")){//【系统管理员|】
			articles = cmsArticlePreviewService.queryTopicArticle(topicId, article, param);
		}else{
			articles=cmsArticlePreviewService.queryTopicLimitArticle(sysUser.getId(), topicId, article, param);
		}
		//---update by yyj 2016-11-09栏目右侧列表权限添加end
		//PageRecord<CmsArticlePreview> articles = cmsArticlePreviewService.queryTopicArticle(topicId, article, param);
		return ViewFactory.json(articles);
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
			topics = cmsTopicService.findArticleColumnByParentId(parent_id);
		}else{
			topics=cmsTopicService.findArticleColumnByUserId(sysUser.getId(),1);
			parent_id="";//无总节点
			
		}
		//---update by yyj 2016-11-09栏目左侧树状结构权限添加end
		//List<CmsTopic> topics = cmsTopicService.findArticleColumnByParentId(parent_id);
		MyBeetlView view = new MyBeetlView("/cms/article/article_tree.html");
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
			//topics = cmsTopicService.findArticleColumnByParentId(parent_id);
			topics = cmsTopicService.findArticleByParentId(parent_id);
		}else{
			topics=cmsTopicService.findArticleByUserId(sysUser.getId(),1);
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

	/**
	 * update by yyj 2016-11-10所属栏目下拉框根据用户所拥有的角色栏目权限动态展示
	 * @param topicId
	 * @return
	 */
	public View add(String topicId) {
		MyBeetlView view = new MyBeetlView("/cms/article/article_add.html");
		view.put("topicId", topicId);
		//---update by yyj 2016-11-10所属栏目下拉框根据用户所拥有的角色栏目权限动态展示start
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
			topics = cmsTopicService.queryList();
		}else{
			topics = cmsTopicService.findArticleColumnByUserId(sysUser.getId(),2);
		}
		//---update by yyj 2016-11-10所属栏目下拉框根据用户所拥有的角色栏目权限动态展示end		
		//List<CmsTopic> topics = cmsTopicService.queryList();
		view.put("topics", topics);
		List<CmsModel> models = cmsModelService.queryList();
		view.put("models", models);
		List<CmsAccessPermission> list = cmsAccessPermissionService.queryList();
		for (Iterator<CmsAccessPermission> iterator = list.iterator(); iterator.hasNext();) {
			CmsAccessPermission cmsAccessPermission = iterator.next();
			if ("2".equals(cmsAccessPermission.getStatus())) {
				iterator.remove();
			}
		}
		//List<Record> plist = recordService.queryProInfoList("");
		//view.put("plist", plist);
		view.put("permissions", list);
		return view;
	}

	@Validate(CmsArticleValidator.class)
	public View insert() {
		String article_uuid = UUID.randomUUID().toString();//生成uuid作为实际同一篇文章标识
		CmsArticlePreview article = PuffContext.getModel(CmsArticlePreview.class);
		String isTop = PuffContext.getParameter("isTop");
		if (!"1".equals(isTop)) {
			article.setI_top(0);
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
			article.setAccess_permission(access_permission);
		}
		String[] pro_idsArr = PuffContext.getParameterValues("pro_idsarr");
		String pro_ids = "";
		//处理产品id
		if(pro_idsArr != null && pro_idsArr.length>0){
			for(int i=0;i<pro_idsArr.length;i++){
				pro_ids += pro_idsArr[i]+"|";
			}
			if(StringUtil.notEmpty(pro_ids)){
				pro_ids = pro_ids.substring(0,pro_ids.length()-1);
			}
		}
		article.setPro_ids(pro_ids);
		article.setStatus(0);
		article.setCreate_time(DateTime.currentTimeStamp());
		article.setCreate_user(getSysUser().getName());
		
		List<String> topicIds = PuffContext.getParameterList("topicIds");//获取新增文章关联的栏目id
		for (String toi : topicIds) {//遍历文章关联的栏目id，分别保存对应不同栏目的文章
			CmsArticlePreview article_add = new CmsArticlePreview();//new一个CmsArticlePreview对象，存入数据库
			article_add = article;
			article_add.setId(null);
			article_add.setTopic_id(toi);
			article_add.setArticle_resource_id(article_uuid);
			cmsArticlePreviewService.insert(article_add);
			
			if (PuffContext.getParameter("type_id").equals("1")) {
				CmsTopic topic = cmsTopicService.query(article_add.getTopic_id());
				article_add.setArticle_location(topic.getLocation() + "/" + article_add.getId());
				cmsArticlePreviewService.updateArticleUrl(article_add.getId(), topic.getLocation(), topic.getLocation());
			}
			
			String content = PuffContext.getParameter("content");
			//add by yyj 2016-08-30 替换保留字为"***"start
			if(StringUtil.notBlank(content)){
				String newChar="***";
				//获取系统设定的关键字
				List<String> reserveKeyList=cmsReservekeyService.queryReservekeyList();
				if(reserveKeyList!=null){
					for(String str:reserveKeyList){
						content=content.replace(str, newChar);
					}
				}
			}
			////add by yyj 2016-08-30 替换保留字为"***"end
			cmsArticlePreviewService.saveArticleContent(article_add.getId(), content);
		}
//		cmsArticlePreviewService.insert(article);
//		
//		if (PuffContext.getParameter("type_id").equals("1")) {
//			CmsTopic topic = cmsTopicService.query(article.getTopic_id());
//			article.setArticle_location(topic.getLocation() + "/" + article.getId());
//			cmsArticlePreviewService.updateArticleUrl(article.getId(), topic.getLocation(), topic.getLocation());
//		}
		
//		String content = PuffContext.getParameter("content");
//		cmsArticlePreviewService.saveArticleContent(article.getId(), content);
		return ViewFactory.json(RetMsg.success("新增成功!"));
	}

	/***
	 * update by yyj 2016-11-10所属栏目下拉框根据用户所拥有的角色栏目权限动态展示
	 * @param id
	 * @return
	 */
	public View edit(String id) {
		MyBeetlView view = new MyBeetlView("/cms/article/article_edit.html");
		CmsArticlePreview article = cmsArticlePreviewService.query(id);
		if(article.getPro_ids() == null){
			article.setPro_ids("");
		}
		view.put("article", article);
		String content = cmsArticlePreviewService.queryArticleContent(id);
		view.put("content", content);
		//---update by yyj 2016-11-10所属栏目下拉框根据用户所拥有的角色栏目权限动态展示start
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
			topics = cmsTopicService.queryList();
		}else{
			topics = cmsTopicService.findArticleColumnByUserId(sysUser.getId(),2);
		}
		//---update by yyj 2016-11-10所属栏目下拉框根据用户所拥有的角色栏目权限动态展示end				
		//List<CmsTopic> topics = cmsTopicService.queryList();
		view.put("topics", topics);
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

		String[] access_permission = article.getAccess_permission();
		if (access_permission != null && access_permission.length > 0) {
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (int i = 0; i < access_permission.length; i++) {
				map.put(access_permission[i], i + 1);
			}
			view.put("access_permission", map);
		}
		
		//查询产品表
		//List<Record> plist = recordService.queryProInfoList("");
		//view.put("plist", plist);
		
		return view;
	}

	/**
	 * 1,获取article_resource_id（文章同一来源的标识符），据此查出所有来源同一文章的文章，修改所有文章的基本信息；
	 * 2,获取topicIds，判断topicIds若为空，先对来源相同文章的所有文章做基本信息update操作，然后将当前这篇文章删除；
	 *   若topicIds不为空，根据当前这篇文章id查询数据库该文章对应的栏目id0，若topicIds中包含id0，则对其他topicId
	 *   与文章关联，并新增文章；若topicIds中不包含id0，则将id0这篇文章删除，其他topicId都要判断当前文章来源是否已经
	 *   关联该栏目，若是则update，反之新增；
	 * 3,所有同一来源文章共同修改的字段：title,short_title,digest,author,content;其余单独修改；
	 * 时间：2016-09-01
	 * 修改人：wangd
	 * 
	 * @return
	 */
	@Validate(CmsArticleValidator.class)
	public View update() {
		CmsArticlePreview article = PuffContext.getModel(CmsArticlePreview.class);
		String id_old = article.getId();//获取当前文章id
		//获取需要共同修改的字段
		String title_new = article.getTitle();//文章标题
		String short_title_new = article.getShort_title();//文章小标题
		String digest_new = article.getDigest();//简短摘要
		String author_new = article.getAuthor();//文章作者
		
		//获取当前文章标识article_resource_id
		String article_resource_id = article.getArticle_resource_id();
	/*=================================查找所有同一来源文章，对其基本信息进行update-start=================================*/
		String sql = "select * from CMS_ARTICLE_PREVIEW ca where ca.article_resource_id = ? ";
		//获取文章来源唯一标识相同的所有文章
		List<CmsArticlePreview> article_list = cmsArticlePreviewService.queryList(sql, article_resource_id);
		//遍历article_list，获取文章对应的所有栏目id
		List<String> topicId_list = new ArrayList<String>();
		for (CmsArticlePreview cap : article_list) {
			if(! cap.getId().equals(id_old) ){//除当前form提交上来的这篇文章
				String topic_id = cap.getTopic_id();//获取当前文章对应栏目id
				
				topicId_list.add(topic_id);

				cap.setTitle(title_new);
				cap.setShort_title(short_title_new);
				cap.setDigest(digest_new);
				cap.setAuthor(author_new);
				cap.setLast_modify_time(DateTime.currentTimeStamp());
				cap.setLast_modify_user(getSysUser().getName());

				cmsArticlePreviewService.update(cap);//文章更新
				
				String content = PuffContext.getParameter("content");
				//add by yyj 2016-08-30 替换保留字为"***"start
				if(StringUtil.notBlank(content)){
					String newChar="***";
					//获取系统设定的关键字
					List<String> reserveKeyList=cmsReservekeyService.queryReservekeyList();
					if(reserveKeyList!=null){
						for(String str:reserveKeyList){
							content=content.replace(str, newChar);
						}
					}
				}
				////add by yyj 2016-08-30 替换保留字为"***"end
				cmsArticlePreviewService.saveArticleContent(cap.getId(), content);
			}
		}
	/*=================================查找所有同一来源文章，对其基本信息进行update-end=================================*/
		CmsArticlePreview capforreview = cmsArticlePreviewService.query(id_old);//获取当前id的文章，方便获取原来关联的topicid，以便对比现在提交过来的关联topicids是否依旧包含原来的topicid
		String topicid_old = capforreview.getTopic_id();
		topicId_list.add(topicid_old);
	/*=================================查找所有同一来源文章所对应的栏目，判断新增栏目对应添加文章-start=================================*/	
		List<String> topicIds = PuffContext.getParameterList("topicIds");//获取表单提交过来的栏目id
		for (String toi : topicIds) {//遍历文章关联的栏目id，分别保存对应不同栏目的文章
			if(! topicId_list.contains(toi)){//条件成立说明文章还没有关联该栏目，所以新增关联该栏目的文章信息
				CmsArticlePreview article_add = new CmsArticlePreview();//new一个CmsArticlePreview对象，存入数据库
				article_add = article;
				article_add.setId(null);
				article_add.setTopic_id(toi);//关联栏目id
				article_add.setArticle_resource_id(article_resource_id);//文章同一来源标识符
				article_add.setTitle(title_new);//标题
				article_add.setShort_title(short_title_new);//小标题
				article_add.setDigest(digest_new);//简短摘要
				article_add.setAuthor(author_new);//文章作者
				article_add.setCreate_time(DateTime.currentTimeStamp());//创建时间
				article_add.setCreate_user(getSysUser().getName());//创建人
				
				cmsArticlePreviewService.insert(article_add);//新增toi栏目下该文章！！！！！
				
				//若是没有选择文章类型，默认为普通文章
				String type_id = PuffContext.getParameter("type_id");
				if(type_id==null || "".equals(type_id)){
					type_id = "1";
				}
				if ("1".equals(type_id)) {
					CmsTopic topic = cmsTopicService.query(article_add.getTopic_id());
					article_add.setArticle_location(topic.getLocation() + "/" + article_add.getId());
					cmsArticlePreviewService.updateArticleUrl(article_add.getId(), topic.getLocation(), topic.getLocation());
				}
				String content = PuffContext.getParameter("content");
				//add by yyj 2016-08-30 替换保留字为"***"start
				if(StringUtil.notBlank(content)){
					String newChar="***";
					//获取系统设定的关键字
					List<String> reserveKeyList=cmsReservekeyService.queryReservekeyList();
					if(reserveKeyList!=null){
						for(String str:reserveKeyList){
							content=content.replace(str, newChar);
						}
					}
				}
				////add by yyj 2016-08-30 替换保留字为"***"end
				cmsArticlePreviewService.saveArticleContent(article_add.getId(), content);
			}
		}
	/*=================================查找所有同一来源文章所对应的栏目，判断新增栏目对应添加文章-end=================================*/	
		
		if(!topicIds.contains(topicid_old)){//判断若成立，说明修改时已经将原来关联的栏目id删除，所以删除原来文章article_old
			CmsTopic topic = cmsTopicService.query(topicid_old);
			cmsArticleService.delete(id_old);
			cmsArticlePreviewService.delete(id_old);
			cmsArticleCache.remove(id_old);
			cmsArticleContentCache.remove(id_old);
			cmsTopicArticleCache.restTopicArticle(topic.getCode());
		}else{
			capforreview=article;
			//capforreview.setTitle(title_new);
			//capforreview.setShort_title(short_title_new);
			//capforreview.setDigest(digest_new);
			//capforreview.setAuthor(author_new);

			//置顶，每篇文章单独处理-2016-09-01
			String isTop = PuffContext.getParameter("isTop");
			String[] pro_idsArr = PuffContext.getParameterValues("pro_idsarr");
			if (!"1".equals(isTop)) {
				capforreview.setI_top(0);
			}else{
				capforreview.setI_top(article.getI_top());
			}
			
			//权限访问，每篇文章单独处理-2016-09-01
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
				capforreview.setAccess_permission(access_permission);
			}
			capforreview.setLast_modify_time(DateTime.currentTimeStamp());
			capforreview.setLast_modify_user(getSysUser().getName());
			//add by 2016-1002-start
			capforreview.setPublish_time(article.getPublish_time());
			capforreview.setBig_picurl(article.getBig_picurl());
			capforreview.setSmall_picurl(article.getSmall_picurl());
			//add by 2016-1002-end
			//对应产品，每篇文章单独处理-2016-09-01
			String pro_ids = "";
			//处理产品id
			if(pro_idsArr != null && pro_idsArr.length>0){
				for(int i=0;i<pro_idsArr.length;i++){
					pro_ids += pro_idsArr[i]+"|";
				}
				if(StringUtil.notEmpty(pro_ids)){
					pro_ids = pro_ids.substring(0,pro_ids.length()-1);
				}
			}
			capforreview.setPro_ids(pro_ids);
			capforreview.setStatus(0);
			
			cmsArticlePreviewService.update(capforreview);
			String content = PuffContext.getParameter("content");
			cmsArticlePreviewService.saveArticleContent(capforreview.getId(), content);
			
			// 改变了置顶
			String old_top = PuffContext.getParameter("old_top");
			if (!old_top.equals(String.valueOf(capforreview.getI_top()))) {
				CmsTopic topic = cmsTopicService.query(capforreview.getTopic_id());
				cmsTopicArticleCache.restTopicArticle(topic.getCode());
			}
			if (PuffContext.getParameter("type_id").equals("1")) {
				CmsTopic topic = cmsTopicService.query(capforreview.getTopic_id());
				capforreview.setArticle_location(topic.getLocation() + "/" + capforreview.getId());
				cmsArticlePreviewService.updateArticleUrl(capforreview.getId(), topic.getLocation(), topic.getLocation());
			}
		}
		
		return ViewFactory.json(RetMsg.success("修改成功!"));
	}

	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		for (String id : ids) {
			CmsArticlePreview article = cmsArticlePreviewService.query(id);
			CmsTopic topic = cmsTopicService.query(article.getTopic_id());
			cmsArticleService.delete(id);
			cmsArticlePreviewService.delete(id);
			cmsArticleCache.remove(id);
			cmsArticleContentCache.remove(id);
			cmsTopicArticleCache.restTopicArticle(topic.getCode());
		}
		return ViewFactory.json(RetMsg.success("删除成功!"));
	}

	/**
	 * 文章审核
	 * 
	 * @param id
	 * @param result
	 * @return
	 */
	public View audit(String id, String result) {
		cmsArticlePreviewService.auditArticle(id, result, getSysUser().getName());
		CmsArticlePreview articlePreview = cmsArticlePreviewService.query(id);
		CmsArticle article_old = cmsArticleService.query(id);
		String articleContent = cmsArticlePreviewService.queryArticleContent(id);
		CmsArticle	article = new CmsArticle();
		
		article.setAccess_permission(articlePreview.getAccess_permission());
		article.setAllow_review(articlePreview.getAllow_review());
		article.setAllpro_ids(articlePreview.getAllpro_ids());
		article.setArticle_auth(articlePreview.getArticle_auth());
		article.setArticle_location(articlePreview.getArticle_location());
		article.setAudit_time(articlePreview.getAudit_time());
		article.setAudit_user(articlePreview.getAudit_user());
		article.setAuthor(articlePreview.getAuthor());
		article.setBig_picurl(articlePreview.getBig_picurl());
		article.setCentre_tip(articlePreview.getCentre_tip());
		article.setCreate_time(articlePreview.getCreate_time());
		article.setCreate_user(articlePreview.getCreate_user());
		article.setDigest(articlePreview.getDigest());
		article.setExcerpts(articlePreview.getExcerpts());
		article.setI_top(articlePreview.getI_top());
		article.setId(articlePreview.getId());
		article.setLast_modify_time(articlePreview.getLast_modify_time());
		article.setLast_modify_user(articlePreview.getLast_modify_user());
		article.setMeta_desc(articlePreview.getMeta_desc());
		article.setMeta_keywords(articlePreview.getMeta_keywords());
		article.setMeta_title(articlePreview.getMeta_title());
		article.setModel_id(articlePreview.getModel_id());
		article.setOriginal(articlePreview.getOriginal());
		article.setPro_ids(articlePreview.getPro_ids());
		article.setPublish_time(articlePreview.getPublish_time());
		article.setShort_title(articlePreview.getShort_title());
		article.setSmall_picurl(articlePreview.getSmall_picurl());
		article.setSource(articlePreview.getSource());
		article.setSource_url(articlePreview.getSource_url());
		article.setStatus(articlePreview.getStatus());
		article.setTag(articlePreview.getTag());
		article.setTitle(articlePreview.getTitle());
		article.setTitle_style(articlePreview.getTitle_style());
		article.setTopic_id(articlePreview.getTopic_id());
		article.setType_id(articlePreview.getType_id());
		article.setArticle_resource_id(articlePreview.getArticle_resource_id());//新增-2016-09-02w
		cmsArticleService.delete(article.getId());
		cmsArticleService.insert(article);
		cmsArticleService.saveArticleContent(article.getId(), articleContent);
		
		// 审核通过
		if ("1".equals(result)) {
			cmsArticleCache.cache( id, article);
			cmsArticleContentCache.cache(id, articleContent);
		} else {
			cmsArticleCache.remove( id);
			cmsArticleContentCache.remove(id);
		}
		CmsTopic topic = cmsTopicService.query(article.getTopic_id());
		cmsTopicArticleCache.restTopicArticle(topic.getCode());
		if(article_old!=null&&!article_old.getArticle_location().equals(articlePreview.getArticle_location())){
			CmsTopic old_topic = cmsTopicService.query(article_old.getTopic_id());
			if(old_topic!=null){
				cmsTopicArticleCache.restTopicArticle(old_topic.getCode());
			}
		}
		return ViewFactory.json(RetMsg.success("操作成功!"));
	}
	
	/**
	 * 通过产品名称查询产品,名称根据@分割
	 * @return
	 */
	public View queryProByName(){
		try{
			String pro_name=PuffContext.getParameter("pro_name");
			List<Record> plist = recordService.queryProInfoList(pro_name);
			return ViewFactory.json(RetMsg.success(plist));
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return ViewFactory.json(RetMsg.error("查询失败"));
	}
	
	/**
	 * 拷贝文章选择栏目弹窗
	 * @return
	 */
	public View copyTo(String id){
		View view=new MyBeetlView("/cms/article/article_copy.html");
		view.put("id", id);
		CmsArticlePreview cmsArticlePreview=cmsArticlePreviewService.query(id);
		view.put("vo", cmsArticlePreview);
		//---update by yyj 2016-11-10所属栏目下拉框根据用户所拥有的角色栏目权限动态展示start
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
			topics = cmsTopicService.queryList();
		}else{
			topics = cmsTopicService.findArticleColumnByUserId(sysUser.getId(),2);
		}
		//---update by yyj 2016-11-10所属栏目下拉框根据用户所拥有的角色栏目权限动态展示end				
		//List<CmsTopic> topics = cmsTopicService.queryList();
		view.put("topics", topics);
		return view;
	}
	
	/**
	 * 拷贝文章到具体的栏目下
	 * @return
	 */
	public View copy(){
		try{
			String id=PuffContext.getParameter("id");
			if(StringUtil.blank(id)){
				return ViewFactory.json(RetMsg.error("请选择要拷贝的文章"));
			}
			CmsArticlePreview cmsArticlePreview=cmsArticlePreviewService.query(id);
			List<String> topicIds = PuffContext.getParameterList("topicIds");//获取新增文章关联的栏目id
			if(topicIds==null||topicIds.size()<=0){
				return ViewFactory.json(RetMsg.error("请选择拷贝到的栏目"));
			}
			CmsArticlePreview newCmsArticlePreview=null;
			for(int i=0;i<topicIds.size();i++){
				newCmsArticlePreview=cmsArticlePreview;
				newCmsArticlePreview.setId(null);
				newCmsArticlePreview.setTopic_id(topicIds.get(i));
				cmsArticlePreviewService.insert(newCmsArticlePreview);
				if ("1".equals(cmsArticlePreview.getType_id())) {
					CmsTopic topic = cmsTopicService.query(topicIds.get(i));
					//newCmsArticlePreview.setArticle_location(topic.getLocation() + "/" + newCmsArticlePreview.getId());
					cmsArticlePreviewService.updateArticleUrl(newCmsArticlePreview.getId(), topic.getLocation(), topic.getLocation());
				}
				
			}
			return ViewFactory.json(RetMsg.success("拷贝成功"));
		}catch(Exception ex){
			ex.printStackTrace();
			return ViewFactory.json(RetMsg.error("拷贝异常"));
		}
	
	}
}
