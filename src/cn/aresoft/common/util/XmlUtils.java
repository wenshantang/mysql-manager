package cn.aresoft.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * XML MAP工具类
 * @author PZG
 */
public class XmlUtils {
	
	public static Map<String,String> mapByXml(String xmlStr) throws DocumentException{
		Document doc = DocumentHelper.parseText(xmlStr);
		return getXmlByMap(doc);
	}
	public static Map<String,Object> mapByXmlnext(String xmlStr) throws DocumentException{
		Document doc = DocumentHelper.parseText(xmlStr);
		return xmlToMap(doc);
	}
	
	public static Map<String,String> getXmlByMap(Document doc){
		Map<String, String> map = new HashMap<String, String>();
		if(doc == null)
            return map; 
        Element root = doc.getRootElement(); 
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) { 
            Element e = (Element) iterator.next();
            map.put(e.getName(), e.getText());
        }
        return map;
	}
	/**
	 * XML转MAP
	 * @param doc
	 * @return
	 */
	public static Map<String, Object> xmlToMap(Document doc){ 
        Map<String, Object> map = new HashMap<String, Object>(); 
        if(doc == null)
            return map; 
        Element root = doc.getRootElement(); 
        for (Iterator iterator = root.elementIterator(); iterator.hasNext();) { 
            Element e = (Element) iterator.next(); 
            List list = e.elements(); 
            if(list.size() > 0){
                map.put(e.getName(), Dom2Map(e)); 
            }else 
                map.put(e.getName(), e.getText()); 
        } 
        return map; 
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map Dom2Map(Element e){ 
        Map map = new HashMap(); 
        List list = e.elements(); 
        if(list.size() > 0){ 
            for (int i = 0;i < list.size(); i++) { 
                Element iter = (Element) list.get(i); 
                List mapList = new ArrayList(); 
                if(iter.elements().size() > 0){ 
                    Map m = Dom2Map(iter); 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(m); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(m); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), m); 
                }else{ 
                    if(map.get(iter.getName()) != null){ 
                        Object obj = map.get(iter.getName()); 
                        if(!obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = new ArrayList(); 
                            mapList.add(obj); 
                            mapList.add(iter.getText()); 
                        } 
                        if(obj.getClass().getName().equals("java.util.ArrayList")){ 
                            mapList = (List) obj; 
                            mapList.add(iter.getText()); 
                        } 
                        map.put(iter.getName(), mapList); 
                    }else 
                        map.put(iter.getName(), iter.getText()); 
                } 
            } 
        }else{
            map.put(e.getName(), e.getText());
        }
        return map; 
    }
	/**
	 * Map转XML
	 * @param map
	 * @return
	 */
	public static String getXmlStr(Map<String,String> map){
		if(null != map && map.size() > 0){
			StringBuffer sb = new StringBuffer();
			Set<String> set = map.keySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				String s = (String) it.next();
				sb.append("<").append(s).append(">")
				.append(map.get(s))
				.append("</").append(s).append(">");
			}
			return sb.toString();
		}else{
			return null;
		}
	}
	
	/**
     * map转xml
     * @param map
     * @return
     */
    public static String maptoXml(Map<String, String> map,String prikey) {
    	StringBuffer xmlstr = new StringBuffer();
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key);
            xmlstr.append("<").append(key).append(">")
            .append(value).append("</").append(key).append(">");
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
      /*  String sign ="xx";
        xmlstr.append("<sign>").append(sign).append("</sign>");*/
        String nodestr = addPayforreq(xmlstr.toString());
        return nodestr;
        
    }
    
    /**
     * map转xml
     * @param map
     * @return
     */
    public static String maptoXml1(Map<String, String> map,String prikey) {
    	StringBuffer xmlstr = new StringBuffer();
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key)+"";
            xmlstr.append("<").append(key).append(">")
            .append(value).append("</").append(key).append(">");
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
      /*  String sign ="xx";
        xmlstr.append("<sign>").append(sign).append("</sign>");*/
        String nodestr = addQrytransreq(xmlstr.toString());
        return nodestr;
        
    }
    
	/**
	 * 添加根节点
	 * @param xmlStr
	 * @return
	 */
	public static String addRootNode(String xmlStr){
		StringBuffer sub = new StringBuffer();
		sub.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		sub.append("<Message>").append(xmlStr).append("</Message>");
		return sub.toString();
	}
	
	/**
	 * 添加根节点
	 * @param xmlStr
	 * @return
	 */
	public static String addPayforreq(String xmlStr){
		StringBuffer sub = new StringBuffer();
		sub.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		sub.append("<payforreq>").append(xmlStr).append("</payforreq>");
		return sub.toString();
	}
	
	/**
	 * 添加根节点
	 * @param xmlStr
	 * @return
	 */
	public static String addQrytransreq(String xmlStr){
		StringBuffer sub = new StringBuffer();
		sub.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		sub.append("<qrytransreq>").append(xmlStr).append("</qrytransreq>");
		return sub.toString();
	}
	
	/**
	 * 富友手机下单拼接xml
	 * @param xmlStr
	 */
	private static String fyMobileAddHeadNode(String xmlStr){
		StringBuffer sub = new StringBuffer();
		sub.append("");
		sub.append("<FM>").append(xmlStr).append("</FM>");
		return sub.toString();
	}
	
	/**
     * map转xml[富友手机下单]
     * @param map
     * @return
     */
    public static String fyMobileMaptoXml(Map<String, Object> map) {
    	StringBuffer xmlstr = new StringBuffer();
        List<String> keys = new ArrayList<String>(map.keySet());
        //Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key)+"";
            xmlstr.append("<").append(key).append(">")
            .append(value).append("</").append(key).append(">");
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
      /*  String sign ="xx";
        xmlstr.append("<sign>").append(sign).append("</sign>");*/
        String nodestr = fyMobileAddHeadNode(xmlstr.toString());
        return nodestr;
        
    }
    
	public static void main(String[] args) throws IOException, DocumentException {
		Map<String,Object> resultMap=mapByXmlnext("<?xml version='1.0' encoding='UTF-8'?><ap><plain><order_pay_code>0000</order_pay_code><order_pay_error>成功</order_pay_error><order_id>20160330150255M1000071</order_id><order_st>11</order_st><fy_ssn>000021914972</fy_ssn><resv1></resv1></plain><md5>9335048eb79163e11b80219ec417a9ee</md5></ap>");
		System.out.println(resultMap.get("plain"));
	}
}
