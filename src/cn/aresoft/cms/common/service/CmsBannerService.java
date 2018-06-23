package cn.aresoft.cms.common.service;

import java.util.List;

import cn.aresoft.cms.common.model.CmsBanner;
import cn.aresoft.common.service.BaseService;

public interface CmsBannerService extends BaseService<CmsBanner> {

	List<String> queryAllType();

	List<String> queryByType(String type);

}
