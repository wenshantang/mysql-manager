package cn.aresoft.cms.common.model.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import cn.aresoft.cms.common.SmsSender;

/**
 * 短信配置
 * 
 * @author dongchao
 *
 */
public class SmsConfig implements Config {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7286433121528610537L;

	private static SmsSender smsSender;

	private String realize_class_name;// 发短信实现类
										// 必须实现cn.aresoft.cms.common.SmsSender

	public String getRealize_class_name() {
		return realize_class_name;
	}

	public SmsSender getSmsSender() {
		if (smsSender == null) {
			try {
				Class<?> klass = Class.forName(realize_class_name);
				if (!SmsSender.class.isAssignableFrom(klass)) {
					throw new IllegalArgumentException("The class: '" + realize_class_name + "' must implements " + SmsSender.class.getName());
				}
				smsSender = (SmsSender) klass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return smsSender;
	}

	public static void setSmsSender(SmsSender smsSender) {
		SmsConfig.smsSender = smsSender;
	}

	public void setRealize_class_name(String realize_class_name) {
		this.realize_class_name = realize_class_name;
	}

	@Override
	public String toJson() {
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter("realize_class_name");
		return JSON.toJSONString(this, filter);
	}

}
