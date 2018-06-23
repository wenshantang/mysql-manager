package cn.aresoft.common.service.bankcard;

import java.util.List;

import com.puff.jdbc.core.Record;

import cn.aresoft.common.model.BaseModel;
import cn.aresoft.common.service.BaseService;

public interface BankCardService extends BaseService<BaseModel> {

	List<Record> queryBankList();

	Record queryBank(String code);

	List<Record> queryCustomerBank(String customer_no);

	void bindBankCard(String customer_no, String code, String card);

	void unBindBankCard(String customer_no, String code, String card);

}
