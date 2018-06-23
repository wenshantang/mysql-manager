package cn.aresoft.cms.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class CmsTopicValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		//update by yyj 2016-11-10 添加上级栏目判断start
		required("parent_id","parent_id_tip", "上级栏目不能为空");
		//update by yyj 2016-11-10 添加上级栏目判断end
		required("menu_name", "菜单名称不能为空");
		string("menu_name", 1, 20, "菜单名称长度为1-100");
		required("idx", "栏目排序不能为空");
		integer("idx", 1, 9999, "栏目排序为1-9999的数字");
		required("code", "栏目代码不能为空");
		string("code", 1, 100, "栏目代码长度为1-100");
		required("location", "访问路径不能为空");
		string("location", 1, 100, "访问路径长度为1-100");
		required("name", "栏目名称不能为空");
		string("name", 1, 200, "栏目名称长度为1-200");
		required("simple_name", "栏目简称不能为空");
		string("simple_name", 1, 100, "栏目简称长度为1-100");
		required("meta_title", "栏目标题不能为空");
		string("meta_title", 1, 200, "栏目标题长度为1-200");
		required("meta_keywords", "栏目关键字不能为空");
		string("meta_keywords", 1, 200, "栏目关键字长度为1-200");
		required("meta_desc", "栏目描述不能为空");
		string("meta_desc", 1, 300, "栏目描述长度为1-300");
		required("template_id", "template_id_tip", "请选择栏目模板");

		integer("page_size", 1, 9999, "每页显示数量为1-9999的数字");

	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
