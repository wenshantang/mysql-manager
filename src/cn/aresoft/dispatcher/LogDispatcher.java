package cn.aresoft.dispatcher;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.puff.core.Puff;
import com.puff.framework.utils.JsonUtil;
import com.puff.framework.utils.StringUtil;
import com.puff.log.Log;
import com.puff.log.LogFactory;
import com.puff.web.mvc.Dispatcher;
import com.puff.web.mvc.Executor;
import com.puff.web.mvc.ExecutorProvider;

import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.utils.LogUtils;

public class LogDispatcher extends Dispatcher{
    Log log=LogFactory.get(getClass());
    
	@Override
	public void dispatching(String target, HttpServletRequest request, HttpServletResponse response, ExecutorProvider provider) {
		String oldTarget=target;
		String paramJson="";
		String logType=Puff.getConstant("logType");//记录日志类型
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user!=null){
			if(StringUtil.blank(logType)){
				insertAdminLog(target, oldTarget, request, response, provider, paramJson, user);
			}else{
				String[] logTypes=logType.split("-");
				for(String logTypeStr:logTypes){
					if(oldTarget.indexOf(logTypeStr)>5){
						insertAdminLog(target, oldTarget, request, response, provider, paramJson, user);
						break;
					}
				}
				
			}
			
		}
		chain.dispatching(oldTarget, request, response, provider);
	}
	
	
	private void insertAdminLog(String target,String oldTarget, HttpServletRequest request, HttpServletResponse response, ExecutorProvider provider
			,String paramJson,SysUser user){
		if(!target.startsWith("/resource-injar")){
			log.info("invoke dispatcher....."+oldTarget);
			Executor executor=null;
			Map<String,String[]> parameterMap=new HashMap<String,String[]>();//获取操作参数
			//获取executor
			try{
				executor=provider.getExecutor(request, target);
			}catch(Exception ex){
				//ex.printStackTrace();
				//log.error(ex);
				try{
					String[] values=target.substring(target.lastIndexOf("/")+1).split(Puff.getUrlParamSeparator());
					parameterMap.put("request-values", values);
					target=target.substring(0, target.lastIndexOf("/"));
					System.out.println("new target is =====>>>"+target);
					executor=provider.getExecutor(request, target);
				}catch(Exception ex2){
					ex2.printStackTrace();
					//log.error(ex2);
				}
			}
			try{
				if(executor!=null){
					/*System.out.println("========LogDispatcher start>>>> ");
					System.out.println("executor.beanId==="+executor.beanId);
					System.out.println("executor.controllerKey==="+executor.controllerKey);
					System.out.println("executor.executorKey==="+executor.executorKey);
					System.out.println("executor.methodIdx==="+executor.methodIdx);
					System.out.println("executor.methodName==="+executor.methodName);
					System.out.println("executor.requestMethod==="+executor.requestMethod);
					System.out.println("========LogDispatcher end >>>>> ");*/
					try{
						if(parameterMap.size()==0){
							parameterMap=request.getParameterMap();
						}
						paramJson=JsonUtil.toJson(parameterMap);
					}catch(Exception ex){
						ex.printStackTrace();
					}
					/*if(parameterMap.size()>0){
						for(String key:parameterMap.keySet()){
							String[] values=parameterMap.get(key);
							//System.out.println(key+"=======value:"+Arrays.toString(values));
						}
						
					}*/
					
				
				}
			}catch(Exception ex){
				ex.printStackTrace();
				log.error(ex);
			}

		}else{
			//log.info("invoke others.....");
		}
		
		LogUtils.insertSsysLog(user.getId(), "", oldTarget, "", paramJson,"",request);
	}

}

