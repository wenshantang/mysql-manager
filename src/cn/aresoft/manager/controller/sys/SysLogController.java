package cn.aresoft.manager.controller.sys;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysLog;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysLogService;
/**
 * 后台操作日志记录
 * @author yyj
 *
 */
@Controller(value = "/admin/sys/log", scope = BeanScope.SINGLETON)
public class SysLogController extends CommonController {

	@Inject
	private SysLogService sysLogService;

	public View index() {
		//List<SysLog> results = sysLogService.queryList();
		return new MyBeetlView("/admin/log/log_list.html");
	}

	public View json() throws Exception {
		PageRecord<SysLog> data = sysLogService.paging(PuffContext.getModel(SysLog.class), getCommonParam());
		return ViewFactory.json(data);
	}
	
	public View detail(String id){
		SysLog vo=sysLogService.query(id);
		MyBeetlView view=new MyBeetlView("/admin/log/log_detail.html");
		view.put("vo", vo);
		return view;
	}

}
