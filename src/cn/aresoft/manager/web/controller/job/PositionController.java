package cn.aresoft.manager.web.controller.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.cms.common.cache.CmsPropertiesCache;
import cn.aresoft.cms.common.model.CmsProperties;
import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.model.job.Position;
import cn.aresoft.common.service.job.PositionService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.web.validate.PositionValidate;

@Controller(value = "/admin/position")
public class PositionController extends CommonController {
    @Inject
    private PositionService positionService;
    @Inject
    private CmsPropertiesCache cmsPropertiesCache;
    //private static SimpleDateFormat ct = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat ut = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public View index() {
	// 招聘类型
	List<CmsProperties> recruit = cmsPropertiesCache.findListFormCache("RECRUIT");
	// 招聘标题
	List<CmsProperties> tittletype = cmsPropertiesCache.findListFormCache("TITTLETYPE");
	// 部门
	List<CmsProperties> department = cmsPropertiesCache.findListFormCache("DEPARTMENT");
	MyBeetlView view = new MyBeetlView("/job/position_list.html");
	view.put("recruit", recruit);
	view.put("tittletype", tittletype);
	view.put("department", department);
	return view;
    }

    public View json() {
	Position position = PuffContext.getModel(Position.class);
	/*
	 * System.out.println(PuffContext.getParameter("create_date"));
	 * System.out.println(PuffContext.getParameter("create_date1"));
	 */
	PageRecord<Position> data = positionService.paging(position, getCommonParam());
	return ViewFactory.json(data);
    }

    // 增加
    public View add() {
	// 招聘类型
	List<CmsProperties> recruit = cmsPropertiesCache.findListFormCache("RECRUIT");
	// 招聘标题
	List<CmsProperties> tittletype = cmsPropertiesCache.findListFormCache("TITTLETYPE");
	// 部门
	List<CmsProperties> department = cmsPropertiesCache.findListFormCache("DEPARTMENT");
	MyBeetlView view = new MyBeetlView("/job/position_add.html");
	view.put("recruit", recruit);
	view.put("tittletype", tittletype);
	view.put("department", department);
	return view;
    }

    @Validate(PositionValidate.class)
    public View insert() {

	Position position = PuffContext.getModel(Position.class);
	position.setCreate_by(getSysUser().getName());
//	position.setShow_index("1");
	position.setDel_flag("0");
	position.setCreate_date(ut.format(new Date()));
	positionService.insert(position);
	return ViewFactory.json(RetMsg.success("新增成功！"));
    }

    // 查询单条数据
    public View edit(String id) {
	// 招聘类型
	List<CmsProperties> recruit = cmsPropertiesCache.findListFormCache("RECRUIT");
	// 招聘标题
	List<CmsProperties> tittletype = cmsPropertiesCache.findListFormCache("TITTLETYPE");
	// 部门
	List<CmsProperties> department = cmsPropertiesCache.findListFormCache("DEPARTMENT");
	Position position = positionService.query(id);
	MyBeetlView view = new MyBeetlView("/job/position_edit.html", "ap", position);
	view.put("recruit", recruit);
	view.put("tittletype", tittletype);
	view.put("department", department);
	return view;
    }

    @Validate(PositionValidate.class)
    public View update() {
	Position position = PuffContext.getModel(Position.class);
	position.setUpdate_date(ut.format(new Date()));
	position.setUpdate_by(getSysUser().getName());
	positionService.update(position);

	return ViewFactory.json(RetMsg.success("修改成功！"));
    }

    public View delete() {
	List<String> ids = PuffContext.getParameterList("ids", ",");
	positionService.updates(ids);
	return ViewFactory.json(RetMsg.success("删除成功！"));
    }
}
