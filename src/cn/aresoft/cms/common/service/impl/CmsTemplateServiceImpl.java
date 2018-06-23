package cn.aresoft.cms.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Transaction;
import com.puff.jdbc.core.Record;

import cn.aresoft.cms.common.model.CmsTemplate;
import cn.aresoft.cms.common.service.CmsTemplateService;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "cmsTemplateService")
public class CmsTemplateServiceImpl extends CenterServiceImpl<CmsTemplate> implements CmsTemplateService {

	/**
	 * 构建左侧树
	 * add by luoyg 2016-12-19
	 * @param parent_id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findTemplateByParentId(String parent_id) {
		String sql = "select id,parent_id,dir,(case when dir is not null or ifnull(dir,'')!='' then dir else name end) text,id href from cms_template";
		List<Record> list = executor.queryListRecord(sql);
		List<Map<String,Object>> baseList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> nodes = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("text", "根路径");
		map.put("href", "888888888888888888");
		for(Record r:list){
			if(r.getString("parent_id").equals(parent_id)){
				nodes.add(buldChildren(r,list));
			}
		}	
		map.put("nodes", nodes);
		baseList.add(map);
		return baseList;
	}
	private Map<String,Object> buldChildren(Record record, List<Record> list) {		
		Map<String, Object> map = record.getRecord();	
		List<Record> tempList = new ArrayList<Record>();
		for(Record r:list){
			if(r.getString("parent_id").equals(map.get("id").toString())){
				tempList.add(r);
			}
		}
		if(tempList.size()>0){
			for(Record r:tempList){
				if(map.get("nodes")==null){
					List<Map<String, Object>> nodes = new ArrayList<Map<String,Object>>();
					Map<String, Object> m = new HashMap<String,Object>();				
					m = buldChildren(r,list);
					nodes.add(m);
					map.put("nodes", nodes);
				}else{
					List<Map<String, Object>> nodes = (List<Map<String, Object>>) map.get("nodes");
					Map<String, Object> m = new HashMap<String,Object>();
					m = buldChildren(r,list);
					nodes.add(m);
				}
			}
		}
		return map;
	}	
	
	@Override
	public List<CmsTemplate> findByParentId(String parent_id) {
		return queryList(buildQuerySql() + " where parent_id = ? ", parent_id);
	}

	@Override
	public String findPathById(String id) {
		return executor.queryString("select path from cms_template where id=?", id);
	}

	@Override
	public CmsTemplate findByDirAndParentId(String dir, String parent_id) {
		return query(buildQuerySql() + " where dir=? and parent_id = ?", dir, parent_id);
	}

	@Override
	@Transaction
	public void deleteDir(String id) {
		deleteBySql("delete from cms_template where id=?", id);
		deleteBySql("delete from cms_template where parent_id=?", id);
	}

	@Override
	public CmsTemplate findByNameAndParentId(String name, String parent_id) {
		return query(buildQuerySql() + " where name=? and parent_id = ?", name, parent_id);
	}

	@Override
	public List<CmsTemplate> findHtmlTemplate() {
		return queryList("select id, name, path from cms_template where type ='1' ");
	}

	@Override
	public CmsTemplate findByPath(String path) {
		return query(buildQuerySql() + " where path=? ", path);
	}

	@Override
	public long countTemplate() {
		return executor.count(getClazz());
	}

	@Override
	public List<CmsTemplate> queryListPage(int page, int size) {
		return executor.queryListPage(getClazz(), buildQuerySql(), page, size);
	}

}
