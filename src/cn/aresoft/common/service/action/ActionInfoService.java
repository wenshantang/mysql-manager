package cn.aresoft.common.service.action;
import cn.aresoft.common.model.action.ActionInfo;
import cn.aresoft.common.service.BaseService;
public interface ActionInfoService extends BaseService<ActionInfo>{
	/**
	 * 根据code查询活动的信息
	 * @param code
	 * @return
	 */
	ActionInfo queryByCode(String code);
	/**
	 * 查询表中最大的code值
	 * @return
	 */
	String queryMaxCode();
}

