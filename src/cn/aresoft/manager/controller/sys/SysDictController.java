package cn.aresoft.manager.controller.sys;

import java.util.List;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysDict;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysDictService;
import cn.aresoft.manager.validator.SysDictValidator;

/**
 * 系统字典管理
 * 
 * @author dongchao
 *
 */

@Controller(value = "/admin/sys/dict", scope = BeanScope.SINGLETON)
public class SysDictController extends CommonController {

	@Inject
	private SysDictService sysDictService;

	public View index() {
		List<String> types = sysDictService.queryAllType();
		return new MyBeetlView("/admin/dict/dict_list.html", "dictTypes", types);
	}

	public View json() throws Exception {
		PageRecord<SysDict> data = sysDictService.paging(PuffContext.getModel(SysDict.class), getCommonParam());
		return ViewFactory.json(data);
	}

	public View edit(String id) {
		SysDict dict = sysDictService.query(id);
		MyBeetlView jsp = new MyBeetlView("/admin/dict/dict_edit.html", "dict", dict);
		List<String> dictTypes = sysDictService.queryAllType();
		jsp.put("dictTypes", dictTypes);
		return jsp;
	}

	@Validate(SysDictValidator.class)
	public View update() {
		SysDict dict = PuffContext.getModel(SysDict.class);
		sysDictService.update(dict);
		sysDictService.clearCache();
		return ViewFactory.json(RetMsg.success("更新成功！！！"));
	}

	public View add() {
		List<String> dictTypes = sysDictService.queryAllType();
		return new MyBeetlView("/admin/dict/dict_add.html", "dictTypes", dictTypes);
	}

	@Validate(SysDictValidator.class)
	public View insert() {
		SysDict dict = PuffContext.getModel(SysDict.class);
		sysDictService.insert(dict);
		sysDictService.clearCache();
		return ViewFactory.json(RetMsg.success("添加成功！！！"));
	}

	public View delete() {
		sysDictService.deleteIn(PuffContext.getParameterList("ids", ","));
		sysDictService.clearCache();
		return ViewFactory.json(RetMsg.success("删除成功！！！"));
	}

}
