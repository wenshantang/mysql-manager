package cn.aresoft.manager.plugin;

public class RunType {

	private static final String RUNTYPE = System.getProperty("runType", "pro");

	public static boolean dev() {
		return RUNTYPE.equalsIgnoreCase("dev");
	}

}
