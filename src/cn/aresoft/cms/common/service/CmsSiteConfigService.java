package cn.aresoft.cms.common.service;

import java.util.List;

import com.puff.jdbc.core.Record;

import cn.aresoft.cms.common.model.config.CmsSiteConfig;
import cn.aresoft.common.service.BaseService;

public interface CmsSiteConfigService extends BaseService<CmsSiteConfig> {

	
	List<Record> queryFtpServer();

}
