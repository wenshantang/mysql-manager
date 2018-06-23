package cn.aresoft.common.service.impl.webpublish;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.webpublish.WebTemplate;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.common.service.webpublish.WebTemplateService;
@Bean(id = "webTemplateService")
public class WebTemplateServiceImpl extends CenterServiceImpl<WebTemplate> implements WebTemplateService{
	@Override
	public PageRecord<WebTemplate> paging(WebTemplate temparam, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(WebTemplate.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(temparam.getTemplate_name())) {
			sb.append("AND template_name =? ");
			condition.add(temparam.getTemplate_name());
		}
		return paging(sb.toString(), condition, param);
	}
	
	@Override
	public List<WebTemplate> queryList(){
		String sql = "select * from cms_web_template where status = 1 ";
		return queryList(WebTemplate.class, sql);
	}

}
