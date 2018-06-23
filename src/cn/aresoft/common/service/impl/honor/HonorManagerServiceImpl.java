package cn.aresoft.common.service.impl.honor;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.container.DBInfoContainer;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.honor.HonorManager;
import cn.aresoft.common.service.honor.HonorManagerService;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "honorManagerService")
public class HonorManagerServiceImpl extends CenterServiceImpl<HonorManager> implements HonorManagerService{
	
	@Override
	public PageRecord<HonorManager> paging(HonorManager honorManager, CommonParam param){
		String sql = SqlBuilder.buildQuerySQL(HonorManager.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" WHERE del_flag ='0' ");
		List<Object> condition = new ArrayList<Object>();
         if(StringUtil.notBlank(honorManager.getFrom_date())){
        	 sb.append(" and honor_year >=?");
        	 condition.add(honorManager.getFrom_date());
         }
         if(StringUtil.notBlank(honorManager.getTo_date())){
        	 sb.append(" and honor_year <=?");
        	 condition.add(honorManager.getTo_date());
         }
         if(StringUtil.notBlank(honorManager.getHonor_type())){
        	 sb.append(" and honor_type=?");
        	 condition.add(honorManager.getHonor_type());
         }
         if(StringUtil.notBlank(honorManager.getHonor_name())){
        	 sb.append(" and honor_name like ?");
        	 condition.add("%"+honorManager.getHonor_name()+"%");
         }
		return paging(sb.toString(), condition, param);
	}

	@Override
	public int insertHonorManager(HonorManager model) {
		String sql="insert into zq_honer(honor_year,honor_month,honor_type,honor_name,honor_from,url,show_index,remark,del_flag,create_by,update_by) values(?,?,?,?,?,?,?,?,?,?,?)";
		return executor.insert(sql,model.getHonor_year(),model.getHonor_month(),model.getHonor_type(),model.getHonor_name(),model.getHonor_from(),model.getUrl(),model.getShow_index(),model.getRemark(),model.getDel_flag(),model.getCreate_by(),model.getUpdate_by());
		
	}
	
	  @Override
	    public void updates(List<String> ids) {
		if (ids == null || ids.size() == 0) {
		    return;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("update ").append(DBInfoContainer.getTableName(getClazz()));
		sb.append(" set del_flag = '1' ");
		sb.append(" where ").append(DBInfoContainer.getPkName(getClazz()));
		if (ids.size() == 1) {
		    sb.append(" = ?");
		    getSimpleExecutor().updateBySql(sb.toString(), ids.get(0));
		    return;
		}
		sb.append(" in ");
		int size = ids.size();
		int count = 0;
		int max = 500;
		if (size > max) {
		    if (size % max == 0) {
			count = size / max;
		    } else {
			count = size / max + 1;
		    }
		    for (int i = 0; i < count; i++) {
			StringBuffer temp = new StringBuffer();
			temp.append(" ( ");
			int end = max;
			int endIdx = (i + 1) * max;
			if (i + 1 == count) {
			    end = size - (max * i);
			    endIdx = size;
			}
			for (int j = 0; j < end;) {
			    if (j++ > 0) {
				temp.append(",");
			    }
			    temp.append("?");
			}
			temp.append(" )");
			getSimpleExecutor().updateBySql(sb.toString() + temp, ids.subList(i * max, endIdx));
		    }
		} else {
		    sb.append(" ( ");
		    for (int i = 0; i < size;) {
			if (i++ > 0) {
			    sb.append(",");
			}
			sb.append("?");
		    }
		    sb.append(" )");
		    getSimpleExecutor().updateBySql(sb.toString(), ids);
		}
	    }
	  @Override
		public List<String> queryYear() {
			String sql="SELECT DISTINCT(honor_year) from fun_honer where del_flag='0'  order by honor_year desc";
			return executor.querySimpleListString(sql);
			
		}

	@Override
	public List<HonorManager> queryList(String year) {
		String sql = SqlBuilder.buildQuerySQL(HonorManager.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" WHERE 1=1 ");
		if(StringUtil.notEmpty(year)){
			sb.append("and honor_year ='"+year+"' ");
		}
		sb.append("and del_flag='0'");
		return getSimpleExecutor().queryList(HonorManager.class,sb.toString());
	}

	@Override
	public List<HonorManager> queryTime() {
		String sql = "select honor_type,honor_name,honor_from,create_date from fun_honer where create_date = (select max( create_date) from fun_honer) ";
		return  executor.queryList(HonorManager.class, sql);
	}

	@Override
	public void updateIsH5Flag(String temp) {
		String sql = "update fun_honer set is_h5_flag = '0' where is_h5_flag = ? ";
		executor.updateBySql(sql, temp);
	}

	@Override
	public void updateHonor(HonorManager model) {
		String sql = "update fun_honer set is_h5_flag = '0' where is_h5_flag = ? ";
		executor.updateBySql(sql, model);
		
	}

	@Override
	public HonorManager h5QueryHonor() {
		String sql ="select * from fun_honer where is_h5_flag='1'";
		return executor.query(HonorManager.class, sql);
	}   
	
	
	
	
}
