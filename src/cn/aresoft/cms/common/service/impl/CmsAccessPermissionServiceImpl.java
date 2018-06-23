package cn.aresoft.cms.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.cms.common.model.CmsAccessPermission;
import cn.aresoft.cms.common.service.CmsAccessPermissionService;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "cmsAccessPermissionService")
public class CmsAccessPermissionServiceImpl extends CenterServiceImpl<CmsAccessPermission>implements CmsAccessPermissionService {

	@Override
	public PageRecord<CmsAccessPermission> paging(CmsAccessPermission t, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(CmsAccessPermission.class);
		List<Object> condition = new ArrayList<Object>();
		return paging(sql, condition, param);
	}

}
