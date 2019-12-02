package edu.handong.csee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	
	public ArrayList<String> getData(String path) {
		ArrayList<String> values = new ArrayList<String>();
		
		System.out.println(path);
		
		try (InputStream inp = new FileInputStream(path)) {
		    //InputStream inp = new FileInputStream("workbook.xlsx");
		    
		        Workbook wb = WorkbookFactory.create(inp);
		        Sheet sheet = wb.getSheetAt(0);
		        Row row = sheet.getRow(2);
		        Cell cell = row.getCell(1);
		        if (cell == null)
		            cell = row.createCell(3);
		        
		        values.add(cell.getStringCellValue());
		        
		    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return values;
	}
	
	public ArrayList<String> getData(InputStream is, int check, String filename) {
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<String> values2 = new ArrayList<String>();
		try (InputStream inp = is) {
		    //InputStream inp = new FileInputStream("workbook.xlsx");
		        Workbook wb = WorkbookFactory.create(inp);
		        Sheet sheet = wb.getSheetAt(0);
		        int rows = sheet.getPhysicalNumberOfRows();
		        String result2="";
		        for(int rowIndex=0; rowIndex<rows; rowIndex++){
		        	String result = "";
		        	//if(rowIndex==0)
		        		result = filename.substring(0,filename.lastIndexOf("."))+";;;";
		        	//else
		        	//	result = ""+";;;";
		        	Row row = sheet.getRow(rowIndex);
		        	 if (row != null) {
		                    int cells = row.getPhysicalNumberOfCells();
		                    for (int columnIndex = 0; columnIndex <= cells; columnIndex++) {
		                        Cell cell = row.getCell(columnIndex); // 셀에 담겨있는 값을 읽는다.
		                        String value="";
		                       // System.out.println(cell);
		                        if(cell==null) {
		                        	result+=";;;";
		                        	continue;
		                        }
		                        //cell style 엔터시도1
		                        CellStyle cs = wb.createCellStyle();
		                        cs.setWrapText(true);
		                        cell.setCellStyle(cs);
		                        //HSSFCellStyle.setWrapText(true);
		                        //if(cell.getCellType()==NUMERIC)
		                        switch(cell.getCellType()) {
		                        case NUMERIC:
		                            value = cell.getNumericCellValue() + "";
		                            break;
		                        case STRING:
		                            value = cell.getStringCellValue() + "";
		                            break;
		                        case ERROR:
		                            value = cell.getErrorCellValue() + "";
		                            break;
		                         default :
		                        	 value=cell.getStringCellValue();
		                        }
		                        //System.out.println(cell);
			                    //values.add(cell.getStringCellValue());
		                        if(result.contains("\n"))
		                        	result = result.replace("\n", "hh" );
		                        if(value=="")//||value=="\n")
		                        	 result+=value;
		                        if(value=="\n")
		                        	result=result;
		                        result+=value+";;;";
		                        /*
		                        if(cell.getStringCellValue()==""||cell.getStringCellValue()=="\n")
		                        	 result+=cell.getStringCellValue();
		                        result+=cell.getStringCellValue()+",";*/
		                        //result+=value+",";
		                    }
		                    result=result.substring(0,result.length()-3);
		                    result+="***";
		                    result2+=result+"\n";
		        	 }
		        }
		        	if(check==1)
		        	values.add(result2);
		        	else if (check==2)
		        	values2.add(result2);
		        
		    } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(check==1)
			return values;
		else if (check==2)
			return values2;
		else return values;
	}
}