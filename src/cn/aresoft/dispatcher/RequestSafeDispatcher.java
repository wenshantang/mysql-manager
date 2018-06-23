package cn.aresoft.dispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.puff.core.Puff;
import com.puff.framework.utils.StringUtil;
import com.puff.log.Log;
import com.puff.log.LogFactory;
import com.puff.web.mvc.Dispatcher;
import com.puff.web.mvc.ExecutorProvider;

public class RequestSafeDispatcher extends Dispatcher{
    static Log log=LogFactory.get();
   
    private static String[] safeless = ResourceBundle.getBundle("xssfilter").getString("filterSymbols").toLowerCase().split("\\|");
    static List<String> excludeUrlList=new ArrayList<String>();
    static{
    	excludeUrlList.add(Puff.getContextPath()+"/admin/cms/template/insert");
    	excludeUrlList.add(Puff.getContextPath()+"/admin/cms/template/update");
    	excludeUrlList.add(Puff.getContextPath()+"/admin/cms/article/insert");
    	excludeUrlList.add(Puff.getContextPath()+"/admin/cms/article/update");
    }
		
	@Override
	public void dispatching(String target, HttpServletRequest request, HttpServletResponse response, ExecutorProvider provider) {
		boolean isSafe = true;
		String requestUrl = request.getRequestURI();
		if(!isStartWith(requestUrl)){
			if (isSafe(requestUrl)) {
				Enumeration<String> params = request.getParameterNames();
				while (params.hasMoreElements()) {
					String[] values = request.getParameterValues(params.nextElement());// 防止利用同名参数攻击
					if (null != values) {
						for (String value : values) {
							if (StringUtil.notBlank(value)) {
								if (!isSafe(value)) {
									isSafe = false;
									break;
								}
							}
						}
					}
				}
			} else {
				isSafe = false;
			}
			if (!isSafe) {
				if (!ajax(request)) {
					try {
						String contextPath=StringUtil.empty(request.getContextPath(), "/");
						String homePath="admin/console";
						contextPath=contextPath.endsWith("/")==true?(contextPath+homePath):(contextPath+"/"+homePath);
						response.sendRedirect(contextPath);
					} catch (IOException e) {
					}
				} else {
					try {
						PrintWriter writer = response.getWriter();
						response.setCharacterEncoding(Puff.getEncoding());
						response.setContentType("application/json; charset=" + Puff.getEncoding());
						writer.write("{\"msg\":\"非法的参数\"}");
						return;
					} catch (IOException e) {
					}
				}
			}
		}

		chain.dispatching(target, request, response, provider);
	}
	
	private static boolean isSafe(String str) {
		for (String s : safeless) {
			if (str.toLowerCase().contains(s)) {
				log.info("=========request resources (" + str + ") contains unsafe string :" + s);
				return false;
			}
		}
		return true;
	}	

	private boolean ajax(HttpServletRequest request) {
		return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
	}
	
	private boolean isStartWith(String requrl){
		 for(int i=0;i<excludeUrlList.size();i++){
			   if(requrl.indexOf(excludeUrlList.get(i))>=0){
				  return true;
			   }else{
				  continue;   
			   }
					  
		   }
		 return false;
	}
}

