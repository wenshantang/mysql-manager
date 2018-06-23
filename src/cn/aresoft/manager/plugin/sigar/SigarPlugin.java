package cn.aresoft.manager.plugin.sigar;

import java.util.Properties;
import java.util.Timer;

import com.puff.log.Log;
import com.puff.log.LogFactory;
import com.puff.plugin.Plugin;

import cn.aresoft.manager.plugin.Start;

public class SigarPlugin implements Plugin {
	private static final Log logger = LogFactory.get(SigarPlugin.class);

	private SystemMonitorTask task;

	private Timer timer;

	private Properties prop;

	@Override
	public void init(Properties prop) {
		try {
			new Start().run();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.prop = prop;
		task = new SystemMonitorTask();
	}

	@Override
	public boolean start() {
		logger.info("启动系统定时任务");
		timer = new Timer("Task-SystemMonitor");
		long time = Long.parseLong(prop.getProperty("saveInvokeLogInterval", "60000"));
		timer.schedule(task, 0, time);
		logger.info("保存调用日志任务启动成功！任务运行间隔" + time / 1000 + "秒");
		return true;
	}

	@Override
	public boolean stop() {
		logger.info("关闭系统定时任务");
		if (task != null) {
			task.cancel();
		}
		if (timer != null) {
			timer.cancel();
		}
		return true;
	}

}
