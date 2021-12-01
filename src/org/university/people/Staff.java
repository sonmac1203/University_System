package org.university.people;

import org.university.hardware.Department;
import org.university.software.CampusCourse;
import org.university.software.OnlineCourse;

import java.io.Serializable;

public class Staff extends Employee {
	
	private Department department;
	private double payRate;
	private int monthlyHours;
	private int tuitionFee;
	
	
	public Staff() {
		setDepartment(null);
		setPayRate(0);
		setMonthlyHours(0);
		tuitionFee = 0;
	}
	
	// getters and setter
	public int getTuitionFee() {
		if (onlineCourseList.size() > 0) {
			if (onlineCourseList.get(0).getCreditUnits() == 3) {
				setTuitionFee(2000);
			}
			else {
				setTuitionFee(3000);
			}
		}
		else {
			setTuitionFee(campusCourseList.get(0).getCreditUnits() * 300);
		}
		return tuitionFee;
	}
	
	public void setTuitionFee(int tuitionFee) {
		this.tuitionFee = tuitionFee;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public double getPayRate() {
		return payRate;
	}

	public void setPayRate(double payRate) {
		this.payRate = payRate;
	}

	public int getMonthlyHours() {
		return monthlyHours;
	}

	public void setMonthlyHours(int monthlyHours) {
		this.monthlyHours = monthlyHours;
	}

	@Override
	public double earns() {
		return getPayRate() * getMonthlyHours();
		
	}

	@Override
	public void raise(int percent) {
		setPayRate(payRate*(1+ 1.0*percent/100));
	}

	@Override
	public void addCourse(CampusCourse cCourse) {
		if (campusCourseList.size() == 0 && onlineCourseList.size() == 0) {
			campusCourseList.add(cCourse);
			for (int schedule: cCourse.getSchedule()) {
				getSchedule().add(schedule);
			}
			cCourse.addStudentToRoster(this);
		}
		else {
			if (campusCourseList.size() > 0) {
				System.out.println(campusCourseList.get(0).getDepartment().getDepartmentName() + campusCourseList.get(0).getCourseNumber() + 
				           " is removed from " + getName() +
				           "'s schedule (Staff can only take one class at a time). " + 
				           cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() + 
				           " has been added to " + getName() + "'s Schedule.");	
				campusCourseList.remove(0);
			}
			else {
				System.out.println(onlineCourseList.get(0).getDepartment().getDepartmentName() + onlineCourseList.get(0).getCourseNumber() + 
				           " is removed from " + getName() +
				           "'s schedule (Staff can only take one class at a time). " + 
				           cCourse.getDepartment().getDepartmentName() + cCourse.getCourseNumber() + 
				           " has been added to " + getName() + "'s Schedule.");
				onlineCourseList.remove(0);
			}
			campusCourseList.add(cCourse);
			for (int schedule: cCourse.getSchedule()) {
				getSchedule().add(schedule);
			}
			cCourse.addStudentToRoster(this);
		}
	}

	@Override
	public void addCourse(OnlineCourse oCourse) {
		if (campusCourseList.size() == 0 && onlineCourseList.size() == 0) {
			onlineCourseList.add(oCourse);
			oCourse.addStudentToRoster(this);
		}
		else {
			if (campusCourseList.size() > 0) {
				System.out.println(campusCourseList.get(0).getDepartment().getDepartmentName() + campusCourseList.get(0).getCourseNumber() + 
				           " is removed from " + getName() +
				           "'s schedule(Staff can only take one class at a time). " + 
				           oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + 
				           " has been added to " + getName() + "'s Schedule.");	
				campusCourseList.remove(0);
			}
			else {
				System.out.println(onlineCourseList.get(0).getDepartment().getDepartmentName() + onlineCourseList.get(0).getCourseNumber() + 
				           " is removed from " + getName() +
				           "'s schedule(Staff can only take one class at a time). " + 
				           oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + 
				           " has been added to " + getName() + "'s Schedule.");
				onlineCourseList.remove(0);
			}
			onlineCourseList.add(oCourse);
			oCourse.addStudentToRoster(this);
		}
		
	}
}
