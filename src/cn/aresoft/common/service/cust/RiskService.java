package cn.aresoft.common.service.cust;

import cn.aresoft.common.model.cust.UCustomerRisk;
import cn.aresoft.common.service.BaseService;

public interface RiskService extends BaseService<UCustomerRisk>{

	UCustomerRisk queryUCustomerRiskBYCustId(String cust_id);

	UCustomerRisk queryUCustomerRiskAndAnswers(String id);

	UCustomerRisk queryUCustomerRiskAndAnswersByCustId(String id);
	UCustomerRisk queryqueUCustomerRiskAndAnswersByCustId(String id);

	
	
	
}
