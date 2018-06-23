package cn.aresoft.manager.web.controller.honor;

import java.util.List;

import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.DateTime;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.model.honor.HonorManager;
import cn.aresoft.common.service.honor.HonorManagerService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;

@Controller(value = "/admin/honorManager")
public class HonorManagerController extends CommonController{
	@Inject
	private HonorManagerService honorManagerService;
	
	public View index() {
		MyBeetlView view =new  MyBeetlView("/honor/honorManager_list.html");
		return view;
	}
	public View json() {
		HonorManager am=PuffContext.getModel(HonorManager.class);
		PageRecord<HonorManager> data = honorManagerService.paging(am,getCommonParam());
		return ViewFactory.json(data);
	}
	
	
	/**
	 * 添加页面
	 * @return
	 */
	public View add() {
		return new MyBeetlView("/honor/honorManager_add.html");
	}
	/**
	 * 新增
	 * @return
	 */
	//@Validate(HonorManagerValidate.class)
	public View insert() {
		//HonorManager model = PuffContext.getModel(HonorManager.class);
		String honor_name = PuffContext.getParameter("honor_name");
		String honor_type = PuffContext.getParameter("honor_type");
		String honor_from = PuffContext.getParameter("honor_from");
		String url = PuffContext.getParameter("url");
		String show_index = PuffContext.getParameter("show_index");
		String remark = PuffContext.getParameter("remark");
		String honor_year = PuffContext.getParameter("honor_year");
		String honor_month = PuffContext.getParameter("honor_month");
		String is_h5_flag = PuffContext.getParameter("is_h5_flag");
		
		HonorManager model = new HonorManager();
		model.setHonor_name(honor_name);
		model.setHonor_type(honor_type);
		model.setHonor_from(honor_from);
		model.setUrl(url);
		model.setShow_index(show_index);
		model.setRemark(remark);
		model.setHonor_year(honor_year);
		model.setHonor_month(honor_month);
		model.setIs_h5_flag(is_h5_flag);
		model.setDel_flag("0");//
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		model.setCreate_date(df.format(new Date()));
//		model.setUpdate_date(df.format(new Date()));
		model.setCreate_date(DateTime.currentTimeStamp());
		model.setUpdate_date(DateTime.currentTimeStamp());
		model.setCreate_by(getSysUser().getName());
		model.setUpdate_by(getSysUser().getName());
		if(model.getIs_h5_flag().equals("1")){
			honorManagerService.updateIsH5Flag(model.getIs_h5_flag());
			honorManagerService.insert(model);
		}else{
			
			honorManagerService.insert(model);
		}
		
		return ViewFactory.json(RetMsg.success("新增成功！"));
	}

	/**
	 * 修改页面
	 * @param id
	 * @return
	 */
	public View edit(String id) {
		
		HonorManager model = honorManagerService.query(id);
		MyBeetlView view = new MyBeetlView("/honor/honorManager_edit.html", "msg", model);
		
		return view;
	}
	/**
	 * 更新
	 * @return
	 */
	//@Validate(HonorManagerValidate.class)
	public View update() {
		//HonorManager model = PuffContext.getModel(HonorManager.class);
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		model.setUpdate_date(df.format(new Date()));
		//model.setUpdate_date(DateTime.currentTimeStamp());
//		model.setCreate_by(getSysUser().getName());
		//model.setUpdate_by(getSysUser().getName());
		String honor_name = PuffContext.getParameter("honor_name");
		String honor_type = PuffContext.getParameter("honor_type");
		String honor_from = PuffContext.getParameter("honor_from");
		String url = PuffContext.getParameter("url");
		String show_index = PuffContext.getParameter("show_index");
		String remark = PuffContext.getParameter("remark");
		String honor_year = PuffContext.getParameter("honor_year");
		String honor_month = PuffContext.getParameter("honor_month");
		String is_h5_flag = PuffContext.getParameter("is_h5_flag");
		String honor_id = PuffContext.getParameter("honor_id");
		HonorManager model = new HonorManager();
		model.setId(honor_id);
		model.setHonor_name(honor_name);
		model.setHonor_type(honor_type);
		model.setHonor_from(honor_from);
		model.setUrl(url);
		model.setShow_index(show_index);
		model.setRemark(remark);
		model.setHonor_year(honor_year);
		model.setHonor_month(honor_month);
		model.setIs_h5_flag(is_h5_flag);
		model.setDel_flag("0");//
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		model.setCreate_date(df.format(new Date()));
//		model.setUpdate_date(df.format(new Date()));
		model.setCreate_date(DateTime.currentTimeStamp());
		model.setUpdate_date(DateTime.currentTimeStamp());
		model.setCreate_by(getSysUser().getName());
		model.setUpdate_by(getSysUser().getName());
//		if(model.getIs_h5_flag()==null){
//			model.setIs_h5_flag("0");
//		}
		if(model.getIs_h5_flag().equals("1")){
			honorManagerService.updateIsH5Flag(model.getIs_h5_flag());
			honorManagerService.update(model);
		}else{
			
			honorManagerService.update(model);
		}
		
		
		return ViewFactory.json(RetMsg.success("修改成功！"));
	}
	/**
	 * 删除
	 * @return
	 */
	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		honorManagerService.updates(ids);
		return ViewFactory.json(RetMsg.success("删除成功！"));
		
//		List<String> ids = PuffContext.getParameterList("ids", ",");
//		for (String id : ids) {
//			honorManagerService.delete(id);
//		}
//		return ViewFactory.json(RetMsg.success("删除成功！"));
	}

}
		
