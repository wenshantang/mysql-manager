package cn.aresoft.cms.common;

import com.puff.ioc.BeanFactory;

public class StartCommonInjar {

	String beanPkg = "cn.aresoft.cms.common.cache,cn.aresoft.cms.common.service.impl";

	public void run() {
		for (String bean : beanPkg.split(",")) {
			BeanFactory.loadPackage(bean);
		}
	}
}
