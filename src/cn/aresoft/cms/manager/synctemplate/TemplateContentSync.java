package cn.aresoft.cms.manager.synctemplate;

import com.puff.core.Puff;
import com.puff.exception.ReflectException;
import com.puff.plugin.cache.CachePlugin;
import com.puff.plugin.cache.sync.CacheSync;
import com.puff.plugin.msg.Command;
import com.puff.plugin.msg.CommandFactory;
import com.puff.plugin.msg.CommandHandler;
import com.puff.plugin.quartz.Reflect;

public class TemplateContentSync implements CommandHandler {

	private final String EXECUTORCHANNEL = "1";

	private final byte OPT_UPDATE_CONTENT = 0x03;
	private CacheSync channel;

	private static class Inter {
		private static final TemplateContentSync cache = new TemplateContentSync();
	}

	/**
	 * 单例方法
	 * 
	 * @return
	 */
	public final static TemplateContentSync getInstance() {
		return Inter.cache;
	}

	private TemplateContentSync() {
		CachePlugin plugin = Puff.getPlugin(CachePlugin.class);
		channel = plugin.getCacheSync();
		CommandFactory.bindCommand(this);
	}

	public void notifyContentUpdate(String url) {
		if (channel != null) {
			Command cmd = new Command(getNameSpace(), OPT_UPDATE_CONTENT, EXECUTORCHANNEL, url);
			channel.sendCommand(cmd);
		} else {// 单机模式 前后台合一快
			try {
				Reflect.on("cn.aresoft.cms.portal.plugin.CacheResourceLoader").call("getInstance").call("addUpdateFlag", url);
			} catch (ReflectException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public byte getNameSpace() {
		return 1;
	}

	@Override
	public void handle(Command cmd) {

	}

}
