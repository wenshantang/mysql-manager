package cn.aresoft.common.service.impl.cust;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.cust.Visitrecord;
import cn.aresoft.common.service.cust.VisitrecordService;
import cn.aresoft.common.service.impl.CenterServiceImpl;
@Bean(id = "visitrecordService")
public class VisitrecordServiceImpl extends CenterServiceImpl<Visitrecord> implements VisitrecordService{
	@Override
	public PageRecord<Visitrecord> paging(Visitrecord record, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(Visitrecord.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(record.getVisitsubject())) {
			sb.append("AND visitsubject  like ? ");
			condition.add("%"+record.getVisitsubject()+"%");
		}
		if(StringUtil.empty(param.getSort())){
			sb.append(" order by create_time desc ");
		}
		return paging(sb.toString(), condition, param);
	}

}
