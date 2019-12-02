package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentId;
	private ArrayList<Course> coursesTaken;
	private HashMap<String, Integer> semesterByYearAndSemester;
	
	public Student(String studentId) {
		this.studentId = studentId;
		coursesTaken = new ArrayList<Course>();
		semesterByYearAndSemester = new HashMap<String, Integer>();
	}
	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);
	}
	public HashMap<String, Integer> getSemestersByYearAndSemester(){
		return semesterByYearAndSemester;
	}
	public int getNumCourseInNthSemester(int semester) {
		int num=0;
		int year=0;
		int sem=0;
		for (String key : semesterByYearAndSemester.keySet())
		{
			if(semesterByYearAndSemester.get(key)==semester) {
				year=Integer.parseInt(key.split("-")[0].trim());
				sem=Integer.parseInt(key.split("-")[1].trim());
				break;
			}
		}
		for(Course currentline:coursesTaken) {
			if(currentline.getYearTaken()==year&&currentline.getSemesterCourseTaken()==sem)
				num++;
		}
		return num;
	}
	//add getter and setter for the field if needed
	public String getStudentId() {
		return studentId;
	}
}
