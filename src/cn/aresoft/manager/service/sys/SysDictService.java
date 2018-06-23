package cn.aresoft.manager.service.sys;

import java.util.List;
import java.util.Map;

import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysDict;

public interface SysDictService extends BaseService<SysDict> {

	List<String> queryAllType();

	List<SysDict> findListFormCache(String key);

	String findValueFormCache(String type, String key);

	void updateByType(String dictType, String dictKey, String status);

	Map<String, List<SysDict>> queryAllDict();

	void clearCache();
	
	void clearCacheByType(String type);

}
