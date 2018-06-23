package cn.aresoft.cms.common.service;

import java.util.List;

import cn.aresoft.cms.common.model.CmsModel;
import cn.aresoft.cms.common.model.CmsModelAttr;
import cn.aresoft.common.service.BaseService;

public interface CmsModelService extends BaseService<CmsModel> {

	void addModel(CmsModel model, List<CmsModelAttr> attrs);

	void updateModel(CmsModel model, List<CmsModelAttr> attrs);

	List<CmsModelAttr> queryModelAttr(String code);

	void deleteModelAttr(List<String> ids);

}
