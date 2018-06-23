package cn.aresoft.manager.plugin;

import java.io.IOException;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;

import com.puff.exception.ViewException;
import com.puff.web.view.BeetlRender;
import com.puff.web.view.View;

public class MyBeetlView extends View {
	public static GroupTemplate groupTemplate = null;
	private static BeetlRender beetlRender;
	private String view;

	static {
		try {
			JarResourceLoader resourceLoader = new JarResourceLoader();
			Configuration cfg = Configuration.defaultConfiguration();
			groupTemplate = new GroupTemplate(resourceLoader, cfg);
			groupTemplate.registerTag("permission", PermissionTag.class);
			groupTemplate.registerTag("dictforEach", DictforEachTag.class);
			beetlRender = new BeetlRender(groupTemplate);
		} catch (IOException e) {
			throw new RuntimeException("can not init beetl template ", e);
		}
	}

	public MyBeetlView(String view) {
		this.view = ("resource/template/" + view).replace("//", "/");
	}

	public MyBeetlView(String view, String name, Object value) {
		this(view);
		super.put(name, value);
	}

	@Override
	public void view() throws ViewException {
		response.setContentType("text/html;charset=" + encoding);
		beetlRender.render(view, request, response);
	}

}
