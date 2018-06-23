package cn.aresoft.cms.common.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.puff.framework.utils.JsonUtil;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.FieldProcessor;

public class JsonMapProcessor implements FieldProcessor {

	@Override
	public Object insert(Object value) {
		if (value != null) {
			return JsonUtil.toJson(value);
		}
		return "";
	}

	@Override
	public Object load(Object value) {
		if (value != null && StringUtil.notBlank(value.toString())) {
			String tmp = value.toString();
			JSONObject jo = JSON.parseObject(tmp);
			return jo;
		}
		return null;
	}

}
