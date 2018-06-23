package cn.aresoft.common.service.impl.cust;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.Record;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.cust.UCustomerRisk;
import cn.aresoft.common.service.cust.RiskService;
import cn.aresoft.common.service.impl.CenterServiceImpl;
@Bean(id = "riskService")
public class RiskServiceImpl extends CenterServiceImpl<UCustomerRisk> implements RiskService{
	@Override
	public PageRecord<UCustomerRisk> paging(UCustomerRisk risk, CommonParam param) {
		String sql = "select t.* from (select a.*,b.short_name from u_customer_risk a left join  u_customer_info b on a.CUST_Id = b.CUST_ID) t ";
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(risk.getCust_id())) {
			sb.append("AND cust_id like ? ");
			condition.add("%"+risk.getCust_id()+"%");
		}
		
		if(StringUtil.empty(param.getSort())){
			sb.append(" order by create_time desc ");
		}
		return paging(sb.toString(), condition, param);
	}
 
	@Override
	public UCustomerRisk queryUCustomerRiskBYCustId(String cust_id){
		return query("select * from u_customer_risk where cust_id = ? ", cust_id);
	}
	
	
	@Override
	public UCustomerRisk queryUCustomerRiskAndAnswers(String id){
		String sql = "select t.* from (select a.*,b.short_name from u_customer_risk a left join  u_customer_info b on a.CUST_Id = b.CUST_ID) t where id = ? ";
		UCustomerRisk model = query(sql, id);
		List<Record> listRecord = new ArrayList<Record>();
		//格式： 1:A:5|2:C:3
		String answers = model.getAnswers();
		String[] answersarr = answers.split("\\|");
		if(StringUtil.notEmpty(answersarr)){
			for(int i=0;i<answersarr.length;i++){
				String answer = answersarr[i];
				if(answer != null){
					int one = answer.indexOf(':');
					int two = answer.lastIndexOf(':');
					Record re = new Record();
					re.set("tm_code", answer.substring(0,one));
					re.set("tm_answer", answer.substring(one+1,two));
					re.set("tm_score", answer.substring(two+1));
					listRecord.add(re);
				}
			}
		}
		model.setListRecord(listRecord);
		return model;
	}

	@Override
	public UCustomerRisk queryUCustomerRiskAndAnswersByCustId(String id) {
		String sql = "select a.*,b.short_name from u_customer_risk a, u_customer_info b where a.CUST_Id =? and  a.CUST_Id = b.CUST_ID";
		UCustomerRisk model = query(sql,id);
		List<Record> listRecord = new ArrayList<Record>();
		//格式： 1:A:5|2:C:3
		String answers = model.getAnswers();
		String[] answersarr = answers.split("\\|");
		if(StringUtil.notEmpty(answersarr)){
			for(int i=0;i<answersarr.length;i++){
				String answer = answersarr[i];
				if(answer != null){
					int one = answer.indexOf(':');
					int two = answer.lastIndexOf(':');
					Record re = new Record();
					re.set("tm_code", answer.substring(0,one));
					re.set("tm_answer", answer.substring(one+1,two));
					re.set("tm_score", answer.substring(two+1));
					listRecord.add(re);
				}
			}
		}
		model.setListRecord(listRecord);
		return model;
	}
	
	@Override
	public UCustomerRisk queryqueUCustomerRiskAndAnswersByCustId(String id) {
		String sql = "select a.*,b.short_name from u_customer_risk a, u_customer_info b where a.CUST_Id =? and  a.CUST_Id = b.CUST_ID";
		UCustomerRisk model = query(sql,id);
		//格式： 1:A:5|2:C:3
		if(model!=null){
			List<Record> listRecord = new ArrayList<Record>();
		String answers = model.getAnswers();
		String[] answersarr = answers.split("\\|");
		if(StringUtil.notEmpty(answersarr)){
			for(int i=0;i<answersarr.length;i++){
				String answer = answersarr[i];
				if(answer != null){
					int one = answer.indexOf(':');
					int two = answer.lastIndexOf(':');
					Record re = new Record();
					re.set("tm_code", answer.substring(0,one));
					re.set("tm_answer", answer.substring(one+1,two));
					re.set("tm_score", answer.substring(two+1));
					listRecord.add(re);
				}
			}
		}
		model.setListRecord(listRecord);
		}
		return model;
	}
	
}
