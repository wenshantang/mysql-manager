package cn.aresoft.common.service.impl.webpublish;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.DateTime;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;
import com.puff.web.mvc.PuffContext;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.webpublish.TemplateParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.common.service.webpublish.TemplateParamService;
import cn.aresoft.manager.model.sys.SysUser;
@Bean(id = "templateParamService")
public class TemplateParamServiceImpl extends CenterServiceImpl<TemplateParam> implements TemplateParamService{
	@Override
	public PageRecord<TemplateParam> paging(TemplateParam temparam, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(TemplateParam.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(temparam.getTemplate_name())) {
			sb.append("AND template_name =? ");
			condition.add(temparam.getTemplate_name());
		}
		if (StringUtil.notEmpty(temparam.getParam_type())) {
			sb.append("AND param_type =? ");
			condition.add(temparam.getParam_type());
		}
		return paging(sb.toString(), condition, param);
	}
	
	
	@Override
	public void updateByKey(String templateName,String paramKey,String paramValye){
		String sql ="update cms_template_param t set param_value = ? where t.template_name =? and param_key = ? ";
		update(sql, paramValye,templateName,paramKey);
	}
	
	
	@Override
	public TemplateParam queryByKey(String templateName,String paramKey){
		String sql ="select * from cms_template_param t where t.template_name =? and param_key = ? ";
		TemplateParam templateParam = executor.query(TemplateParam.class,sql, templateName,paramKey);
		return templateParam;
	}


	@Override
	public List<TemplateParam> queryParamsByTemplateName(String templateName) {
		String sql ="select * from cms_template_param t where t.template_name =? ";
		return queryList(sql, templateName);
	}
	
	@Override
	public List<TemplateParam> queryParamsByTemplateNameAndKey(String templateName,String key) {
		String sql ="select * from cms_template_param t where t.template_name =? and t.param_key like '%"+key+"%' order by param_key ";
		return queryList(sql, templateName);
	}
	
	/**
	 * @param templateName
	 * @param paramkey
	 * @param paramvalue
	 */
	@Override
	public void updateTemplateParam(String templateName,String paramkey,String paramvalue,String paramlink){
		String username = ((SysUser)PuffContext.getSessionAttribute("user")).getName();
		TemplateParam model = queryByKey(templateName,paramkey);
		if (model != null) {
			model.setParam_value(paramvalue);
			model.setParam_link(paramlink);
			model.setUpdate_time(DateTime.currentTimeStamp());
			model.setUpdate_user(username);
			update(model);
		} else {
			model = new TemplateParam();
			model.setTemplate_name(templateName);
			model.setParam_key(paramkey);
			model.setParam_value(paramvalue);
			model.setParam_link(paramlink);
			model.setCreate_time(DateTime.currentTimeStamp());
			model.setCreate_user(username);
			model.setIsdelete("0");
			insert(model);
		}
	}
	
	@Override
	public List<TemplateParam> queryParams(){
		String sql = "select DISTINCT template_name from cms_template_param ";
		return queryList(sql);
	}

}
