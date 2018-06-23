package cn.aresoft.cms.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.cms.common.model.CmsBanner;
import cn.aresoft.cms.common.service.CmsBannerService;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "cmsBannerService")
public class CmsBannerServiceImpl extends CenterServiceImpl<CmsBanner> implements CmsBannerService {

	@Override
	public PageRecord<CmsBanner> paging(CmsBanner t, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(CmsBanner.class);
		StringBuffer sb = new StringBuffer();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(t.getType())) {
			sb.append("and type=? ");
			condition.add(t.getType());
		}
		if (StringUtil.notEmpty(t.getName())) {
			sb.append("and name like ? ");
			condition.add("%" + t.getName() + "%");
		}
		if (StringUtil.notEmpty(t.getTitle())) {
			sb.append("and title like ? ");
			condition.add("%" + t.getTitle() + "%");
		}
		return paging(sb.toString(), condition, param);
	}

	@Override
	public List<String> queryAllType() {
		return executor.querySimpleListString("select t.type from cms_banner t group by t.type ");
	}

	@Override
	public List<String> queryByType(String type) {
		return executor.querySimpleListString("select t.id from cms_banner t where t.type=? and t.status=? order by idx", type, "1");
	}
}
