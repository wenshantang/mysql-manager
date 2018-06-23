package cn.aresoft.manager.service.impl.sys;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Transaction;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysLeftMenu;
import cn.aresoft.manager.service.sys.SysMenuService;

@Bean(id = "sysMenuService")
public class SysMenuServiceImpl extends CenterServiceImpl<SysLeftMenu>implements SysMenuService {

	@Override
	public List<SysLeftMenu> getLeftMenu() {
		Map<Object, Object> map = executor.queryMap("select id,url from sys_resource ");
		List<SysLeftMenu> topMenus = executor.queryList(SysLeftMenu.class, SqlBuilder.buildQuerySQL(SysLeftMenu.class) + " where parent_id='888888888888888888' order by idx ");
		if (topMenus != null) {
			String sql = SqlBuilder.buildQuerySQL(SysLeftMenu.class) + " where parent_id=? order by idx";
			Iterator<SysLeftMenu> it = topMenus.iterator();
			while (it.hasNext()) {
				SysLeftMenu sysTopMenu = it.next();
				if ("2".equals(sysTopMenu.getType())) {
					if (StringUtil.empty(sysTopMenu.getUrl())) {
						sysTopMenu.setUrl(String.valueOf(map.get(sysTopMenu.getResourceId())));
					}
					continue;
				}
				List<SysLeftMenu> leftMenus = executor.queryList(SysLeftMenu.class, sql, sysTopMenu.getId());// 取出一级菜单
				if (leftMenus == null || leftMenus.size() == 0) {
					continue;
				} else {
					Iterator<SysLeftMenu> lt = leftMenus.iterator();
					while (lt.hasNext()) {
						SysLeftMenu menu = lt.next();
						List<SysLeftMenu> sonMenus = executor.queryList(SysLeftMenu.class, sql, menu.getId());// 取出二级菜单
						if (sonMenus == null || sonMenus.size() == 0) {
							continue;
						} else {
							Iterator<SysLeftMenu> son = sonMenus.iterator();
							while (son.hasNext()) {
								SysLeftMenu m = son.next();
								if (StringUtil.empty(m.getUrl())) {
									m.setUrl(String.valueOf(map.get(m.getResourceId())));
								}
							}
							menu.setSons(sonMenus);
						}
					}
					sysTopMenu.setSons(leftMenus);
				}
			}
		}
		return topMenus;
	}

	@Override
	@Transaction
	public void deleteMenu(String id) {
		executor.delete("delete from sys_left_menu where id=?", id);
		deleteBySql("delete from sys_left_menu where parent_id=?", id);
	}

	private final static Map<String, List<SysLeftMenu>> menu_cache = new HashMap<String, List<SysLeftMenu>>();

	@Override
	public void clearMenu() {
		menu_cache.clear();
	}

	@Override
	public List<SysLeftMenu> findUserMenu(String userId) {
		List<SysLeftMenu> list = menu_cache.get(userId);
		if (list == null) {
			list = queryUserMenu(userId);
			menu_cache.put(userId, list);
		}
		return list;
	}

	List<SysLeftMenu> queryUserMenu(String userId) {
		Map<Object, Object> map = executor.queryMap(
				"select id,url from sys_resource where id in (select distinct resource_id from sys_role_resource where role_id in (select role_id from sys_user_role where user_id=?) )",
				userId);

		List<SysLeftMenu> topMenus = executor.queryList(SysLeftMenu.class, SqlBuilder.buildQuerySQL(SysLeftMenu.class) + " where parent_id='888888888888888888' order by idx ");
		if (topMenus != null) {
			String sql = SqlBuilder.buildQuerySQL(SysLeftMenu.class) + " where parent_id=? order by idx";
			Iterator<SysLeftMenu> it = topMenus.iterator();
			while (it.hasNext()) {
				SysLeftMenu sysTopMenu = it.next();
				if ("2".equals(sysTopMenu.getType())) {
					if (!map.containsKey(sysTopMenu.getResourceId())) {
						it.remove();
					} else {
						if (StringUtil.empty(sysTopMenu.getUrl())) {
							sysTopMenu.setUrl(String.valueOf(map.get(sysTopMenu.getResourceId())));
						}
					}
					continue;
				}
				List<SysLeftMenu> leftMenus = executor.queryList(SysLeftMenu.class, sql, sysTopMenu.getId());// 取出一级菜单
				if (leftMenus == null || leftMenus.size() == 0) {
					it.remove();
				} else {
					Iterator<SysLeftMenu> lt = leftMenus.iterator();
					while (lt.hasNext()) {
						SysLeftMenu menu = lt.next();
						List<SysLeftMenu> sonMenus = executor.queryList(SysLeftMenu.class, sql, menu.getId());// 取出二级菜单
						if (sonMenus == null || sonMenus.size() == 0) {
							lt.remove();
						} else {
							Iterator<SysLeftMenu> son = sonMenus.iterator();
							while (son.hasNext()) {
								SysLeftMenu m = son.next();
								if (!map.containsKey(m.getResourceId())) {
									son.remove();
								} else {
									if (StringUtil.empty(m.getUrl())) {
										m.setUrl(String.valueOf(map.get(m.getResourceId())));
									}
								}
							}
							if (sonMenus == null || sonMenus.size() == 0) {
								lt.remove();
							} else {
								menu.setSons(sonMenus);
							}
						}
					}
					if (leftMenus == null || leftMenus.size() == 0) {
						it.remove();
					} else {
						sysTopMenu.setSons(leftMenus);
					}
				}
			}
		}
		return topMenus;
	}

	@Override
	public SysLeftMenu findLeftMenu(String pid) {
		return executor.queryByPk(SysLeftMenu.class, pid);
	}

	@Override
	public void saveLeftMenu(SysLeftMenu menu) {
		executor.save(menu);
	}

	@Override
	public void updateLeftMenu(SysLeftMenu menu) {
		executor.update(menu);
	}

	@Override
	@Transaction
	public void deleteTopMenu(String id) {
		executor.delete("delete from sys_left_menu where id=?", id);
		deleteBySql("delete from sys_left_menu where parent_id=?", id);
	}

	@Override
	public SysLeftMenu queryTopMenu(String id) {
		return executor.query(SysLeftMenu.class, SqlBuilder.buildQuerySQL(SysLeftMenu.class) + " where id=? and parent_id='888888888888888888' ", id);
	}

}
