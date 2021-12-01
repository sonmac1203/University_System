package org.university.hardware;

import java.util.ArrayList;

import java.util.Collections;

import org.university.software.CampusCourse;

import java.io.PrintStream;
import java.io.Serializable;

public class Classroom implements Serializable {
	private String roomNumber;
	private ArrayList<Integer> schedule;
	private ArrayList<CampusCourse> campusCourseList;
	private String[] days;
	private String[] slots;
	
	public Classroom() {
		roomNumber = "unknown";
		campusCourseList = new ArrayList<CampusCourse>();
		schedule = new ArrayList<Integer>();
		
		days = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri"};
		slots = new String[] {"8:00am to 9:15am",
							 "9:30am to 10:45am",
							 "11:00am to 12:15pm",
							 "12:30pm to 1:45pm",
							 "2:00pm to 3:15pm",
							 "3:30pm to 4:45pm"};
	}
	
	// getters and setter
	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public ArrayList<CampusCourse> getCourseList() {
		return campusCourseList;
	}
	
	public ArrayList<Integer> getSchedule() {
		return schedule;
	}
	
	// Translate the time code to detailed time slot
	public String getTime(int timeCode) {
		return days[timeCode / 100 - 1] + " " + slots[timeCode % 10 - 1];
	}

	// Add a course to the classroom
	public void addCourse(CampusCourse course) {
		campusCourseList.add(course);
		for (int schedule: course.getSchedule()) {
			this.schedule.add(schedule);
		}
	}
	
	// Print the schedule of the classroom
	public void printSchedule() {
		printScheduleGUI(System.out);
	}
	
	//GUI version
	public void printScheduleGUI(PrintStream s) {
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
	}
}
