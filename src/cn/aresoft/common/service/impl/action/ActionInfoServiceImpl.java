package cn.aresoft.common.service.impl.action;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.action.ActionInfo;
import cn.aresoft.common.service.action.ActionInfoService;
import cn.aresoft.common.service.impl.CenterServiceImpl;
@Bean(id="actionInfoService")
public class ActionInfoServiceImpl extends CenterServiceImpl<ActionInfo> implements ActionInfoService {
    
    @Override
    public PageRecord<ActionInfo> paging(ActionInfo actionInfo,CommonParam param){
	String sql = SqlBuilder.buildQuerySQL(ActionInfo.class);
	StringBuilder sb = new StringBuilder();
	List<Object> condition = new ArrayList<Object>();
	sb.append(sql).append(" where 1=1 ");
	if (StringUtil.notBlank(actionInfo.getAct_type())) {
	    sb.append(" and act_type = ?");
	    condition.add(actionInfo.getAct_type());
	}
	if (StringUtil.notBlank(actionInfo.getAct_scope())) {
	    sb.append(" and act_scope = ?");
	    condition.add(actionInfo.getAct_scope());
	}
	if(StringUtil.notBlank(actionInfo.getAct_name())){
   	 sb.append(" and act_name like ?");
   	 condition.add("%"+actionInfo.getAct_name()+"%");
	}
	if (StringUtil.notBlank(actionInfo.getPro_code())) {
	    sb.append(" and pro_code = ?");
	    condition.add(actionInfo.getPro_code());
	}
	if (StringUtil.notBlank(actionInfo.getEnd_flg())) {
	    sb.append(" and end_flg = ?");
	    condition.add(actionInfo.getEnd_flg());
	}
	if(StringUtil.empty(param.getSort())){
		sb.append(" order by valid_begin_time desc    ");
	}
	return paging(sb.toString(), condition, param);
    }

	@Override
	public ActionInfo queryByCode(String code) {
		String sql ="select *  from  fun_act_info where act_code=?";
		return query(sql, code);
	}

	@Override
	public String queryMaxCode() {
		String sql ="select max(act_code)  from  fun_act_info ";
		return executor.queryString(sql);
	}
  
}
