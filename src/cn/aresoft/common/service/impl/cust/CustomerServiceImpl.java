package cn.aresoft.common.service.impl.cust;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.DateTime;
import com.puff.framework.utils.Security;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;
import com.puff.web.mvc.PuffContext;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.cust.Customer;
import cn.aresoft.common.service.cust.CustomerService;
import cn.aresoft.common.service.impl.CenterServiceImpl;
@Bean(id = "customerService")
public class CustomerServiceImpl extends CenterServiceImpl<Customer> implements CustomerService{
	@Override
	public PageRecord<Customer> paging(Customer customer, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(Customer.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(customer.getCust_name())) {
			sb.append("AND cust_name  like ? ");
			condition.add("%"+customer.getCust_name()+"%");
		}
		if (StringUtil.notEmpty(customer.getMobile())) {
			sb.append("AND mobile =? ");
			condition.add(customer.getMobile());
		}
		//客户类别
		if(StringUtil.notEmpty(customer.getCust_kind())){
			sb.append("AND cust_kind =? ");
			condition.add(customer.getCust_kind());
		}
		//客户等级
		if(StringUtil.notEmpty(customer.getCust_level())){
			sb.append("AND cust_level =? ");
			condition.add(customer.getCust_level());
		}
		if(StringUtil.empty(param.getSort())){
			sb.append(" order by create_time desc ");
		}
		return paging(sb.toString(), condition, param);
	}

	@Override
	public Customer queryCustByTypeAndNum(String type, String num) {
		String sql ="select * from u_customer_info t where t.cer_type=? and t.cer_num = ? ";
		return query(sql, type,num);
	}

	@Override
	public String register(Customer cust) {
		Customer model = PuffContext.getModel(Customer.class);
		model.setCreate_time(DateTime.currentTimeStamp());
		model.setIsdelete("0");
		if(StringUtil.empty(model.getShort_name())){
			model.setShort_name(model.getCust_name());
		}
		//取证件号码后六位   字母转换为0  加密后保存为密码
		String certNo = model.getCer_num();
		String password = cust.getCer_num().substring(certNo.length()-6, certNo.length());
		password = StringToZero(password);
    	cust.setPassword(Security.md5(password));

    	String cust_id=executor.queryString("select cust_id_seq() from dual");
    	cust.setCust_id(cust_id);
    	insert(cust);
		return cust_id;
	}

	/**
	 * 字母转换成0
	 * @param oldString
	 * @return
	 */
	private String StringToZero(String oldString){
		StringBuffer newString = new StringBuffer();
		String reg = "[a-zA-Z]";
		for (char c : oldString.toCharArray()) {  
            if (String.valueOf(c).matches(reg)) {  
            	newString.append(0);  
            } else {  
            	newString.append(c);  
            }  
        } 
		return newString.toString();
	}

	@Override
	public void restPassword(Customer cust) {
		//取证件号码后六位   字母转换为0  加密后保存为密码
		String certNo = cust.getCer_num();
		String password = cust.getCer_num().substring(certNo.length()-6, certNo.length());
		password = StringToZero(password);
		cust.setPassword(Security.md5(password));
		update(cust);
	}

	@Override
	public void approve(Customer cust) {
		cust.setCust_status("1");
		update(cust);
	}
	
}
