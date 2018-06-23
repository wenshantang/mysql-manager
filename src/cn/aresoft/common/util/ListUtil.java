package cn.aresoft.common.util;

import java.util.Collection;
import java.util.Iterator;

public class ListUtil {

	public static final String default_split = ",";

	public static String list2Str(Collection<String> collection) {
		return list2Str(collection, default_split);
	}

	public static String list2Str(Collection<String> collection, String split) {
		StringBuffer sb = new StringBuffer();
		if (collection != null) {
			for (int i = 0, size = collection.size(); i < size; i++) {
				for (Iterator<String> iterator = collection.iterator(); iterator.hasNext();) {
					String str = iterator.next();
					sb.append(str).append(split);
				}
			}
		}
		return sb.toString();
	}

}
