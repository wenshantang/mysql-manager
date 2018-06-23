package cn.aresoft.cms.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.puff.web.view.View;

import cn.aresoft.cms.common.model.CmsTopic;

public interface AccessIntercept {

	/*
	 * 判断能否访问栏目，不能访问返回View,可以访问请返回null
	 */
	public View intercept(HttpServletRequest request, HttpServletResponse response, CmsTopic cmsTopic);

}