package cn.aresoft.cms.common.service;

import java.util.List;
import java.util.Map;

import cn.aresoft.cms.common.model.CmsTemplate;
import cn.aresoft.common.service.BaseService;

public interface CmsTemplateService extends BaseService<CmsTemplate> {

	List<CmsTemplate> findByParentId(String parent_id);

	String findPathById(String id);

	CmsTemplate findByDirAndParentId(String dir, String parent_id);

	void deleteDir(String id);

	CmsTemplate findByNameAndParentId(String name, String parent_id);

	List<CmsTemplate> findHtmlTemplate();

	CmsTemplate findByPath(String group);

	long countTemplate();

	List<CmsTemplate> queryListPage(int page, int size);

	List<Map<String, Object>> findTemplateByParentId(String parent_id);
}
