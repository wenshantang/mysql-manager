package cn.aresoft.manager.web.controller.sys;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.manager.model.sys.SysDict;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysDictExtService;
import cn.aresoft.manager.web.controller.BaseController;
/***
 * 网站流量统计分析<br/>
 * 链接到百度统计，支持免密查看与单独使用密码登录查看<br/>
 * 如果使用了密码，密码保存在字典管理(sys_dict表)中:{字典项分类:baidutongji,字典名称 :password}<br/>
 * 备注（memo）为是否免密
 * @author yyj
 *
 */
@Controller(value = "/admin/sys/websiteAnlytics", scope = BeanScope.SINGLETON)
public class WebsiteAnalyticsController extends BaseController{
	@Inject
	SysDictExtService sysDictExtService;

	private String baidutongjiType="baidutongji";
	private String baidutongjiKey="password";
	
	public View index(){
		//查询保存在系统字典表里的百度统计密码
		SysDict sysdict=sysDictExtService.querySysDictByTypeAndName(baidutongjiType,baidutongjiKey);
		String baidutongjiPassword="";//百度统计密码
		String baidutongjiNoPassword="";//百度统计是否免密登陆【0：否；1；是】
		if(sysdict!=null){
			baidutongjiPassword=sysdict.getValue();
			baidutongjiNoPassword=sysdict.getMemo();
		}
		return new MyBeetlView("/sys/website/websiteAnalyticsIndex.html")
				.put("baidutongjiPassword", baidutongjiPassword)
				.put("baidutongjiNoPassword", baidutongjiNoPassword);
	}
	
	/**
	 * 修改统计密码
	 * @return
	 */
	public View updateTjPassword(){
		try{
			String baidutongjiPassword=PuffContext.getParameter("baidutongjiPassword");
			String baidutongjiNoPassword=PuffContext.getParameter("baidutongjiNoPassword");
			sysDictExtService.updateByType(baidutongjiType, baidutongjiKey, baidutongjiPassword,baidutongjiNoPassword);
		}catch(Exception ex){
			ex.printStackTrace();
			log.error("修改统计密码异常》》", ex);
			return ViewFactory.json(RetMsg.error("修改失败"));
		}
		return ViewFactory.json(RetMsg.success("修改成功"));
	}
}
