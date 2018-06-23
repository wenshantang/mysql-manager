package cn.aresoft.common.service.webpublish;

import java.util.List;

import cn.aresoft.common.model.webpublish.TemplateParam;
import cn.aresoft.common.service.BaseService;

public interface TemplateParamService extends BaseService<TemplateParam>{
	public void updateByKey(String templateName,String paramKey,String paramValue);
	public TemplateParam queryByKey(String templateName,String paramKey);
	public List<TemplateParam> queryParamsByTemplateName(String templateName);
	void updateTemplateParam(String templateName, String paramkey,String paramvalue,String paramlink);
	List<TemplateParam> queryParams();
	List<TemplateParam> queryParamsByTemplateNameAndKey(String templateName,String key);
}
