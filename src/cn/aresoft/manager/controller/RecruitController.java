package cn.aresoft.manager.controller;

import java.util.List;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.DateTime;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.Recruit;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.RecruitService;
/**
 * 应聘功能
 */
@Controller(value = "/admin/recruit", scope = BeanScope.SINGLETON)
public class RecruitController extends CommonController {
    @Inject
    private RecruitService recruitService;    
    public View index() {
        MyBeetlView view = new MyBeetlView("/recruit/recruit_list.html");
        return view;
    }

    public View json() {
        Recruit r = PuffContext.getModel(Recruit.class);
        PageRecord<Recruit> data = recruitService.paging(r,
                getCommonParam());
        return ViewFactory.json(data);
    }

    // 查询单条数据
    public View edit(String id) {
        Recruit r = recruitService.query(id);
        MyBeetlView view = new MyBeetlView("/recruit/recruit_edit.html");
        view.put("r", r);
        return view;
    }

    public View update() {
        Recruit r = PuffContext.getModel(Recruit.class);
        r.setUpdate_time(DateTime.currentTimeStamp());
        recruitService.update(r);
        return ViewFactory.json(RetMsg.success("修改成功！"));
    }

    public View delete() {
        List<String> ids = PuffContext.getParameterList("ids", ",");
        for (String id : ids) {
            recruitService.delete(id);
        }
        return ViewFactory.json(RetMsg.success("删除成功！"));
    }
    

}
