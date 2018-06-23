package cn.aresoft.common.service.activity;

import java.io.ByteArrayOutputStream;

import cn.aresoft.common.model.activity.WebActivityUser;
import cn.aresoft.common.service.BaseService;

public interface WebActivityUserService extends BaseService<WebActivityUser> {

	
	public ByteArrayOutputStream exportExcel(WebActivityUser t);
}
