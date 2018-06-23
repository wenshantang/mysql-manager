package cn.aresoft.manager.service.impl.sys;

import com.puff.framework.annotation.Bean;

import cn.aresoft.manager.model.sys.SysDict;
import cn.aresoft.manager.service.sys.SysDictExtService;
@Bean(id = "sysDictExtService")
public class SysDictExtServiceImpl extends SysDictServiceImpl implements SysDictExtService{

	public SysDict querySysDictByTypeAndName(String type,String name){
		String sql="select * from sys_dict where type=? and name= ?";
		return query(sql, type,name);
	}

	@Override
	public void updateByType(String dictType, String dictKey, String dictVal,String memo) {
		//判断是否存在，存在则更新，不存在则修改
		SysDict sysDict=querySysDictByTypeAndName(dictType, dictKey);
		if(sysDict!=null){
			String sql = "update sys_dict t set t.value=?,t.memo=? where t.type=? and t.name=? ";
			update(sql, dictVal,memo,dictType, dictKey);
			super.updateByType(dictType, dictKey, dictVal);
		}else{
			String sql="insert into sys_dict(type,name,value,memo) values(?,?,?,?)";
			insert(sql, dictKey,dictKey,dictVal,memo);
		}
		
	}
	
	
}
