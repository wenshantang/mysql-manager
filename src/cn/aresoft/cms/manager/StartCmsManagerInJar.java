package cn.aresoft.cms.manager;

import java.util.Arrays;
import java.util.Collection;

import com.puff.core.Puff;
import com.puff.framework.annotation.Controller;
import com.puff.framework.utils.PackageSearch;

public class StartCmsManagerInJar {
	
	private String controllerPkg = "cn.aresoft.cms.manager.controller";
	
	public void run(){
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
