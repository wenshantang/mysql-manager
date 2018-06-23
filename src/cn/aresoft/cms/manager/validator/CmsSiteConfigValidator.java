package cn.aresoft.cms.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class CmsSiteConfigValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		if ("/admin/cms/siteconfig/update/basic".equals(excutor.getExecutorKey())) {
			required("site_name", "站点名称不能为空");
			required("simple_name", "站点简称不能为空");
			required("site_domain", "站点域名不能为空");
			required("html_meta_title", "站点标题不能为空");
			required("html_meta_keywords", "站点关键字不能为空");
			required("html_meta_description", "站点描述不能为空");
		}
		if ("/admin/cms/siteconfig/update/email".equals(excutor.getExecutorKey())) {
			required("smtp_server", "邮件服务器地址不能为空");
			required("smtp_port", "邮件服务器端口不能为空");
			required("email", "发件人邮箱不能为空");
			required("password", "发件人邮箱密码不能为空");
			required("nick_name", "发件人昵称不能为空");
		}
		if ("/admin/cms/siteconfig/update/sms".equals(excutor.getExecutorKey())) {
			required("realize_class_name", "短信发送器不能为空");
		}

	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
