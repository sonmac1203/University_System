package org.university.software;

import org.university.people.Student;

import java.io.PrintStream;
import java.io.Serializable;

public class OnlineCourse extends Course {
	
	public OnlineCourse() {
		
	}

	// Determine if the online course is available to the student
	@Override
	public boolean availableTo(Student aStudent) {
		return availableToGUI(aStudent, System.out);
	}
	
	public boolean availableToGUI(Student aStudent, PrintStream s) {
		if (aStudent.getCampusCredits() < 6) {
			s.println("Student " + aStudent.getName() + " has only " + aStudent.getCampusCredits() + 
					   " on campus credits enrolled. Should have at least 6 credits registered before registering online courses.");
		}
		return aStudent.getCampusCredits() >= 6;
	}
}
