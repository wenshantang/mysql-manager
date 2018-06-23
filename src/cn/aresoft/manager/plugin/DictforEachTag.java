package cn.aresoft.manager.plugin;

import java.util.List;

import org.beetl.core.GeneralVarTagBinding;

import com.puff.ioc.BeanFactory;

import cn.aresoft.manager.model.sys.SysDict;
import cn.aresoft.manager.service.sys.SysDictService;

public class DictforEachTag extends GeneralVarTagBinding {

	private SysDictService sysDictService = BeanFactory.getBean("sysDictService");

	@Override
	public void render() {
		String key = (String) getAttributeValue("key");
		List<SysDict> dicts = sysDictService.findListFormCache(key);
		if (dicts != null) {
			for (SysDict sysDict : dicts) {
				this.binds(sysDict);
				this.doBodyRender();
			}
		}

	}

}
