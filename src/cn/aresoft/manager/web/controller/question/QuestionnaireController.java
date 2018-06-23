package cn.aresoft.manager.web.controller.question;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.PathUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.model.question.Question;
import cn.aresoft.common.model.question.Questionnaire;
import cn.aresoft.common.service.question.QuestionnaireService;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.question.QuestionService;
import cn.aresoft.manager.web.validate.QuestionnaireValidate;


@Controller(value = "/admin/question/questionnaire")
public class QuestionnaireController extends CommonController {
	@Inject
	private QuestionService questionService;
	@Inject
	private QuestionnaireService questionnaireService;
	public View index() {
		return new  MyBeetlView("/question/questionnaire_list.html");
	}
	
	public View json() {
		PageRecord<Questionnaire> data = questionnaireService.paging(PuffContext.getModel(Questionnaire.class), getCommonParam());
		return ViewFactory.json(data);	
	}
	
	public View add() {
		 return  new MyBeetlView("/question/questionnaire_add.html");
	}
	
	@Validate(QuestionnaireValidate.class)
	public View insert() {
		String  saas_id= PuffContext.getSessionAttribute("saas_id");
		Questionnaire model = PuffContext.getModel(Questionnaire.class);
		model.setCreat_by(getSysUser().getName());
		model.setSAAS_ID(saas_id);
		UUID randomUUID = UUID.randomUUID();
		String uuid = randomUUID.toString().replaceAll("-", "");
		model.setId(uuid);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		model.setCreat_date(format.format(new Date()));
		questionnaireService.insert(model);
		String file_url = PuffContext.getParameter("ftp_path");
		File file = new File(PathUtil.getWebRootPath(), file_url);
		String suffixName = "";
		int i = file_url.lastIndexOf(".");
		if (i != -1) {
			suffixName = file_url.substring(i + 1);
		}
		try {
			questionService.importQuestionsByExcel(file, suffixName, getSysUser(), uuid);
		} catch (IOException e) {
			e.printStackTrace();
			return ViewFactory.json(RetMsg.error("导入失败！"));
		}
		return ViewFactory.json(RetMsg.success("添加成功！！！"));
	}
	
	public View edit(String id) {
	 Questionnaire questionnaire = questionnaireService.query(id);
		MyBeetlView view =new  MyBeetlView("/question/questionnaire_edit.html", "questionnaire", questionnaire);
		return view;
	}
	@Validate(QuestionnaireValidate.class)
	public View update() {
		Questionnaire model = PuffContext.getModel(Questionnaire.class);
		model.setUpdate_by(getSysUser().getName());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		model.setUpdate_date(format.format(new Date()));
		questionnaireService.update(model);
		return ViewFactory.json(RetMsg.success("更新成功！！！"));
	}
	
	public View delete() {
		List<String> ids = PuffContext.getParameterList("ids", ",");
		if(ids!=null && ids.size()>0)
		{
			questionnaireService.deleteIn(ids);
			for(String wid:ids)
			{
				questionService.deleteByWid(wid);
			}
		}
		return ViewFactory.json(RetMsg.success("删除成功！！！"));
	}
	
	public View detail(String id){
		 Questionnaire questionnaire = questionnaireService.query(id);
		 List<Question> question_list = questionService.queryList("select * from fun_quertion where wid=? order by qidx,aidx ", id);
		 MyBeetlView view =new  MyBeetlView("/question/questionnaire_detail.html", "questionnaire", questionnaire);
		 view.put("question_list", question_list);
		 return view;
	}
}
