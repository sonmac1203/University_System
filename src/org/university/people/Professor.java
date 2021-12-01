package org.university.people;

import org.university.hardware.Department;
import org.university.software.CampusCourse;
import org.university.software.Course;
import org.university.software.OnlineCourse;

import java.io.Serializable;

public class Professor extends Employee {

	private Department department;
	private double salary;
	
	public Professor() {
		setDepartment(null);
		salary = 0.0;		
	}
	
	// getters and setters
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	
	// Detect professor conflict
	public boolean detectProfessorConflict(Course aCourse) {
		if (aCourse.getProfessor() == null) {
			return false;
		}
		if (aCourse.getProfessor().getName().equals(this.getName())) {
			return false;
		}
		return true;
	}
	
	// Add a campus course to the professor's schedule
	@Override
	public void addCourse(CampusCourse cCourse) {
		if (detectProfessorConflict(cCourse)) {
			System.out.println("The professor " + getName() + " cannot be assigned to this campus course because professor " + 
					cCourse.getProfessor().getName() + 
					" is already assigned to the course " + 
					cCourse.getName() + ".");				
		}
		else if (detectConflict(cCourse) == false) {
			campusCourseList.add(cCourse);
			for (int schedule: cCourse.getSchedule()) {
				getSchedule().add(schedule);
			}
			cCourse.setProfessor(this);
		}
		
	}

	// Add an online course to professor's schedule
	@Override
	public void addCourse(OnlineCourse oCourse) {
		if (detectProfessorConflict(oCourse)) {
			System.out.println("The professor cannot be assigned to this online course because professor " + 
					oCourse.getProfessor().getName() + 
					" is already assigned to the online course " + 
					oCourse.getName() + ".");				
		}
		else {
			onlineCourseList.add(oCourse);
			//teachingCourses.add(oCourse);
			oCourse.setProfessor(this);
		}
		
	}

	// Make a raise for the professor
	@Override
	public void raise(int percent) {
		salary = salary*(1 + 1.0*percent/100);
	}

	// Determine the earnings of the professor
	@Override
	public double earns() {
		return getSalary() / 26;
	}
}
