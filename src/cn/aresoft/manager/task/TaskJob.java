package cn.aresoft.manager.task;

import cn.aresoft.manager.model.sys.SysTask;

/**
 * 任务
 * 
 * @author dongchao
 *
 */
public abstract class TaskJob {

	public abstract boolean waitting(SysTask task);

	public abstract void execute(SysTask task);

}
