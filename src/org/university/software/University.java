package org.university.software;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.university.hardware.Classroom;
import org.university.hardware.Department;
import org.university.people.Professor;
import org.university.people.Staff;
import org.university.people.Student;


public class University implements Serializable {
	public ArrayList<Department> departmentList;
	public ArrayList<Classroom> classroomList;
	
	public University() {
		departmentList = new ArrayList<Department>();
		classroomList = new ArrayList<Classroom>();
	}
	
	// getters and setters
	public ArrayList<Department> getDepartmentList(){
		return departmentList;
	}
	
	public ArrayList<Classroom> getClassroomList(){
		return classroomList;
	}
	
	// Add a department to the university
	public void addDepartment(Department department) {
		departmentList.add(department);
	}
	
	// Print the list of departments of the university
	public void printDepartmentList() {
		for (Department department: departmentList) {
			System.out.println(department.getDepartmentName());
		}
	}
	
	// GUI version
	public void printDepartmentListGUI(PrintStream stream) {
		for (Department department: departmentList) {
			stream.println(department.getDepartmentName());
		}
	}
	
	// Print the list of classrooms of the university
	public void printClassroomList() {
		for (Classroom classroom: classroomList) {
			System.out.println(classroom.getRoomNumber());
		}
	}
	
	// GUI version
	public void printClassroomListGUI(PrintStream stream) {
		for (Classroom classroom: classroomList) {
			stream.println(classroom.getRoomNumber());
		}
	}
	
	
	// Print the list of students of the university
	public void printStudentList() {
		for (Department department: departmentList) {
			department.printStudentList();
		}
	}
	
	// Print the list of professors of the university
	public void printProfessorList() {
		for (Department department: departmentList) {
			department.printProfessorList();
		}
	}
	
	// Print the list of staffs of the university
	public void printStaffList() {
		for (Department department: departmentList) {
			department.printStaffList();
		}
	}
	
	// Print the list of courses of the university
	public void printCourseList() {
		for (Department department: departmentList) {
			for (CampusCourse campusCourse: department.getCampusCourseList()) {
				System.out.println(campusCourse.getDepartment().getDepartmentName() + 
								   campusCourse.getCourseNumber() + " " + 
								   campusCourse.getName());
			}
		}
		for (Department department: departmentList) {
			for (OnlineCourse onlineCourse: department.getOnlineCourseList()) {
				System.out.println(onlineCourse.getDepartment().getDepartmentName() + 
								   onlineCourse.getCourseNumber() + " " + 
								   onlineCourse.getName());
			}
		}
	}
	
	public void printAll() {
		printAllGUI(System.out);
	}
	
	// printAll used for GUI
	public void printAllGUI(PrintStream stream) {
		
		newLineGUI(stream);
		
		// Department list
		stream.println("Department list:");
		this.printDepartmentListGUI(stream);
		
		newLineGUI(stream);
		
		// Classroom list
		stream.println("Classroom list:");
		this.printClassroomListGUI(stream);
		
		newLineGUI(stream);
		
		// Professor list for 1st, 2nd,..., nth  dept
		for(Department dept: departmentList) {
			stream.println("The professor list for department " + dept.getDepartmentName());
			dept.printProfessorListGUI(stream);
			newLineGUI(stream);
		}
		
		
		// Course list for 1st, 2nd,..., nth dept
		for(Department dept: departmentList) {
			stream.println("The course list for department " + dept.getDepartmentName());
			dept.printCourseListGUI(stream);
			newLineGUI(stream);
		}
		
		
		// Schedule for 1st, 2nd,..., nth classroom
		for (Classroom cr: classroomList) {
			stream.println("The schedule for classroom " + cr.getRoomNumber());
			cr.printScheduleGUI(stream);
			newLineGUI(stream);
		}
		

		// Deparment information
		for (Department dept: departmentList) {
			stream.println("Department " + dept.getDepartmentName()); // Department ...
			newLineGUI(stream);
						
			// Printing professor schedule
			stream.println("Printing professor schedules:");
			newLineGUI(stream);
			
			for (Professor prof: dept.getProfessorList()) {
				stream.println("The schedule for Prof. " + prof.getName() + ":");
				prof.printScheduleGUI(stream);
				newLineGUI(stream);
			}
			
			// Printing student schedule
			stream.println("Printing student schedules:");
			newLineGUI(stream);
			for (Student stu: dept.getStudentList()) {
				stream.println("The schedule for student " + stu.getName() + ":");
				stu.printScheduleGUI(stream);
				newLineGUI(stream);
			}
			
			// Printing staff schedule
			stream.println("Printing staff schedules:");
			newLineGUI(stream);
			if (dept.getStaffList().size() == 0) {
				newLineGUI(stream);
			}
			for (Staff staff: dept.getStaffList()) {
				stream.println("The schedule for employee " + staff.getName() + ":");
				staff.printScheduleGUI(stream);
				newLineGUI(stream);
				stream.println("Staff: "+ staff.getName() + " earns " + staff.earns() + " this month");
				newLineGUI(stream);
			}
			
			// Roster for courses offered by dept 1
			stream.println("The rosters for courses offered by " + dept.getDepartmentName());
			newLineGUI(stream);
			for (Course course: dept.getCampusCourseList()) {
				stream.println("The roster for course " + dept.getDepartmentName() + course.getCourseNumber());
				course.printCourseRosterGUI(stream);
				newLineGUI(stream);
			}
		}
	}
	
	// serialization method
	public static void saveData(University univ) {
		try {
			File file = new File("./root/university.ser");
			FileOutputStream stream = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(stream);
			out.writeObject(univ);
			out.close();
			stream.close();

		} 
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static University loadData() {
		University univ = null;
		try {
			File file = new File("./root/university.ser");
			FileInputStream stream = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(stream);
			univ = (University)in.readObject();
			in.close();
			stream.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return univ;
	}
	
	void newLine() {
		System.out.print("\n");
	}
	
	void newLineGUI(PrintStream stream) {
		stream.print("\n");
	}
	
	
}
