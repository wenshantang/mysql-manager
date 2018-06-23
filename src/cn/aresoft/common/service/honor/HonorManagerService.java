package cn.aresoft.common.service.honor;



import java.util.List;

import cn.aresoft.common.model.honor.HonorManager;
import cn.aresoft.common.service.BaseService;


public interface HonorManagerService extends BaseService<HonorManager>{

	  public int insertHonorManager(HonorManager model);
	  public void updates(List<String> ids);
	  
	  //查询年份
	  List<String> queryYear();
	  
	  //按照年份查询历年荣誉
	  List<HonorManager> queryList(String year);
	  //h5荣誉获取
	  HonorManager h5QueryHonor();
	  List<HonorManager> queryTime();
	  
	  public void updateIsH5Flag(String temp);
	  
	  public void updateHonor(HonorManager model);
	  
}
