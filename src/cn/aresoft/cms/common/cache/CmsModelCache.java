package cn.aresoft.cms.common.cache;

import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.ListUtil;

import cn.aresoft.cache.AbstractCache;
import cn.aresoft.cms.common.model.CmsModel;
import cn.aresoft.cms.common.service.CmsModelService;

@Bean(id = "cmsModelCache")
public class CmsModelCache extends AbstractCache {

	@Inject
	private CmsModelService cmsModelService;

	@Override
	public String region() {
		return "CMS_MODEL";
	}

	/**
	 * 初始化模型缓存
	 */
	public void init() {
		List<CmsModel> models = cmsModelService.queryList();
		if (ListUtil.notEmpty(models)) {
			for (CmsModel model : models) {
				cache(model.getCode(), model);
			}
		}
	}

}
