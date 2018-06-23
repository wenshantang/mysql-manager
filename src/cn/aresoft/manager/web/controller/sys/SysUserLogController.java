package cn.aresoft.manager.web.controller.sys;

import java.util.List;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.model.sys.SysUserLog;
import cn.aresoft.common.service.sys.SysUserLogService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;
/**
 * 密码管理、修改手机号等功能的接口处业务日志
 * @author 
 *
 */
@Controller(value = "/admin/sys/userlog", scope = BeanScope.SINGLETON)
public class SysUserLogController extends CommonController{
	@Inject
	private SysUserLogService userLogService;
	public View index(){
		return new MyBeetlView("/sys/log_list.html");
	}
	public View json(){
		SysUserLog log = PuffContext.getModel(SysUserLog.class);
		PageRecord<SysUserLog> data = userLogService.paging(log, getCommonParam());
		return ViewFactory.json(data);
	}
	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		for (String id : ids) {
			userLogService.delete(id);
		}
		return ViewFactory.json(RetMsg.success("删除成功！"));
	}

}

