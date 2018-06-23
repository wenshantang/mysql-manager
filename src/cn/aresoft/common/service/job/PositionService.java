package cn.aresoft.common.service.job;

import java.util.List;

import com.puff.jdbc.core.PageRecord;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.job.Position;
import cn.aresoft.common.service.BaseService;

public interface PositionService extends BaseService<Position> {
	public PageRecord<Position> paging(Position position,CommonParam param);
	public void updates(List<String> ids);

}
