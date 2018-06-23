package cn.aresoft.manager.service.impl.question;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.puff.framework.annotation.Bean;
import com.puff.framework.utils.StringUtil;

import cn.aresoft.common.model.question.Question;
import cn.aresoft.common.service.impl.CenterServiceImpl;
import cn.aresoft.manager.model.sys.SysUser;
import cn.aresoft.manager.service.question.QuestionService;

@Bean(id = "questionService")
public class QuestionServiceImpl extends CenterServiceImpl<Question> implements QuestionService {
	  private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private final String[] xx={"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
	  @Override
	public int importQuestionsByExcel(File file, String filetype,SysUser user, String ques_id) throws IOException {
			int count = 0;
			int qid=1;
			InputStream is = new FileInputStream(file);
			Workbook workbook = null;
			if ("xls".equals(filetype)) {// excel 2003
				workbook = new HSSFWorkbook(is);
			} else { // excel 2007
				workbook = new XSSFWorkbook(is);
			}
			Question question = null;
			
			
			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet Sheet = workbook.getSheetAt(numSheet);
				if (Sheet == null) {
					continue;
				}
				int num = Sheet.getNumMergedRegions();
				Map<Integer, CellRangeAddress> map=new TreeMap<>(new Comparator<Integer>() {

					@Override
					public int compare(Integer arg0, Integer arg1) {
						return arg0-arg1;
					}
					
				});
				for (int i = 0; i <num; i++) {
					CellRangeAddress ca = Sheet.getMergedRegion(i);
					map.put(ca.getFirstRow(), ca);
				}
				
				for (Entry<Integer, CellRangeAddress> entry:map.entrySet()) {
					CellRangeAddress ca = entry.getValue();
					int firstRow = ca.getFirstRow();
					int lastRow = ca.getLastRow();
					Row frow = Sheet.getRow(firstRow);
					Cell fcell = frow.getCell(0);
					String value = getValue(fcell);
					if(StringUtil.notEmpty(value))
					{
						question = new Question();
						UUID randomUUID = UUID.randomUUID();
						String uuid = randomUUID.toString().replaceAll("-", "");
						question.setId(uuid);
						question.setWid(ques_id);
						question.setQidx(qid);
						question.setAidx("0");
						question.setCreat_date(format.format(new Date()));
						question.setCreat_by(user.getName());
						question.setContent(value);
						insert(question);
						count++;
						for(int rownum=firstRow;rownum<=lastRow;rownum++)
						{
							Row row = Sheet.getRow(rownum);
							Cell ansCell = row.getCell(1);
							Cell sorCell = row.getCell(2);
							String ans = getValue(ansCell);
							if(StringUtil.notEmpty(ans))
							{
								question=new Question();
								question.setAidx(xx[rownum-firstRow]);
								question.setContent(ans);
								question.setCreat_by(user.getName());
								question.setWid(ques_id);
								question.setQidx(qid);
								question.setScore((int)Double.parseDouble(getValue(sorCell)));
								question.setCreat_date(format.format(new Date()));
								insert(question);
								count++;
							}
						}
						qid++;
					}
					
				}
			
			}
			
		return count;
	}

	
	private String getValue(Cell cell) {
		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
//			 返回布尔类型的值
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
//			 返回数值类型的值
			return String.valueOf(cell.getNumericCellValue());
		} else {
//			 返回字符串类型的值
			return String.valueOf(cell.getStringCellValue());
		}
	}


	@Override
	public void deleteByWid(String wid) {
		String sql="delete from fun_quertion where wid=?";
		deleteBySql(sql, wid);
	}


}
