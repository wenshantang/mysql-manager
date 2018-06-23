package cn.aresoft.manager.service.impl.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysDict;
import cn.aresoft.manager.service.sys.SysDictService;

@Bean(id = "sysDictService")
public class SysDictServiceImpl extends CenterServiceImpl<SysDict>implements SysDictService {

	private static final Map<String, List<SysDict>> cache = new HashMap<String, List<SysDict>>();

	private static final Map<String, String> cache_typekey = new HashMap<String, String>();

	@Override
	public PageRecord<SysDict> paging(SysDict dict, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(SysDict.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(dict.getName())) {
			sb.append("and name like ? ");
			condition.add("%" + dict.getName() + "%");
		}
		if (StringUtil.notEmpty(dict.getValue())) {
			sb.append("and value like ? ");
			condition.add("%" + dict.getValue() + "%");
		}
		if (StringUtil.notEmpty(dict.getType())) {
			sb.append("and type like ? ");
			condition.add("%" + dict.getType() + "%");
		}

		return paging(sb.toString(), condition, param);
	}

	@Override
	public List<String> queryAllType() {
		return executor.querySimpleListString("select type from sys_dict group by type ");
	}

	@Override
	public Map<String, List<SysDict>> queryAllDict() {
		if (cache == null || cache.isEmpty()) {
			buildCache();
		}
		return cache;
	}

	private void buildCache() {
		List<String> list = queryAllType();
		if (list != null) {
			for (String type : list) {
				cache.put(type, queryByType(type));
			}
		}
	}

	private List<SysDict> queryByType(String type) {
		String sql = SqlBuilder.buildQuerySQL(getClazz()) + " where type = ? order by idx ";
		List<SysDict> result = executor.queryList(getClazz(), sql, type);
		return result;
	}

	@Override
	public List<SysDict> findListFormCache(String type) {
		if (cache == null || cache.isEmpty()) {
			buildCache();
		}
		return cache.get(type);
	}

	/**
	 * 根据 type 和 key 找
	 * 
	 * @param type
	 * @param key
	 * @return
	 */
	@Override
	public String findValueFormCache(String type, String key) {
		String value = cache_typekey.get(type + "_" + key);
		if (StringUtil.empty(value)) {
			List<SysDict> list = findListFormCache(type);
			if (list != null) {
				for (SysDict dict : list) {
					if (dict != null && dict.getName().equals(key)) {
						value = dict.getValue();
						cache_typekey.put(type + "_" + key, value);
						break;
					}
				}
			}
		}
		return value;
	}

	@Override
	public void updateByType(String dictType, String dictKey, String value) {
		String sql = "update sys_dict t set t.value=? where t.type=? and t.name=? ";
		update(sql, value, dictType, dictKey);
	}

	@Override
	public void clearCache() {
		cache.clear();
		cache_typekey.clear();
	}
	
	public void clearCacheByType(String type){
		cache.remove(type);
	}
}
