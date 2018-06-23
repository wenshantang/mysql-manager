package cn.aresoft.manager.service.impl.sys;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;
import com.puff.web.mvc.PuffContext;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysResource;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.service.sys.SysResourceService;

@Bean(id = "sysResourceService")
public class SysResourceServiceImpl extends CenterServiceImpl<SysResource>implements SysResourceService {

	@Override
	public PageRecord<SysResource> querySysResource(CommonParam param, SysResource res) {
		StringBuffer sb = new StringBuffer();
		sb.append(SqlBuilder.buildQuerySQL(getClazz()));
		sb.append("where parent_id =? ");
		List<Object> search = new ArrayList<Object>();
		search.add("888888888888888888");
		if (StringUtil.notBlank(res.getId())) {
			sb.append(" and id=? ");
			search.add(res.getId());
		}
		long count = executor.count(sb.toString(), search);
		PageRecord<SysResource> page = new PageRecord<SysResource>();
		page.setPage(param.getPage());
		page.setPageSize(param.getRows());
		page.setTotalCount(Integer.parseInt(String.valueOf(count)));
		if (count > 0) {
			String sql = buildQuerySql() + " where parent_id=? ";
			List<SysResource> list = executor.queryListPage(getClazz(), sb.toString(), page.getPage(), param.getRows(), search);
			if (list != null) {
				for (SysResource sysResource : list) {
					List<SysResource> sons = queryList(sql, sysResource.getId());
					sysResource.setSons(sons);
				}
				page.setDataList(list);
			}
		}
		return page;
	}

	@Override
	public SysResource querySysResource(String id) {
		SysResource sysResource = query(id);
		List<SysResource> sons = queryList(SqlBuilder.buildQuerySQL(getClazz()) + " where parent_id =? ", sysResource.getId());
		sysResource.setSons(sons);
		return sysResource;
	}
	
	@Override
	public List<SysResource> querySysResourceList(String role_id) {
		SysUser user = PuffContext.getSessionAttribute("user");
		List<SysResource> list =null;
		if("superAdmin".equals(user.getLogin_name())){
			String sql = SqlBuilder.buildQuerySQL(getClazz()) + " where parent_id =? ";
			 list = queryList(sql, "888888888888888888");
			if (list != null) {
				for (SysResource sysResource : list) {
					List<SysResource> sons = queryList(sql, sysResource.getId());
					sysResource.setSons(sons);
				}
			}
		}else{
			String role_sql = "select resource_id from sys_role_resource where role_id in(select role_id from sys_user_role where user_id=?)";
			List<String> resource_idList = executor.querySimpleListString(role_sql, role_id);
			String parent_sql = SqlBuilder.buildQuerySQL(getClazz()) +"where parent_id =?";
			String sql = SqlBuilder.buildQuerySQL(getClazz()) +
					" where parent_id =? and id in(select parent_id from sys_resource where id in(select resource_id from sys_role_resource where role_id in(select role_id from sys_user_role where user_id=?)) group by parent_id) ";
			list = queryList(sql, "888888888888888888",role_id);
			if (list != null) {
				for (SysResource sysResource : list) {
					List<SysResource> sons = queryList(parent_sql, sysResource.getId());
					List<SysResource> newsons = new ArrayList<SysResource>();
					for (SysResource son : sons) {
						if(resource_idList.contains(son.getId())){
							newsons.add(son);
						}
					}
					sysResource.setSons(newsons);
				}
			}
		}
		return list;
	}

	@Override
	public void deleteRes(String id) {
		String sql = "delete from sys_resource where id=? ";
		String delSql = "delete from sys_role_resource where resource_id=? ";
		executor.delete(sql, id);
		executor.delete(delSql, id);
		String qSql = "select id from sys_resource t where t.parent_id=?";
		List<String> ids = executor.querySimpleList(qSql, id);
		for (String str : ids) {
			executor.delete(sql, str);
			executor.delete(delSql, str);
		}
	}

	@Override
	public List<SysResource> querySysResource() {
		String sql = SqlBuilder.buildQuerySQL(getClazz()) + " where parent_id =? ";
		List<SysResource> list = queryList(sql, "888888888888888888");
		if (list != null) {
			for (SysResource sysResource : list) {
				List<SysResource> sons = queryList(sql, sysResource.getId());
				sysResource.setSons(sons);
			}
		}
		return list;
	}

	@Override
	public List<SysResource> queryTopMenu() {
		String sql = SqlBuilder.buildQuerySQL(getClazz()) + " where parent_id =? ";
		List<SysResource> list = queryList(sql, "888888888888888888");
		return list;
	}
}
