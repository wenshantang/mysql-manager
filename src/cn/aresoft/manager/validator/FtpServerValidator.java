package cn.aresoft.manager.validator;

import java.nio.charset.Charset;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class FtpServerValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("server_name", "服务器名称不能为空");
		string("server_name", true, 1, 50, "服务器名称长度不得大于50");
		required("server_ip", "服务器IP地址不能为空");
		string("server_ip", true, 1, 20, "服务器IP地址长度不得大于20");
		required("port", "服务器端口不能为空");
		integer("port", 1, 65535, "服务器端口为小于65535数字");

		required("username", "登录用户名不能为空");
		string("username", true, 1, 50, "登录用户名长度不得大于50");

		required("passwd", "登录密码不能为空");
		string("passwd", true, 1, 50, "登录密码长度不得大于50");

		required("path", "默认路径不能为空");
		string("path", true, 1, 200, "默认路径长度不得大于200");

		required("encoding", "编码不能为空");

		String encoding = PuffContext.getParameter("encoding");
		try {
			Charset.forName(encoding);
		} catch (Exception e) {
			addError("encoding", "不合法的编码");
		}

		required("timeout", "超时时间不能为空");

		integer("timeout", 0, Integer.MAX_VALUE, "超时时间为0-" + Integer.MAX_VALUE + "");

		required("visit_url", "访问ULR不能为空");
		string("visit_url", true, 1, 200, "访问ULR长度不得大于200");

		required("server_type", "服务器类型不能为空");
		string("server_type", true, 1, 50, "服务器类型长度不得大于50");

		string("memo", false, 0, 200, "描述长度不得大于200");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
