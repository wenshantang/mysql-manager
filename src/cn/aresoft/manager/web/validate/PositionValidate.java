package cn.aresoft.manager.web.validate;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class PositionValidate extends Validator{

    
    @Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {
		required("post_name","岗位名称不能为空");
		required("job_name","岗位职称不能为空");
		required("degree","学历要求不能为空");
		required("experience","经验要求不能为空");
		required("recruit_type","招聘类型不能为空");
		required("recruit_title","招聘标题不能为空");
		required("dep_type","部门不能为空");
		required("show_index","索引不能为空");
		integer("show_index", 0, 100000, "索引为0-100000的整数");
		required("req_num","需求人数不能为空");
		required("description","岗位职责不能为空");
		required("post_request","任职资格不能为空");
	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}
	
}
