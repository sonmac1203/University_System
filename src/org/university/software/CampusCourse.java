package org.university.software;

import java.util.ArrayList;
import java.util.Collections;

import org.university.hardware.Classroom;
import org.university.people.Student;

import java.io.PrintStream;
import java.io.Serializable;

public class CampusCourse extends Course {
	private int maxCourseLimit;
	private ArrayList<Integer> schedule;
	private Classroom classroom;

	
	public CampusCourse() {
		maxCourseLimit = 0;
		classroom = null;
		schedule = new ArrayList<Integer>();
	}
	
	// getters and setters
	public int getMaxCourseLimit() {
		return maxCourseLimit;
	}

	public void setMaxCourseLimit(int maxCourseLimit) {
		this.maxCourseLimit = maxCourseLimit;
	}

	public ArrayList<Integer> getSchedule() {
		return schedule;
	}

	public void setSchedule(int schedule) {
		this.schedule.add(schedule);	
	}
	
	public void setClassroom(Classroom classroom) {
		this.classroom = classroom;
	}
	
	public Classroom getClassroom() {
		return this.classroom;
	}
	
	// Detect if there is a class room conflict
	public boolean detectConflict(Classroom classroom) {
		if (classroom.getCourseList().size() == 0) {
			return false;
		}
		
		for (CampusCourse course: classroom.getCourseList()) {
			for (int scheduleRegistered: course.getSchedule()) {
				for (int scheduleCourse: this.getSchedule()) {
					if (scheduleRegistered == scheduleCourse) {
						setConflictingCourse(course);
						setConflictingSlot(scheduleRegistered);
						return true;
					}
				}
			}
		}
		return false;
	}

	// Print the schedule of the course
	public void printSchedule() {
		Collections.sort(getSchedule());
		for (int schedule: getSchedule()) {
			System.out.println(getTime(schedule) + " " + getClassroom().getRoomNumber());
		}
	}

	
	// Assign a classroom for the course
	public void setRoomAssigned(Classroom classroom) {
		setRoomAssignedGUI(classroom, System.out);
	}
	
	public void setRoomAssignedGUI(Classroom classroom, PrintStream s) {
		if (detectConflict(classroom) == false) {
			this.classroom = classroom;
			classroom.addCourse(this);
		}
		else {
			s.println(this.getDepartment().getDepartmentName() + this.getCourseNumber() +
					" conflicts with " +
					getConflictingCourse().getDepartment().getDepartmentName() + getConflictingCourse().getCourseNumber() +
					". Conflicting time slot " +
					getTime(getConflictingSlot()) + ". " +
					this.getDepartment().getDepartmentName() + this.getCourseNumber() +
					" course cannot be added to " +
					classroom.getRoomNumber() + 
					"'s Schedule.");
		}
	}
	
	// Determine if the campus course is available to a student
	@Override
	public boolean availableTo(Student aStudent) {
		return getStudentRoster().size() < maxCourseLimit;
	}


}
