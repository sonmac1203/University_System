package org.university.hardware;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.university.software.CampusCourse;
import org.university.software.OnlineCourse;
import org.university.people.Professor;
import org.university.people.Staff;
import org.university.people.Student;

public class Department implements Serializable {
	private String name;
	private ArrayList<Student> studentList;
	private ArrayList<Professor> professorList;
	private ArrayList<Staff> staffList;
	private ArrayList<CampusCourse> campusCourseList;
	private ArrayList<OnlineCourse> onlineCourseList;

	
	public Department() {
		name = "unknown";
		professorList = new ArrayList<Professor>();
		studentList = new ArrayList<Student>();
		
		staffList = new ArrayList<Staff>();
		campusCourseList = new ArrayList<CampusCourse>();
		onlineCourseList = new ArrayList<OnlineCourse>();
		
	}
	
	// getters and setters
	public String getDepartmentName() {
		return this.name;
	}
	
	public void setDepartmentName(String name) {
		this.name = name;
	}
	
	public ArrayList<CampusCourse> getCampusCourseList() {
		return campusCourseList;
	}
	
	public ArrayList<OnlineCourse> getOnlineCourseList(){
		return onlineCourseList;
	}
	
	public ArrayList<Student> getStudentList() {
		return studentList;
	}
	
	public ArrayList<Professor> getProfessorList() {
		return professorList;
	}
	
	public ArrayList<Staff> getStaffList() {
		return staffList;
	}
	
	// Add a new student to the Department
	public void addStudent(Student student) {
		studentList.add(student);
		student.setDepartment(this);
	}
	
	// Add a new campus course to the Department
	public void addCourse(CampusCourse cCourse) {
		campusCourseList.add(cCourse);
		cCourse.setDepartment(this);
	}
	
	// Add a new online course to the Department
	public void addCourse(OnlineCourse oCourse) {
		onlineCourseList.add(oCourse);
		oCourse.setDepartment(this);
	}
	
	// Add a new professor to the Department
	public void addProfessor(Professor professor) {
		professorList.add(professor);
		
	}
	
	// Add a new staff to the Department
	public void addStaff(Staff staff) {
		staffList.add(staff);
	}
	
	// Print the list of students that belong to the Department
	public void printStudentList() {
		for (Student student: studentList) {
			System.out.println(student.getName());
		}
	}
	
	// Print the list of professors that belong to the Department
	public void printProfessorList() {
		printProfessorListGUI(System.out);
	}
	
	// GUI version
	public void printProfessorListGUI(PrintStream s) {
		for (Professor professor: professorList) {
			s.println(professor.getName());
		}
	}
	
	// Print the list of courses that belong to the Department
	public void printCourseList() {
		printCourseListGUI(System.out);
	}
	
	//GUI version
	public void printCourseListGUI(PrintStream s) {
		if (campusCourseList != null) {
			for (CampusCourse cCourse: campusCourseList) {
				s.println(cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() + " " + cCourse.getName());
			}
		}
		if (onlineCourseList != null) {
			for (OnlineCourse oCourse: onlineCourseList) {
				s.println(oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + " " + oCourse.getName());
			}
		}
	}
	
	// Print the list of staffs that belong to the Department
	public void printStaffList() {
		for (Staff staff: staffList) {
			System.out.println(staff.getName());
		}
	}
}
