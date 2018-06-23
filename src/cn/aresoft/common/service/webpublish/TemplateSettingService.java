package cn.aresoft.common.service.webpublish;

import java.util.List;

import cn.aresoft.common.model.webpublish.TemplateSetting;
import cn.aresoft.common.service.BaseService;

public interface TemplateSettingService extends BaseService<TemplateSetting>{
	public List<TemplateSetting> getFrontMenu();
	TemplateSetting queryTopMenu(String id);
	void deleteMenu(String id);
	/**
	 * 根据id 获取菜单下所有子菜单
	 * @param parentid
	 * @return
	 */
	List<TemplateSetting> getSonMenuByParentid(String parentid);
	/**
	 * 根据菜单代码获取父菜单
	 * @param templateName
	 * @return
	 */
	TemplateSetting queryParentByTemplateName(String templateName);
	TemplateSetting querySetByTemplateName(String templateName);
}
