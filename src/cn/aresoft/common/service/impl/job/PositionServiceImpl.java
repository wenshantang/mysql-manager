package cn.aresoft.common.service.impl.job;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.container.DBInfoContainer;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.job.Position;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.common.service.job.PositionService;

@Bean(id = "positionService")
public class PositionServiceImpl extends CenterServiceImpl<Position> implements PositionService {
    @Override
    public PageRecord<Position> paging(Position position, CommonParam param) {
	String sql = SqlBuilder.buildQuerySQL(Position.class);
	StringBuilder sb = new StringBuilder();
	sb.append(sql).append(" WHERE del_flag ='0' ");
	List<Object> condition = new ArrayList<Object>();
	if (StringUtil.notBlank(position.getPost_name())) {
	    sb.append(" and post_name like ?");
	    condition.add("%" + position.getPost_name() + "%");
	}
	if (StringUtil.notBlank(position.getRecruit_type())) {
	    sb.append(" and recruit_type = ?");
	    condition.add(position.getRecruit_type());
	}
	if (StringUtil.notBlank(position.getRecruit_title())) {
	    sb.append(" and recruit_title = ?");
	    condition.add(position.getRecruit_title());
	}
	if (StringUtil.notBlank(position.getDep_type())) {
	    sb.append(" and dep_type = ?");
	    condition.add(position.getDep_type());
	}
	return paging(sb.toString(), condition, param);
    }

    @Override
    public void updates(List<String> ids) {
	if (ids == null || ids.size() == 0) {
	    return;
	}
	StringBuffer sb = new StringBuffer();
	sb.append("update ").append(DBInfoContainer.getTableName(getClazz()));
	sb.append(" set del_flag = '1' ");
	sb.append(" where ").append(DBInfoContainer.getPkName(getClazz()));
	if (ids.size() == 1) {
	    sb.append(" = ?");
	    getSimpleExecutor().updateBySql(sb.toString(), ids.get(0));
	    return;
	}
	sb.append(" in ");
	int size = ids.size();
	int count = 0;
	int max = 500;
	if (size > max) {
	    if (size % max == 0) {
		count = size / max;
	    } else {
		count = size / max + 1;
	    }
	    for (int i = 0; i < count; i++) {
		StringBuffer temp = new StringBuffer();
		temp.append(" ( ");
		int end = max;
		int endIdx = (i + 1) * max;
		if (i + 1 == count) {
		    end = size - (max * i);
		    endIdx = size;
		}
		for (int j = 0; j < end;) {
		    if (j++ > 0) {
			temp.append(",");
		    }
		    temp.append("?");
		}
		temp.append(" )");
		getSimpleExecutor().updateBySql(sb.toString() + temp, ids.subList(i * max, endIdx));
	    }
	} else {
	    sb.append(" ( ");
	    for (int i = 0; i < size;) {
		if (i++ > 0) {
		    sb.append(",");
		}
		sb.append("?");
	    }
	    sb.append(" )");
	    getSimpleExecutor().updateBySql(sb.toString(), ids);
	}
    }
}
