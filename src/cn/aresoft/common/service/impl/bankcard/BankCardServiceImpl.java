package cn.aresoft.common.service.impl.bankcard;

import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.DateTime;
import com.puff.framework.utils.IdWorker;
import com.puff.jdbc.core.Record;

import cn.aresoft.common.model.BaseModel;
import cn.aresoft.common.service.bankcard.BankCardService;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "bankCardService")
public class BankCardServiceImpl extends CenterServiceImpl<BaseModel>implements BankCardService {

	@Override
	public List<Record> queryBankList() {
		
		return executor.queryListRecord("select * from s_bankcard where status=? order by memo asc", "1");
	}

	@Override
	public Record queryBank(String code) {
		return executor.queryRecord("select * from s_bankcard where code=?", code);
	}

	@Override
	public List<Record> queryCustomerBank(String customer_no) {
		String sql = "select mc.*,mb.full_name,mb.name from u_customer_bank mc,m_bankcard mb where mc.bank_code=mb.code and mc.customer_no=? and mc.status=? order by mc.bind_time desc ";
		return executor.queryListRecord(sql, customer_no, "1");
	}

	@Override
	public void bindBankCard(String customer_no, String code, String card) {
		String sql = "insert into u_customer_bank(id,customer_no,bank_code,bank_card,status,bind_time) values(?,?,?,?,?,?)";
		executor.insert(sql, new IdWorker(12, 21).nextStrId(), customer_no, code, card, "1", DateTime.currentTimeStamp());
	}

	@Override
	public void unBindBankCard(String customer_no, String code, String card) {
		String sql = "update u_customer_bank set status=?,bind_time=? where customer_no=? and bank_code=? and bank_card=?";
		executor.updateBySql(sql, "0", DateTime.currentTimeStamp(), customer_no, code, card);
	}

}
