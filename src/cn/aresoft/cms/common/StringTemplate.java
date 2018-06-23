package cn.aresoft.cms.common;

import java.util.Map;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

import com.puff.framework.utils.StringUtil;
import com.puff.log.Log;
import com.puff.log.LogFactory;

public class StringTemplate {
	public static final Log log = LogFactory.get();

	private static GroupTemplate gt;

	static {
		gt = new GroupTemplate();
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		gt.setResourceLoader(resourceLoader);
	}

	public static Template getTemplate(String content) {
		return gt.getTemplate(content);
	}

	public static String parser(String content, Map<String, Object> param) {
		if(StringUtil.empty(content)) return null;
		try {
			Template template = gt.getTemplate(content);
			template.binding(param);
			return template.render();
		} catch (Exception e) {
			log.error("解析模板出错\n模板内容{1}}", e, content);
			return null;
		}
	}

}
