package cn.center.xlsx;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wdnnccey on 17/2/27.
 * <p>
 * <p>
 * 2017年02月27日21:27:26，
 *
 */
public class TestXlsx2 {

    public static void main(String[] args) throws FileNotFoundException, IOException {


    	String filed = "D:\\Soft_Cache_Document\\WeChat Files\\WeChat Files\\makeit_133\\FileStorage\\File\\2019-05\\八达通找零增值差异数据明细（5.01-5.08）(1).xlsx";
        File excelFile = new File(filed);
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(excelFile));
        int numberOfSheets = wb.getNumberOfSheets();
        
        List<xlsx> xlsxs = new ArrayList();
        
        for (int x = 0; x < numberOfSheets; x++) {
        	XSSFSheet sheet = wb.getSheetAt(x);
        	int columnNum = 0;
        	if (sheet.getRow(0) != null) {
        		columnNum = sheet.getRow(0).getLastCellNum() - sheet.getRow(0).getFirstCellNum();
            }
            if (columnNum > 0) {
                for (Row row : sheet) {
                    String[] singleRow = new String[columnNum];
                    //行
                    String line = "";
                    int n = 0;
                    for (int i = 0; i < columnNum; i++) {
                        Cell cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
                        //单元格
                        String cel = readCell(cell, singleRow, n);
                        line += cel;
                        n++;
                    }
                    System.out.println(line);
                    String[] split = line.split("\\|");
                    xlsx xlsx = new xlsx(split[0],split[1],split[2],split[3],split[4],split[5],split[6],split[7],split[8],split[9],split[10],split[11],split[12],split[13],split[14],split[15]);
                    xlsxs.add(xlsx);
                }
                System.out.println("===========================================================Sheet分割线===========================================================");
                
            }
        }
        for (xlsx xlsx : xlsxs) {
			System.out.println(xlsx.toString());
		}
    }

    
    
	private static String readCell(Cell cell, String[] singleRow, int n) {
		String line = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_BLANK:
                singleRow[n] = "";
                if (cell == null || cell.equals("") || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
                	line = "<Null>|";
                } else {
                	line = singleRow[n] + "|";
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                singleRow[n] = Boolean.toString(cell.getBooleanCellValue());
                line = singleRow[n] + "|";
                break;
            // 数值
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    line = sdf.format(date) + "|";
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String temp = cell.getStringCellValue();
                    // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                    if (temp.indexOf(".") > -1) {
                        singleRow[n] = String.valueOf(new Double(temp)).trim();
                        line = singleRow[n] + "|";
                    } else {
                        singleRow[n] = temp.trim();
                        line = singleRow[n] + "|";
                    }
                }
                break;
            case Cell.CELL_TYPE_STRING:
                singleRow[n] = cell.getStringCellValue().trim();
                line = singleRow[n] + "|";
                break;
            case Cell.CELL_TYPE_ERROR:
                singleRow[n] = "";
                line = singleRow[n] + "|";
                break;
            case Cell.CELL_TYPE_FORMULA:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String temp = cell.getStringCellValue();
                // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                if (temp.indexOf(".") > -1) {
                    temp = String.valueOf(new Double(temp))
                            .trim();
                    Double cny = Double.parseDouble(temp);//6.2041
                    DecimalFormat df = new DecimalFormat("0.00");
                    String CNY = df.format(cny);
                    line = CNY + "|";
                } else {
                    singleRow[n] = temp.trim();
                    line = singleRow[n] + "|";
                }
            default:
                singleRow[n] = "";
                break;
        }
        return line;
	}
    
    
    
}