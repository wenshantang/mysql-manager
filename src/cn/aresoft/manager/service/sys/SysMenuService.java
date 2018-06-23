package cn.aresoft.manager.service.sys;

import java.util.List;

import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysLeftMenu;

public interface SysMenuService extends BaseService<SysLeftMenu> {

	public void clearMenu();

	public List<SysLeftMenu> getLeftMenu();

	public void deleteMenu(String id);

	public List<SysLeftMenu> findUserMenu(String id);

	public SysLeftMenu findLeftMenu(String pid);

	public void saveLeftMenu(SysLeftMenu menu);

	public void updateLeftMenu(SysLeftMenu menu);

	public void deleteTopMenu(String id);

	public SysLeftMenu queryTopMenu(String id);
}
