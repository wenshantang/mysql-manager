package cn.aresoft.manager.web.controller.sys;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.sys.LogCount;
import cn.aresoft.common.service.sys.LogCountService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysLog;
import cn.aresoft.manager.plugin.MyBeetlView;

@Controller(value = "/admin/sys/logcount", scope = BeanScope.SINGLETON)
public class LogCountController extends CommonController{

	@Inject
	private LogCountService logCountService;
	
	public View index() {
		
		return new MyBeetlView("/logcount/logcount_list.html");
	}

	public View json() throws Exception {
		LogCount model = PuffContext.getModel(LogCount.class);
		PageRecord<LogCount> data = logCountService.paging(model, getCommonParam());
		return ViewFactory.json(data);
	}
	


}

