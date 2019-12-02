package edu.handong.csee;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class JavaFinalProject{
	
	String input;
	String output;	
	boolean help;
	static int filekind=1;
	
	public void run(String[] args) throws IOException, InterruptedException {
		Options options = createOptions();
		
		if(parseOptions(options, args)){	
			if (help){
				printHelp(options);
				return;
			}
			
			String dataPath = input; // csv file to be analyzed
			//String resultPath = output; // the file path where the results are saved.
			
				readFileInZip(dataPath);

				//ArrayList<String> imsi = null;
				// Write a file (named like the value of resultPath) with linesTobeSaved.
				//Utils.writeAFile(imsi, resultPath);

		}//parseOption 
	}

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);
			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			help = cmd.hasOption("h");
			

		} catch (Exception e) {
			printHelp(options);
			System.exit(0);
			return false;
		}
		return true;
	}//parseOptions
	
	private Options createOptions() {
		Options options = new Options();
		
		options.addOption(Option.builder("i").longOpt("input")
				.desc("Set an input file path")
				.hasArg()
				.argName("Input path")
				.required()
				.build());
		
		options.addOption(Option.builder("o").longOpt("output")
				.desc("Set an output file path")
				.hasArg()
				.argName("Output path")
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				.hasArg()
				.argName("Help")
				//.required()
				.build());
		return options;
	}//createOptions
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "JavaFinalProject";
		String footer ="";
		formatter.printHelp("JavaFinalProject", header, options, footer, true);
	}
	
	public void readFileInZip(String path) throws IOException, InterruptedException {
		int count=0;
		File dir = new File(path);
		File []fileList=dir.listFiles();
		//ArrayList<String> values = new ArrayList<String>();
		//ArrayList<String> values2 = new ArrayList<String>();
		heejuLinkedList<String> values = new heejuLinkedList();
		heejuLinkedList<String> values2 = new heejuLinkedList();
		HelloThread[] threads = new HelloThread[5];
		for(File file : fileList) {
				values.addANodeToStart("this is start!");
				values.resetIteration();
				values2.addANodeToStart("this is start!");
				values.resetIteration();
				threads[count]=new HelloThread();
				HelloThread.threadRun(file, filekind, path, values, values2, output);
				threads[count].start();
				threads[count].join(1000);
				count++;


			}
	}//readFileinZip
	
}//class
