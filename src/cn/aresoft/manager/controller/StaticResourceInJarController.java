package cn.aresoft.manager.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.puff.core.Puff;
import com.puff.framework.annotation.BeanScope;
import com.puff.framework.annotation.Controller;
import com.puff.framework.utils.FileUtil;
import com.puff.framework.utils.PathUtil;
import com.puff.framework.utils.StringUtil;
import com.puff.web.mvc.PuffContext;
import com.puff.web.view.ImageView;
import com.puff.web.view.View;
import com.puff.web.view.ViewFactory;

import cn.aresoft.manager.plugin.FlashView;

@Controller(value = "/resource-injar", scope = BeanScope.SINGLETON, report = false)
public class StaticResourceInJarController {
	protected static final Map<String, String> resMap = new HashMap<String, String>();
	private static final String RES_PKGNAME = "resource/static/";
	private static final String IMG_PKGNAME = "resource/static/images/";

	public View script() {
		String res = PuffContext.getParameter("t");
		String content = resMap.get(res);
		if (StringUtil.empty(content)) {
			try {
				content = FileUtil.toString("UTF-8", PathUtil.fromJar((RES_PKGNAME + res).replace("//", "/")));
				content = content.replace("${ctx}", Puff.getContextPath());
			} catch (Exception e) {
				System.err.println(PuffContext.getExecutorKey());
				return null;
			}
			resMap.put(res, content);
		}
		HttpServletResponse response = PuffContext.getResponse();
		response.setHeader("Cache-control", "max-age=315360000");
		return ViewFactory.text(content, "text/javascript;charset=UTF-8");
	}

	public View swf() {
		String res = PuffContext.getParameter("t");
		return new FlashView(PathUtil.fromJar((RES_PKGNAME + res).replace("//", "/")));
	}

	public View css() {
		String res = PuffContext.getParameter("t");
		String content = resMap.get(res);
		if (StringUtil.empty(content)) {
			try {
				content = FileUtil.toString("UTF-8", PathUtil.fromJar((RES_PKGNAME + res).replace("//", "/")));
				content = content.replace("${ctx}", Puff.getContextPath());
			} catch (Exception e) {
				System.err.println(PuffContext.getExecutorKey());
				return null;
			}
			resMap.put(res, content);
		}
		HttpServletResponse response = PuffContext.getResponse();
		response.setHeader("Cache-control", "max-age=315360000");
		return ViewFactory.text(content, "text/css;charset=UTF-8");
	}

	public View image() {
		try {
			String res = PuffContext.getParameter("t");
			HttpServletResponse response = PuffContext.getResponse();
			response.setHeader("Cache-control", "max-age=315360000");
			InputStream imagePath = PathUtil.fromJar((IMG_PKGNAME + res).replace("//", "/"));
			if(imagePath==null){
				imagePath=new ByteArrayInputStream((IMG_PKGNAME + res+" not exist").replace("//", "/").getBytes());
				System.err.println(IMG_PKGNAME + res+" not exist");
			}
			return new ImageView(imagePath);
		} catch (Exception e) {
			System.err.println(PuffContext.getExecutorKey());
			return null;
		}
	}

}
