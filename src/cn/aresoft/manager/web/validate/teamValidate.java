package cn.aresoft.manager.web.validate;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class teamValidate  extends Validator{
    @Override
   	protected boolean isShort() {
   		return false;
   	}

   	@Override
   	protected void validate(DispatcherExecutor excutor) {
   		required("mem_name","姓名不能为空");
   		required("mem_duty","职务不能为空");
   		required("mem_summary","个人简介不能为空");
   		required("team_type","团队类型不能为空");
//   		required("mem_photo_shot","个人照片(小)不能为空");
//   		required("mem_photo","个人照片(大)能为空");
//   		required("show_index","索引不能为空");
   		integer("show_index", 0, 100000, "索引为0-100000的整数");
   		required("mem_sex","性别不能为空");
   	}

   	@Override
   	protected View handleError(DispatcherExecutor excutor) {
   		return ViewFactory.json();
   	}
   	
}
