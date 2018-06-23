package cn.aresoft.common.service.impl.record;

import java.util.ArrayList;
import java.util.List;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.Record;

import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.common.service.record.RecordService;
@Bean(id = "recordService")
public class RecordServiceImpl extends CenterServiceImpl<Record> implements RecordService{
	
	@Override
	public List<Record> queryProInfoList(String pro_name) {
		//私募[使用产品代码]
		//String sql = "select pro_id,PRO_SHORTNAME from p_product_info ";
		//公募[使用基金代码]
		String sql="select fundcode as pro_id,pro_name as PRO_SHORTNAME from P_PRODUCT_PUBLICINFO ";
		List<String> condition=new ArrayList<String>();
		if(StringUtil.notBlank(pro_name)){
			//对pro_name进行@拆分
			String[] pro_nameArray=pro_name.split("@");
			for(int i=0;i<pro_nameArray.length;i++){
				if(i==0){
					sql+=" where pro_name like ? ";
				}else{
					sql+=" or pro_name like ? ";
				}
				condition.add("%" + pro_nameArray[i] + "%");
			}
		}
		return executor.queryListRecord(sql,condition);
	}

}
