/*
 * File ： HttpPoster.java
 * 
 * Project: wcmn
 *
 * Modify Information:
 * =============================================================================
 *   Author          Date                      Description
 *   ------------ ---------- ---------------------------------------------------
 *   IBM          2008-10-8          Implement Programming Spec V1.0
 * ============================================================================= 
 */
package cn.aresoft.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.puff.log.Log;
import com.puff.log.LogFactory;




public class HttpPoster {
	private Log log=LogFactory.get(getClass());
	private String responseString = "";
	  public HttpPoster() {
	  }
    /**
     * 使用http协议发送xmltext到url
     * @param url   将要发送的地址
     * @param xmltext  将要发送的内容
     * @return   http返回
     */
	  public  String post(String url,Map parameters) throws IOException{
	    PostMethod xmlpost;
	    int responseStatCode = 0;
	    HttpClient httpclient = new HttpClient();
	    httpclient.setConnectionTimeout(1000*30);
	    xmlpost = new PostMethod(url);
	    httpclient.getParams().setParameter(
	    		HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

	    try {
	    	NameValuePair[] nameValuePairs = new NameValuePair[parameters.size()];
	    	Iterator keyIterator = parameters.keySet().iterator();
	    	int i = 0;
			while (keyIterator.hasNext())
			{
				Object key = keyIterator.next();
				String value = (String)parameters.get(key);
				NameValuePair name = new NameValuePair((String)key, value);
				nameValuePairs[i]=name;
				i++;
			}
	    	            
	         xmlpost.setRequestBody(nameValuePairs); 
	         
	    	responseStatCode = httpclient.executeMethod(xmlpost);
	    	this.responseString = xmlpost.getResponseBodyAsString();
	    }
	    catch (IOException ex2) {
	    	log.error("报文发送到["+url+"]失败:"+ex2.getMessage());
	    	throw ex2;
	    }
	    log.info("HTTP返回码="+responseStatCode);
	    log.info("应答数据="+responseString);
	    return responseString;
	    
	  }
	 
	public String getResponseString() {
		return responseString;
	} 
	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("merid","0002900F0345178");
		map.put("reqtype","payforreq");
		map.put("xml","<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><payforreq><ver>1.00</ver><merdt>20160222</merdt><orderno>201602220000003</orderno><bankno>0102</bankno><cityno>2900</cityno><branchnm>中国农业银行</branchnm><accntno>6228480489270088078</accntno><accntnm>恒信信金富</accntnm><amt>100000</amt></payforreq>");
		map.put("mac","13cc07398b51660efcdc7e514ca0747f");
		               
		HttpPoster poster = new HttpPoster();
		try {
			System.out.println(poster.post("http://www-1.fuiou.com:8992/fuMer/req.do",map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
