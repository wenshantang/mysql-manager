package cn.aresoft.cms.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.cms.common.model.CmsProperties;
import cn.aresoft.cms.common.service.CmsPropertiesService;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "cmsPropertiesService")
public class CmsPropertiesServiceImpl extends CenterServiceImpl<CmsProperties> implements CmsPropertiesService {

	@Override
	public PageRecord<CmsProperties> paging(CmsProperties dict, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(CmsProperties.class);
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
		return executor.querySimpleListString("select t.type from cms_properties t group by t.type ");
	}

	@Override
	public void updateByType(String dictType, String dictKey, String value) {
		String sql = "update cms_properties set value=? where status='1' and type=? and name=? ";
		update(sql, value, dictType, dictKey);
	}

	@Override
	public List<CmsProperties> queryByType(String type) {
		String sql = SqlBuilder.buildQuerySQL(getClazz()) + " where status='1' and type = ? order by idx ";
		return executor.queryList(getClazz(), sql, type);
	}

	@Override
	public CmsProperties queryByTypeAndKey(String type, String key) {
		return executor.query(getClazz(), buildQuerySql() + " where status='1' and type=? and name=? ", type, key);
	}

	@Override
	public CmsProperties queryByTypeAndValue(String type, String value) {
		return executor.query(getClazz(), buildQuerySql() + " where status='1' and type=? and value=? ", type, value);
	}
	
	
}
