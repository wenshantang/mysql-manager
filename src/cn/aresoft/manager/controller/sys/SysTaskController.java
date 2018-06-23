package cn.aresoft.manager.controller.sys;

import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import com.puff.core.Puff;
import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.DateTime;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.log.Log;
import com.puff.log.LogFactory;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysTask;
import cn.aresoft.manager.model.sys.Week;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysTaskService;
import cn.aresoft.manager.task.Scheduler;
import cn.aresoft.manager.task.SchedulerUtil;
import cn.aresoft.manager.task.TaskPlugin;
import cn.aresoft.manager.validator.SysTaskValidator;

@Controller(value = "/admin/sys/task", scope = BeanScope.SINGLETON)
public class SysTaskController extends CommonController {

	private static final Log logger = LogFactory.get();

	@Inject
	private SysTaskService sysTaskService;

	public View index() {
		return new MyBeetlView("/admin/task/task_list.html");
	}

	public View json() {
		PageRecord<SysTask> data = sysTaskService.paging(PuffContext.getModel(SysTask.class), getCommonParam());
		return ViewFactory.json(data);
	}

	/**
	 * 跳到编辑页面
	 * 
	 * @return
	 */
	public View edit(String id) {
		View view = new MyBeetlView("/admin/task/task_edit.html");
		SysTask task = sysTaskService.query(id);
		view.put("task", task);
		if ("1".equals(task.getPeriodsType())) {
			Week week = SchedulerUtil.getWeek(task.getWorkDay());
			view.put("week", week);
		}
		return view;
	}

	/**
	 * 更新任务
	 * 
	 * @return
	 */
	@Validate(SysTaskValidator.class)
	public View update() {
		SysTask task = PuffContext.getModel(SysTask.class);
		String[] workDays = PuffContext.getParameterValues("workDays");
		task.setWorkDay(initWorkDay(workDays));
		task = SchedulerUtil.refresh(task);
		sysTaskService.update(task);
		updateScheduleInServletContext(task.getId());
		return ViewFactory.json(RetMsg.success("更新成功！！！"));
	}

	/**
	 * 跳到add页面
	 * 
	 * @return
	 */
	public View add() {
		return new MyBeetlView("/admin/task/task_add.html");
	}

	/**
	 * 计算工作日
	 */
	private static String initWorkDay(String[] workDays) {
		String retrunValue = "";
		if (StringUtil.notEmpty(workDays)) {
			String[] result = { "0", "0", "0", "0", "0", "0", "0" };
			for (int i = 0; i < workDays[0].length(); i++) {
				int a = Integer.parseInt(result[i]);
				for (int j = 0; j < workDays.length; j++) {
					int b = Integer.parseInt(String.valueOf(workDays[j].charAt(i)));
					a = a | b;
					if (a == 1)
						break;
				}
				result[i] = String.valueOf(a);
			}
			String tmp = "";
			for (int i = 0; i < result.length; i++)
				tmp += result[i];
			retrunValue = tmp;
		} else
			retrunValue = "0000000";
		return retrunValue;
	}

	/**
	 * 新增任务
	 * 
	 * @return
	 */

	@Validate(SysTaskValidator.class)
	public View insert() {
		SysTask task = PuffContext.getModel(SysTask.class);
		String[] workDays = PuffContext.getParameterValues("workDays");
		task.setWorkDay(initWorkDay(workDays));
		task = SchedulerUtil.refresh(task);
		task.setCreateTime(DateTime.currentTimeStamp());
		sysTaskService.insert(task);
		// .刷新ServletContext容器 只有任务为运行状态才更新
		if ("1".equals(task.getIsRun())) {
			String scheduleId = task.getId();
			updateScheduleInServletContext(scheduleId);
		}
		return ViewFactory.json(RetMsg.success("新增成功！！！"));
	}

	/**
	 * 删除任务
	 * 
	 * @return
	 */
	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		sysTaskService.deleteIn(ids);
		for (String id : ids) {
			updateScheduleInServletContext(id);
		}
		return ViewFactory.json(RetMsg.success("删除成功！！！"));
	}

	/**
	 * 新增、修改、删除任务之后需要刷新TaskPlugin
	 */
	private void updateScheduleInServletContext(String scheduleId) {
		logger.info("开始更新AUTOTASK任务");
		TaskPlugin plugin = Puff.getPlugin(TaskPlugin.class);
		Hashtable<String, Scheduler> cacheTask = plugin.getTasks();
		// 1.在cacheTask集合中删除当前操作的Task
		for (Entry<String, Scheduler> entry : cacheTask.entrySet()) {
			String id = entry.getKey();
			if (id.equals(scheduleId)) {
				Scheduler sch = cacheTask.remove(scheduleId);
				sch.cancel();
				logger.info("Frist:成功停止更改的任务-->" + scheduleId);
				break;
			}
		}
		// 2.从数据库中查出当前操作后的task
		SysTask task = sysTaskService.query(scheduleId);
		if (task != null) {
			// 运行状态下才存进cacheTask
			if ("1".equals(task.getIsRun())) {
				logger.info("往任务容器存入新的任务-->" + task.getScheduleName());
				cacheTask.put(scheduleId, SchedulerUtil.getScheduler(task));
			} else {
				// 修改任务为不运行状态
				logger.info("已经将任务-->" + task.getScheduleName() + " 设置成不运行状态,从任务容器中删除");
				cacheTask.remove(scheduleId);
			}
		} else {
			// 找不到，表示当前操作为删除任务
			logger.info("已将任务成功删除");
		}
		plugin.setTasks(cacheTask);
	}

}
