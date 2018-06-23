package cn.aresoft.manager.plugin;

import java.util.List;

import org.beetl.core.GeneralVarTagBinding;

import cn.aresoft.cms.common.cache.CmsPropertiesCache;
import cn.aresoft.cms.common.model.CmsProperties;

import com.puff.ioc.BeanFactory;
/**
 * 配置模板tag
 * @author yyj
 *
 */
public class CmsPropertiesTag extends GeneralVarTagBinding{
	private CmsPropertiesCache cmsPropertiesCache = BeanFactory.getBean("cmsPropertiesCache");

	@Override
	public void render() {
		String key = (String) getAttributeValue("key");
		List<CmsProperties> dicts = cmsPropertiesCache.findListFormCache(key);
		if (dicts != null) {
			for (CmsProperties sysDict : dicts) {
				this.binds(sysDict);
				this.doBodyRender();
			}
		}

	}


}
