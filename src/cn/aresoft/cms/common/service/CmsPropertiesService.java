package cn.aresoft.cms.common.service;

import java.util.List;

import cn.aresoft.cms.common.model.CmsProperties;
import cn.aresoft.common.service.BaseService;

public interface CmsPropertiesService extends BaseService<CmsProperties> {
	List<String> queryAllType();

	void updateByType(String type, String key, String status);

	List<CmsProperties> queryByType(String type);

	CmsProperties queryByTypeAndKey(String type, String key);

	CmsProperties queryByTypeAndValue(String type, String value);
}
