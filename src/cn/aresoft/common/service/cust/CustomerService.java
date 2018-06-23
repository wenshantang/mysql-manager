package cn.aresoft.common.service.cust;

import cn.aresoft.common.model.cust.Customer;
import cn.aresoft.common.service.BaseService;

public interface CustomerService extends BaseService<Customer>{
	
	/**
	 * 根据证件类型和证件号码查询用户
	 * @param type
	 * @param num
	 * @return
	 */
	Customer queryCustByTypeAndNum(String type,String num);
	
    // 注册事物控制
	public String register(Customer cust);
	/**
	 * 重置密码
	 * @param cust
	 */
	void restPassword(Customer cust);

	void approve(Customer cust);

}
