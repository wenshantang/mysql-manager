package cn.aresoft.manager.plugin;

import java.util.Arrays;
import java.util.Collection;

import com.puff.core.Puff;
import com.puff.framework.annotation.Controller;
import com.puff.framework.utils.PackageSearch;
import com.puff.ioc.BeanFactory;

public class Start {

	String beanPkg = "cn.aresoft.manager.service";
	String controllerPkg = "cn.aresoft.manager.controller";

	public void run() {

		for (String bean : beanPkg.split(",")) {
			BeanFactory.loadPackage(bean);
		}
		Collection<Class<Controller>> classes = PackageSearch.findClassByClazz(Controller.class, Arrays.asList(controllerPkg), getClass());
		if (classes == null || classes.size() == 0) {
			return;
		}
		for (Class<?> clazz : classes) {
			try {
				Puff.addMapping(clazz);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
