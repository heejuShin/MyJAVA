package edu.handong.analysis.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {
	
	public static ArrayList<String> getLines(String file, boolean removeHeader) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<String>();
		/*
		//File을 이용해 파일 읽기ㅣ 
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			System.out.println("---------------Error--------------\n\nThe file path does not exist. Please check your CLI argument!\n");
			System.out.println("-----------------------------------\n\n");
			System.exit (0);
		}
		 int i=0;
		while (inputStream.hasNextLine ()) {
			String line = inputStream.nextLine ();
			if(i!=0)
				list.add(line);
			i++;
		}

		inputStream.close ();
		*/
		
		//외부 라이브러리를 이용해 파일 읽기
		///*
		try (
			Reader reader = Files.newBufferedReader(Paths.get(file));
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
		){
			int i=0;
			for(CSVRecord csvRecord : csvParser) {
				String line = csvRecord.get(0)+", "+csvRecord.get(1)+", "+csvRecord.get(2)+", "+csvRecord.get(3)+", "+csvRecord.get(4)+", "+csvRecord.get(5)+", "+csvRecord.get(6)+", "+csvRecord.get(7)+", "+csvRecord.get(8);
				if(i!=0)
					list.add(line);
				i++;
			}
		}
		//*/
		return list;
	}

	public static void writeAFile(ArrayList<String> lines, String targetFileName){
		// TODO Auto-generated method stub
		String fileName=targetFileName;
		File file = new File(targetFileName);
		File abFile = file.getAbsoluteFile();
		if(!abFile.getParentFile().exists()) {
			abFile.getParentFile().mkdirs();
		}
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(fileName);
		} catch(FileNotFoundException e) {
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}
		for(String line:lines) {
			outputStream.println(line);
		}
		outputStream.close();
	}

}
