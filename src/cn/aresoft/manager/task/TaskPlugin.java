package cn.aresoft.manager.task;

import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import com.puff.ioc.BeanFactory;
import com.puff.log.Log;
import com.puff.log.LogFactory;
import com.puff.plugin.Plugin;

import cn.aresoft.manager.model.sys.SysTask;
import cn.aresoft.manager.service.sys.SysTaskService;

public class TaskPlugin implements Plugin {
	private static final Log logger = LogFactory.get();

	private Hashtable<String, Scheduler> cacheTask;

	public Hashtable<String, Scheduler> getTasks() {
		return cacheTask;
	}

	public void setTasks(Hashtable<String, Scheduler> cacheTask) {
		this.cacheTask = cacheTask;
	}

	@Override
	public void init(Properties prop) {
		cacheTask = new Hashtable<String, Scheduler>();
	}

	@Override
	public boolean start() {
		SysTaskService service = BeanFactory.getBean("sysTaskService");
		List<SysTask> tasks = service.queryList();
		try {
			logger.info("开始初始化自动任务......");
			for (SysTask task : tasks) {
				cacheTask.put(task.getId(), SchedulerUtil.getScheduler(task));
			}
			logger.info("自动任务初始化成功!");
		} catch (Exception e) {
			logger.error("自动任务启动错误!" + e);
		}
		return true;
	}

	@Override
	public boolean stop() {
		if (cacheTask != null) {
			for (Entry<String, Scheduler> entry : cacheTask.entrySet()) {
				Scheduler scheduler = entry.getValue();
				if (scheduler != null) {
					scheduler.cancel();
				}
			}
		}
		return true;
	}

}
