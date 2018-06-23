package cn.aresoft.common.service.impl.activity;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.puff.framework.annotation.Bean;
import com.puff.framework.annotation.Inject;
import com.puff.framework.utils.StringUtil;
import com.puff.jdbc.core.PageRecord;

import cn.aresoft.cms.common.cache.CmsPropertiesCache;
import cn.aresoft.cms.common.model.CmsProperties;
import cn.aresoft.common.model.CommonParam;
import cn.aresoft.common.model.activity.WebActivityUser;
import cn.aresoft.common.service.activity.WebActivityUserService;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.common.util.ExcelUtil;


@Bean(id="webActivityUserService")
public class WebActivityUserServiceImpl extends CenterServiceImpl<WebActivityUser> implements WebActivityUserService {
	@Inject
	private CmsPropertiesCache cmsPropertiesCache;
	@Override
	public PageRecord<WebActivityUser> paging(WebActivityUser t, CommonParam param) {
		String sql=buildQuerySql();
		StringBuilder sb = new StringBuilder();
		sb.append(sql).append(" where 1=1 ");
		List<Object> condition = new ArrayList<Object>();
		if(StringUtil.notEmpty(t.getSex())){
			sb.append(" and sex=?");
			condition.add(t.getSex());
		}
		if(StringUtil.notEmpty(t.getUndergraduate_major())){
			sb.append(" and undergraduate_major = ?");
			condition.add(t.getUndergraduate_major());
		}
		if(StringUtil.notEmpty(t.getPresent_major())){
			sb.append(" and present_major = ?");
			condition.add(t.getPresent_major());
		}
		if(StringUtil.notEmpty(t.getInterested_industry())){
			sb.append(" and (interested_industry =? or interested_industry like ? or interested_industry like ? or interested_industry like ?)");
			condition.add(t.getInterested_industry());
			condition.add(""+t.getInterested_industry()+",%");
			condition.add("%,"+t.getInterested_industry()+",%");
			condition.add("%,"+t.getInterested_industry()+"");
		}
		if(StringUtil.empty(param.getSort())){
			sb.append(" order by create_date desc");
		}
		return paging(sb.toString(), condition, param);
		
	}

	@Override
	public ByteArrayOutputStream exportExcel(WebActivityUser t) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		 try {
			HSSFWorkbook book = new HSSFWorkbook();
			 HSSFSheet sheet = book.createSheet();
			 book.setSheetName(0, "学员名单");
			 sheet.setDefaultColumnWidth(30);
			 sheet.setDefaultRowHeight((short)500);
			 String[] headArray=new String[]{"姓名","性别","年龄","手机号","本科专业","现修专业","感兴趣的研究方向 ","本科毕业学校","来源","备注"};
			 ExcelUtil.setHeadResult(sheet, book, headArray);
			 String sql=buildQuerySql();
				StringBuilder sb = new StringBuilder();
				sb.append(sql).append(" where 1=1 ");
				List<Object> condition = new ArrayList<Object>();
				if(StringUtil.notEmpty(t.getSex())){
					sb.append(" and sex='"+t.getSex()+"'");
					
				}
				if(StringUtil.notEmpty(t.getUndergraduate_major())){
					sb.append(" and undergraduate_major ='"+t.getUndergraduate_major()+"'");
				}
				if(StringUtil.notEmpty(t.getPresent_major())){
					sb.append(" and present_major = '"+t.getPresent_major()+"'");
				}
				if(StringUtil.notEmpty(t.getInterested_industry())){
					sb.append(" and (interested_industry ='"+t.getInterested_industry()+"' or interested_industry like '"+t.getInterested_industry()+",%' or interested_industry like '%,"+t.getInterested_industry()+",%' or interested_industry like '%,"+t.getInterested_industry()+"' )");
				}
			 List<WebActivityUser> list = queryList(sb.toString());
			
			 List<CmsProperties> discipline_specialty = cmsPropertiesCache.findListFormCache("discipline_specialty");
				List<CmsProperties> research_direction=cmsPropertiesCache.findListFormCache("research_direction");
				List<CmsProperties> graduate_institutions=cmsPropertiesCache.findListFormCache("graduate_institutions");
				Map<String,String> mapd= new HashMap<>();
				Map<String,String> mapr= new HashMap<>();
				Map<String,String> mapg= new HashMap<>();
				for(CmsProperties c:discipline_specialty){
					mapd.put(c.getName(), c.getValue());
				}
				for(CmsProperties c:research_direction){
					mapr.put(c.getName(), c.getValue());
				}
				for(CmsProperties c:graduate_institutions){
					mapg.put(c.getName(), c.getValue());
				}
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						 String[] contentArray=new String[10];
						WebActivityUser activityUser = list.get(i);
						//contentArray[0]=activityUser.getOpenid();
						contentArray[0]=activityUser.getName();
						String sex = activityUser.getSex();
						if("0".equals(sex)){
							contentArray[1]="女";
						}
						if("1".equals(sex)){
							contentArray[1]="男";
						}
						contentArray[2]=activityUser.getAge();
						contentArray[3]=activityUser.getMobile();
						if(StringUtil.notEmpty(activityUser.getUndergraduate_major())){
							contentArray[4]=mapd.get(activityUser.getUndergraduate_major());
						}
						if(StringUtil.notEmpty(activityUser.getPresent_major())){
							contentArray[5]=mapd.get(activityUser.getPresent_major());
						}
						if(StringUtil.notEmpty(activityUser.getInterested_industry())){
							String industry = activityUser.getInterested_industry();
							String[] split = industry.split(",");
							String str="";
							for(String s:split){
								str=str+","+mapr.get(s);
							}
							if(str.length()>0){
								str=str.substring(1);
							}
							contentArray[6]=str;
						}
						if(StringUtil.notEmpty(activityUser.getUndergraduate_school())){
							contentArray[7]=mapg.get(activityUser.getUndergraduate_school());
						}
						if(StringUtil.notEmpty(activityUser.getSource())){
							contentArray[8]=activityUser.getSource();
						}
						if(StringUtil.notEmpty(activityUser.getSource())){
							contentArray[9]=activityUser.getRemarks();
						}
						ExcelUtil.setcontentResultforRow(book, sheet, contentArray,i+1);
					}
				}
				ExcelUtil.writeExcel(byteArrayOutputStream, book);
		} catch (Exception ex) {
			ex.printStackTrace();
			
		}
		 return byteArrayOutputStream;
	}
	

}
