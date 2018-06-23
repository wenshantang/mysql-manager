package cn.aresoft.manager.plugin;

import java.math.BigDecimal;

import org.beetl.core.Context;
import org.beetl.core.Function;
/**
 * bigdecimal格式化
 * 包含3个参数
 * 1》需要格式化的值<br/>
 * 2》需要保留的位数<br/>
 * 3》格式化方式{1：四舍五入；2：截取}<br/>
 * http://blog.csdn.net/ahwr24/article/details/7048724
 * @author yyj
 *
 */
public class BigDecimalFormatFunction implements Function {
	
	@Override
	public Object call(Object[] params, Context cx) {
		try{
			if(params!=null&&params.length==3){
				if(params[0]==null) params[0]=0;
				BigDecimal decimal=new BigDecimal(params[0]+"");//传入的值
				int scale=(Integer)params[1];//精度
				int flag=(Integer)params[2];//传入的格式化标志
				 BigDecimal b=new BigDecimal(0);
				if(flag==1){
					b=decimal.setScale(scale,BigDecimal.ROUND_HALF_UP);//四舍五入，如2.35会变成2.4
				}else if(flag==2){
					b=decimal.setScale(scale,BigDecimal.ROUND_DOWN);//直接删除多余的小数位，如2.35会变成2.3
				}
			    return b.doubleValue();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}

}
