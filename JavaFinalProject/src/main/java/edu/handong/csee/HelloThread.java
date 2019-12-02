package edu.handong.csee;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


public class HelloThread extends Thread {
	static String value0="";
	static String value1="";
	static int num=0;
	public static void threadRun(File file, int filekind, String path, heejuLinkedList<String>values, heejuLinkedList<String>values2, String output) {
		//values.addANodeToStart("this is start!");
		values.resetIteration();
		//values2.addANodeToStart("this is start!");
		values2.resetIteration();
		if(file.isFile()) {
			String Filename=file.getName();
			///////////////////
			//System.out.println(Filename);
			ZipFile zipFile;//=new ZipFile(Filename);
	//	}
	//}
	//ZipFile zipFile;
	if(Filename.substring(Filename.lastIndexOf(".")).equals(".zip"))
	try {
		filekind=1;
		//zipFile = new ZipFile(path);
		zipFile = new ZipFile(path+"/"+Filename);
		Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();
	    while(entries.hasMoreElements()){
	    	ZipArchiveEntry entry = entries.nextElement();
	        InputStream stream = zipFile.getInputStream(entry);
	        ExcelReader myReader = new ExcelReader();	
	        
	        for(String value:myReader.getData(stream, filekind, Filename)) {
	        	if(filekind==1) {
	        		//values.add(value);
	        		//values.addANodeToStart("this is start!");
	        		//values.resetIteration();
	        		//values.
	        		values.setDataAtCurrent(value);
	        		//values.insertNodeAfterCurrent(value);
	        		//values.insertNodeAfterCurrent(value);
	        		//lists = null;
	        		//values.showList();
	        		//System.out.println("///////////");
	        		/*
	        		for(int count=0; count<values.length(); count++) {
	        			lists[count]= values.getDataAtCurrent();
	        			System.out.println(lists[count]);
	        		}*/
	        		value0=value0+values.getList(num);
	        		 int i=0, j=0;
	 			    Workbook workbook =  WorkbookFactory.create(true); // 새 엑셀 생성
	 		        Sheet sheet = workbook.createSheet("sheet0"); // 새 시트(Sheet) 생성
	 		        //for(String value0 : lists) {
	 			        Row row = sheet.createRow(i); // 엑셀의 행은 0번부터 시작
	 			        String[] splits= value0.split(";;;");
	 			        for(String split : splits) {
		                        if(split.contains("***")) {
		                        	split = split.replace("***", "");
		                        	i++;
		                        	j=0;
				 			        row = sheet.createRow(i);
		                        }
		                        	split = split.replace("hh","\n");
	 			        		Cell cell = row.createCell(j); 
	 			        		cell.setCellValue(split);
	 			        		j++;
	 			        	}//생성한 셀에 데이터 삽입
	 			        i++;
	 			        j=0;
	 		        //}
	 		        try {
	 		            FileOutputStream fileoutputstream = new FileOutputStream(output.substring(0,output.lastIndexOf("."))+Integer.toString(filekind++)+".xlsx");
	 		            workbook.write(fileoutputstream);
	 		            fileoutputstream.close();
	 		            //System.out.println("엑셀파일생성성공");
	 		        } catch (IOException e) {
	 		        	myException myexc = new myException(null); 
	 		            e.printStackTrace();
	 		            System.out.println("엑셀파일생성실패");
	 		        }	    
	 			    
	        		//Utils.writeAFile(values, output.substring(0,output.lastIndexOf("."))+Integer.toString(filekind++)+output.substring(output.lastIndexOf(".")));
	        		////////////
	        	}
	        	else if(filekind==2) {
	        		//values.add(value);
	        		//values.addANodeToStart("this is start!");
	        		//values.resetIteration();
	        		//values.
	        		//System.out.println(value);
	        		values2.setDataAtCurrent(value);
	        		//values.insertNodeAfterCurrent(value);
	        		//values.insertNodeAfterCurrent(value);
	        		//lists = null;
	        		//values2.showList();
	        		/*
	        		for(int count=0; count<values.length(); count++) {
	        			lists[count]= values.getDataAtCurrent();
	        			System.out.println(lists[count]);
	        		}*/
	        		value1=value1+values2.getList(num);
	        		 int i=0, j=0;
	 			    Workbook workbook =  WorkbookFactory.create(true); // 새 엑셀 생성
	 		        Sheet sheet = workbook.createSheet("sheet0"); // 새 시트(Sheet) 생성
	 		        //for(String value0 : lists) {
	 			        Row row = sheet.createRow(i); // 엑셀의 행은 0번부터 시작
	 			        String[] splits= value1.split(";;;");
	 			        for(String split : splits) {
		                        if(split.contains("***")) {
		                        	split = split.replace("***", "");
		                        	i++;
		                        	j=0;
				 			        row = sheet.createRow(i);
		                        }
		                        	split = split.replace("hh","\n");
	 			        		Cell cell = row.createCell(j); 
	 			        		//CellStyle headStyle = workbook.createCellStyle();
	 			        		//headStyle.setBorderTop(BorderStyle.THIN);
	 			        	    //headStyle.setBorderBottom(BorderStyle.THIN);
	 			        	    //headStyle.setBorderLeft(BorderStyle.THIN);
	 			        	    //headStyle.setBorderRight(BorderStyle.THIN);
	 			        	    //cell.setCellStyle(headStyle);
	 			        		cell.setCellValue(split);
	 			        		j++;
	 			        	}//생성한 셀에 데이터 삽입
	 			        i++;
	 			        j=0;
	 		        //}
	 		        try {
	 		            FileOutputStream fileoutputstream = new FileOutputStream(output.substring(0,output.lastIndexOf("."))+Integer.toString(filekind++)+".xlsx");
	 		            workbook.write(fileoutputstream);
	 		            fileoutputstream.close();
	 		            //System.out.println("엑셀파일생성성공");
	 		        } catch (IOException e) {
	 		        	myException myexc = new myException(null); 
	 		            e.printStackTrace();
	 		            System.out.println("엑셀파일생성실패");
	 		        }	    
	 			    
	        		//Utils.writeAFile(values, output.substring(0,output.lastIndexOf("."))+Integer.toString(filekind++)+output.substring(output.lastIndexOf(".")));
	        		////////////
	        	}
	        	//Utils.writeAFile(value, output);
	        }//for
	    }
		zipFile.close();

	    
	    
	}
	catch (IOException e) {
		// TODO Auto-generated catch block
		myException myexc = new myException(null); 
		e.printStackTrace();
	}
		}
	}
}