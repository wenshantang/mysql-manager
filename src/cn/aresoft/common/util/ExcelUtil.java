package cn.aresoft.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/**
 * excel工具类
 * @author Administrator
 *
 */
public abstract class ExcelUtil {
	private static int sheetNum = 0; // 第sheetnum个工作表
	/**
	 * Excel默认样式,添加数据居中
	 */
	public static final int CENTER = 0;
	/**
	 * 字符串居中
	 */
	public static final int STRING_CENTER = 1;
	/**
	 * 整型数据
	 */
	public static final int NUMBER_INT = 2;
	/**
	 * 
	 */
	public static final int NUMBER_DOUBLE2 = 3;
	/**
	 * 浮点型数据
	 */
	public static final int NUMBER_DOUBLE4 = 4;
	/**
	 * 数字格式为百分比
	 */
	public static final int NUMBER_PERCENT = 5;
	private XSSFWorkbook workbook;
	private int sheetSize;
	private String[] keys;
	private Map<String, String> valueTypeMap;
	private Map<String, XSSFCellStyle> styleMap;

	public ExcelUtil() {
		setWorkbook(new XSSFWorkbook());
	}

	/**
	 * 创建一个新的excel实例<br>
	 * 设置其sheet的最大行数、列的对应数据类型及样式
	 * 
	 * @param sheetSize
	 *            最大行数
	 * @param keys
	 *            数据列的key
	 * @param valueTypeMap
	 *            key对应列的数据类型
	 * @param styleKeyMap
	 *            key对应列的样式,可以去Excel中的几个静态常理
	 */
	public ExcelUtil(int sheetSize, String[] keys,
			Map<String, String> valueTypeMap, Map<String, Integer> styleKeyMap) {
		this();
		this.setSheetSize(sheetSize);
		this.setKeys(keys);
		this.setValueTypeMap(valueTypeMap);
		Map<String, XSSFCellStyle> styleMap = new HashMap<String, XSSFCellStyle>();
		for (String key : styleKeyMap.keySet()) {
			styleMap.put(key, this.getStyle(styleKeyMap.get(key)));
		}
		this.setStyleMap(styleMap);
	}

	/**
	 * 将结果集写入工作簿
	 * 
	 * @param resultList
	 * @return XSSFWorkbook
	 */
	public XSSFWorkbook writeExcelFile(String[] keys,List<Map<String, Object>> resultList,
			int startNum) {
		int sheetNum = 0;// 记录sheet数
		int rowNum = 0;// 记录行数
		XSSFSheet sheet = null;
		XSSFCell cell = null;
		XSSFRow row = null;
		if (resultList != null && resultList.size() > 0) {
			for (Map<String, Object> rsMap : resultList) {
				if (rowNum == 0) {
					sheetNum++;
					// 创建新sheet
					sheet = getWorkbook().createSheet("sheet" + sheetNum);
					// 写表头
					writeHeads(sheet);
					rowNum = startNum;
				}
				row = sheet.createRow(rowNum);
				int cellNum = 0;
				for (String key : keys) {
					cell = row.createCell(cellNum);
					String valueType = valueTypeMap.get(key);
					if ("int".equals(valueType)) {
						cell.setCellValue(getInt(rsMap, key));
					} else if ("long".equals(valueType)) {
						cell.setCellValue(getLong(rsMap, key));
					} else if ("double".equals(valueType)) {
						cell.setCellValue(getDouble(rsMap, key));
					} else {
						cell.setCellValue(getString(rsMap, key));
					}
					cell.setCellStyle(styleMap.get(key));
					cellNum++;
				}
				rowNum++;
				if (rowNum == getSheetSize()) {
					rowNum = 0;
				}
			}
		} else {
			
			return null;
		}
		return null;
	}

	/**
	 * 2012-09-28 xuyan
	 * 仅仅将resultList的内容创建到sheet中，但并不写入文件中；可以多次调用该方法，将不同的resultList写入到一个sheeet中
	 * 
	 * @param resultList
	 *            ：要写入excel的数据
	 * @param keys
	 *            ：excel数据列对应的resultList中的map的key
	 * @param colIndex
	 *            ：跟keys中一一对应的数据在excel中的列索引
	 */
	public void writeListToExcel(List<Map<String, Object>> resultList,
			String[] keys, int[] colIndex) {
		int sheetNum = 0;// 记录sheet数
		int rowNum = 0;// 记录行数
		XSSFSheet sheet = null;
		XSSFCell cell = null;
		XSSFRow row = null;
		if (resultList != null && resultList.size() > 0) {

			for (Map<String, Object> rsMap : resultList) {
				List<int[]> mergeInfo = new ArrayList<int[]>();

				if (rowNum == 0) {
					sheetNum++;
					// 创建新sheet
					sheet = getWorkbook().getSheet("sheet" + sheetNum);
					if (sheet == null) {
						sheet = getWorkbook().createSheet("sheet" + sheetNum);
						// 写表头
						rowNum = writeHeads(sheet) + 1;
					}

				}
				rowNum = sheet.getLastRowNum() + 1;
				row = sheet.createRow(rowNum);
				int cellNum = 0;
				for (String key : keys) {
					int cIndex = colIndex[cellNum];
					cell = row.createCell(cIndex);
					if (cIndex > cellNum) {// 说明前一个数据列有列合并，否则两个数据是相等的
						mergeInfo.add(new int[] { rowNum, rowNum,
								colIndex[cellNum - 1], cIndex - 1 });
					}
					Object value = rsMap.get(key);
					if (value != null) {
						if (value instanceof Integer) {
							cell.setCellValue(Integer.parseInt(value.toString()));
						} else if (value instanceof Float
								|| value instanceof Double
								|| value instanceof BigDecimal) {
							cell.setCellValue(Double.parseDouble(value
									.toString()));
						} else {
							cell.setCellValue(value.toString());
						}
					} else {
						cell.setCellValue("");
					}
					cell.setCellStyle(styleMap.get(key));
					cellNum++;
				}
				rowNum++;
				if (rowNum == getSheetSize()) {
					rowNum = 0;
				}
				if (mergeInfo.size() > 0) {// 若一行数据中有列合并
					for (int i = 0; i < mergeInfo.size(); i++) {
						int[] merge = mergeInfo.get(i);
						sheet.addMergedRegion(new CellRangeAddress(merge[0],
								merge[1], merge[2], merge[3]));
					}
				}
			}
		} else {
			

		}

	}
	/** 2012-11-19 xuyan
	 * 在最后一行数据后新添加一行sum公式行，用于合计
	 * @param sumColIndex:合计列的列索引，从0开始
	 * @param sumStartRow：合计列的起始合计行索引,默认合计到最后一行，从1开始。
	 * @param colLetterIndex:合计列的列英文名称，大写
	 */
	public void writeSumFormulaToExcel(int[] sumColIndex,int[] sumStartRow,char[] colLetterIndex) {
		int rowNum = 0;// 记录行数
		XSSFSheet sheet =workbook.getSheet("sheet1");
		XSSFCell cell = null;
		rowNum=sheet.getLastRowNum();
		XSSFRow row = sheet.createRow(rowNum+1);
		cell=row.createCell(0);
		cell.setCellValue("总合计：");
		//System.out.println("rowAll======"+rowNum);
		for(int i=0;i<sumColIndex.length;i++){
			cell= row.createCell(sumColIndex[i]);  				
			cell.setCellFormula("SUM("+colLetterIndex[i]+sumStartRow[i]+":"+colLetterIndex[i]+(rowNum+1)+")"); 
		}
		
		
	}
	/**
	 * 2012-09-28 xuyan 写入excel文件中
	 * 
	 * @return InputStream
	 */
	public InputStream writeToExcelFile() {
	
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			workbook.write(outputStream);
			outputStream.flush();
			byte[] byteArray = outputStream.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(byteArray, 0,
					byteArray.length);
			outputStream.close();
			return inputStream;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 2012-09-27 xuyan 仅将本类中实现了抽象方法writeHeads中写入的数据写入excel中
	 * 
	 * @return InputStream
	 */
	public InputStream writeHeadExcelFile() {
		int sheetNum = 0;// 记录sheet数
		int rowNum = 0;// 记录行数
		XSSFSheet sheet = null;
		// XSSFCell cell = null;
		// XSSFRow row=null;
		if (rowNum == 0) {
			sheetNum++;
			// 创建新sheet
			sheet = getWorkbook().createSheet("sheet" + sheetNum);
			// 写表头
			rowNum = writeHeads(sheet) + 1;
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			workbook.write(outputStream);
			outputStream.flush();
			byte[] byteArray = outputStream.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(byteArray, 0,
					byteArray.length);
			outputStream.close();
			return inputStream;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * xuyan：2012-09-12 将结果写入二进制数据流：数据列形式是单行单列
	 * 
	 * @param resultList
	 * @return InputStream 输入流
	 */
	public InputStream writeExcelFile(String[] keys,List<Map<String, Object>> resultList) {
		int sheetNum = 0;// 记录sheet数
		int rowNum = 0;// 记录行数
		XSSFSheet sheet = null;
		XSSFCell cell = null;
		XSSFRow row = null;
		if (resultList != null && resultList.size() > 0) {
			for (Map<String, Object> rsMap : resultList) {
				if (rowNum == 0) {
					//sheetNum++;
					// 创建新sheet
					sheet = getWorkbook().getSheet("sheet" + sheetNum);
					if (sheet == null) {
						sheet = getWorkbook().createSheet("sheet" + sheetNum);
						// 写表头
						rowNum = writeHeads(sheet) + 1;
					}
				}
				row = sheet.createRow(rowNum);
				int cellNum = 0;
				for (String key : keys) {
					cell = row.createCell(cellNum);
					Object value = rsMap.get(key);
					if (value != null) {

						if (value instanceof Integer) {
							cell.setCellValue(Integer.parseInt(value.toString()));
							// styleMap.put(key,this.getStyle(Excel.NUMBER_INT));
						} else if (value instanceof Float
								|| value instanceof Double
								|| value instanceof BigDecimal) {
							cell.setCellValue(Double.parseDouble(value
									.toString()));

						} else {
							cell.setCellValue(value.toString());
						}
					} else {
						cell.setCellValue("");
					}
					cell.setCellStyle(styleMap.get(key));
					cellNum++;
				}
				rowNum++;
				if (rowNum == getSheetSize()) {
					rowNum = 0;
				}
			}
		} else {
			
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			workbook.write(outputStream);
			outputStream.flush();
			byte[] byteArray = outputStream.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(byteArray, 0,
					byteArray.length);
			outputStream.close();
			return inputStream;// byteArray;
		} catch (IOException e) {
			e.printStackTrace();
			// e.printStackTrace();
			// failMsg = "导出文件失败,I/O异常!";
		}
		return null;
	}

	/**
	 * xuyan：2012-11-19 将结果写入XSSFSheet中，并不直接返回InputStream流：数据列有行并格式
	 * 
	 * @param resultList
	 *            :resultList中的map除查询的数据外，还需要存用于控制样式的key_value, 目前增加的固定样式控制key有：
	 *            1.需要对数据进行行并的样式控制key有：NotRowMergeColumns、rowMergeLen、
	 *            NotRowMergeFirstRow、NotRowMergeOtherRow
	 * 
	 */
	public void writeMultiStyleListToExcel(List<Map<String, Object>> resultList) {

		int sheetNum = 0;// 记录sheet数
		int rowNum = 0;// 记录行数
		XSSFSheet sheet = null;
		XSSFCell cell = null;
		XSSFRow row = null;
		if (resultList != null && resultList.size() > 0) {
			List<int[]> mergeInfo = new ArrayList<int[]>();
			for (int i = 0; i < resultList.size(); i++) {
				if (rowNum == 0) {
					sheetNum++;
					// 创建新sheet
					sheet = getWorkbook().createSheet("sheet" + sheetNum);
					// 写表头
					rowNum = writeHeads(sheet) + 1;
				}
				Map<String, Object> eachRecord = resultList.get(i);
				row = sheet.createRow(rowNum);
				for (int j = 0; j < keys.length; j++) {
					cell = row.createCell(j);
					String key = keys[j];
					Object value = null;
					// 该列是否行并
					boolean isRowMerge = false;
					isRowMerge = ((String) eachRecord.get("NotRowMergeColumns"))
							.contains(key) ? false : true;
					if (!isRowMerge) {// 如果是非行并列，则取值从key=NotRowMergeFirstRow的List<map>中取
						value = ((Map<String, Object>) eachRecord
								.get("NotRowMergeFirstRow")).get(key);

					} else {
						value = eachRecord.get(key);
						int rowSpan = (Integer) eachRecord.get("rowMergeLen");// 取得行并的行数
						if (rowSpan > 1) {
							// System.out.println(key+"===="+rowNum+","+(rowNum+rowSpan-1)+","+j+","+j);
							mergeInfo.add(new int[] { rowNum,
									(rowNum + rowSpan - 1), j, j });
						}
					}
					if (value != null) {
						// System.out.println(value+"*********"+value.getClass()+"************************"+value.toString().contains("."));
						if (value instanceof Integer) {
							cell.setCellValue(Integer.parseInt(value.toString()));
							styleMap.put(key, this.getStyle(ExcelUtil.NUMBER_INT));
						} else if (value instanceof Float
								|| value instanceof Double
								|| value instanceof BigDecimal) {
							cell.setCellValue(Double.parseDouble(value
									.toString()));
						} else {
							cell.setCellValue(value.toString());
						}
					} else {
						cell.setCellValue("");
					}
					cell.setCellStyle(styleMap.get(key));
				}
				rowNum++;
				// 如果有行并，则非行并列需要从key=NotRowMergeOtherRow的List<map>中取,并且需要创建row
				int rowSpan = (Integer) eachRecord.get("rowMergeLen");
				if (rowSpan > 1
						&& ((List<Map<String, Object>>) eachRecord
								.get("NotRowMergeOtherRow")).size() > 0) {
					for (Map<String, Object> notMergeMap : ((List<Map<String, Object>>) eachRecord
							.get("NotRowMergeOtherRow"))) {
						row = sheet.createRow(rowNum);
						for (int k = 0; k < keys.length; k++) {
							String key = keys[k];
							boolean isMerge = ((String) eachRecord
									.get("NotRowMergeColumns")).contains(key) ? false
									: true;
							Object value = null;
							if (!isMerge)
								value = notMergeMap.get(key);
							if (value != null) {
								cell = row.createCell(k);
								if (value instanceof Integer) {
									cell.setCellValue(Integer.parseInt(value
											.toString()));
									// styleMap.put(key,this.getStyle(Excel.NUMBER_INT));
								} else if (value instanceof Float
										|| value instanceof Double
										|| value instanceof BigDecimal) {
									cell.setCellValue(Double.parseDouble(value
											.toString()));

								} else {
									cell.setCellValue(value.toString());
								}
							} else {
								cell = row.createCell(k);
								cell.setCellValue("");

							}
							cell.setCellStyle(styleMap.get(key));
						}
						rowNum++;
					}
				}
				if (rowNum == getSheetSize()) {
					if (mergeInfo != null && mergeInfo.size() > 0) {
						for (int l = 0; l < mergeInfo.size(); l++) {
							int[] merge = mergeInfo.get(l);
							sheet.addMergedRegion(new CellRangeAddress(
									merge[0], merge[1], merge[2], merge[3]));
						}
					}
					mergeInfo = new ArrayList<int[]>();
					rowNum = 0;
				}
			}
			if (mergeInfo != null && mergeInfo.size() > 0) {
				for (int l = 0; l < mergeInfo.size(); l++) {
					int[] merge = mergeInfo.get(l);
					sheet.addMergedRegion(new CellRangeAddress(merge[0],
							merge[1], merge[2], merge[3]));
				}
			}

			
		} else {
			
		}
		
	}
	/**
	 * xuyan：2012-09-25 将结果写入二进制数据流：数据列有行并格式
	 * 
	 * @param resultList
	 *            :resultList中的map除查询的数据外，还需要存用于控制样式的key_value, 目前增加的固定样式控制key有：
	 *            1.需要对数据进行行并的样式控制key有：NotRowMergeColumns、rowMergeLen、
	 *            NotRowMergeFirstRow、NotRowMergeOtherRow
	 * @return InputStream 输入流
	 */
	@SuppressWarnings("unchecked")
	public InputStream writeMultiStyleDataToExcelFile(
			List<Map<String, Object>> resultList) {

		int sheetNum = 0;// 记录sheet数
		int rowNum = 0;// 记录行数
		XSSFSheet sheet = null;
		XSSFCell cell = null;
		XSSFRow row = null;
		if (resultList != null && resultList.size() > 0) {
			List<int[]> mergeInfo = new ArrayList<int[]>();
			for (int i = 0; i < resultList.size(); i++) {
				if (rowNum == 0) {
					sheetNum++;
					// 创建新sheet
					sheet = getWorkbook().createSheet("sheet" + sheetNum);
					// 写表头
					rowNum = writeHeads(sheet) + 1;
				}
				Map<String, Object> eachRecord = resultList.get(i);
				row = sheet.createRow(rowNum);
				for (int j = 0; j < keys.length; j++) {
					cell = row.createCell(j);
					String key = keys[j];
					Object value = null;
					// 该列是否行并
					boolean isRowMerge = false;
					isRowMerge = ((String) eachRecord.get("NotRowMergeColumns"))
							.contains(key) ? false : true;
					if (!isRowMerge) {// 如果是非行并列，则取值从key=NotRowMergeFirstRow的List<map>中取
						value = ((Map<String, Object>) eachRecord
								.get("NotRowMergeFirstRow")).get(key);

					} else {
						value = eachRecord.get(key);
						int rowSpan = (Integer) eachRecord.get("rowMergeLen");// 取得行并的行数
						if (rowSpan > 1) {
							// System.out.println(key+"===="+rowNum+","+(rowNum+rowSpan-1)+","+j+","+j);
							mergeInfo.add(new int[] { rowNum,
									(rowNum + rowSpan - 1), j, j });
						}
					}
					if (value != null) {
						// System.out.println(value+"*********"+value.getClass()+"************************"+value.toString().contains("."));
						if (value instanceof Integer) {
							cell.setCellValue(Integer.parseInt(value.toString()));
							styleMap.put(key, this.getStyle(ExcelUtil.NUMBER_INT));
						} else if (value instanceof Float
								|| value instanceof Double
								|| value instanceof BigDecimal) {
							cell.setCellValue(Double.parseDouble(value
									.toString()));
						} else {
							cell.setCellValue(value.toString());
						}
					} else {
						cell.setCellValue("");
					}
					cell.setCellStyle(styleMap.get(key));
				}
				rowNum++;
				// 如果有行并，则非行并列需要从key=NotRowMergeOtherRow的List<map>中取,并且需要创建row
				int rowSpan = (Integer) eachRecord.get("rowMergeLen");
				if (rowSpan > 1
						&& ((List<Map<String, Object>>) eachRecord
								.get("NotRowMergeOtherRow")).size() > 0) {
					for (Map<String, Object> notMergeMap : ((List<Map<String, Object>>) eachRecord
							.get("NotRowMergeOtherRow"))) {
						row = sheet.createRow(rowNum);
						for (int k = 0; k < keys.length; k++) {
							String key = keys[k];
							boolean isMerge = ((String) eachRecord
									.get("NotRowMergeColumns")).contains(key) ? false
									: true;
							Object value = null;
							if (!isMerge)
								value = notMergeMap.get(key);
							if (value != null) {
								cell = row.createCell(k);
								if (value instanceof Integer) {
									cell.setCellValue(Integer.parseInt(value
											.toString()));
									// styleMap.put(key,this.getStyle(Excel.NUMBER_INT));
								} else if (value instanceof Float
										|| value instanceof Double
										|| value instanceof BigDecimal) {
									cell.setCellValue(Double.parseDouble(value
											.toString()));

								} else {
									cell.setCellValue(value.toString());
								}
							} else {
								cell = row.createCell(k);
								cell.setCellValue("");

							}
							cell.setCellStyle(styleMap.get(key));
						}
						rowNum++;
					}
				}
				if (rowNum == getSheetSize()) {
					if (mergeInfo != null && mergeInfo.size() > 0) {
						for (int l = 0; l < mergeInfo.size(); l++) {
							int[] merge = mergeInfo.get(l);
							sheet.addMergedRegion(new CellRangeAddress(
									merge[0], merge[1], merge[2], merge[3]));
						}
					}
					mergeInfo = new ArrayList<int[]>();
					rowNum = 0;
				}
			}
			if (mergeInfo != null && mergeInfo.size() > 0) {
				for (int l = 0; l < mergeInfo.size(); l++) {
					int[] merge = mergeInfo.get(l);
					sheet.addMergedRegion(new CellRangeAddress(merge[0],
							merge[1], merge[2], merge[3]));
				}
			}

		} else {
			
		}

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			workbook.write(outputStream);
			outputStream.flush();
			byte[] byteArray = outputStream.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(byteArray, 0,
					byteArray.length);
			outputStream.close();
			return inputStream;// byteArray;
		} catch (IOException e) {
			e.printStackTrace();
			// e.printStackTrace();
			// failMsg = "导出文件失败,I/O异常!";
		}
		return null;
	}

	/**
	 * 获取一个样式
	 * 
	 * @param styleType
	 *            预设定的样式,Excel的常量
	 * @return XSSFCellStyle
	 */
	public XSSFCellStyle getStyle(int styleType) {
		XSSFCellStyle style = null;
		try {
			switch (styleType) {
			case CENTER:
				style = getWorkbook().createCellStyle();
				style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
				style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				break;
			case STRING_CENTER:// 文字居中并自动换行
				style = getWorkbook().createCellStyle();
				style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
				style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				style.setWrapText(true); // 文本自动换行
				break;
			case NUMBER_INT:
				style = getWorkbook().createCellStyle();
				style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
				style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				break;
			case NUMBER_DOUBLE2:
				style = getWorkbook().createCellStyle();
				style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
				style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
				break;
			case NUMBER_DOUBLE4:
				style = getWorkbook().createCellStyle();
				style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
				style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				// 如果 getBuiltinFormat 返回不了参数，格式化就会失效
				// style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.0000"));
				style.setDataFormat(workbook.createDataFormat().getFormat(
						"0.0000"));
				break;
			case NUMBER_PERCENT:
				style = getWorkbook().createCellStyle();
				style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
				style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
				break;
			default:
				style = getWorkbook().createCellStyle();
				style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
				style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
				style.setDataFormat(workbook.createDataFormat().getFormat(
						"￥#,##0.0000;￥-#,##0.0000"));
				break;
			}
		} catch (Exception e) {
		}
		return style;
	}

	/**
	 * 设置sheet的表头
	 * 
	 * @param sheet
	 */
	public abstract int writeHeads(XSSFSheet sheet);

	public XSSFWorkbook getWorkbook() {
		return workbook;
	}

	private void setWorkbook(XSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public Map<String, XSSFCellStyle> getStyleMap() {
		return styleMap;
	}

	public void setStyleMap(Map<String, XSSFCellStyle> styleMap) {
		this.styleMap = styleMap;
	}

	public Map<String, String> getValueTypeMap() {
		return valueTypeMap;
	}

	public void setValueTypeMap(Map<String, String> valueTypeMap) {
		this.valueTypeMap = valueTypeMap;
	}

	private String getString(Map<String, Object> rsMap, String key) {
		Object obj = rsMap.get(key);
		if (obj == null) {
			return "";
		}
		return obj.toString();
	}

	private Integer getInt(Map<String, Object> rsMap, String key) {
		Integer r = rsMap.get(key) == null ? 0 : (Integer) rsMap.get(key);
		return r;
	}

	private Long getLong(Map<String, Object> rsMap, String key) {
		Object obj = rsMap.get(key);
		if (obj == null) {
			return 0L;
		}
		if (obj instanceof Integer) {
			Integer r = (Integer) obj;
			return r.longValue();
		}
		if (obj instanceof Long) {
			return (Long) obj;
		}
		try {
			return Long.parseLong(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}

	private Double getDouble(Map<String, Object> rsMap, String key) {
		Object obj = rsMap.get(key);
		if (obj == null) {
			return 0D;
		}
		if (obj instanceof Double) {
			return (Double) obj;
		}
		Double r = Double.parseDouble(obj.toString());
		return r;
	}

	public int getSheetSize() {
		return sheetSize;
	}

	public void setSheetSize(int sheetSize) {
		this.sheetSize = sheetSize;
	}

	public String[] getKeys() {
		return keys;
	}

	public void setKeys(String[] keys) {
		this.keys = keys;
	}
	
	
	/**
	 * 返回sheet表数目
	 * 
	 * @return int
	 */
	public static int getSheetCount(Workbook wb) {
		int sheetCount = -1;
		sheetCount = wb.getNumberOfSheets();
		return sheetCount;
	}

	/**
	 * sheetNum下的记录行数
	 * 
	 * @return int
	 */
	public static int getRowCount(Workbook wb) {
		return getRowCount(wb,sheetNum);
	}

	/**
	 * 读取指定sheetNum的rowCount
	 * 
	 * @param sheetNum
	 * @return int
	 */
	public static int getRowCount(Workbook wb,int sheetNum) {
		if (wb == null)
			System.out.println("=============>WorkBook为空");
		HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(sheetNum);
		int rowCount = -1;
		rowCount = sheet.getLastRowNum();
		return rowCount;
	}

	/**
	 * 得到指定行的内容
	 * 
	 * @param lineNum
	 * @return String[]
	 */
	public static String[] readExcelLine(Workbook wb,int lineNum) {
		return readExcelLine(wb, sheetNum, lineNum);
	}

	/**
	 * 指定工作表和行数的内容
	 * 
	 * @param sheetNum
	 * @param lineNum
	 * @return String[]
	 */
	public static String[] readExcelLine(Workbook wb,int sheetNum, int lineNum) {
		if (sheetNum < 0 || lineNum < 0)
			return null;
		String[] strExcelLine = null;
		try {
			HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(sheetNum);
			HSSFRow row = sheet.getRow(lineNum);

			int cellCount = row.getLastCellNum();
			strExcelLine = new String[cellCount + 1];
			for (int i = 0; i <= cellCount; i++) {
				strExcelLine[i] = readStringExcelCell(wb,lineNum, i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strExcelLine;
	}

	/**
	 * 指定行和列编号的内容
	 * 
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public static String readStringExcelCell(Workbook wb,int rowNum, int cellNum) {
		return readStringExcelCell(wb, sheetNum, rowNum, cellNum);
	}

	/**
	 * 指定工作表、行、列下的内容
	 * 
	 * @param sheetNum
	 * @param rowNum
	 * @param cellNum
	 * @return String
	 */
	public static String readStringExcelCell(Workbook wb,int sheetNum, int rowNum, int cellNum) {
		if (sheetNum < 0 || rowNum < 0)
			return "";
		String strExcelCell = "";
		try {
			HSSFSheet sheet = (HSSFSheet) wb.getSheetAt(sheetNum);
			HSSFRow row = sheet.getRow(rowNum);

			Cell cell = row.getCell(cellNum);
			if (cell != null) { // add this condition
				strExcelCell = getCellValue(cell);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strExcelCell;
	}

	public static String getCellValue(Cell cell) {
		String strExcelCell;
		// judge
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_FORMULA:
			strExcelCell = "";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
//			if(HSSFDateUtil.isCellDateFormatted(cell)){
//				strExcelCell = DateUtil.date2Str(cell.getDateCellValue(), "yyyy/mm/dd");
//				break;
//			}
			strExcelCell = String.valueOf((int) cell.getNumericCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_STRING:
			strExcelCell = cell.getStringCellValue().trim();
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strExcelCell = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strExcelCell = "";
			break;
		default:
			strExcelCell = "";
			break;
		}
		return strExcelCell;
	}
	
	// 創建Excel單元格
	public static HSSFCell createCell(HSSFRow row, int column, HSSFCellStyle style,
			int cellType, Object value) {
		HSSFCell cell = row.createCell(column); // HSSFCellUtil.createCell(row,
		cell.setCellType(cellType);
		if (style != null) {
			cell.setCellStyle(style);
		}
		if(value==null) value="";
		switch (cellType) {
		case HSSFCell.CELL_TYPE_STRING: {
			cell.setCellValue(new HSSFRichTextString(value.toString()));
		}
			break;
		case HSSFCell.CELL_TYPE_NUMERIC: {
			cell.setCellValue(Double.parseDouble(value.toString()));
		}
			break;
		default:
			break;
		}
		return cell;
	}
	
	public static void writeExcel(OutputStream op, HSSFWorkbook wb)
			throws ServletException, IOException {
		try {
			//OutputStream op = response.getOutputStream();
			wb.write(op);
			op.flush(); 
			op.close();
			op=null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getCellStringValue(HSSFRow row, int cellIndex) {
		HSSFCell cell = row.getCell(cellIndex);
		String re = "";
		if(cell != null){
			try {
				re= cell.getRichStringCellValue().getString();
			} catch (RuntimeException e) {
				try {
					NumberFormat nf1 = NumberFormat.getInstance(Locale.CHINA);
					re =  nf1.format(cell.getNumericCellValue());
				} catch (RuntimeException e1) {
					e1.printStackTrace();
				}
			}
		}
		return re;
	}
	//add by zyf
	public static String getCellStringValueNew(Row row, int cellIndex) {
		Cell cell = row.getCell((short)cellIndex);
		String re = "";
		if(cell != null){
			try {
				re= cell.getRichStringCellValue().getString();
				
			} catch (RuntimeException e) {
				try {
					String re1 = "";
					NumberFormat nf1 = NumberFormat.getInstance(Locale.CHINA);
					re1 =  nf1.format(cell.getNumericCellValue());
					re = re1.replaceAll(",","");
					
				} catch (RuntimeException e1) {
					e1.printStackTrace();
				}
			}
		}
		return re;
	}
	
   /**
    * 填充标题
    * @param sheet
    * @param book
    * @param headArray
    */
	public static void setHeadResult(HSSFSheet sheet, HSSFWorkbook book,String[] headArray) {
		HSSFCellStyle hcsCenter = book.createCellStyle();
		hcsCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直 居中
		hcsCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		HSSFRow row = sheet.createRow(0);
		int col = 0;
		for(String str:headArray){
			ExcelUtil.createCell(row, col++, hcsCenter, HSSFCell.CELL_TYPE_STRING, str);
		}
	}
	
	/**
	 * 填充内容
	 * @param book
	 * @param sheet
	 * @param contentArray
	 */
	public static void setcontentResult(HSSFWorkbook book, HSSFSheet sheet,String[] contentArray) {
		HSSFCellStyle hcsCenter = book.createCellStyle();
		hcsCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直 居中
		hcsCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		HSSFRow row = sheet.createRow(1);
		int col = 0;
		for(String str:contentArray){
			ExcelUtil.createCell(row, col++, hcsCenter, HSSFCell.CELL_TYPE_STRING, str);
		}
	}
	
	/**
	 * 填充内容
	 * @param book
	 * @param sheet
	 * @param contentArray
	 */
	public static void setcontentResultforRow(HSSFWorkbook book, HSSFSheet sheet,String[] contentArray,int rownumber) {
		HSSFCellStyle hcsCenter = book.createCellStyle();
		hcsCenter.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直 居中
		hcsCenter.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		HSSFRow row = sheet.createRow(rownumber);
		int col = 0;
		for(String str:contentArray){
			ExcelUtil.createCell(row, col++, hcsCenter, HSSFCell.CELL_TYPE_STRING, str);
		}
	}
	
	
	
	/** Excel 文件要存放的位置 */
	public static String outputFile = "/zlxx/excel_demo.xls";

	/**
	 * 标题列表
	 */
	private static String[] HEAD_LIST = { "序号", "名字", "年龄", "备注" };

	private static String[] VALUE_LIST = { "01", "张代浩", "20", "1986-04-03",
			"........." };

	/**
	 * 字段列表
	 */
	private static String[] FIELD_LIST = { "index", "name", "age", "content" };

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// ------------------------------------------------------------
		// List<String[]> list = new ArrayList<String[]>();
		// list.add(VALUE_LIST);
		// list.add(VALUE_LIST);
		// list.add(VALUE_LIST);
		// createExcel(outputFile, HEAD_LIST, list);

		// ------------------------------------------------------------
		// List<Map<String, Object>> dataList = new ArrayList<Map<String,
		// Object>>();
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("index", "001");
		// map.put("name", "张三");
		// map.put("age", "22");
		// map.put("content", "大家好");
		// dataList.add(map);
		// dataList.add(map);
		// dataList.add(map);
		//
		// createExcel(outputFile, HEAD_LIST, FIELD_LIST, dataList);

		// -------------------------------------------------------------
		// readExcel(null);
		// --------------------------------------------------------------
		//new ExcelUtil().testReadExcel();
	}

	/**
	 * 使用举例
	 * 
	 */
	public void testCreateExcel() {

		List<Map<String, Object>> dataList = getDataList();
		List<String> headList = getHeadList();
		List<String> fieldList = getFieldList();

		try {
			createExcel("TEST01.xls", headList, fieldList, dataList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 使用举例
	 * 
	 * @throws Exception
	 * 
	 */
	@SuppressWarnings("static-access")
	public void testReadExcel() throws Exception {
		String excelUrl = "C:/javadeveloper/workspace/Mybatis_one/src/测试台账2003.xls";
		List<String[]> list = this.readExcel(excelUrl);
		for (String[] str : list) {
			for (String s : str) {
				//System.out.print(s + " | ");
			}
			//System.out.println("");
		}
	}

	/**
	 * 测试举例
	 * 
	 * @return
	 */
	private List<Map<String, Object>> getDataList() {
		/**
		 * 数据集合
		 */
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// 单行数据
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", "001");
		map.put("name", "张三");
		map.put("age", "22");
		map.put("content", "大家好");
		map.put("date", new Date());
		dataList.add(map);
		dataList.add(map);
		dataList.add(map);

		return dataList;
	}

	/**
	 * 测试举例
	 * 
	 * @return
	 */
	private List<String> getHeadList() {
		List<String> headList = new ArrayList<String>();
		headList.add("序号");
		headList.add("名字");
		headList.add("年龄");
		headList.add("出生");
		headList.add("备注");

		return headList;
	}

	/**
	 * 测试举例
	 * 
	 * @return
	 */
	private List<String> getFieldList() {
		List<String> fieldList = new ArrayList<String>();
		fieldList.add("index");
		fieldList.add("name");
		fieldList.add("age");
		fieldList.add("date");
		fieldList.add("content");

		return fieldList;
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createExcel(String excel_name, String[] headList,
			String[] fieldList, List<Map<String, Object>> dataList)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.length; i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList[i]);
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.length; i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(dataMap.get(fieldList[i])));
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//System.out.println("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void createExcel(String excel_name, List<String> headList,
			List<String> fieldList, List<Map<String, Object>> dataList)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.size(); i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//System.out.println("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题集合
	 * @param field_list
	 *            Excel文件Field标题集合
	 * @param dataList
	 *            Excel文件数据内容部分
	 * @throws HSSFWorkbook
	 */
	public static HSSFWorkbook createExcel(List<String> headList,
			List<String> fieldList, List<Map<String, Object>> dataList)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.size(); i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList.get(i));
		}
		// ===============================================================

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			Map<String, Object> dataMap = dataList.get(n);
			// ===============================================================
			for (int i = 0; i < fieldList.size(); i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(dataMap.get(fieldList.get(i))));
			}
			// ===============================================================
		}
		return workbook;
	}

	private static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			if (obj instanceof String) {
				return (String) obj;
			} else if (obj instanceof Date) {
				return null;// DateUtil.dateToString((Date)
							// obj,DateUtil.DATESTYLE_SHORT_EX);
			} else {
				return obj.toString();
			}
		}
	}

	/**
	 * 
	 * @param excel_name
	 *            生成的Excel文件路径+名称
	 * @param headList
	 *            Excel文件Head标题部分
	 * @param valueList
	 *            Excel文件数据内容部分
	 * @throws Exception
	 */
	public static void bulidExcel(String excel_name, String[] headList,
			List<String[]> valueList) throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================
		for (int i = 0; i < headList.length; i++) {

			// 在索引0的位置创建单元格（左上端）
			HSSFCell cell = row.createCell(i);
			// 定义单元格为字符串类型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			cell.setCellValue(headList[i]);
		}
		// ===============================================================

		for (int n = 0; n < valueList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			String[] valueArray = valueList.get(n);
			// ===============================================================
			for (int i = 0; i < valueArray.length; i++) {

				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(i);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(valueArray[i]);
			}
			// ===============================================================
		}

		// 新建一输出文件流
		FileOutputStream fOut = new FileOutputStream(excel_name);
		// 把相应的Excel 工作簿存盘
		workbook.write(fOut);
		fOut.flush();
		// 操作结束，关闭文件
		fOut.close();
		//System.out.println("[" + excel_name + "]" + "文件生成...");
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<String[]> readExcel(String excel_name) throws Exception {
		// 结果集
		List<String[]> list = new ArrayList<String[]>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(new FileInputStream(
				excel_name));

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if(hssfrow!=null){
			int col = hssfrow.getPhysicalNumberOfCells();
			// 单行数据
			String[] arrayString = new String[col];
			for (int i = 0; i < col; i++) {
				HSSFCell cell = hssfrow.getCell(i);
				if (cell == null) {
					arrayString[i] = "";
				} else if (cell.getCellType() == 0) {
					// arrayString[i] = new Double(cell.getNumericCellValue()).toString();
					if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) { 
						  if (HSSFDateUtil.isCellDateFormatted(cell)) {    
						    Date d = cell.getDateCellValue();    
//						    DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");    
						     DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						    arrayString[i] = formater.format(d);   
						   } else {    
						       arrayString[i] = new BigDecimal(cell.getNumericCellValue()).longValue()+"";    
						}
					}
				} else {// 如果EXCEL表格中的数据类型为字符串型
					arrayString[i] = cell.getStringCellValue().trim();
				}
			}
			list.add(arrayString);
		}
		}
		return list;
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> readExcelByList(String excel_name)
			throws Exception {
		// 结果集
		List<List<Object>> list = new ArrayList<List<Object>>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(new FileInputStream(
				excel_name));

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if (hssfrow != null) {
				int col = hssfrow.getPhysicalNumberOfCells();
				// 单行数据
				List<Object> arrayString = new ArrayList<Object>();
				for (int i = 0; i < col; i++) {
					HSSFCell cell = hssfrow.getCell(i);
					if (cell == null) {
						arrayString.add("");
					} else if (cell.getCellType() == 0) {
						arrayString.add(new Double(cell.getNumericCellValue())
								.toString());
					} else {// 如果EXCEL表格中的数据类型为字符串型
						arrayString.add(cell.getStringCellValue().trim());
					}
				}
				list.add(arrayString);
			}
		}
		return list;
	}

	/**
	 * 读取 Excel文件内容
	 * 
	 * @param excel_name
	 * @return
	 * @throws Exception
	 */
	public static List<List<Object>> readExcelByInputStream(
			InputStream inputstream) throws Exception {
		// 结果集
		List<List<Object>> list = new ArrayList<List<Object>>();

		HSSFWorkbook hssfworkbook = new HSSFWorkbook(inputstream);

		// 遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数
		HSSFSheet hssfsheet = hssfworkbook.getSheetAt(0);

		// 遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数

		// //System.out.println("excel行数： "+hssfsheet.getPhysicalNumberOfRows());
		for (int j = 0; j < hssfsheet.getPhysicalNumberOfRows(); j++) {
			HSSFRow hssfrow = hssfsheet.getRow(j);
			if (hssfrow != null) {
				int col = hssfrow.getPhysicalNumberOfCells();
				// 单行数据
				List<Object> arrayString = new ArrayList<Object>();
				for (int i = 0; i < col; i++) {
					HSSFCell cell = hssfrow.getCell(i);
					if (cell == null) {
						arrayString.add("");
					} else if (cell.getCellType() == 0) {
						arrayString.add(new Double(cell.getNumericCellValue())
								.toString());
					} else {// 如果EXCEL表格中的数据类型为字符串型
						arrayString.add(cell.getStringCellValue().trim());
					}
				}
				list.add(arrayString);
			}
		}
		return list;
	}


	// update-begin--Author:Quainty Date:20130523 for：日期类型数据导入不对(顺便扩大支持了Excel的数据类型)
	// --------------------------------------------------------------------------------------------
	// update-begin--Author:lihuan Date:20130423 for：直接将导出的Excel导入出现的bug的修复
	/**
	 * 字符串转换为Date类型数据（限定格式      YYYY-MM-DD hh:mm:ss）或（YYYY/MM/DD hh:mm:ss）
	 * 
	 * @param cellValue : 字符串类型的日期数据
	 * @return
	 */
	private static Date stringToDate(String cellValue) {
		if (cellValue.length() > 19)
			cellValue = cellValue.substring(0, 19);
		Calendar calendar = Calendar.getInstance();
		String[] dateStr = cellValue.split(" ");
		String[] dateInfo = dateStr[0].split("-");
		if (dateInfo.length != 3) {
			dateInfo = dateStr[0].split("/");	// 让  yyyy/mm/dd 的格式也支持
		}
		if (dateInfo.length == 3) {
			int year = Integer.parseInt(dateInfo[0]);
			int month = Integer.parseInt(dateInfo[1])-1;	// 0~11
			int day = Integer.parseInt(dateInfo[2]);
			calendar.set(year, month, day);
		} else {
			return null;	// 格式不正确
		}
		if (dateStr.length > 1) {// 有时间（限定格式     hh:mm:ss）
			String[] timeStr = dateStr[1].split(":");
			if (timeStr.length == 3) {
				int hour = Integer.parseInt(timeStr[0]);
				int minute = Integer.parseInt(timeStr[1]);
				int second = Integer.parseInt(timeStr[2]);
				calendar.set(Calendar.HOUR_OF_DAY, hour);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, second);
			} else {
				return null;	// 格式不正确
			}
		}
		return calendar.getTime();
	}

	// update-end--Author:lihuan Date:20130423 for：导入bug修复直接将导出的Excel导入出现的bug的修复
	// --------------------------------------------------------------------------------------------
	// update-end--Author:Quainty Date:20130523 for：日期类型数据导入不对(顺便扩大支持了Excel的数据类型)
}
