package org.university.people;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import org.university.software.CampusCourse;
import org.university.software.Course;
import org.university.software.OnlineCourse;

public abstract class Person implements Serializable {
	
	private String name;
	private ArrayList<Integer> schedule;
	protected ArrayList<CampusCourse> campusCourseList;
	protected ArrayList<OnlineCourse> onlineCourseList;
	
	protected ArrayList<Integer> conflictingSlot;
	protected Course conflictingCourse;
	
	private String[] days;
	private String[] slots;
	
	public Person() {
		name = "unknown";
		schedule = new ArrayList<Integer>();
		campusCourseList = new ArrayList<CampusCourse>();
		onlineCourseList = new ArrayList<OnlineCourse>();
		
		conflictingSlot = new ArrayList<Integer>();
		conflictingCourse  = null;
		
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
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Course getConflictingCourse() {
		return conflictingCourse;
	}

	public void setConflictingCourse(Course conflictingCourse) {
		this.conflictingCourse = conflictingCourse;
	}

	public ArrayList<Integer> getSchedule() {
		return schedule;
	}

	public void setSchedule(ArrayList<Integer> schedule) {
		this.schedule = schedule;
	}
	
	
	// Abstract methods
	public abstract void addCourse(CampusCourse cCourse);
	
	public abstract void addCourse(OnlineCourse oCourse);
	
	
	// Returns a boolean true if a schedule conflict is detected with the campus course aCourse
	public boolean detectConflict(CampusCourse aCourse) {
		return detectConflictGUI(aCourse, System.out);
	}
	
	public boolean detectConflictGUI(CampusCourse aCourse, PrintStream s) {
		for (int courseSchedule: aCourse.getSchedule()) {
			for (CampusCourse course: campusCourseList) {
				for (int personSchedule: course.getSchedule()) {
					if (courseSchedule == personSchedule) {
						setConflictingCourse(course);
						conflictingSlot.add(personSchedule);
					}
				}
			}
		}
		if (conflictingSlot.size() > 0) {
			for (int slot: conflictingSlot) {
				s.println(aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber() + 
									" course cannot be added to " + 
									this.getName() + "'s Schedule. " +
									aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber() + " conflicts with " +
									getConflictingCourse().getDepartment().getDepartmentName() + getConflictingCourse().getCourseNumber() + 
									". Conflicting time slot is " + 
									getTime(slot) + ".");
			}
			conflictingSlot.clear();
			return true;
		}
		else {
			return false;
		}
	}
	
	
	public void printSchedule() {
		printScheduleGUI(System.out);
	}
	
	// GUI version
	public void printScheduleGUI(PrintStream s) {
		if (getSchedule() != null) {
			Collections.sort(getSchedule());
			for (int schedule: getSchedule()) {
				s.print(getTime(schedule) + " ");
				for (CampusCourse course: campusCourseList) {
					for (int courseSchedule: course.getSchedule()) {
						if (courseSchedule == schedule) {
							s.println(course.getDepartment().getDepartmentName() + course.getCourseNumber() + " " + course.getName());
							break;
						}
					}
				}
			}
			
			if (onlineCourseList != null) {
				for (OnlineCourse oCourse: onlineCourseList) {
					s.println(oCourse.getDepartment().getDepartmentName() + oCourse.getCourseNumber() + " " + oCourse.getName());
				}
			}
		}
		else {
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
	}
	
	// Translate the time code to detailed time slot
	public String getTime(int timeCode) {
		return days[timeCode / 100 - 1] + " " + slots[timeCode % 10 - 1];
	}	
}
