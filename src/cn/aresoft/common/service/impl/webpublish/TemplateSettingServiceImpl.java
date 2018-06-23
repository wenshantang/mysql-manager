package cn.aresoft.common.service.impl.webpublish;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Transaction;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.webpublish.TemplateSetting;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.common.service.webpublish.TemplateSettingService;
@Bean(id = "templateSettingService")
public class TemplateSettingServiceImpl extends CenterServiceImpl<TemplateSetting> implements TemplateSettingService{
	@Override
	public PageRecord<TemplateSetting> paging(TemplateSetting temset, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(TemplateSetting.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(temset.getTemplate_name())) {
			sb.append("AND template_name =? ");
			condition.add(temset.getTemplate_name());
		}
		return paging(sb.toString(), condition, param);
	}

	@Override
	public List<TemplateSetting> getFrontMenu() {
		Map<Object, Object> map = executor.queryMap("select template_name,count(1) paramcount from cms_template_param group by template_name ");
		List<TemplateSetting> topMenus = executor.queryList(TemplateSetting.class, SqlBuilder.buildQuerySQL(TemplateSetting.class) + " where parent_id='888888888888888888' order by idx ");
		if (topMenus != null) {
			String sql = SqlBuilder.buildQuerySQL(TemplateSetting.class) + " where parent_id=? order by idx";
			Iterator<TemplateSetting> it = topMenus.iterator();
			while (it.hasNext()) {
				TemplateSetting toptempSetting = it.next();
				//判断是否有对应的参数
				Long param_count = (Long) map.get(toptempSetting.getTemplate_name());
				if(param_count != null){
					toptempSetting.setParam_count(param_count.intValue());
				}
				List<TemplateSetting> templateSettings = executor.queryList(TemplateSetting.class, sql, toptempSetting.getId());// ȡ��һ���˵�
				if (templateSettings == null || templateSettings.size() == 0) {
					continue;
				} else {
					toptempSetting.setSons(templateSettings);
					Iterator<TemplateSetting> sonit = templateSettings.iterator();
					while (sonit.hasNext()) {
						TemplateSetting tempSetting = sonit.next();
						//判断是否有对应的参数
						param_count = (Long) map.get(tempSetting.getTemplate_name());
						if(param_count != null){
							tempSetting.setParam_count(param_count.intValue());
						}
					}
				}
			}
		}
		return topMenus;
	}
	
	@Override
	@Transaction
	public void deleteMenu(String id) {
		executor.delete("delete from cms_template_setting where id=?", id);
		deleteBySql("delete from cms_template_setting where parent_id=?", id);
	}
	
	@Override
	public TemplateSetting queryTopMenu(String id) {
		return executor.query(TemplateSetting.class, SqlBuilder.buildQuerySQL(TemplateSetting.class) + " where id=? and parent_id='888888888888888888' ", id);
	}
	
	@Override
	public List<TemplateSetting> getSonMenuByParentid(String parentid) {
		String sql = "select * from  cms_template_setting where parent_id=? and ISUSED = '0' order by idx";
		List<TemplateSetting> templateSettings = executor.queryList(TemplateSetting.class, sql,parentid);//ȡ��ͬ���˵�
		return templateSettings;
	}
	
	@Override
	public TemplateSetting queryParentByTemplateName(String templateName) {
		return executor.query(TemplateSetting.class, SqlBuilder.buildQuerySQL(TemplateSetting.class) + " where id=(select parent_id from cms_template_setting where template_name=?) ", templateName);
	}
	
	@Override
	public TemplateSetting querySetByTemplateName(String templateName) {
		return executor.query(TemplateSetting.class, SqlBuilder.buildQuerySQL(TemplateSetting.class) + " where template_name=? ", templateName);
	}
	
}
