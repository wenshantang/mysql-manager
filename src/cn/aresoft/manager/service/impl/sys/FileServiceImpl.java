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
import cn.aresoft.manager.service.sys.FileService;
@Bean(id = "fileService")
public class FileServiceImpl extends CenterServiceImpl<SysFile> implements FileService{
	@Override
	public PageRecord<SysFile> paging(SysFile file, CommonParam param) {
		String sql = SqlBuilder.buildQuerySQL(SysFile.class);
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if (StringUtil.notEmpty(file.getFile_auth())) {
			sb.append("and file_auth  = ? ");
			condition.add(file.getFile_auth());
		}
		sb.append("and not isnull(pro_ids) ");
		sb.append(" order by upload_time desc ");
		return paging(sb.toString(), condition, param);
	}


}
