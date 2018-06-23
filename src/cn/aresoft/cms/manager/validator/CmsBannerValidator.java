package cn.aresoft.cms.manager.validator;

import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class CmsBannerValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {

		required("type", "请设置BANNER分类");
		string("type", true, 1, 80, "BANNER分类长度不得大于80");
		
		integer("idx", 1, 9999, "索引为1-9999");
		
		integer("stop_time", 1, 9999, "轮播时间为1-9999");
		
		required("name", "BANNER名称不能为空");
		string("name", true, 1, 50, "BANNER名称长度不得大于50");
		
		
		required("title", "BANNER标题不能为空");
		string("title", true, 1, 100, "BANNER标题长度不得大于100");
		
		
		required("href_url", "跳转URL不能为空");
		string("href_url", true, 1, 300, "BANNER标题长度不得大于300");
		

		required("big_picurl", "big_picurl_tip", "请选择预览大图片");

		required("small_picurl", "small_picurl_tip", "请选择预览小图片");


	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
