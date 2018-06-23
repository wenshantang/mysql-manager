package cn.aresoft.manager.plugin;

import org.beetl.core.GeneralVarTagBinding;

import cn.aresoft.manager.utils.PermissionUtil;
import com.puff.framework.utils.StringUtil;

public class PermissionTag extends GeneralVarTagBinding {

	@Override
	public void render() {
		String url = (String) getAttributeValue("url");
		if (StringUtil.notEmpty(url) && PermissionUtil.hasPerm(url)) {
			this.doBodyRender();
		}
	}

}
