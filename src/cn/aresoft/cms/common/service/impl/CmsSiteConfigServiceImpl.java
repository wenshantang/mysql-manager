package cn.aresoft.cms.common.service.impl;

import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.jdbc.core.Record;

import cn.aresoft.cms.common.model.config.CmsSiteConfig;
import cn.aresoft.cms.common.service.CmsSiteConfigService;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "cmsSiteConfigService")
public class CmsSiteConfigServiceImpl extends CenterServiceImpl<CmsSiteConfig> implements CmsSiteConfigService {

	@Override
	public List<Record> queryFtpServer() {
		String sql = "select id,server_name from sys_ftpserver where server_status='1'";
		return executor.queryListRecord(sql);
	}

}
