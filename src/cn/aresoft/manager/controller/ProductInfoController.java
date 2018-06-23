package cn.aresoft.manager.controller;

import java.util.List;

import cn.aresoft.cms.common.cache.CmsPropertiesCache;
import cn.aresoft.cms.common.cache.CmsTopicArticleCache;
import cn.aresoft.cms.common.model.CmsProperties;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.ProductInfo;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.ProductInfoService;

import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.DateTime;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

@Controller(value = "/admin/product")
public class ProductInfoController extends CommonController {
	@Inject
	private ProductInfoService productInfoService;
	@Inject
	private CmsPropertiesCache cmsPropertiesCache;
	@Inject
	private CmsTopicArticleCache cmsTopicArticleCache;
	public View index() {
		MyBeetlView view = new MyBeetlView("/product/pro_list.html");
		return view;
	}

	public View json() {
		ProductInfo p = PuffContext.getModel(ProductInfo.class);
		PageRecord<ProductInfo> data = productInfoService.paging(p,
				getCommonParam());
		return ViewFactory.json(data);
	}

	public View add() {
		// 产品类型 固定类，浮动类，海外类
		List<CmsProperties> types = cmsPropertiesCache
				.findListFormCache("pro_type");
		// 行业的分类，为了关联文章
		List<CmsProperties> industrys = cmsPropertiesCache
				.findListFormCache("industry");
		// 钱码头首页的产品分类
		List<CmsProperties> directions = cmsPropertiesCache
						.findListFormCache("pro_direction");
		//相关产品的查询
		List<ProductInfo> products=productInfoService.queryList();
		MyBeetlView view = new MyBeetlView("/product/pro_add.html");
		view.put("types", types);
		view.put("industrys", industrys);
		view.put("directions", directions);
		view.put("products", products);
		return view;
	}

	public View insert() {
		ProductInfo p = PuffContext.getModel(ProductInfo.class);
		p.setD_lastmodtime(DateTime.currentTimeStamp());//修改时间
		p.setI_moduser(getSysUser().getId());//修改人
		p.setI_createuser(getSysUser().getId());//创建人
		p.setI_createtime(DateTime.currentTimeStamp());//创建时间
		p.setStatus("1");//1待审核 2：审核通过
		ProductInfo  product = productInfoService.queryByCode(p.getCode());
		if(product!=null){
		    return ViewFactory.json(RetMsg.error("产品code值已存在!"));
		}
		productInfoService.insert(p);
		return ViewFactory.json(RetMsg.success("新增成功！"));
	}

	// 查询单条数据
	public View edit(int id) {
		ProductInfo p = productInfoService.query(id);
		List<ProductInfo> products=productInfoService.queryList();
		MyBeetlView view = new MyBeetlView("/product/pro_edit.html");
		view.put("p", p);
		view.put("products", products);
		return view;
	}

	public View update() {
		ProductInfo p = PuffContext.getModel(ProductInfo.class);
		ProductInfo  p1 = productInfoService.queryByCode(p.getCode());
		if(p1!=null){
		    if(p.getId()!=p1.getId()){
		        return ViewFactory.json(RetMsg.error("产品code值已存在!"));
		    }
		}
		p.setD_lastmodtime(DateTime.currentTimeStamp());
		p.setI_moduser(getSysUser().getId());
		productInfoService.update(p);
		return ViewFactory.json(RetMsg.success("修改成功！"));
	}

	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		for (String id : ids) {
			productInfoService.delete(id);
		}
		return ViewFactory.json(RetMsg.success("删除成功！"));
	}
	
	
	public View removeCache() {
		cmsPropertiesCache.clear();
		cmsTopicArticleCache.clear();
		return ViewFactory.json(RetMsg.success("清除缓存成功！"));
	}
	
	public View audit(int id) {
		productInfoService.auditproduct(id);
		return ViewFactory.json(RetMsg.success("审核成功！"));
	}
	

}
