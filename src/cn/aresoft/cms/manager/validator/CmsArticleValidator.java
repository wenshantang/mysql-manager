package cn.aresoft.cms.manager.validator;

import java.util.List;

import com.puff.framework.utils.StringUtil;
import com.puff.web.interceptor.Validator;
import com.puff.web.mvc.DispatcherExecutor;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

public class CmsArticleValidator extends Validator {

	@Override
	protected boolean isShort() {
		return false;
	}

	@Override
	protected void validate(DispatcherExecutor excutor) {

		String isTop = PuffContext.getParameter("isTop");
		if ("1".equals(isTop)) {
			integer("i_top", 1, Integer.MAX_VALUE, "置顶排序为1-" + Integer.MAX_VALUE + "");
		}
		required("topicIds", "topic_id_tip", "请选择文章栏目");
		//List<String> topicIds = PuffContext.getParameterList("topicIds");//获取新增文章关联的栏目id
		
		String isOut = PuffContext.getParameter("isOut");
		if ("1".equals(isOut)) {
			required("article_location", "外部URL不能为空");
			string("article_location", true, 1, 300, "外部URL长度不得大于300");
		}
		required("model_id", "model_id_tip", "请选择文章模型");
		
		required("topic_id", "topic_id_tip", "请选择文章栏目");

		required("title", "文章标题不能为空");
		string("title", true, 1, 80, "文章标题长度不得大于80");

//		required("short_title", "文章小标题不能为空");
		string("short_title", false, 0, 50, "文章小标题长度不得大于50");

//		required("short_title", "文章小标题不能为空");
//		string("short_title", false, 0, 50, "文章小标题长度不得大于50");

//		required("meta_title", "html metatitle不能为空");
//		string("meta_title", false, 0, 100, "html metatitle长度不得大于100");

//		required("meta_keywords", "html metakeywords不能为空");
//		string("meta_keywords", false, 0, 200, "html metakeywords长度不得大于200");

//		required("meta_desc", "html metadescription不能为空");
//		string("meta_desc", false, 0, 300, "html metadescription长度不得大于300");

		required("digest", "简短摘要不能为空");
		string("digest", true, 1, 400, "简短摘要长度不得大于400");

//		required("centre_tip", "核心提示不能为空");
//		string("centre_tip", false, 0, 150, "核心提示长度不得大于150");

		string("author", false, 0, 50, "文章作者长度不得大于50");

//		required("big_picurl", "big_picurl_tip", "请选择预览大图片");

//		required("small_picurl", "small_picurl_tip", "请选择预览小图片");

		required("publish_time", "publish_time_tip", "发布时间不能为空");
		date("publish_time", "yyy-MM-dd hh:mm:ss", "publish_time_tip", "发布时间不合法");

	}

	@Override
	protected View handleError(DispatcherExecutor excutor) {
		return ViewFactory.json();
	}

}
