package cn.aresoft.cms.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Transaction;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.Record;
import com.puff.jdbc.core.SqlBuilder;

import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.model.CmsTopic;
import cn.aresoft.cms.common.model.CmsTopicAttr;
import cn.aresoft.cms.common.service.CmsTopicService;
import cn.aresoft.common.service.impl.CenterServiceImpl;

@Bean(id = "cmsTopicService")
public class CmsTopicServiceImpl extends CenterServiceImpl<CmsTopic> implements CmsTopicService {

	@Override
	public List<CmsTopic> findByParentId(String parent_id) {
		List<CmsTopic> list = queryList(buildQuerySql() + " where parent_id=? order by idx", parent_id);
		if (list != null) {
			String sql = "select count(*) from cms_topic where parent_id=? ";
			for (CmsTopic cmsTopic : list) {
				cmsTopic.setHas_child(executor.queryLong(sql, cmsTopic.getId()) > 0);
			}
		}
		return list;
	}

	@Override
	public List<String> queryRoleTopic(String id){
		String sql = "select topic_id from sys_role_topic where role_id=? ";
		return executor.querySimpleList(sql, new Object[] { id });
	}
	
	@Override
	@Transaction
	public void updateIdx(String[] arr) {
		for (String string : arr) {
			String[] tmpArr = string.split("\\-");
			executor.updateBySql("update cms_topic set idx=? where id=?", tmpArr[1], tmpArr[0]);
		}
	}

	@Override
	@Transaction
	public void deleteTopic(String id) {
		deleteBySql("delete from cms_topic where id=? ", id);
		deleteBySql("delete from cms_topic where parent_id=? ", id);
	}

	@Override
	public List<CmsTopicAttr> queryTopicAttr(String id) {
		return executor.queryList(CmsTopicAttr.class, SqlBuilder.buildQuerySQL(CmsTopicAttr.class) + " where topic_id=?", id);
	}

	@Override
	public void saveTopicAttr(CmsTopicAttr topicAttr) {
		executor.save(topicAttr);
	}

	@Override
	public List<String> queryTopicArticle(String topicCode) {
		String sql ="select a.id from"
				+ " (select case  when i_top > 0 then 1 else 0 end as r, t.*from cms_article t) a, "
				+" cms_topic t "
				+" where a.topic_id = t.id "
				+" and t.code = ? "
				+" and a.status = ? "
				+" order by a.r desc, a.publish_time  desc, a.i_top desc ";
		return executor.querySimpleListString(sql, topicCode, 1);
	}

	@Override
	public boolean locationExist(String location) {
		String sql = "select id from cms_topic where location=?";
		return StringUtil.notEmpty(executor.queryString(sql, location));
	}
	
	@Override
	public CmsTopic queryTopicByCode(String code) {
		String sql = "select * from cms_topic where code=?";
		return executor.query(sql, code);
	}
	
	@Override
	public List<CmsTopic> findArticleColumnByParentId(String parent_id) {
		List<CmsTopic> list = queryList(buildQuerySql() + " where article_column='1' and parent_id=? order by idx", parent_id);
		if (list != null) {
			String sql = "select count(*) from cms_topic where article_column='1' and parent_id=? ";
			for (CmsTopic cmsTopic : list) {
				cmsTopic.setHas_child(executor.queryLong(sql, cmsTopic.getId()) > 0);
			}
		}
		return list;
	}
	
	@Override
	public List<CmsTopic> queryArticleColumn() {
		List<CmsTopic> list = queryList(buildQuerySql() + " where article_column='1' order by id,idx");
		return list;
	}

	//add 2016-7-8 12:28:50 start
	@Override
	public CmsTopic queryTopicAllByCode(String code) {
		String sql = "select * from cms_topic where code=?";
		return executor.query(CmsTopic.class,sql, code);
	}
	//end	
	
	@Override
	public List<CmsArticle> queryTopicArticleList(String topicCode) {
		String sql ="select a.* from"
				+ " (select case  when i_top > 0 then 1 else 0 end as r, t.* from cms_article t) a, "
				+" cms_topic t "
				+" where a.topic_id = t.id "
				+" and t.code = ? "
				+" and a.status = ? "
				+" order by a.r desc, a.publish_time  desc, a.i_top desc ";
		return executor.queryList(CmsArticle.class,sql, topicCode, 1);
	}
	
	//add 2016-08-24 17:39:11 lxj	
	@Override
	public List<CmsTopic> findArticleColumn() {
//		String sql="select t.* from  cms_topic t where t.ARTICLE_COLUMN='1' and SAAS_ID=? and t.PARENT_ID!='888888888888888888'";
	    	String sql="select t.* from  cms_topic t where t.ARTICLE_COLUMN='1'  ";
		return executor.queryList(CmsTopic.class, sql);
	}

	@Override
	public CmsTopic queryTopicByLocation(String location) {
		String sql = "select * from cms_topic where location=?";
		return executor.query(CmsTopic.class,sql, location);
	}

	@Override
	public List<CmsTopic> queryTopicByUserId(String userId,int flag) {
		String sql= "select ct.* from CMS_TOPIC ct,"
				+ " (select DISTINCT TOPIC_ID from SYS_ROLE_TOPIC rt,SYS_USER_ROLE ur "
				+ " where RT.ROLE_ID=UR.ROLE_ID "
				+ " and UR.USER_ID=? ) ut "
				+ " where (CT.id=ut.TOPIC_ID ";
		if(flag==2){
			sql+=" or ct.parent_id=ut.TOPIC_ID )";
		}else{
			sql+=" ) order by idx ";
		}
		List<CmsTopic> list =queryList(sql, userId);
		if (list != null) {
			String sql2 = "select count(*) from cms_topic where parent_id=? ";
			for (CmsTopic cmsTopic : list) {
				cmsTopic.setHas_child(executor.queryLong(sql2, cmsTopic.getId()) > 0);
			}
		}
		return list;
	}

	@Override
	public List<CmsTopic> findArticleColumnByUserId(String userId,int flag) {
		String sql= "select ct.* from CMS_TOPIC ct,"
				+ " (select DISTINCT TOPIC_ID from SYS_ROLE_TOPIC rt,SYS_USER_ROLE ur "
				+ " where RT.ROLE_ID=UR.ROLE_ID "
				+ " and UR.USER_ID=? ) ut "
				+ " where article_column='1' and (CT.id=ut.TOPIC_ID ";
		if(flag==2){
			sql+=" or ct.parent_id=ut.TOPIC_ID )";
		}else{
			sql+=" ) order by idx ";
		}
		List<CmsTopic> list =queryList(sql, userId);
		if (list != null) {
			String sql2 = "select count(*) from cms_topic where article_column='1' and parent_id=? ";
			for (CmsTopic cmsTopic : list) {
				cmsTopic.setHas_child(executor.queryLong(sql2, cmsTopic.getId()) > 0);
			}
		}
		return list;
	}
	
	@Override
	public List<Map<String,Object>> findArticleByUserId(String userId,int flag) {
		String sql= "select ct.id,ct.parent_id,ct.name text,ct.id href,ifnull(ct.article_column,0) article_column from CMS_TOPIC ct,"
				+ " (select DISTINCT TOPIC_ID from SYS_ROLE_TOPIC rt,SYS_USER_ROLE ur "
				+ " where RT.ROLE_ID=UR.ROLE_ID "
				+ " and UR.USER_ID=? ) ut "
				+ " where article_column='1' and (CT.id=ut.TOPIC_ID ";
		if(flag==2){
			sql+=" or ct.parent_id=ut.TOPIC_ID )";
		}else{
			sql+=" ) order by idx ";
		}
		List<Record> list = executor.queryListRecord(sql,userId);
		String sql2 = "select id,parent_id,name text,id href,ifnull(article_column,0) article_column from cms_topic "
				+ " where id in(select ct.parent_id from CMS_TOPIC ct,"
				+ " (select DISTINCT TOPIC_ID from SYS_ROLE_TOPIC rt,SYS_USER_ROLE ur "
				+ " where RT.ROLE_ID=UR.ROLE_ID "
				+ " and UR.USER_ID=? ) ut "
				+ " where article_column='1' and (CT.id=ut.TOPIC_ID ";
		if(flag==2){
			sql2+=" or ct.parent_id=ut.TOPIC_ID )";
		}else{
			sql2+=" ))";
		}
		List<Record> list2 = executor.queryListRecord(sql2,userId);
		List<Map<String,Object>> baseList = new ArrayList<Map<String,Object>>();
		for(Record r:list2){
			baseList.add(buldChildren(r,list));
		}
		return baseList;
	}
	
	/**
	 * 构建左侧树
	 * add by luoyg 2016-12-15
	 * @param parent_id
	 * @return
	 */
	@Override
	public List<Map<String,Object>> findArticleByParentId(String parent_id) {
		String sql = "select id,parent_id,name text,id href,ifnull(article_column,0) article_column from cms_topic order by idx";
		List<Record> list = executor.queryListRecord(sql);
		List<Map<String,Object>> baseList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> nodes = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("text", "网站栏目");
		map.put("href", "888888888888888888");	
		for(Record r:list){
			if(r.getString("parent_id").equals(parent_id) && r.getString("article_column").equals("1")){
				nodes.add(buldChildren(r,list));
			}
		}	
		map.put("nodes", nodes);
		baseList.add(map);
		return baseList;
	}

	//文章列表栏目使用
	private Map<String,Object> buldChildren(Record record, List<Record> list) {		
		Map<String, Object> map = record.getRecord();	
		List<Record> tempList = new ArrayList<Record>();
		for(Record r:list){
			if(r.getString("parent_id").equals(map.get("id").toString())&&"1".equals(r.getString("article_column"))){
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
	
	//栏目列表栏目使用
	private Map<String,Object> buldChildrenTopic(Record record, List<Record> list) {		
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
	/**
	 * 构建左侧树
	 * add by luoyg 2016-12-19
	 * @param parent_id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findTemplateByParentId(String parent_id) {
		String sql = "select id,parent_id,name text,id href from cms_topic order by idx";
		List<Record> list = executor.queryListRecord(sql);
		List<Map<String,Object>> baseList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> nodes = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("text", "网站栏目");
		map.put("href", "888888888888888888");	
		for(Record r:list){
			if(r.getString("parent_id").equals(parent_id)){
				nodes.add(buldChildrenTopic(r,list));
			}
		}	
		map.put("nodes", nodes);
		baseList.add(map);
		return baseList;
	}
	/**
	 * 构建左侧树
	 * add by luoyg 2016-12-19
	 * @param parent_id
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findTemplateByUserId(String userId, int flag) {
		String sql= "select ct.id,ct.parent_id,ct.name text,ct.id href,ifnull(ct.article_column,0) article_column from CMS_TOPIC ct,"
				+ " (select DISTINCT TOPIC_ID from SYS_ROLE_TOPIC rt,SYS_USER_ROLE ur "
				+ " where RT.ROLE_ID=UR.ROLE_ID "
				+ " and UR.USER_ID=? ) ut "
				+ " where (CT.id=ut.TOPIC_ID ";
		if(flag==2){
			sql+=" or ct.parent_id=ut.TOPIC_ID )";
		}else{
			sql+=" ) order by idx ";
		}
		List<Record> list = executor.queryListRecord(sql,userId);
		String sql2 = "select id,parent_id,name text,id href,ifnull(article_column,0) article_column from cms_topic "
				+ " where id in(select ct.parent_id from CMS_TOPIC ct,"
				+ " (select DISTINCT TOPIC_ID from SYS_ROLE_TOPIC rt,SYS_USER_ROLE ur "
				+ " where RT.ROLE_ID=UR.ROLE_ID "
				+ " and UR.USER_ID=? ) ut "
				+ " where (CT.id=ut.TOPIC_ID ";
		if(flag==2){
			sql2+=" or ct.parent_id=ut.TOPIC_ID )";
		}else{
			sql2+=" ))";
		}
		List<Record> list2 = executor.queryListRecord(sql2,userId);
		List<Map<String,Object>> baseList = new ArrayList<Map<String,Object>>();
		for(Record r:list2){
			baseList.add(buldChildren(r,list));
		}
		return baseList;	
	}	
	
}
