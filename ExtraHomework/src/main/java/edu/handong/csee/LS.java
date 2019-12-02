package edu.handong.csee;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class LS{
	
	int check=0;
	boolean p;
	boolean c1;
	boolean m;
	boolean t;
	boolean a;
	boolean r;
	boolean help;
	
	String datapath;
	String path=null;
	
	public void run(String[] args)  {
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<1)
				throw new Error();
		} catch (Error e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
			Options options = createOptions();
			for(String arg : args) {
				if(!arg.contains("-")&&!arg.equals("ls"))
					path=arg;
			}
			if (path==null)
				path=System.getProperty("user.dir");	
			
			
		if(args[0].equals("ls")) {
			if(parseOptions(options, args)){	
				if (help){
					printHelp(options);
					if(p||c1||m||t||a||r)
						;
					else
					return;
				}

				List <String> result = new ArrayList<String>();
				
				
				if(p&&!t) {
					File dirFile=new File(path);
					File []fileList=dirFile.listFiles();
					String list="";
					for(File tempFile : fileList) {
						String tempFileName=tempFile.getName();
						if(!a&&tempFile.isHidden())
							;
						else {
							if(tempFile.isDirectory())
								result.add(tempFile.getName()+"/");
							else
								result.add(tempFile.getName());
						}
					}
					Collections.sort(result);
					if(!t&&!m)
						for(String str : result) {
							System.out.println(str);
						}

					check++;
				}
				
				if(t) {
					File dirFile=new File(path);
					File []fileList=dirFile.listFiles();
					Calendar fileTm = new GregorianCalendar();
					Iterator<Double> iteratorKey;
					HashMap <Double, String> map = new HashMap<Double, String>();
					for(File tempFile : fileList) {
						//if(!tempFile.isDirectory()) {
				         fileTm.setTimeInMillis( tempFile.lastModified() );
				         String year = "" + fileTm.get( Calendar.YEAR  );
				         String mnth = "" + ( fileTm.get( Calendar.MONTH ) + 1) ;
				         String date = "" + fileTm.get( Calendar.DATE  );
				         String hour = "" + fileTm.get( Calendar.HOUR_OF_DAY  );
				         String minute = "" + fileTm.get( Calendar.MINUTE );
				         String second = "" + fileTm.get( Calendar.SECOND );
				         String tempFileName=tempFile.getName();
				         if(a) {
				        	 if(tempFile.isDirectory())
				        		 map.put(Integer.parseInt(year)*100000000+Integer.parseInt(mnth)*1000000+Integer.parseInt(date)*10000+Integer.parseInt(hour)*10+Integer.parseInt(minute)+Integer.parseInt(second)*0.01, tempFileName+"/");
				        	 else 
				        		 map.put(Integer.parseInt(year)*100000000+Integer.parseInt(mnth)*1000000+Integer.parseInt(date)*10000+Integer.parseInt(hour)*10+Integer.parseInt(minute)+Integer.parseInt(second)*0.01, tempFileName);
				         }
				        else {
				        	 if(!tempFile.isHidden()) {
				        		 if(tempFile.isDirectory())
					        	 map.put(Integer.parseInt(year)*100000000+Integer.parseInt(mnth)*1000000+Integer.parseInt(date)*10000+Integer.parseInt(hour)*10+Integer.parseInt(minute)+Integer.parseInt(second)*0.01, tempFileName+"/");
				        		 else
				        			 map.put(Integer.parseInt(year)*100000000+Integer.parseInt(mnth)*1000000+Integer.parseInt(date)*10000+Integer.parseInt(hour)*10+Integer.parseInt(minute)+Integer.parseInt(second)*0.01, tempFileName);
				        	 }
				        	}
				        }//for
					TreeMap<Double, String> tm = new TreeMap<Double, String>(map);
					if(r)
						iteratorKey = tm.keySet().iterator();
					else
						iteratorKey = tm.descendingKeySet().iterator();
					
					while(iteratorKey.hasNext()) {
						Double key=iteratorKey.next();
						result.add(map.get(key));
					if(!m)
						System.out.println(map.get(key));
					}
					check++;
				}//option t
				
				if(m&&c1) {
					int count=0;
					int im=0;
					int ic1=0;
					for(String arg : args) {
						if(!arg.contains("-m"))
							im=count;
						if(!arg.contains("-1"))
							ic1=count;
						count++;
					}
					if(im>ic1)
						m=false;
					else
						c1=false;
				}
				
				if(m&&!c1) {
					if(!t&&!p) {
						File dirFile=new File(path);
						File []fileList=dirFile.listFiles();
						for(File tempFile : fileList) {
							String tempFileName=tempFile.getName();
							if(a)
								result.add(tempFile.getName());
							else
								if(!tempFile.isHidden())
									result.add(tempFile.getName());
						}
						Collections.sort(result);
					}
					String list="";
					for(String str : result) {
							list+=str+", ";
					}
					list=list.substring(0,list.length()-2);
					System.out.println(list);
					check++;
				}
	
				
				if(a&&!m&&!t&&!p) {
					File dirFile=new File(path);
					File []fileList=dirFile.listFiles();
						result.add(".");
						result.add("..");
					for(File tempFile : fileList) {
						String tempFileName=tempFile.getName();
						result.add(tempFile.getName());
						//System.out.println(tempFileName);
					}
					if(r)
						Collections.sort(result, Collections.reverseOrder());
					else
						Collections.sort(result);
					for(String str : result) {
						System.out.println(str);
					}
					check++;
				}
				
				
				if(c1&&!m) {
					File dirFile=new File(path);
					File []fileList=dirFile.listFiles();
					if(a) {
						result.add(".");
						result.add("..");
					}
					for(File tempFile : fileList) {
						String tempFileName=tempFile.getName();
						if(a)
							;
						else
							if(tempFile.isHidden())
								;
							else
								result.add(tempFile.getName());
						//System.out.println(tempFileName);
					}
					if(r)
						Collections.sort(result, Collections.reverseOrder());
					else
					Collections.sort(result);
					for(String str : result) {
						System.out.println(str);
					}
					check++;
				}
				
				
			}//parseOption 

			
			//System.out.println("---->path:"+path);
			
			if(check==0) {
				List <String> result = new ArrayList<String>();
				File dirFile=new File(path);
				File []fileList=dirFile.listFiles();
				for(File tempFile : fileList) {
					String tempFileName=tempFile.getName();
					if(tempFile.isHidden())
						;
					else
						result.add(tempFile.getName());
					//System.out.println(tempFileName);
				}
				if(r)
					Collections.sort(result, Collections.reverseOrder());
				else
				Collections.sort(result);
				for(String str : result) {
					System.out.println(str);
				}
			}
			
		}//ls 확인    
		else {
			System.out.println("This is command [LS] program. Please enter ls at first.");
			for(String arg : args) {
				int check=1;
				if(arg.contains("h")) {
					check=0;
				}
				if (check==0)
					printHelp(options);
			}
		}
	}
	
	
	
	

	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);
			m = cmd.hasOption("m");
			p = cmd.hasOption("p");
			c1 = cmd.hasOption("1");
			t = cmd.hasOption("t");
			a = cmd.hasOption("a");
			r = cmd.hasOption("r");
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
		
		options.addOption(Option.builder("p")
				.desc("mark '/' on the back of the folder")
				//.hasArg()
				.argName("ls -p")
				//.required()
				.build());
		
		options.addOption(Option.builder("r").longOpt("reverse")
				.desc("list upside down")
				//.hasArg()
				.argName("ls -r")
				//.required()
				.build());
		
		options.addOption(Option.builder("m").longOpt("commas")
				.desc("Prints horizontally listed list separated by commas")
				//.hasArg()
				.argName("ls -m")
				//.required()
				.build());
		
		options.addOption(Option.builder("1").longOpt("single-column")
				.desc("List one file per line.")
				//.hasArg()
				.argName("ls -1")
				//.required()
				.build());
		
		options.addOption(Option.builder("t").longOpt("time")
				.desc("List recently created files first.")
				//.hasArg()
				.argName("ls -t")
				//.required()
				.build());
		options.addOption(Option.builder("a").longOpt("all")
				.desc("Prints out all files")
				//.hasArg()
				.argName("ls -a")
				//.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				//.hasArg()
				.argName("Help")
				//.required()
				.build());
		return options;
	}//createOptions
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "LS";
		String footer ="";
		formatter.printHelp("LS", header, options, footer, true);
	}
	
}//class
