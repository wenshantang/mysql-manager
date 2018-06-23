package cn.aresoft.cms.manager.controller;

import java.util.List;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Skip;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.DateTime;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsBannerCache;
import cn.aresoft.cms.common.model.CmsBanner;
import cn.aresoft.cms.common.model.CmsTopic;
import cn.aresoft.cms.common.service.CmsArticleService;
import cn.aresoft.cms.common.service.CmsBannerService;
import cn.aresoft.cms.common.service.CmsTopicService;
import cn.aresoft.cms.manager.validator.CmsBannerValidator;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.interceptor.UserPermissionInterceptor;
import cn.aresoft.manager.plugin.MyBeetlView;

/**
 * cms banner管理
 * 
 * @author dongchao
 *
 */

@Controller(value = "/admin/cms/banner", scope = BeanScope.SINGLETON)
public class CmsBannerController extends CommonController {

	@Inject
	private CmsBannerService cmsBannerService;

	@Inject
	private CmsBannerCache cmsBannerCache;

	@Inject
	private CmsTopicService cmsTopicService;

	@Inject
	private CmsArticleService cmsArticleService;

	public View index() {
		MyBeetlView view = new MyBeetlView("/cms/banner/banner_list.html");
		List<String> types = cmsBannerService.queryAllType();
		view.put("types", types);
		return view;
	}

	public View json() {
		PageRecord<CmsBanner> data = cmsBannerService.paging(PuffContext.getModel(CmsBanner.class), getCommonParam());
		return ViewFactory.json(data);
	}

	public View add() {
		List<String> types = cmsBannerService.queryAllType();
		MyBeetlView view = new MyBeetlView("/cms/banner/banner_add.html");
		view.put("types", types);
		List<CmsTopic> topics = cmsTopicService.queryList();
		view.put("topics", topics);
		return view;
	}

	@Skip.ONE(UserPermissionInterceptor.class)
	public View article() {
		MyBeetlView view = new MyBeetlView("/cms/banner/banner_article.html");
		return view;
	}

	@Validate(CmsBannerValidator.class)
	public View insert() {
		CmsBanner banner = PuffContext.getModel(CmsBanner.class);
		banner.setStatus(PuffContext.getIntParam("status", 0));
		banner.setCreate_time(DateTime.currentTimeStamp());
		banner.setCreate_user(getSysUser().getName());
		cmsBannerService.insert(banner);
		cmsBannerCache.cache(banner.getId(), banner);
		cmsBannerCache.restType();
		return ViewFactory.json(RetMsg.success("新增成功！"));
	}

	public View edit(String id) {
		CmsBanner banner = cmsBannerService.query(id);
		MyBeetlView view = new MyBeetlView("/cms/banner/banner_edit.html", "banner", banner);
		List<String> types = cmsBannerService.queryAllType();
		view.put("types", types);
		return view;
	}

	@Validate(CmsBannerValidator.class)
	public View update() {
		CmsBanner banner = PuffContext.getModel(CmsBanner.class);
		banner.setStatus(PuffContext.getIntParam("status", 0));
		banner.setCreate_time(DateTime.currentTimeStamp());
		banner.setCreate_user(getSysUser().getName());
		cmsBannerService.update(banner);
		cmsBannerCache.update(banner.getId(), banner);
		cmsBannerCache.restType();
		return ViewFactory.json(RetMsg.success("修改成功！"));
	}

	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		cmsBannerService.deleteIn(ids);
		cmsBannerCache.batchRemove(ids);
		cmsBannerCache.restType();
		return ViewFactory.json(RetMsg.success("删除成功！"));
	}

}
