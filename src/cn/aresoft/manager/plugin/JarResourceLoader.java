package cn.aresoft.manager.plugin;

import java.util.HashMap;
import java.util.Map;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;

import com.puff.framework.utils.FileUtil;
import com.puff.framework.utils.PathUtil;
import com.puff.framework.utils.StringUtil;

public class JarResourceLoader implements ResourceLoader {
	private static final Map<String, String> template = new HashMap<String, String>();

	@Override
	public Resource getResource(String key) {
		String content = template.get(key);
		if (StringUtil.empty(content)) {
			content = FileUtil.toString("UTF-8", PathUtil.fromJar(key));
			if (!RunType.dev()) {
				template.put(key, content);
			}
		}
		return new JarTemplateResource(key, content, this);
	}

	@Override
	public boolean isModified(Resource key) {
		return RunType.dev();
	}

	@Override
	public boolean exist(String key) {
		return true;
	}

	@Override
	public void close() {

	}

	@Override
	public void init(GroupTemplate gt) {

	}

	@Override
	public String getResourceId(Resource resource, String id) {
		return id;
	}

}
