package cn.aresoft.manager.plugin.sigar;

import java.util.TimerTask;

import org.hyperic.sigar.SigarException;

public class SystemMonitorTask extends TimerTask {

	@Override
	public void run() {
		SystemMonitor task = new SystemMonitor();
		try {
			task.start();
		} catch (SigarException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
