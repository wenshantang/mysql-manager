package cn.aresoft.manager.service.admin;

import java.util.List;

import cn.aresoft.common.model.cms.CmsReservekey;
import cn.aresoft.common.service.BaseService;
/**
 * cms保留字service
 * @author yyj
 *
 */
public interface CmsReservekeyService extends BaseService<CmsReservekey> {
	
	/**
	 * 查询所有的未删除的关键字
	 * @return
	 */
	public List<String> queryReservekeyList();

}
