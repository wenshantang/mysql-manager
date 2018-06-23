package cn.aresoft.manager.service;
import java.util.List;

import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.ProductInfo;

public interface ProductInfoService extends BaseService<ProductInfo>{
	/**
	 * 根据产品code值查询产品
	 * @param code 产品code
	 * @return
	 */
	ProductInfo queryByCode(String code);
	
	/**
	 * 查询所有的产品code 和 产品name
	 * @return
	 */
	List<ProductInfo> products();
	
	
	/**
	 * 审核产品信息
	 * @return
	 */
	void auditproduct(int id);
}
