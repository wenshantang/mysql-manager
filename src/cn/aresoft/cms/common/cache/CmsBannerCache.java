
package cn.aresoft.cms.common.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.ListUtil;
import com.puff.plugin.cache.CacheClient;

import cn.aresoft.cache.AbstractCache;
import cn.aresoft.cms.common.model.CmsBanner;
import cn.aresoft.cms.common.service.CmsBannerService;

@Bean(id = "cmsBannerCache")
public class CmsBannerCache extends AbstractCache {

	@Inject
	private CmsBannerService cmsBannerService;

	@Override
	public String region() {
		return "CMS_BANNER";
	}

	private String banner_type_region = "CMS_BANNER_TYPE";

	public void restType() {
		CacheClient client = getCacheClient();
		client.clear(banner_type_region);
		init();
	}

	public List<CmsBanner> findByType(String type, int rows) {
		CacheClient client = getCacheClient();
		List<String> ids = client.get(banner_type_region, type);
		//update by yyj 2016-10-25 start
		//修改获取策略[先从缓存里获取，获取不到再从数据库里查询，然后存放到缓存中]
		if(ListUtil.empty(ids)){
			List<String> bannerIdList=cmsBannerService.queryByType(type);
			client.put(banner_type_region, type, bannerIdList);
		}
		//update by yyj 2016-10-25 end
		if (ListUtil.notEmpty(ids)) {
			List<CmsBanner> banners = new ArrayList<CmsBanner>(ids.size());
			if (rows > 0) {
				int size = ids.size();
				if (rows < size) {
					ids = ids.subList(0, rows);
				}
			}
			for (String id : ids) {
				CmsBanner e = get(id);
				//update by yyj 2016-10-25 start
				//修改获取策略[先从缓存里获取，获取不到再从数据库里查询，然后存放到缓存中]
				if(e==null){
					e=cmsBannerService.query(id);
					cache(id, e);
				}
				//update by yyj 2016-10-25 end
				banners.add(e);
			}
			return banners;
		}
		return Collections.emptyList();
	}

	public void start() {
		List<CmsBanner> list = cmsBannerService.queryList();
		if (ListUtil.notEmpty(list)) {
			for (CmsBanner cmsBanner : list) {
				cache(cmsBanner.getId(), cmsBanner);
			}
		}
		init();
	}

	protected void init() {
		List<String> types = cmsBannerService.queryAllType();
		if (ListUtil.notEmpty(types)) {
			CacheClient client = getCacheClient();
			for (String type : types) {
				List<String> ids = cmsBannerService.queryByType(type);
				client.put(banner_type_region, type, ids);
			}
		}
	}

}
