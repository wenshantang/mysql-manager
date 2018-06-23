package cn.aresoft.manager.controller.sys;

import java.util.List;

import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.ftp.model.FtpServer;
import cn.aresoft.ftp.service.FtpServerService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.validator.FtpServerValidator;

@Controller("/admin/sys/ftp")
public class SysFtpServerController extends CommonController {

	@Inject
	private FtpServerService ftpServerService;

	public View index() {
		return new MyBeetlView("/admin/ftp/server_list.html");
	}

	public View json() {
		PageRecord<FtpServer> data = ftpServerService.paging(PuffContext.getModel(FtpServer.class), getCommonParam());
		return ViewFactory.json(data);
	}

	public View add() {
		return new MyBeetlView("/admin/ftp/server_add.html");
	}

	@Validate(FtpServerValidator.class)
	public View insert() {
		FtpServer ftp = PuffContext.getModel(FtpServer.class);
		ftpServerService.insert(ftp);
		return ViewFactory.json(RetMsg.success("新增成功！"));
	}

	public View edit(String id) {
		FtpServer server = ftpServerService.query(id);
		MyBeetlView view = new MyBeetlView("/admin/ftp/server_edit.html", "server", server);
		return view;
	}

	@Validate(FtpServerValidator.class)
	public View update() {
		FtpServer ftp = PuffContext.getModel(FtpServer.class);
		ftpServerService.update(ftp);
		return ViewFactory.json(RetMsg.success("修改成功！"));
	}

	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		ftpServerService.deleteIn(ids);
		return ViewFactory.json(RetMsg.success("删除成功！"));
	}

}
