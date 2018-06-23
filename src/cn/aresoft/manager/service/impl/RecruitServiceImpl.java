package cn.aresoft.manager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.Recruit;
import cn.aresoft.manager.service.RecruitService;

@Bean(id = "recruitService")
public class RecruitServiceImpl extends CenterServiceImpl<Recruit>implements RecruitService {
	@Override
	public PageRecord<Recruit> paging(Recruit r, CommonParam param) {
		StringBuffer sql = new StringBuffer(SqlBuilder.buildQuerySQL(Recruit.class));
		List<Object> condition = new ArrayList<Object>();
		sql.append(" where 1=1 ");
		if(StringUtil.notEmpty(r.getSex())){
            sql.append(" and sex =?  ");
            condition.add(r.getSex());
        }
		if(StringUtil.notEmpty(r.getMessagesTitle())){
		    sql.append(" and messagesTitle  like ?  ");
            condition.add("%"+r.getMessagesTitle()+"%");
        }
		if(StringUtil.notEmpty(r.getAddress())){
		    sql.append(" and address like ?  ");
		    condition.add("%"+r.getAddress()+"%");
        }
		if(StringUtil.empty(param.getSort())){
			sql.append(" order by  create_time desc ");
		}
		return paging(sql.toString(), condition, param);
	}
}

