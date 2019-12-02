package edu.handong.analysis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysis.utils.Utils;

public class HGUCoursePatternAnalyzer {
	
	String input;
	String output;
	String analysis;
	String coursecode;
	String startyear;
	String endyear;
	boolean help;	

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 * @throws IOException 
	 */
	public void run(String[] args) throws IOException {
		Options options = createOptions();
		if(PreParseOptions(options, args)) {
			if (help){
				printHelp(options);
				return;
			}
			options = createOptions2();	
		}
		if(parseOptions(options, args)){	
			if (help){
				printHelp(options);
				return;
			}
			String dataPath = input; // csv file to be analyzed
			String resultPath = output; // the file path where the results are saved.
			
			if(analysis.equals("1")) {
				ArrayList<String> lines = Utils.getLines(dataPath, true);
				
				students = loadStudentCourseRecords(lines);
				
				// To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
				Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
				
				// Generate result lines to be saved.
				ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);
				
				// Write a file (named like the value of resultPath) with linesTobeSaved.
				Utils.writeAFile(linesToBeSaved, resultPath);
			}
			if(analysis.equals("2")) {
				ArrayList<String> lines = Utils.getLines(dataPath, true);
				students = loadStudentCourseRecords2(lines);
				Map<String, Student> sortedStudents = new TreeMap<String,Student>(students); 
				ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester2(sortedStudents);
				Utils.writeAFile(linesToBeSaved, resultPath);
			}
		}//parseOption 
	}

	private boolean PreParseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();
		try {
		CommandLine cmd = parser.parse(options, args);
		analysis = cmd.getOptionValue("a");
		if(analysis.equals("2"))
			return true;
		else
			return false;
		} catch (Exception e) {
			//System.out.println("---------------Error--------------\n\nWrong CLI arguments. Please read the help and check your CLI argument!\n");
			//System.out.println("-----------------------------------\n\n");
			printHelp(options);
			System.exit(0);
			return false;
		}
	}
	private boolean parseOptions(Options options, String[] args) {
		CommandLineParser parser = new DefaultParser();

		try {
			CommandLine cmd = parser.parse(options, args);
			input = cmd.getOptionValue("i");
			output = cmd.getOptionValue("o");
			analysis = cmd.getOptionValue("a");		
			coursecode = cmd.getOptionValue("c");
			startyear = cmd.getOptionValue("s");
			endyear = cmd.getOptionValue("e");
			help = cmd.hasOption("h");
			

		} catch (Exception e) {
			//System.out.println("---------------Error--------------\n\nWrong CLI arguments. Please read the help and check your CLI argument!\n");
			//System.out.println("-----------------------------------\n\n");
			printHelp(options);
			System.exit(0);
			return false;
		}
		return true;
	}

	// Definition Stage
	
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
		
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());

		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("course code")
				//.required()//only for 'a 2'
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				.hasArg()
				.argName("Help")
				//.required()
				.build());
		return options;
	}
	
	private Options createOptions2() {
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
		
		options.addOption(Option.builder("a").longOpt("analysis")
				.desc("1: Count courses per semester, 2: Count per course name and year")
				.hasArg()
				.argName("Analysis option")
				.required()
				.build());

		options.addOption(Option.builder("c").longOpt("coursecode")
				.desc("Course code for '-a 2' option")
				.hasArg()
				.argName("course code")
				.required()//only for 'a 2'
				.build());
		
		options.addOption(Option.builder("s").longOpt("startyear")
				.desc("Set the start year for analysis e.g., -s 2002")
				.hasArg()
				.argName("Start year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("e").longOpt("endyear")
				.desc("Set the end year for analysis e.g., -e 2005")
				.hasArg()
				.argName("End year for analysis")
				.required()
				.build());
		
		options.addOption(Option.builder("h").longOpt("help")
				.desc("Show a Help page")
				.hasArg()
				.argName("Help")
				//.required()
				.build());
		return options;
	}
	
	private void printHelp(Options options) {
		// automatically generate the help statement
		HelpFormatter formatter = new HelpFormatter();
		String header = "HGU Course Analyzer";
		String footer ="";
		formatter.printHelp("HGUCourseCounter", header, options, footer, true);
	}

	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		
		// TODO: Implement this method
		HashMap<String, Student> map = new HashMap<String, Student>();
		for(String line:lines) {
			String studentId = line.split(",")[0].trim();
			Student newStudent = new Student(studentId);
			map.put(studentId, newStudent);
		}
		for(String line:lines) {
			int num;
			String studentId = line.split(",")[0].trim();
			Course newCourse = new Course(line);
			map.get(studentId).addCourse(newCourse);
			if(newCourse.getYearTaken()>=Integer.parseInt(startyear)&&newCourse.getYearTaken()<=Integer.parseInt(endyear)) {
				num=newCourse.getYearTaken()*10+newCourse.getSemesterCourseTaken();
				map.get(studentId).getSemestersByYearAndSemester().put(String.valueOf(newCourse.getYearTaken())+"-"+String.valueOf(newCourse.getSemesterCourseTaken()),num);
			}
		}
		for (String key : map.keySet()){
			int num=0;
			int j=1;
			while(true) {
				num=10000000;
				for(String key2 : map.get(key).getSemestersByYearAndSemester().keySet()) {
					int value=map.get(key).getSemestersByYearAndSemester().get(key2);
					if(value<num&&value>5000)
						num=value;
				}
				for(String key2 : map.get(key).getSemestersByYearAndSemester().keySet()) {
					int value=map.get(key).getSemestersByYearAndSemester().get(key2);
					if(value==num) {
						map.get(key).getSemestersByYearAndSemester().put(key2, j);
						j++;
					}
				}
				if(num==10000000) {
					break;
				}
			}//while
		}
		return map; // do not forget to return a proper variable.
	}
	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords2(ArrayList<String> lines) {
		
		// TODO: Implement this method
		HashMap<String, Student> map = new HashMap<String, Student>();
		for(String line:lines) {
			String studentId = line.split(",")[0].trim();
			Student newStudent = new Student(studentId);
			map.put(studentId, newStudent);
		}//student id입
		for(String line:lines) {
			int num;
			String studentId = line.split(",")[0].trim();
			Course newCourse = new Course(line);
			map.get(studentId).addCourse(newCourse);
			//start year이랑 end year 적용시
			if(newCourse.getYearTaken()>=Integer.parseInt(startyear)&&newCourse.getYearTaken()<=Integer.parseInt(endyear)) {
				num=newCourse.getYearTaken()*10+newCourse.getSemesterCourseTaken();
				map.get(studentId).getSemestersByYearAndSemester().put(String.valueOf(newCourse.getYearTaken())+"-"+String.valueOf(newCourse.getSemesterCourseTaken())+"-"+newCourse.getCourseCode()+"-"+newCourse.getCourseName(),num);
			}
			}//각 student에 course더하기  
		for (String key : map.keySet()){
			int num=0;
			int j=1;
			while(true) {
				num=10000000;
				for(String key2 : map.get(key).getSemestersByYearAndSemester().keySet()) {
					int value=map.get(key).getSemestersByYearAndSemester().get(key2);
					if(value<num&&value>5000)
						num=value;
				}
				for(String key2 : map.get(key).getSemestersByYearAndSemester().keySet()) {
					int value=map.get(key).getSemestersByYearAndSemester().get(key2);
					if(value==num) {
						map.get(key).getSemestersByYearAndSemester().put(key2, j);
						j++;
					}
				}
				if(num==10000000) {
					break;
				}
			}//while 학기 수 차례대로 붙이기  
		}
		return map; // do not forget to return a proper variable.
	}
	
	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semeter (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		// TODO: Implement this method
		ArrayList<String> result = new ArrayList<String>();
		result.add("StudentID"+", "+"TotalNumberOfSemestersRegistered"+", "+"Semester"+", "+"NumCoursesTakenInTheSemester");
		for (String key : sortedStudents.keySet()){
			int num=0;
			String string1=sortedStudents.get(key).getStudentId();
			//student안의 hashmap
				for(String key2 : sortedStudents.get(key).getSemestersByYearAndSemester().keySet()) {
					int value=sortedStudents.get(key).getSemestersByYearAndSemester().get(key2);
					if(value>num)
						num=value;
				}
				String string2=String.valueOf(num);
			for(int i=1; i<=Integer.parseInt(string2); i++){
				String string3=String.valueOf(i);
				String string4=String.valueOf(sortedStudents.get(key).getNumCourseInNthSemester(i));
				result.add(string1+","+string2+","+string3+","+string4);
				//System.out.println(string1+","+string2+","+string3+","+string4);
			}
		}
		return result; // do not forget to return a proper variable.
	}//-a 1인경우 저장하는 
	
	
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester2(Map<String, Student> sortedStudents) {
		// TODO: Implement this method
		ArrayList<String> result = new ArrayList<String>();
		
		String string4 =null;  
		
		for(int s=Integer.parseInt(startyear); s<=Integer.parseInt(endyear); s++) {
			for(int k=1; k<=4; k++) {
				for (String key : sortedStudents.keySet()){//학생 포문  
					for(String key2 : sortedStudents.get(key).getSemestersByYearAndSemester().keySet()) {
						//int value=sortedStudents.get(key).getSemestersByYearAndSemester().get(key2);
						int year=Integer.parseInt(key2.split("-")[0].trim());
						int sem=Integer.parseInt(key2.split("-")[1].trim());
						String courseCode=key2.split("-")[2].trim();
						String courseName=key2.split("-")[3].trim();
						if(year==s&&sem==k) {
							if(coursecode.equals(courseCode)) {
								string4=courseName;
							}
						}
					}//hash map 포문  
				}//학생 포문
			}
		}
		
		
		
		result.add("Year,Semester,CouseCode, CourseName,TotalStudents,StudentsTaken,Rate");
		for(int i=Integer.parseInt(startyear); i<=Integer.parseInt(endyear); i++) {
			for(int j=1; j<=4; j++) {
				boolean check = false;
				int result5=0;
				int result6=0;
				String string1;
				String string2;
				String string3;
				//String string4;//=null; (그 학기때 들은 학생 수 0일 때 출력안하려면  )  
				String string5;
				String string6;
				String string7;
				for (String key : sortedStudents.keySet()){//학생 포문  
					check=false;
					for(String key2 : sortedStudents.get(key).getSemestersByYearAndSemester().keySet()) {
						//int value=sortedStudents.get(key).getSemestersByYearAndSemester().get(key2);
						int year=Integer.parseInt(key2.split("-")[0].trim());
						int sem=Integer.parseInt(key2.split("-")[1].trim());
						String courseCode=key2.split("-")[2].trim();
						String courseName=key2.split("-")[3].trim();
						if(year==i&&sem==j) {
							check=true;
							if(coursecode.equals(courseCode)) {
								result6++;
								string4=courseName;
							}
						}
					}//hash map 포문  
					if(check)
						result5++;
				}//학생 포문
				//if(string4==null) //(그 학기때 들은 학생 수 0일 때 출력안하려면  )  
				//	continue;
				string1=Integer.toString(i);
				string2=Integer.toString(j);
				string3=coursecode;
				string5=Integer.toString(result5);
				string6=Integer.toString(result6);
				string7=""+Math.round((float)result6/(float)result5*100*10)/10.0+"%";
				result.add(string1+","+string2+","+string3+","+string4+","+string5+","+string6+","+string7);
			}//학기 포문
		}//학년 포문  
		return result; // do not forget to return a proper variable.
	}//-a 2인경우 저장하는 
}