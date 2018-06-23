package cn.aresoft.manager.task;


public abstract class TaskScheduler implements Runnable {
	public static final int INIT_RUN = 0;// 初始化状态
	public static final int WAIT_RUN = 1;// 等待运行
	public static final int KILL_RUN = 2;// 运行完成后退出 Kill线程
	public static final int RUNNING = 3;// 运行中
	private int state = INIT_RUN;

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	protected void setRunningState() {
		// 设置任务为运行状态
		setState(RUNNING);
	}

	protected void setWaitingState() {
		// 设置任务为待运行状态
		setState(WAIT_RUN);
	}

	public abstract void run();

}