package cn.aresoft.manager.service.impl.admin;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.cms.CmsReservekey;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.service.admin.CmsReservekeyService;
/**
 * cms保留字service实现
 * @author yyj
 *
 */
@Bean(id="cmsReservekeyService")
public class CmsReservekeyServiceImpl extends CenterServiceImpl<CmsReservekey> implements CmsReservekeyService {

	@Override
	public PageRecord<CmsReservekey> paging(CmsReservekey vo, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(CmsReservekey.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(vo.getVc_keyword())) {
			sb.append("AND vc_keyword like ?");
			condition.add("%"+vo.getVc_keyword()+"%");
		}
		return paging(sb.toString(), condition, param);
	}

	@Override
	public List<String> queryReservekeyList() {
		String sql="select distinct vc_keyword from CMS_RESERVEKEY where c_deleted is null or c_deleted='0' ";
		return executor.querySimpleListString(sql);
	}
	
	
}
