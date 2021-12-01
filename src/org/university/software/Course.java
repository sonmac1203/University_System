package org.university.software;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.university.hardware.Department;
import org.university.people.Person;
import org.university.people.Professor;
import org.university.people.Staff;
import org.university.people.Student;

public abstract class Course implements Serializable {
	private String name;
	private Department department;
	private int number;
	private ArrayList<Person> studentRoster;
	private Professor professor;
	private int creditUnits;

	private Course conflictingCourse;
	private int conflictingSlot;
	

	
	private String[] days;
	private String[] slots;
	
	public Course() {
		name = "unknown";
		department = null;
		number = 0;
		studentRoster = new ArrayList<Person>();
		professor = null;
		creditUnits = 0;
		
		conflictingCourse = null;
		conflictingSlot = 0;
		
		days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri"};
		slots = new String[] {"8:00am to 9:15am",
							 "9:30am to 10:45am",
							 "11:00am to 12:15pm",
							 "12:30pm to 1:45pm",
							 "2:00pm to 3:15pm",
							 "3:30pm to 4:45pm"};
		
	}
	
	
	// getters and setters
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public int getCourseNumber() {
		return this.number;
	}
	
	public void setCourseNumber(int number) {
		this.number = number;
	}
	
	public Department getDepartment() {
		return this.department;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public ArrayList<Person> getStudentRoster(){
		return studentRoster;
	}
	
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
	public int getCreditUnits() {
		return creditUnits;
	}


	public void setCreditUnits(int creditUnits) {
		this.creditUnits = creditUnits;
	}
	
	public Course getConflictingCourse() {
		return conflictingCourse;
	}

	public void setConflictingCourse(Course conflictingCourse) {
		this.conflictingCourse = conflictingCourse;
	}


	public int getConflictingSlot() {
		return conflictingSlot;
	}

	public void setConflictingSlot(int conflictingSlot) {
		this.conflictingSlot = conflictingSlot;
	}
	
	// Abstract methods
	public abstract boolean availableTo (Student aStudent);
	
	// Add a student to the course'roster
	public void addStudentToRoster(Student student) {
		studentRoster.add(student);	
	}
	
	// Add a staff to the course's roster
	public void addStudentToRoster(Staff staff) {
		studentRoster.add(staff);
	}

	// Translate the time code to detailed time slot
	public String getTime(int timeCode) {
		return days[timeCode / 100 - 1] + " " + slots[timeCode % 10 - 1];
	}
	
	// Print course's roster
	public void printCourseRoster() {
		printCourseRosterGUI(System.out);
	}
	
	// GUI version
	public void printCourseRosterGUI(PrintStream s) {
		for (Person per: studentRoster) {
			s.println(per.getName());
		}
	}	

}
