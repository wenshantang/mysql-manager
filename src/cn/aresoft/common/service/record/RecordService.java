package cn.aresoft.common.service.record;

import java.util.List;

import com.puff.jdbc.core.Record;

import cn.aresoft.common.service.BaseService;

/**
 * example(sql): select '' pro_id, '' pro_shortname
 * @return  List<Record>
 * 根据产品名称查询产品列表【产品名称为空，查询所有】
 */
public interface RecordService extends BaseService<Record>{

	/**
	 * example(sql): select '' pro_id, '' pro_shortname
	 * @return  List<Record>
	 */
	List<Record> queryProInfoList(String pro_name);

}
