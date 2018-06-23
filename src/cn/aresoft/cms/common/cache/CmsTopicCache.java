package cn.aresoft.cms.common.cache;

import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.ListUtil;

import cn.aresoft.cache.AbstractCache;
import cn.aresoft.cms.common.model.CmsTopic;
import cn.aresoft.cms.common.service.CmsTopicService;

@Bean(id = "cmsTopicCache")
public class CmsTopicCache extends AbstractCache {

	@Inject
	private CmsTopicService cmsTopicService;

	@Override
	public String region() {
		return "CMS_TOPIC";
	}

	/**
	 * 初始化 topic cache
	 */
	public void init() {
		List<CmsTopic> list = cmsTopicService.queryList();
		if (ListUtil.notEmpty(list)) {
			for (CmsTopic cmsTopic : list) {
				if ("1".equals(cmsTopic.getIs_show())) {
					addTopic(cmsTopic);
				}
			}
		}
	}

	/**
	 * 新增topic cache
	 * 
	 * @param cmsTopic
	 */
	public void addTopic(CmsTopic cmsTopic) {
		cache(cmsTopic.getLocation(), cmsTopic);
	}

	/**
	 * 删除topic cache
	 * 
	 * @param topic_url
	 */
	public void deleteTopic(String topic_url) {
		remove(topic_url);
	}

	public CmsTopic find(String topic_url) {
		//update by yyj 2016-10-25 start
		//修改获取策略[先从缓存里获取，获取不到再从数据库里查询，然后存放到缓存中]
		CmsTopic cmsTopic=get(topic_url);
		if(cmsTopic==null){
			cmsTopic=cmsTopicService.queryTopicByLocation(topic_url);
			cache(topic_url, cmsTopic);
		}
		return cmsTopic;
		//update by yyj 2016-10-25 end
		//return get(topic_url);
	}

}
