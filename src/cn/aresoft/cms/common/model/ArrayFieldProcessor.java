package cn.aresoft.cms.common.model;

import java.lang.reflect.Array;

import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.FieldProcessor;

public class ArrayFieldProcessor implements FieldProcessor {

	@Override
	public Object insert(Object value) {
		if (value != null) {
			int length = Array.getLength(value);
			if (length > 0) {
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < length;) {
					sb.append(Array.get(value, i));
					if (++i < length) {
						sb.append(",");
					}
				}
				return sb.toString();
			}
		}
		return "";
	}

	@Override
	public Object load(Object value) {
		if (value != null && StringUtil.notBlank(value.toString())) {
			String tmp = value.toString();
			return tmp.split(",");
		}
		return null;
	}

}
