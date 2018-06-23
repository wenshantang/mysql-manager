package cn.aresoft.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class InterfaceApiUtil {
    private static ResourceBundle bundle = ResourceBundle.getBundle("hs");
    private static String cookie = "";
    
    private static String getPropertyValue(String string) {
        return bundle.getString(string);
    }
    
    public static String post(String path, Map<String, String> map) {
        BufferedReader br = null;
        OutputStream os = null;
        HttpURLConnection conn = null;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Cookie", cookie);
            StringBuffer buffer = new StringBuffer();
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<String, String> entry : map.entrySet())
                    buffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                buffer.deleteCharAt(buffer.length() - 1);
            }
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            conn.getOutputStream().write(buffer.toString().getBytes("UTF-8"));

            if (conn.getResponseCode() == 200) {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String lines;
                while ((lines = br.readLine()) != null)
                    sb.append(lines);
                Map<String, List<String>> head = conn.getHeaderFields();
                Set<String> set = head.keySet();
                for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
                    String key = iterator.next();
                    if (key != null && "Set-Cookie".equals(key)) {
                        List<String> list = head.get(key);
                        StringBuilder builder = new StringBuilder();
                        for (String str : list)
                            builder.append(str).toString();
                        cookie = builder.toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.disconnect();
                if (br != null)
                    br.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return sb.toString();
    }
    
    /** 
    * @Title: loginsessionkey 
    * @Description: 获取登录sessionkey
    * @param lognumber 证件号码
    * @param certificatetype 证件类型
    */
    public static String loginsessionkey(String lognumber, String certificatetype) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("lognumber", lognumber);
        map.put("certificatetype", certificatetype);
        return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("sessionkey")),map);
    }
    
    
    /** 
    * @Title: getPhoneAuthCode 
    * @Description: 获取手机验证码
    * @param mobile 手机号码
    */
    public static String getPhoneAuthCode(String mobile) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile", mobile);
        return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("authcode")),map);
    }

    /** 
    * @Title: updateTradePwd 
    * @Description: 修改交易密码
    * @param oldpwd 旧密码
    * @param newpwd 新密码
    * @param sessionkey
    */
    public static String updateTradePwd(String oldpwd,String newpwd,String sessionkey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("oldpwd", oldpwd);
        map.put("newpwd", newpwd);
        map.put("sessionkey",sessionkey);
        return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("updatejypwd")),map);
    }

    /** 
    * @Title: getUserinfo 
    * @Description: 查询用户信息
    * @param sessionkey
    */
    public static String getUserinfo(String sessionkey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sessionkey",sessionkey);
        return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("userinfo")),map);
    }

    /** 
    * @Title: getUserBank 
    * @Description: 查询用户银行卡
    * @param sessionkey
    */
    public static String getUserBank(String sessionkey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("sessionkey",sessionkey);
        return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("querytradeacco")),map);
    }

    /** 
    * @Title: getUserBank 
    * @Description: 获取基金份额值，用于总资产和昨日收益以及累计收益的查询
    * @param sessionkey
    * @return    
    * @return String
    * @throws 
    */
    public static String getUserShare(String sessionkey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("querytype", "1");
        map.put("sessionkey",sessionkey);
        return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("share")),map);
    }
    
    /** 
    * @Title: getUserBank 
    * @Description: 获取基金份额值，用于总资产和昨日收益以及累计收益的查询
    * @param sessionkey
    * @return    
    * @return String
    * @throws 
    */
    public static String getTradeList(String tradetype,String status,String fundcode,String pageSize,String pageNum,String sessionkey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageno", pageNum);
        map.put("fundcode", fundcode);
        map.put("confirmflag", status);
        map.put("callingcode", tradetype);
        map.put("pageno", pageNum);
        map.put("applyrecordno", pageSize);
        map.put("sessionkey",sessionkey);
        return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("tradelist")),map);
    }
    
    /** 
    * @Title: getDict 
    * @Description: 
    * @param dicttype 字典项名
    * @param dictkey 字典key
    */
    public static String getDict(String dicttype,String dictkey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("dictname", dicttype);
        map.put("key",dictkey);
        return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("dict")),map);
    }
    /** 
    * @Title: getUserBank 
    * @Description: 获取基金份额值，用于总资产和昨日收益以及累计收益的查询
    * @param sessionkey
    * @return    
    * @return String
    * @throws 
    */
    public static String getConfirmList(String tradetype,String fundcode,String pageSize,String pageNum,String sessionkey,String requestno) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageno", pageNum);
        map.put("fundcode", fundcode);
        map.put("callingcode", tradetype);
        map.put("pageno", pageNum);
        map.put("applyrecordno", pageSize);
        map.put("sessionkey",sessionkey);
        map.put("requestno",requestno);
        return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("confirmquery")),map);
    }

    /** 
    * @Title: getBonusquery 
    * @Description: 历史分红查询
    * @throws 
    */
    public static String getBonusquery(String melonmethod,String fundcode,String pageSize,String pageNum,String sessionkey) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("pageno", pageNum);
        map.put("fundcode", fundcode);
        map.put("melonmethod", melonmethod);
        map.put("pageno", pageNum);
        map.put("applyrecordno", pageSize);
        map.put("sessionkey",sessionkey);
        return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("bonusquery")),map);
    }
    /**
	 * @author lcl
	 * 当前工作日查询
	 */
	public static String getWorkdate(){
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("workdatequery")),null);
	}
	/**
	 * @author lcl
	 * 下一工作日查询
	 */
	public static String getNextWorkdate(){
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("nextworkdatequery")),null);
	}
	
	/**
	 * @author lcl
	 * 定投，可签解约银行卡列表(I001)
	 */
	public static String getSignquery(String sessionkey,String signtype) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey",sessionkey);
		map.put("signtype",signtype);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("signquery")),map);
	}
	
	/**
	 * @author lcl
	 * 定投首次交易日期查询(I020)
	 */
	public static String getFirstvaludayquery(String sessionkey,String exptype,String cycleunit,String fundcode,String jyrq,String tradeacco){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey", sessionkey);
		map.put("cycleunit", cycleunit);
		map.put("exptype", exptype);
		map.put("fundcode", fundcode);
		map.put("jyrq", jyrq);
		map.put("tradeacco", tradeacco);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("firstvaludayquery")), map);
	}
	
	/**
	 * @author lcl
	 * 可定投支付方式列表(I016)
	 */
	public static String getValubankquery(String sessionkey, String fundcode){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey", sessionkey);
		map.put("fundcode", fundcode);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("valubankquery")),map);
	}
	
	
	/**
	 * @author lcl
	 * 定投新增(I006)
	 */
	public static String getValutrade(String sessionkey,String cycleunit,String applysum,String fundcode,String jyrq,String scjyrq,
			String sharetype,String tradeacco,String tradepassword,String zzrq,String bankacco,String jyzq) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey", sessionkey);
		map.put("cycleunit", cycleunit);
		map.put("applysum", applysum);
		map.put("fundcode", fundcode);
		map.put("jyrq", jyrq);
		map.put("scjyrq", scjyrq);
		map.put("sharetype", sharetype);
		map.put("tradeacco", tradeacco);
		map.put("tradepassword", tradepassword);
		map.put("zzrq", zzrq);
		map.put("bankacco", bankacco);
		map.put("jyzq", jyzq);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("trade")),map);
	}
	
	
	/** 
	* @Title: getBuyfund 
	* @Description: 购买基金
	* @param sessiongkey
	* @param sharetype
	* @param fundcode
	* @param businflag
	* @param applysum
	* @param tradeacco
	* @param tradepassword
	* @return    
	* @return String
	* @throws 
	*/
	public static String getBuyfund(String sessiongkey,String sharetype,String fundcode,String businflag,String applysum,String tradeacco,String tradepassword){
		Map<String,String> map = new HashMap<String,String>();
		map.put("sessionkey",sessiongkey);
		map.put("sharetype", sharetype);
		map.put("fundcode", fundcode);
		map.put("businflag", businflag);
		map.put("applysum", applysum);
		map.put("tradeacco", tradeacco);
		map.put("tradepassword", tradepassword);
		map.put("detailcapitalmode", "01");
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("purchase")),map);
	}
	
	/**
	 * @author lcl
	 * 基金卖出或转换(T006)
	 */
	public static String getSale(String sessionkey,String sharetype,String applysum,String tradeacco,String redeemuseflag,String saleway,
			String fundcode,String tradepassword,String targetfundcode,String targetsharetype,String mintredeem){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey", sessionkey);
		map.put("sharetype", sharetype);
		map.put("applysum", applysum);
		map.put("tradeacco", tradeacco);
		map.put("redeemuseflag", redeemuseflag);
		map.put("saleway", saleway);
		map.put("fundcode", fundcode);
		map.put("tradepassword", tradepassword);
		map.put("targetfundcode", targetfundcode);
		map.put("targetsharetype", targetsharetype);
		map.put("mintredeem", mintredeem);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("sale")), map);
	}

	/**
	 * @author lcl
	 * 交易费率查询(S021)
	 */
	public static String getTradefarequery(String sessionkey,String businflag,String capitalmode,String fundcode,String tradeacco,String otherfundcode,
			String othersharetype,String requestbala,String requestshare,String sharetype){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey", sessionkey);
		map.put("businflag", businflag);
		map.put("capitalmode", capitalmode);
		map.put("fundcode", fundcode);
		map.put("tradeacco", tradeacco);
		map.put("otherfundcode", otherfundcode);
		map.put("othersharetype", othersharetype);
		map.put("requestbala", requestbala);
		map.put("requestshare", requestshare);
		map.put("sharetype", sharetype);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("tradefarequery")), map);
	}
	
	/**
	 * 可定投申购的基金列表(I012) 
	 */
	public static String getTradefundquery(String sessionkey){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey", sessionkey);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("tradefundquery")), map);
	}

	/**
	 * 可修改分红方式列表(T010)
	 * @author lcl
	 */
	public static String getBonuslist(String sessionkey){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey", sessionkey);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("bonuslist")), map);
	}
	
	/**
	 * 分红方式修改(T007)
	 * @author lcl
	 */
	public static String getBonus(String sessionkey,String fundcode,String tradeacco,String melonmethod,String sharetype,String tradepassword){
		Map<String,String> map = new HashMap<String,String>();
		map.put("fundcode", fundcode);
		map.put("melonmethod", melonmethod);
		map.put("sharetype", sharetype);
		map.put("tradeacco", tradeacco);
		map.put("tradepassword", tradepassword);
		map.put("sessionkey", sessionkey);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("bonus")), map);
	}
	
	/**
	 * 撤单(T009)
	 * @author lcl
	 * 2015年10月29日
	 */
	public static String getWithdraw(String sessionkey, String tradeacco, String applyserial, String tradepassword){
		Map<String,String> map = new HashMap<String,String>();
		map.put("sessionkey", sessionkey);
		map.put("tradeacco", tradeacco);
		map.put("applyserial", applyserial);
		map.put("tradepassword", tradepassword);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("withdraw")), map);
	}
	
	/**
	* @Title: getOpenTradeAcco 
	* @Description: 增开交易账号(新增银行卡)
	 * @param sessionkey
	 * @return
	 */
	public static String getOpenTradeAcco(String sessionkey,String customerappellation,String identityno,String identityType,String bankacco,String capitalmode,String bankserial ,String bankname,String yinliancdcard){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey", sessionkey);
		map.put("customername", customerappellation);
		map.put("identityno", identityno);
		map.put("identityType", identityType);
		map.put("bankacco", bankacco);
		map.put("capitalmode", capitalmode);
		map.put("bankserial", bankserial);
		map.put("bankname", bankname);
		map.put("yinliancdcard", yinliancdcard);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("opentradeacco")),map);
	}
	/**
	* @Title: ChangeCard
	* @Description:更换银行卡
	 * @param sessionkey
	 * @return
	 */
	public static String changeCard(String sessionkey,String customerappellation,String identityno,String identityType,String bankacco,String capitalmode,String bankserial ,String bankname,String yinliancdcard,String tradeacco){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey", sessionkey);
		map.put("bankacconame", customerappellation);
		map.put("identityno", identityno);
		map.put("identitytype", identityType);
		map.put("bankacco", bankacco);
		map.put("capitalmode", capitalmode);
		map.put("bankserial", bankserial);
		map.put("bankname", bankname);
		map.put("yinliancdcard", yinliancdcard);
		map.put("tradeacco", tradeacco);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("changecard")),map);
	}

	/** 
	* @Title: tradecancle 
	* @Description: 可撤单列表
	* @param sessionkey
	* @param businflag
	* @throws 
	*/
	public static String tradecancle(String sessionkey){
		Map<String, String> map = new HashMap<String, String>();
		map.put("sessionkey", sessionkey);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("withdrawlist")),map);
	}
	
	/**
	 * 快捷开户(C037)
	 * @return
	 */
	public static String meropenacco(String yinliancdcard,String customername,String idtype,String identityno,
			String bankacco,String mobile,String bankname,String bankserial,String communicationaddr,String zipcode,String invalidate,
			String workcode,String email,String telephone,String capitalmode,String tradepassword){
		Map<String,String> map = new HashMap<String,String>();
		map.put("yinliancdcard", yinliancdcard);//使用B041接口中返回的请求序列号
		map.put("customername", customername);//客户姓名
		map.put("idtype",  idtype);//证件类型
		map.put("identityno",  identityno);//证件号码
		map.put("bankacco",  bankacco);//银行卡号
		map.put("mobile",  mobile);//手机号码
		map.put("bankname",  bankname);//银行名称
		map.put("bankserial", bankserial);//银行编号
		map.put("communicationaddr", communicationaddr);//家庭地址
		map.put("zipcode", zipcode);//邮政编码
		map.put("invalidate", invalidate);//证件有效期
		map.put("workcode",workcode);//职业
		map.put("email", email);//email
		map.put("telephone", telephone);//telephone
		map.put("capitalmode",capitalmode);//资金方式 "M":通联
		map.put("tradepassword", tradepassword);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("meropenacco")),map);
	}
	/**
	 * 修改恒生用户信息
	 * @param sessionkey ...
	 * @return
	 */
	public static String modifyuserinfo(String sessionkey,String communicationaddr,String zipcode,String invalidate,String email,String workcode,String tradepassword ){
		Map<String,String> map = new HashMap<String,String>();
		map.put("sessionkey", sessionkey);//sessionkey
		map.put("communicationaddr", communicationaddr);//家庭地址
		map.put("zipcode",zipcode);//邮政编码
		map.put("invalidate", invalidate);//证件有效期
		map.put("email", email);//email
		map.put("workcode", workcode);//职业
		map.put("tradepassword", tradepassword);//交易密码
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("modifyuserinfo")),map);
	}
	/**
	 * 快捷银行验证码发送(B040)
	 * @param mobile ...
	 * @return
	 */
	public static String genopendata(String mobile,String  customername,String capitalmode,String identityno,String identitytype,String bankserial,String bankacco){
		Map<String,String> map = new HashMap<String,String>();
		map.put("customername",customername);    //客户姓名
		map.put("capitalmode",capitalmode ); //资金方式 仅支持通联（M）
		map.put("identityno",identityno); //证件号码
		map.put("identitytype",identitytype); //证件类型
		map.put("bankserial",bankserial); //银行编号
		map.put("bankacco",bankacco); //银行账号
		map.put("mobile",mobile); //预留手机号码
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("genopendata")),map);
	}
	/**
	 *  快捷银行校验验证码(B041)
	 * @param mobile ...
	 * @return
	 */
	public static String bankverifyauthcode(String mobile,String  customername,String capitalmode,String identityno,String accoreqserial,String bankserial,String bankacco,String originalorderid,String authcode){
		Map<String,String> map = new HashMap<String,String>();
		map.put("customername",customername);  //客户姓名
		map.put("capitalmode",capitalmode ); //资金方式 仅支持通联（M）
		map.put("mobile",mobile); //预留手机号码
		map.put("bankacco",bankacco); //银行账号
		map.put("accoreqserial",accoreqserial); //请求序列号 (B040返回的请求序列号)
		map.put("originalorderid",originalorderid); //请求序列号 (B040返回的流水号)
		map.put("identityno",identityno); //证件号码
		map.put("bankserial",bankserial); //银行编号
		map.put("authcode",authcode); //验证码
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("bankverifyauthcode")),map);
	}
	
	/**
	 *  交易密码验证
	 * @param mobile ...
	 * @return
	 */
	public static String checkTradePwd(String sessionkey,String  pwd){
		Map<String,String> map = new HashMap<String,String>();
		map.put("sessionkey",sessionkey);   
		map.put("password",pwd );  
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("tradelogin")),map);
	}
	/**
	 * 查询所有基金列表
	 * @return
	 */
	public static String fundstatequery(){
		Map<String,String> map = new HashMap<String,String>();
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("fundstatequery")),map);
	}
	
	/**
	 * 查询基金折扣率
	 * @return
	 */
	public static String discountquery(String fundcode,String businflag,String sharetype){
		Map<String,String> map = new HashMap<String,String>();
		map.put("fundcode",fundcode);
		map.put("businflag", businflag);
		map.put("sharetype", sharetype);//收费类型
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("discountquery")),map);
	}
	/**
	 * 可购买基金列表
	 * @return
	 */
	public static String fundlist(){
		Map<String,String> map = new HashMap<String,String>();
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("fundlist")),map);
	}
	/**
	 * 交易限制列表信息
	 * @return
	 */
	public static String tradelimitquery(){
		Map<String,String> map = new HashMap<String,String>();
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("tradelimitquery")),map);
	}
	/**
	 * 修改定投管理
	 * @return
	 */
	public static String updateinvest(Map<String,String> map){
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("tradechange")),map);
	}
	/**
	 * 提交风险测评答案
	 * @param map
	 * @return
	 */
	public static String modifyrisk(String sessionkey,String item){
		Map<String,String> map = new HashMap<String,String>();
		map.put("sessionkey",sessionkey);
		map.put("qnoandanswer", item);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("modifyrisk")),map);
	}
	/**
	 * 获取可绑的银行卡
	 * @param map
	 * @return
	 */
	public static String getUsableBank(){
		Map<String,String> map = new HashMap<String,String>();
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("getUsableBank")),map);
	}
	/**
	 * 获取定投列表
	 * @param map
	 * @return
	 */
	public static String valuquery(String sessionkey){
		Map<String,String> map = new HashMap<String,String>();
		map.put("sessionkey",sessionkey);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("valuquery")),map);
	}
	
	
	/**
	 * 获取风险题目
	 * @param map
	 * @return
	 */
	public static String risk(String sessionkey ){
		Map<String,String> map = new HashMap<String,String>();
		map.put("sessionkey",sessionkey);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("risk")),map);
	}
	
	/**
	 * 转换基金列表
	 * @param map
	 * @return
	 */
	public static String targetfunds(Map<String,String> map){
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("targetfunds")),map);
	}
	/**
	 * 推送消息(多条)
	 * @param map
	 * @return
	 */
	public static String sendMoreMsg(String task_type,String batchaddress,String msg,String subject,String task_id){
		Map<String,String> map = new HashMap<String,String>();
		map.put("task_type",task_type);
		map.put("batchaddress",batchaddress);
		map.put("msg",msg);
		map.put("subject",subject);
		map.put("task_id",task_id);
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("sendMoreMsg")),map);
	}
	
	
	/**
	 * 组合投资基金列表(T013)
	 * @param map
	 * @return
	 */
	public static String groupFundList(){
		Map<String,String> map = new HashMap<String,String>();
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("combinvest")),map);
	}
	/**
	 * 可组合投资支付方式列表(T014)
	 * @param map
	 * @return
	 */
	public static String getGroupBank(Map<String,String> map){
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("combinvestorder")),map);
	}
	/**
	 * 组合投资买入(T015)
	 * @param map
	 * @return
	 */
	public static String buyGroupFund(Map<String,String> map){
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("combinvestpurchase")),map);
	}/**
	 * 组合投资买入(T015)
	 * @param map
	 * @return
	 */
	public static String combadjust(Map<String,String> map){
		return post(String.format(getPropertyValue("jrhttp"), getPropertyValue("combadjust")),map);
	}

}