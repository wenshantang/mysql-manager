package cn.aresoft.manager.service.sys;

import java.util.List;

import com.puff.jdbc.core.PageRecord;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysResource;

public interface SysResourceService extends BaseService<SysResource> {

	public List<SysResource> queryTopMenu();

	public PageRecord<SysResource> querySysResource(CommonParam commonParam, SysResource res);

	public SysResource querySysResource(String id);

	public void deleteRes(String id);

	public List<SysResource> querySysResource();
	public List<SysResource> querySysResourceList(String role_id);

}
