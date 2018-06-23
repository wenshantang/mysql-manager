package cn.aresoft.manager.service.question;

import java.io.File;
import java.io.IOException;

import cn.aresoft.common.model.question.Question;
import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysUser;

public interface QuestionService extends BaseService<Question> {

	public int importQuestionsByExcel(File file, String filetype, SysUser user,String ques_id) throws IOException;


	void deleteByWid(String wid);
}
