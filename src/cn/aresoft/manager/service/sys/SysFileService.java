package cn.aresoft.manager.service.sys;

import java.util.List;

import cn.aresoft.common.service.BaseService;
import cn.aresoft.manager.model.sys.SysFile;

public interface SysFileService extends BaseService<SysFile> {

	List<SysFile> queryFile(String dirName, int page, int rows, String order);

	List<SysFile> queryFileMemu();

}
