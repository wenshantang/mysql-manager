package cn.aresoft.common.service.impl.question;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.question.Questionnaire;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.common.service.question.QuestionnaireService;

@Bean(id = "questionnaireService")
public class QuestionnaireServiceImpl extends CenterServiceImpl<Questionnaire> implements QuestionnaireService {
	@Override
	public PageRecord<Questionnaire> paging(Questionnaire t, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(Questionnaire.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if(StringUtil.notEmpty(t.getWname()))
		{
			sb.append(" and wname like ? ");
			condition.add("%"+t.getWname()+"%");
		}
		if(StringUtil.notEmpty(t.getQ_type()))
		{
			sb.append(" and q_type=? ");
			condition.add(t.getQ_type());
		}
		if(StringUtil.notEmpty(t.getIsuse()))
		{
			sb.append(" and isuse=? ");
			condition.add(t.getIsuse());
		}
		if(StringUtil.empty(param.getSort()))
		{
			sb.append(" order by creat_date desc");
		}
		return paging(sb.toString(), condition, param);
	}


}
