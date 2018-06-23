package cn.aresoft.manager.service.sys;

import cn.aresoft.manager.model.sys.SysDict;

public interface SysDictExtService extends SysDictService {

	/**
	 * 通过字典类型与名称查询字典实体
	 * @param type
	 * @param name
	 * @return
	 */
	SysDict querySysDictByTypeAndName(String type,String name);
	
	
	void updateByType(String dictType, String dictKey, String dictVal,String memo);
}
