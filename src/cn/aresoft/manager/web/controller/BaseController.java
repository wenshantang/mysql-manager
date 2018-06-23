package cn.aresoft.manager.web.controller;

import com.puff.log.Log;
import com.puff.log.LogFactory;

import cn.aresoft.manager.controller.CommonController;

public class BaseController extends CommonController {

	public Log log= LogFactory.get(getClass());
}
