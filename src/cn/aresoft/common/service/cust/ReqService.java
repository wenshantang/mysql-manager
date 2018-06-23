package cn.aresoft.common.service.cust;

import java.util.List;
import java.util.Map;

import cn.aresoft.cms.common.model.CmsProperties;
import cn.aresoft.common.model.cust.Reservation;
import cn.aresoft.common.service.BaseService;

public interface ReqService extends BaseService<Reservation>{
	/**
	 * 查询产品
	 * @return
	 */
	Map<String, List<CmsProperties>> queryAllData();

}
