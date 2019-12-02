package edu.handong.analysis.datamodel;

public class Course {
	private String studentId;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	private String courseName;
	private String courseCredit;
	private int yearTaken;
	private int semesterCourseTaken;
	
	public Course(String line) {
		this.studentId = line.split(",")[0].trim();
		this.yearMonthGraduated = line.split(",")[1].trim();
		this.firstMajor = line.split(",")[2].trim();
		this.secondMajor = line.split(",")[3].trim();
		this.courseCode = line.split(",")[4].trim();
		this.courseName = line.split(",")[5].trim();
		this.courseCredit = line.split(",")[6].trim();
		this.yearTaken = Integer.parseInt(line.split(",")[7].trim());
		this.semesterCourseTaken = Integer.parseInt(line.split(",")[8].trim());
	}
	/* Self-define getter and setter if needed*/
	public int getYearTaken() {
		return yearTaken;
	}
	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public String getCourseName() {
		return courseName;
	}
}
