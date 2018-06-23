package cn.aresoft.cms.common.cache;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Inject;

import cn.aresoft.cache.AbstractCache;
import cn.aresoft.cms.common.model.CmsAccessPermission;
import cn.aresoft.cms.common.service.CmsAccessPermissionService;

@Bean(id = "cmsAccessPermissionCache")
public class CmsAccessPermissionCache extends AbstractCache {

	@Inject
	private CmsAccessPermissionService cmsAccessPermissionService;

	@Override
	public String region() {
		return "CMS_ACCESSPERMISSION";
	}

	/***
	 * 新增模板缓存
	 * 
	 * @param cmsTemplate
	 */
	public void add(CmsAccessPermission cmsAccessPermission) {
		cache(cmsAccessPermission.getId(), cmsAccessPermission);
	}

	/**
	 * 从缓存中查找模板
	 * 
	 * @param id
	 * @return
	 */
	public CmsAccessPermission find(String id) {
		CmsAccessPermission cap = get(id);
		if (cap == null) {
			cap = cmsAccessPermissionService.query(id);
			cache(id, cap);
		}
		return cap;
	}

}
