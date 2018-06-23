package cn.aresoft.cms.manager.synctemplate;

import java.io.File;
import java.util.Properties;

import com.puff.framework.utils.FileUtil;
import com.puff.framework.utils.PathUtil;
import com.puff.framework.utils.StringUtil;
import com.puff.ioc.BeanFactory;
import com.puff.jdbc.executor.SimpleExecutor;
import com.puff.plugin.Plugin;

import cn.aresoft.cms.common.cache.CmsTemplateCache;
import cn.aresoft.cms.common.model.CmsTemplate;

public class TemplateSyncPlugin implements Plugin {

	private int scanIntervalSeconds;
	private Scanner scanner;
	private String templatePath;

	public String getTemplatePath() {
		return templatePath;
	}

	@Override
	public void init(Properties prop) {
		this.scanIntervalSeconds = Integer.valueOf(prop.getProperty("scanIntervalSeconds", "3"));
		this.templatePath = StringUtil.empty(prop.getProperty("templatePath"), PathUtil.getWebRootPath() + File.separatorChar + "WEB-INF" + File.separatorChar + "template");
		this.templatePath = templatePath.replace("\\", "/");
		TemplateContentSync.getInstance();
	}

	@Override
	public boolean start() {
		final CmsTemplateCache cmsTemplateCache = BeanFactory.getBean("cmsTemplateCache");
		final TemplateContentSync contentSync = TemplateContentSync.getInstance();
		if (scanIntervalSeconds > 0) {
			scanner = new Scanner(templatePath, scanIntervalSeconds) {
				public void onChange(File file) {
					try {
						String path = file.getCanonicalPath();
						path = path.replace("\\", "/").replace(templatePath, "").replace("\\", "/");
						System.out.println(path + "  changed.");
						SimpleExecutor executor = SimpleExecutor.getInstance();
						String content = FileUtil.readFile(file, "UTF-8");
						executor.updateBySql("update cms_template set content=? where path=?", content, path);
						CmsTemplate template = executor.query(CmsTemplate.class, "select * from cms_template where path=? ", path);
						cmsTemplateCache.update(template.getPath(), template);
						contentSync.notifyContentUpdate(path);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			scanner.start();
		}

		return true;
	}

	@Override
	public boolean stop() {
		if (scanner != null) {
			scanner.stop();
		}
		return true;
	}

}
