package cn.aresoft.common.service.impl.cust;


import java.util.List;

import com.puff.framework.annotation.Bean;

import cn.aresoft.common.model.cust.Auth;
import cn.aresoft.common.service.cust.AuthService;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "authService")
public class AuthServiceImpl extends CenterServiceImpl<Auth> implements AuthService {

	@Override
	public int getNumOfList(Auth a) {
		// TODO Auto-generated method s
		StringBuffer sql=new StringBuffer();
		 sql.append("select count(*) from u_auth where 1=1 ");
		String cust_id = a.getCust_id();
		if(cust_id!=null&&!"".equals(cust_id))
		{
			sql.append(" and cust_id='"+cust_id+"'");
		}
		int queryInt = executor.queryInt(sql.toString());
		return queryInt;
	}

	@Override
	public List<Auth> getList(Auth a) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method s
		StringBuffer sql=new StringBuffer();
		 sql.append("select * from u_auth where 1=1 ");
		String cust_id = a.getCust_id();
		if(cust_id!=null&&!"".equals(cust_id))
		{
			sql.append(" and cust_id='"+cust_id+"'");
		}
				List<Auth> queryList = executor.queryList(Auth.class, sql.toString());
				return queryList;
	}

	@Override
	public void delAuthsByCustId(String cust_id) {
		StringBuffer sql=new StringBuffer();
		 sql.append("delete from u_auth where 1=1 ");
		if(cust_id!=null&&!"".equals(cust_id))
		{
			sql.append(" and cust_id='"+cust_id+"'");
		}
		
		executor.delete(sql.toString());
	}
	
	

	
}
