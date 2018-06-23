package cn.aresoft.common.service.impl.sys;

import java.util.ArrayList;
import java.util.List;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.sys.SysUserLog;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.common.service.sys.SysUserLogService;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;


/*
 * 密码管理、修改手机号等功能的接口处增加记录业务日志
 */
@Bean(id = "userLogService")
public class SysUserLogServiceImpl extends CenterServiceImpl<SysUserLog> implements SysUserLogService{
    @Override
	public PageRecord<SysUserLog> paging(SysUserLog log,CommonParam param){
    	String sql = SqlBuilder.buildQuerySQL(SysUserLog.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(log.getMobile_tel())) {//客户手机号
			sb.append("and mobile_tel  like ? ");
			condition.add("%"+log.getMobile_tel()+"%");
		}
		return paging(sb.toString(), condition, param);
	}
}
