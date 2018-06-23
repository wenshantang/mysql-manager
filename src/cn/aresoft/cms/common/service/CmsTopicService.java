package cn.aresoft.cms.common.service;

import java.util.List;
import java.util.Map;

import cn.aresoft.cms.common.model.CmsArticle;
import cn.aresoft.cms.common.model.CmsTopic;
import cn.aresoft.cms.common.model.CmsTopicAttr;
import cn.aresoft.common.service.BaseService;

public interface CmsTopicService extends BaseService<CmsTopic> {

	List<CmsTopic> findByParentId(String parent_id);
	
	List<String> queryRoleTopic(String id);

	void updateIdx(String[] arr);

	void deleteTopic(String id);

	List<CmsTopicAttr> queryTopicAttr(String id);

	void saveTopicAttr(CmsTopicAttr topicAttr);

	List<String> queryTopicArticle(String topicCode);

	boolean locationExist(String location);

	CmsTopic queryTopicByCode(String code);

	List<CmsTopic> findArticleColumnByParentId(String parent_id);

	List<CmsTopic> queryArticleColumn();

	CmsTopic queryTopicAllByCode(String code);

	List<CmsArticle> queryTopicArticleList(String topicCode);
	//add 2016-08-24 17:39:11 lxj
	/**
	 * 查询所有非一级文章栏目
	 * @return
	 */
	List<CmsTopic> findArticleColumn();
	
	CmsTopic queryTopicByLocation(String location);
	
	/**
	 * 通过用户id查询用户可以操作的栏目
	 * @param userId
	 * @param flag 查询级别（1:查询一级，2：查询二级）
	 * @return
	 */
	public List<CmsTopic> queryTopicByUserId(String userId,int flag);
	
	
	/**
	 * 查询文章栏目通过用户id
	 * @param userId
	 * @param flag 查询级别（1:查询一级，2：查询二级）
	 * @return
	 */
	public List<CmsTopic> findArticleColumnByUserId(String userId,int flag);
	
	/**
	 * 查询文章栏目通过用户id
	 * @param userId
	 * @param flag 查询级别（1:查询一级，2：查询二级）
	 * @return
	 */
	public List<Map<String,Object>> findArticleByUserId(String userId,int flag);
	
	/**
	 * 构建左侧树
	 * add by luoyg 2016-12-15
	 * @param parent_id
	 * @return
	 */
	List<Map<String,Object>> findArticleByParentId(String parent_id);

	/**
	 * 构建左侧树
	 * add by luoyg 2016-12-15
	 * @param parent_id
	 * @return
	 */
	List<Map<String, Object>> findTemplateByParentId(String parent_id);

	/**
	 * 构建左侧树
	 * add by luoyg 2016-12-15
	 * @param parent_id
	 * @return
	 */
	List<Map<String, Object>> findTemplateByUserId(String parent_id, int type);
}
