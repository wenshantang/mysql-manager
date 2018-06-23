package cn.aresoft.manager.plugin;

import java.io.Reader;
import java.io.StringReader;

import org.beetl.core.Resource;
import org.beetl.core.ResourceLoader;

public class JarTemplateResource extends Resource {
	private String content;

	public JarTemplateResource(String id, ResourceLoader loader) {
		super(id, loader);
	}

	public JarTemplateResource(String key, String content, ResourceLoader loader) {
		this(key, loader);
		this.content = content;
	}

	@Override
	public boolean isModified() {
		return RunType.dev();
	}

	@Override
	public Reader openReader() {
		return new StringReader(this.content);
	}

}
