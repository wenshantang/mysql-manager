package cn.aresoft.manager.service.impl.sys;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysFile;
import cn.aresoft.manager.service.sys.SysFileService;

@Bean(id = "sysFileService")
public class SysFileServiceImpl extends CenterServiceImpl<SysFile> implements SysFileService {

	@Override
	public PageRecord<SysFile> paging(SysFile file, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(SysFile.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(file.getName())) {
			sb.append("and name like ? ");
			condition.add("%" + file.getName() + "%");
		}
		return paging(sb.toString(), condition, param);
	}

	@Override
	public List<SysFile> queryFile(String dirName, int page, int rows, String order) {
		String sql = SqlBuilder.buildQuerySQL(SysFile.class);
		StringBuilder sb = new StringBuilder();
		List<Object> condition = new ArrayList<Object>();
		sb.append(sql).append(" where type=? ");
		condition.add(dirName);
		CommonParam param = new CommonParam();
		param.setOrder("desc");
		param.setPage(page);
		param.setRows(rows);
		param.setSort(order);
		PageRecord<SysFile> record = paging(sb.toString(), condition, param);
		return (List<SysFile>) record.getDataList();
	}
	
	
	
	@Override
	public List<SysFile> queryFileMemu() {
		String sql = "select DISTINCT file_menu from sys_file where file_menu is not null ";
		return queryList(sql);
	}

}