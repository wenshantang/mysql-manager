package cn.aresoft.manager.cache;

import cn.aresoft.cache.AbstractCache;

import com.puff.framework.annotation.Bean;
/**
 * 用户可以访问的url权限
 * @author yyj
 *
 */
@Bean(id = "userUrlPermitCache")
public class UserUrlPermitCache extends AbstractCache {

	@Override
	public String region() {
		return "userUrlPermit";
	}
	
}
