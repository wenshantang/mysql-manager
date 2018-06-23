package cn.aresoft.common.service.impl.sys;

import com.puff.framework.annotation.Bean;

import cn.aresoft.common.model.BaseModel;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.common.service.sys.CRMService;

@Bean(id = "crmService")
public class CRMServiceImpl extends CenterServiceImpl<BaseModel>implements CRMService {


}
