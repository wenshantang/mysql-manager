package cn.aresoft.cms.common.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puff.framework.annotation.Bean;
import com.puff.ioc.BeanFactory;
import com.puff.plugin.cache.CacheClient;

import cn.aresoft.cache.AbstractCache;
import cn.aresoft.cms.common.model.CmsProperties;
import cn.aresoft.cms.common.service.CmsPropertiesService;

@Bean(id = "cmsPropertiesCache")
public class CmsPropertiesCache extends AbstractCache {
	
	@Override
	public String region() {
		return "CMS_PROPERTIES";
	}

	private CmsPropertiesService cmsPropertiesService=BeanFactory.getBean("cmsPropertiesService");

	private CacheClient cache = getCacheClient();
	private final String RESULT_LIST_CACHE = "CMS_PROPERTIES_LIST";

	public List<CmsProperties> findListFormCache(String type) {
		List<CmsProperties> result = cache.get(RESULT_LIST_CACHE, type);
		if (result == null) {
			result = cmsPropertiesService.queryByType(type);
			if (result != null) {
				cache.put(RESULT_LIST_CACHE, type, result);
			}
		}
		return result;
	}

	/**
	 * 根据 type 和 key 找
	 * 
	 * @param type
	 * @param key
	 * @return
	 */
	public String findValueFormCache(String type, String key) {
		CmsProperties result = findPropertiesFormCache(type, key);
		if (result != null) {
			return result.getValue();
		}
		return "";
	}

	/**
	 * 根据 type 和 key 找
	 * 
	 * @param type
	 * @param key
	 * @return
	 */
	public CmsProperties findPropertiesFormCache(String type, String key) {
		String new_key = type + ":" + key;
		CmsProperties value = get(new_key);
		if (value == null) {
			value = cmsPropertiesService.queryByTypeAndKey(type, key);
			if (value != null) {
				cache(new_key, value);
			}
		}
		return value;
	}

	public void clear(String type, String name) {
		cache.remove(RESULT_LIST_CACHE, type);
		remove(type + ":" + name);
	}

	public Map<String, List<CmsProperties>> queryAllProperties() {
		Map<String, List<CmsProperties>> map = new HashMap<String, List<CmsProperties>>();
		List<String> list = get("propertiesAllType");
		if(list==null){
			list = cmsPropertiesService.queryAllType();
			cache("propertiesAllType",list);
		}
		if (list != null) {
			for (String type : list) {
				map.put(type, findListFormCache(type));
			}
		}
		return map;
	}
	
	public CmsProperties findPropertiesFormCacheByValue(String type, String value) {
		String new_key = type + ":" + value;
		CmsProperties cmsPro = get(new_key);
		if (cmsPro == null) {
			cmsPro = cmsPropertiesService.queryByTypeAndValue(type, value);
			if (cmsPro != null) {
				cache(new_key, cmsPro);
			}
		}
		return cmsPro;
	}
	
	public String findNameFormCacheByValue(String type, String value) {
		CmsProperties result = findPropertiesFormCacheByValue(type, value);
		if (result != null) {
			return result.getName();
		}
		return "";
	}	
}
