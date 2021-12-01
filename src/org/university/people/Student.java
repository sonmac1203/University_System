package org.university.people;

import org.university.software.CampusCourse;
import org.university.software.Course;
import org.university.software.OnlineCourse;
import org.university.hardware.Department;

import java.util.ArrayList;
import java.io.PrintStream;

public class Student extends Person {
	
	private int completedUnits;
	private int requiredCredits;
	private Department department;
	private ArrayList<Course> courseList;

	private int tuitionFee;
	private int campusCredits;
	private int onlineCredits;
		
	public Student() {
		completedUnits = 0;
		requiredCredits = 0;
		department = null;

		courseList = new ArrayList<Course>();
		
		tuitionFee = 0;
		campusCredits = 0;
		onlineCredits = 0;
	}
		
	// getters and setters
	
	public int getCompletedUnits() {
		return completedUnits;
	}

	public void setCompletedUnits(int completedUnits) {
		this.completedUnits = completedUnits;
	}

	public int getRequiredCredits() {
		return requiredCredits;
	}

	public void setRequiredCredits (int requiredCredits) {
		this.requiredCredits = requiredCredits;
	}
	
	public Department getDepartment() {
		return this.department;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public ArrayList<Course> getCourseList(){
		return this.courseList;
	}
	
	public int getCampusCredits() {
		return this.campusCredits;
	}
	
	public void setCampusCredits(int campusCredits) {
		this.campusCredits = campusCredits;
	}
	
	public int getOnlineCredits() {
		return this.onlineCredits;
	}
	
	public void setOnlineCredits(int onlineCredits) {
		this.onlineCredits = onlineCredits;
	}
	
	public void setTuitionFee(int tuitionFee) {
		this.tuitionFee = tuitionFee;
	}
	
	// Calculate the total tuition fee
	public int getTuitionFee() {
		int campusFee = campusCredits * 300;
		int onlineFee = 0;
		if (onlineCourseList != null) {
			for (OnlineCourse oCourse: onlineCourseList) {
				if (oCourse.getCreditUnits() == 3) {
					onlineFee += 2000;
				}
				else {
					onlineFee += 3000;
				}
			}
		}
		tuitionFee = campusFee + onlineFee;
		return tuitionFee;
	}
	
	
	// Calculate remaining credits for degree completion
	public int requiredToGraduate() {
		return getRequiredCredits() - (getCompletedUnits() + getCampusCredits() + getOnlineCredits());
	}
	
	
	// Drop the student from a campus course
	public void dropCourse(CampusCourse aCourse) {
		dropCourseGUI(aCourse, System.out);
	}
	
	public void dropCourseGUI(CampusCourse aCourse, PrintStream s) {
		boolean existed = false;
		for (Course course: this.getCourseList()) {
			 if (aCourse.getName().equals(course.getName())) {
				 existed = true;
			 }
		 }
		 
		 if (existed) {
			 if (this.onlineCourseList.size() >= 1) {
				 if (this.campusCredits - aCourse.getCreditUnits() < 6) {
					 s.println(getName() + " can't drop this CampusCourse, " + 
					 				    "because this student doesn't have enough campus course credit to hold the online course");
					 return;
				 }
			 }
			 s.println("Success you have dropped " + aCourse.getName());
			 courseList.remove(aCourse);
			 campusCourseList.remove(aCourse);
			 aCourse.getStudentRoster().remove(this);
			 campusCredits -= aCourse.getCreditUnits();
			 for (int schedule: aCourse.getSchedule()) {
				 getSchedule().remove(Integer.valueOf(schedule));
			 }
		 }
		 else {
			 s.println("The course " + aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber() + 
					 " could not be dropped because " + this.getName() + 
					 " is not enrolled in " +
					 aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber() + ".");
		 }
	}
	
	// Drop a student from an online course
	public void dropCourse(OnlineCourse aCourse) {
		dropCourseGUI(aCourse, System.out);
	}
	
	public void dropCourseGUI(OnlineCourse aCourse, PrintStream s) {
		boolean existed = false;
		for (Course course: this.getCourseList()) {
			if (aCourse.getName().equals(course.getName())) {
				existed = true;
			}
		}
		if (existed) {
			 s.println("Success you have dropped " + aCourse.getName());
			 courseList.remove(aCourse);
			 onlineCourseList.remove(aCourse);
			 aCourse.getStudentRoster().remove(this);
			 onlineCredits -= aCourse.getCreditUnits();
		}
		else {
			 s.println("The course " + aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber() + 
					 " could not be dropped because " + this.getName() + 
					 " is not enrolled in " +
					 aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber() + ".");
		}
	}

	// Add a student to a campus course
	@Override
	public void addCourse(CampusCourse cCourse) {
		if (cCourse.availableTo(this) == false) {
			System.out.println(getName() + 
					   " can't add Campus Course " + 
					   cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() + " " + cCourse.getName() + 
					   ". Because this Campus course has enough student.");
		}		
		else if (detectConflict(cCourse) == false) {
			campusCourseList.add(cCourse);
			courseList.add(cCourse);
			campusCredits += cCourse.getCreditUnits();
			for (int schedule: cCourse.getSchedule()) {
				getSchedule().add(schedule);
			}
			cCourse.addStudentToRoster(this);
		}
	}

	// Add a student to an online course
	@Override
	public void addCourse(OnlineCourse oCourse) {
		// TODO Auto-generated method stub
		if (oCourse.availableTo(this) == false) {
			System.out.println(getName() + " can't add online Course " + 
							   oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + " " + oCourse.getName() + 
							   ". Because this student doesn't have enough Campus course credit.");
		}
		else {
			onlineCourseList.add(oCourse);
			courseList.add(oCourse);
			onlineCredits += oCourse.getCreditUnits();
			oCourse.addStudentToRoster(this);
		}
	}
}
