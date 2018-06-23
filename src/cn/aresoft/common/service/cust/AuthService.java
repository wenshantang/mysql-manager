package cn.aresoft.common.service.cust;

import java.util.List;

import cn.aresoft.common.model.cust.Auth;
import cn.aresoft.common.service.BaseService;

public interface AuthService extends BaseService<Auth> {

	public int getNumOfList(Auth a);
	public List<Auth> getList(Auth a);
	public void delAuthsByCustId(String cust_id);
}
