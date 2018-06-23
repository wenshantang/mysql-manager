package cn.aresoft.common.service.impl.cust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.cms.common.model.CmsProperties;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.cust.Reservation;
import cn.aresoft.common.service.cust.ReqService;
import cn.aresoft.common.service.impl.CenterServiceImpl;
@Bean(id = "reqService")
public class ReqServiceImpl extends CenterServiceImpl<Reservation> implements ReqService{
	@Override
	public PageRecord<Reservation> paging(Reservation model, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(Reservation.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(model.getCust_name())) {
			sb.append("AND cust_name  like ? ");
			condition.add("%"+model.getCust_name()+"%");
		}
		if (StringUtil.notEmpty(model.getMobile())) {
			sb.append("AND mobile =? ");
			condition.add(model.getMobile());
		}
		if (StringUtil.notEmpty(model.getBegin_date())) {
			sb.append("AND req_time >=? ");
			condition.add(model.getBegin_date());
		}
		if (StringUtil.notEmpty(model.getEnd_date())) {
			sb.append("AND req_time <=? ");
			condition.add(model.getEnd_date());
		}
		if (StringUtil.notEmpty(model.getStatus())) {
			sb.append("AND status =? ");
			condition.add(model.getStatus());
		}
		if (StringUtil.notEmpty(model.getPro_id())) {
			sb.append("and pro_id in( select pro_id from p_product_info where pro_name like ? ) ");
			condition.add("%"+model.getPro_id()+"%");
		}
		if(StringUtil.empty(param.getSort())){
			sb.append(" order by req_time desc ");
		}
		return paging(sb.toString(), condition, param);
	}
	public Map<String, List<CmsProperties>> queryAllData() {
		Map<String, List<CmsProperties>> map = new HashMap<String, List<CmsProperties>>();
		List<CmsProperties> productinfoList = queryList(CmsProperties.class, "select t.pro_id name,t.pro_name value,t.pro_shortname from p_product_info t");
		map.put("pro_id_list", productinfoList);
		return map;
	}

}
