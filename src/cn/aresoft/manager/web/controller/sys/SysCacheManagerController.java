package cn.aresoft.manager.web.controller.sys;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.StringUtil;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsArticleCache;
import cn.aresoft.cms.common.cache.CmsArticleContentCache;
import cn.aresoft.cms.common.cache.CmsBannerCache;
import cn.aresoft.cms.common.cache.CmsPropertiesCache;
import cn.aresoft.cms.common.cache.CmsTemplateCache;
import cn.aresoft.cms.common.cache.CmsTopicArticleCache;
import cn.aresoft.cms.common.cache.CmsTopicCache;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysDictService;
import cn.aresoft.manager.web.controller.BaseController;
/**
 * 系统缓存管理
 * @author yyj
 *
 */
@Controller(value = "/admin/sys/cacheManager", scope = BeanScope.SINGLETON)
public class SysCacheManagerController extends BaseController {

	@Inject
	CmsArticleCache cmsArticleCache;
	@Inject
	CmsArticleContentCache cmsArticleContentCache;
	@Inject
	CmsTopicCache cmsTopicCache;
	@Inject
	CmsTopicArticleCache cmsTopicArticleCache;
	@Inject
	CmsTemplateCache cmsTemplateCache;
	@Inject
	SysDictService sysDictService;
	@Inject
	CmsPropertiesCache  cmsPropertiesCache;
	@Inject
	CmsBannerCache  cmsBannerCache;
	public View index(){
		return new MyBeetlView("/sys/cacheManager/cacheManager_index.html");
	}
	
	public View clearSysCache(int cacheFlag){
		try {
			switch(cacheFlag){
			case 1:
				clearArticleCache();
				break;
			case 2:
				clearTopicCache();
				break;
			case 3:
				clearTemplateCache();
				break;
			case 4:
				clearSysDictCache();
				break;
			case 5:
				clearCmsPropertiesCache();
				break;
			case 6:
				clearBannerCache();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("清理缓存错误>>>", e);
			return ViewFactory.json(RetMsg.error("清理缓存异常"));
		}
		return ViewFactory.json(RetMsg.success("清理缓存成功"));
	}
	
	/**
	 * 清理文章缓存
	 */
	private void clearArticleCache(){
		String article_id=PuffContext.getParameter("article_id");
		if(StringUtil.notBlank(article_id)){
			cmsArticleCache.remove(article_id);
			cmsArticleContentCache.remove(article_id);
		}else{
			cmsArticleCache.clear();
			cmsArticleContentCache.clear();
		}
		
	}
	
	/***
	 * 清理栏目缓存
	 */
	private void clearTopicCache(){
		String topic_url=PuffContext.getParameter("topic_url");
		if(StringUtil.notBlank(topic_url)){
			cmsTopicCache.remove(topic_url);
			cmsTopicArticleCache.remove(topic_url);
		}else{
			cmsTopicCache.clear();
			cmsTopicArticleCache.clear();
		}
		
	}
	
	/**
	 * 清理模板缓存
	 */
	private void clearTemplateCache(){
		String template_id=PuffContext.getParameter("template_id");
		if(StringUtil.notBlank(template_id)){
			cmsTemplateCache.remove(template_id);
		}else{
			cmsTemplateCache.clear();
		}
		
	}
	
	/***
	 * 清理后台数据字典缓存
	 */
	private void clearSysDictCache(){
		String sys_dict_type=PuffContext.getParameter("sys_dict_type");
		if(StringUtil.notBlank(sys_dict_type)){
			sysDictService.clearCacheByType(sys_dict_type);
		}else{
			sysDictService.clearCache();
		}
		
	}
	
	/***
	 * 清理cms前后台通用数据字典缓存
	 */
	private void clearCmsPropertiesCache(){
		String cms_properties_type=PuffContext.getParameter("cms_properties_type");
		if(StringUtil.notBlank(cms_properties_type)){
			cmsPropertiesCache.remove(cms_properties_type);
		}else{
			cmsPropertiesCache.clear();
		}
		
	}
	
	/***
	 * 清理banner缓存
	 */
	private void clearBannerCache(){
		String banner_type=PuffContext.getParameter("banner_type");
		if(StringUtil.notBlank(banner_type)){
			cmsBannerCache.remove(banner_type);
		}else{
			cmsBannerCache.clear();
		}
		
	}
	
	
}
