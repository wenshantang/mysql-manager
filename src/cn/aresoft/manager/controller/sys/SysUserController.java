package cn.aresoft.manager.controller.sys;

import java.util.List;

import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.annotation.Inject;
import com.puff.framework.annotation.Validate;
import com.puff.framework.utils.Security;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.common.model.RetMsg;
import cn.aresoft.common.util.MD5Util;
import cn.aresoft.common.util.SHAUtils;
import cn.aresoft.manager.controller.CommonController;
import cn.aresoft.manager.model.sys.SysRole;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.plugin.MyBeetlView;
import cn.aresoft.manager.service.sys.SysMenuService;
import cn.aresoft.manager.service.sys.SysRoleService;
import cn.aresoft.manager.service.sys.SysUserService;
import cn.aresoft.manager.utils.PermissionUtil;
import cn.aresoft.manager.validator.SysUserValidator;

@Controller(value = "/admin/sys/user", scope = BeanScope.SINGLETON)
public class SysUserController extends CommonController {

	@Inject
	private SysUserService sysUserService;

	@Inject
	private SysRoleService sysRoleService;

	@Inject
	private SysMenuService sysMenuService;

	public View index() {
		return new MyBeetlView("/admin/user/user_list.html");
	}

	public View json() throws Exception {
		PageRecord<SysUser> data = sysUserService.paging(PuffContext.getModel(SysUser.class), getCommonParam());
		return ViewFactory.json(data);
	}

	public View edit(String id) {
		SysUser sysUser = sysUserService.query(id);
		List<String> roleIds = sysUserService.queryUserRole(id);
		List<SysRole> roleList = sysRoleService.queryList();
		MyBeetlView view = new MyBeetlView("/admin/user/user_edit.html", "user", sysUser);
		view.put("roleList", roleList);
		StringBuffer sb = new StringBuffer();
		if (roleIds != null) {
			for (String rId : roleIds) {
				sb.append(rId).append("-");
			}
		}
		view.put("roleIds", sb.toString());
		return view;
	}

	@Validate(SysUserValidator.class)
	public View update() {
		SysUser user = PuffContext.getModel(SysUser.class);
		user.setStatus(PuffContext.getParameter("status", "0"));
		List<String> roles = PuffContext.getParameterList("roles");
		sysUserService.updateRole(user, roles);
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success("更新成功！！！"));
	}

	public View toChangePWD(String id) {
		MyBeetlView view = new MyBeetlView("/admin/user/user_changepwd.html", "user_id", id);
		/*SysManagerConfig config = sysManagerConfigService.querySysManagerConfig();
		String pwd_ids = config.getPwd_ids();
		Integer pwd_length_min = config.getPwd_length_min();
		Integer pwd_length_max = config.getPwd_length_max();
		String pwd_context="密码为包含";
		
		if(pwd_ids!=null && pwd_ids.length()>0){
		if(pwd_ids.indexOf("1")!=-1){
			 pwd_context+="小写字母,";
		}
		if(pwd_ids.indexOf("2")!=-1){
			 pwd_context+="大写字母,";
		}
		if(pwd_ids.indexOf("0")!=-1){
			 pwd_context+="数字,";
		}
		if(pwd_ids.indexOf("3")!=-1){
			 pwd_context+="字符,";
		}
		if(pwd_ids.indexOf("4")!=-1){
		//checkSeries("pwd",  "密码中存在连续的数字或字母");
		}
		}
		if(pwd_length_min!=null && pwd_length_max!=null){
			 pwd_context+="长度大于"+pwd_length_min+"小于"+pwd_length_max;
		}
		if(pwd_length_min!=null && pwd_length_max==null){
			 pwd_context+="长度大于"+pwd_length_min;
		}
		if(pwd_length_min==null && pwd_length_max!=null){
			 pwd_context+="长度小于"+pwd_length_max;
		}
		 pwd_context+="的字符串";
		 view.put("pwd_context", pwd_context);*/
		return view;
	}
	
	//@Validate(SysPwdValidator.class)
	public View changePWD(){
		String user_id = PuffContext.getParameter("user_id");
		SysUser user = sysUserService.query(user_id);
		String new_pwd = PuffContext.getParameter("new_pwd");
		String new_pwd_confirm = PuffContext.getParameter("new_pwd_confirm");
		if (new_pwd.equals(user.getLogin_name())) {
			return ViewFactory.json(RetMsg.error("密码和登录名一致！！！"));
		}
		if(StringUtil.blank(new_pwd)){
			return ViewFactory.json(RetMsg.error("新登陆密码不能为空"));
		}
		if(StringUtil.blank(new_pwd_confirm)){
			return ViewFactory.json(RetMsg.error("确认登陆密码不能为空"));
		}
		if(!new_pwd_confirm.equals(new_pwd)){
			return ViewFactory.json(RetMsg.error("2次密码不一致"));
		}
		/*if(Security.md5(new_pwd).equals(user.getPwd()) ||Security.md5(new_pwd).equals(user.getPwd1())||Security.md5(new_pwd).equals(user.getPwd2())){
			return ViewFactory.json(RetMsg.error("密码和前三次的密码有相同的！！！"));
		}
		user.setPwd2(user.getPwd1());
		user.setPwd1(user.getPwd());*/
		user.setPwd(SHAUtils.SHA(MD5Util.GetMD5Code(new_pwd)));
		//user.setUpdate_time(DateUtils.date2Str6(new Date(), 6));
		sysUserService.update(user);
		return ViewFactory.json(RetMsg.success("密码修改成功！！！"));
	}
	
	public View add() {
		List<SysRole> roleList = sysRoleService.queryList();
		return new MyBeetlView("/admin/user/user_add.html", "roleList", roleList);
	}

	@Validate(SysUserValidator.class)
	public View insert() {
		SysUser user = PuffContext.getModel(SysUser.class);
		user.setStatus(PuffContext.getParameter("status", "0"));
		SysUser sysUser = sysUserService.queryUserByLoginName(user.getLogin_name());
		if (sysUser != null) {
			return ViewFactory.json(RetMsg.error("用户名已经存在！！！"));
		}
		user.setPwd(SHAUtils.SHA(MD5Util.GetMD5Code(user.getPwd())));
		sysUserService.insert(user);
		List<String> roles = PuffContext.getParameterList("roles");
		if (roles != null && roles.size() > 0) {
			sysUserService.addUserRole(user.getId(), roles);
		}
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success("添加成功！！！"));
	}

	public View delete() {
		sysUserService.deleteUser(PuffContext.getParameterList("ids", ","));
		PermissionUtil.chearPer();
		sysMenuService.clearMenu();
		return ViewFactory.json(RetMsg.success("删除成功！！！"));
	}
	
	/**
	 * 解锁
	 * @return
	 */
	public View unlock(String id) {
		SysUser sysUser = sysUserService.query(id);
		sysUser.setStatus("1");
		sysUserService.update(sysUser);
		return ViewFactory.json(RetMsg.success("解锁成功！！！"));
	}

}
