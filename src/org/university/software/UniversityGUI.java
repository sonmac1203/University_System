package org.university.software;

import java.awt.FlowLayout;

import org.university.hardware.Classroom;
import org.university.hardware.Department;
import org.university.people.Student;

import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.*;

public class UniversityGUI extends JFrame {
	
	private JMenuBar menuBar;		//the horizontal container
	private JMenu fileMenu;
	private JMenu studentMenu;
	private JMenu adminMenu;		//JMenu objects are added to JMenuBar objects as the "tabs"
	
	// Items in File
	private JMenuItem fileSave, fileLoad, fileExit;
	
	// Items Student
	private JMenuItem addCourse, dropCourse, printSchedule;
	
	// Items in Administrators
	private JMenuItem printAllInfo, newCampusCourse, newOnlineCourse, assignClassroom;
	
	private University univ;
	
	public UniversityGUI(String windowTitle, University univ) {
		super(windowTitle);
		this.univ = univ;
		
		setSize(300, 100);
		
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(new JLabel("<HTML><center>Welcome to the University." +
				"<BR>Choose an action from the above menus.</center></HTML>"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildGUI();	
		setVisible(true);
	}
	
	public University getUniversity() {
		return this.univ;
	}
	
	public void buildGUI() {
		
		menuBar = new JMenuBar(); // Create menu bar
		
		// Set name for each menu
		fileMenu = new JMenu("File");
		studentMenu = new JMenu("Students");
		adminMenu = new JMenu("Administrators");
		
		// Set name for menu items
		fileSave = new JMenuItem("Save");
		fileLoad = new JMenuItem("Load");
		fileExit = new JMenuItem("Exit");
		
		addCourse = new JMenuItem("Add Course");
		dropCourse = new JMenuItem("Drop Course");
		printSchedule = new JMenuItem("Print Schedule");
		
		printAllInfo = new JMenuItem("Print All Info");
		newCampusCourse = new JMenuItem("New Campus Course");
		newOnlineCourse = new JMenuItem("New Online Course");
		assignClassroom = new JMenuItem("Assign Course Classroom");
		
		fileSave.addActionListener(new MenuListener());	
		fileLoad.addActionListener(new MenuListener());	
		fileExit.addActionListener(new MenuListener());	
		
		addCourse.addActionListener(new MenuListener());	
		dropCourse.addActionListener(new MenuListener());	
		printSchedule.addActionListener(new MenuListener());	
		
		printAllInfo.addActionListener(new MenuListener());	
		newCampusCourse.addActionListener(new MenuListener());	
		newOnlineCourse.addActionListener(new MenuListener());	
		assignClassroom.addActionListener(new MenuListener());	
		
		fileMenu.add(fileSave);
		fileMenu.add(fileLoad);
		fileMenu.add(fileExit);
		
		studentMenu.add(addCourse);
		studentMenu.add(dropCourse);
		studentMenu.add(printSchedule);
		
		adminMenu.add(printAllInfo);
		adminMenu.add(newCampusCourse);
		adminMenu.add(newOnlineCourse);
		adminMenu.add(assignClassroom);
		
			
	    menuBar.add(fileMenu);
	    menuBar.add(studentMenu);
	    menuBar.add(adminMenu);
	
		setJMenuBar(menuBar);	
	}
	
	
	private class MenuListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) //this is the method MenuListener must implement, as it comes from the ActionListener interface.
		{
			JMenuItem source = (JMenuItem)(e.getSource());
			
			if(source.equals(fileSave))
			{
				handleFileSave();	
			}
			else if(source.equals(fileLoad))
			{
				handleFileLoad();
			}
			else if(source.equals(fileExit)) {
				handleFileExit();
			}
			else if(source.equals(addCourse)) {
				handleAddCourse();
			}
			else if(source.equals(dropCourse)) {
				handleDropCourse();
			}
			else if(source.equals(printSchedule)) {
				handlePrintSchedule();
			}
			else if(source.equals(printAllInfo)) {
				handlePrintAllInfo();
			}
			else if(source.equals(newCampusCourse)) {
				handleNewCampusCourse();
			}
			else if(source.equals(newOnlineCourse)) {
				handleNewOnlineCourse();
			}
			else if(source.equals(assignClassroom)) {
				handleAssignClassroom();
			}			
		}


		//-------------------------------------------------------- ADMIN MENU ------------------------------------------------//
		
		private void handleAssignClassroom() {
			JTextField departmentName = new JTextField(25);
			JTextField courseNumber = new JTextField(25);
			JTextField roomNumber = new JTextField(25);
			Object[] message = {
				    "Course Department:", departmentName,
				    "Course #:", courseNumber,
				    "Room #:", roomNumber,
			};
			
			int option = JOptionPane.showConfirmDialog(null, message, "Add Campus Course to Classroom", JOptionPane.OK_CANCEL_OPTION);
			
			if (option == JOptionPane.OK_OPTION) {
				
				// If the room does not exist
				if (!isValidRoom(roomNumber.getText())) {
					JOptionPane.showMessageDialog(null, "Classroom \"" + 
							roomNumber.getText() + 
							"\" isn't a valid classroom", 
							"Error Assigning Campus Course to Classroom", JOptionPane.PLAIN_MESSAGE);
				}
				
				// If the department does not exist
				else if (!isValidDept(departmentName.getText())) {
					JOptionPane.showMessageDialog(null, "Department \"" + 
														departmentName.getText() + 
														"\" isn't a valid department", 
														"Error", JOptionPane.PLAIN_MESSAGE);
				}
				
				// If the campus course does not exist
				else if (getCampusCourse(departmentName.getText(), courseNumber.getText()) == null) {
					JOptionPane.showMessageDialog(null, "Course: " + departmentName.getText() + courseNumber.getText()  + " doesn't exist",
												  		"Error", JOptionPane.PLAIN_MESSAGE);	
					
				}
				else {
					// Retrieve an existing Course / an existing Classroom from the given strings
					CampusCourse cCourse = getCampusCourse(departmentName.getText(), courseNumber.getText());
					Classroom classroom = getClassroom(roomNumber.getText());
					ByteArrayOutputStream bstream = new ByteArrayOutputStream();
					PrintStream myPS = new PrintStream(bstream);
					
					if (cCourse.detectConflict(classroom) == false) {
						cCourse.setRoomAssigned(getClassroom(roomNumber.getText()));
						JOptionPane.showMessageDialog(null, "Success you have assigned " +
															 departmentName.getText() +
															 courseNumber.getText() +
															 " to " +
															 roomNumber.getText(), 
															 "Success", JOptionPane.PLAIN_MESSAGE);
					}
					else {
						cCourse.setRoomAssignedGUI(getClassroom(roomNumber.getText()), myPS);
						String msg = new String(bstream.toByteArray());
						JOptionPane.showMessageDialog(null, msg,
								 "Error Assigning Campus Course to Classroom", JOptionPane.PLAIN_MESSAGE);
						
					}
				}
			}
			
		}

		private void handleNewOnlineCourse() {
			
			JTextField courseName = new JTextField(25);
			JTextField departmentName = new JTextField(25);
			JTextField courseNumber = new JTextField(25);
			JTextField credits = new JTextField(25);
			Object[] message = {
				    "Course Name:", courseName,
				    "Department:", departmentName,
				    "Course #:", courseNumber,
				    "Credits:", credits,
			};
			
			int option = JOptionPane.showConfirmDialog(null, message, "Create New Online Course", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (!isValidDept(departmentName.getText())) {
					JOptionPane.showMessageDialog(null, "Department \"" + 
														departmentName.getText() + 
														"\" isn't a valid department", 
							"Error Creating Online Course", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					// Add this course to the system
					OnlineCourse oCourse = new OnlineCourse();
					oCourse.setName(courseName.getText());
					oCourse.setCourseNumber(Integer.valueOf(courseNumber.getText()));
					oCourse.setCreditUnits(Integer.valueOf(credits.getText()));
					for (Department dept: univ.getDepartmentList()) {
						if (dept.getDepartmentName().equals(departmentName.getText())) {
							dept.addCourse(oCourse);
							break;
						}
					}
				}
			}
			
		}

		private void handleNewCampusCourse() {

			JTextField courseName = new JTextField(25);
			JTextField departmentName = new JTextField(25);
			JTextField courseNumber = new JTextField(25);
			JTextField maxStudents = new JTextField(25);
			JTextField credits = new JTextField(25);
			Object[] message = {
				    "Course Name:", courseName,
				    "Department:", departmentName,
				    "Course #:", courseNumber,
				    "Max Students:", maxStudents,
				    "Credits:", credits,
			};
			
			int option = JOptionPane.showConfirmDialog(null, message, "Create New Campus Course", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				if (!isValidDept(departmentName.getText())) {
					JOptionPane.showMessageDialog(null, "Department \"" + 
														departmentName.getText() + 
														"\" isn't a valid department", 
							"Error Creating Campus Course", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					// Add this course to the system
					CampusCourse cCourse = new CampusCourse();
					cCourse.setName(courseName.getText());
					cCourse.setCourseNumber(Integer.valueOf(courseNumber.getText()));
					cCourse.setCreditUnits(Integer.valueOf(credits.getText()));
					cCourse.setMaxCourseLimit(Integer.valueOf(maxStudents.getText()));
					for (Department dept: univ.getDepartmentList()) {
						if (dept.getDepartmentName().equals(departmentName.getText())) {
							dept.addCourse(cCourse);
							break;
						}
					}
				}
			}
		}

		private void handlePrintAllInfo() {
			ByteArrayOutputStream bstream = new ByteArrayOutputStream();
			PrintStream myPS = new PrintStream(bstream);
			univ.printAllGUI(myPS);
			String msg = new String(bstream.toByteArray());
			
			final JFrame frame = new JFrame("scrollBars");  
			  
	        // Display the window.  
	        frame.setSize(500, 500);  
	        frame.setVisible(true);  
	        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	  
	        // set flow layout for the frame  
	        frame.getContentPane().setLayout(new FlowLayout());  
	  
	        JTextArea textArea = new JTextArea(27, 37);
	        textArea.setText(msg);
	        textArea.setEditable(false);
	        JScrollPane scrollableTextArea = new JScrollPane(textArea);  
	  
	        scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
	        scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
	        scrollableTextArea.setVisible(true);
	        
	  
	        frame.getContentPane().add(scrollableTextArea);   
			
			
		}
		

		//------------------------------------------------------- STUDENT MENU ------------------------------------------------//
		
		// Print a student's schedule
		private void handlePrintSchedule() {
			String studentName;
			studentName = JOptionPane.showInputDialog(null, "Student Name: ", "Print Student Schedule", JOptionPane.QUESTION_MESSAGE);
			if (studentName != null) {
				if (!isValidStudent(studentName)) {
					JOptionPane.showMessageDialog(null, "Student " + 
							studentName + 
							" doesn't exist", 
							"Error", JOptionPane.PLAIN_MESSAGE);
				}
				else {
					Student stu = getStudent(studentName);
					ByteArrayOutputStream bstream = new ByteArrayOutputStream();
					PrintStream myPS = new PrintStream(bstream);
					stu.printScheduleGUI(myPS);
					String msg = new String(bstream.toByteArray());
					JOptionPane.showMessageDialog(null, msg, 
							"Student " + studentName + "'s Schedule", JOptionPane.PLAIN_MESSAGE);
				}
			}
			
		}

		private void handleDropCourse() {
			JTextField studentName = new JTextField(25);
			JTextField departmentName = new JTextField(25);
			JTextField courseNumber = new JTextField(25);
			Object[] message = {
				    "Student Name:", studentName,
				    "Department:", departmentName,
				    "Course #:", courseNumber
			};
			int option = JOptionPane.showConfirmDialog(null, message, "Add Course", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				
				// The student does not exist
				if (!isValidStudent(studentName.getText())) {
					JOptionPane.showMessageDialog(null, "Student " + 
							studentName.getText() + 
							" doesn't exist", 
							"Error", JOptionPane.PLAIN_MESSAGE);
				}
				
				// The department is invalid
				else if (!isValidDept(departmentName.getText())) {
					JOptionPane.showMessageDialog(null, "Department \"" + 
														departmentName.getText() + 
														"\" isn't a valid department", 
							"Error Creating Campus Course", JOptionPane.PLAIN_MESSAGE);
				}
				
				// Student and Department are already valid but the course does not exist
				else if (getCampusCourse(departmentName.getText(), courseNumber.getText()) == null && 
						 getOnlineCourse(departmentName.getText(), courseNumber.getText()) == null) {
					JOptionPane.showMessageDialog(null, "Course: " + departmentName.getText() + courseNumber.getText() + " doesn't exist",
												  		"Error", JOptionPane.PLAIN_MESSAGE);	
					
				}
				
				else {
					Student stu = getStudent(studentName.getText());
					CampusCourse c = getCampusCourse(departmentName.getText(), courseNumber.getText());
					ByteArrayOutputStream bstream = new ByteArrayOutputStream();
					PrintStream myPS = new PrintStream(bstream);
					
					// If the course is a campus course
					if (c != null) {
						stu.dropCourseGUI(c, myPS);
						String msg = new String(bstream.toByteArray());
						JOptionPane.showMessageDialog(null, msg, 
								"Info", JOptionPane.PLAIN_MESSAGE);
					}
					
					else {
						OnlineCourse o = getOnlineCourse(departmentName.getText(), courseNumber.getText());
						stu.dropCourseGUI(o, myPS);
						String msg = new String(bstream.toByteArray());
						JOptionPane.showMessageDialog(null, msg, 
								"Info", JOptionPane.PLAIN_MESSAGE);
						
					}
					
				}
				
			}
			
			
		}

		private void handleAddCourse() {
			JTextField studentName = new JTextField(25);
			JTextField departmentName = new JTextField(25);
			JTextField courseNumber = new JTextField(25);
			Object[] message = {
				    "Student Name:", studentName,
				    "Department:", departmentName,
				    "Course #:", courseNumber
			};
			
			int option = JOptionPane.showConfirmDialog(null, message, "Add Course", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				
				// The student does not exist
				if (!isValidStudent(studentName.getText())) {
					JOptionPane.showMessageDialog(null, "Student " + 
							studentName.getText() + 
							" doesn't exist", 
							"Error", JOptionPane.PLAIN_MESSAGE);
				}
				
				// The department is invalid
				else if (!isValidDept(departmentName.getText())) {
					JOptionPane.showMessageDialog(null, "Department \"" + 
														departmentName.getText() + 
														"\" isn't a valid department", 
														"Error Creating Campus Course", JOptionPane.PLAIN_MESSAGE);
				}
				
				// Student and Department are already valid but the course does not exist
				else if (getCampusCourse(departmentName.getText(), courseNumber.getText()) == null && 
						 getOnlineCourse(departmentName.getText(), courseNumber.getText()) == null) {
					JOptionPane.showMessageDialog(null, "Course: " + departmentName.getText() + courseNumber.getText()  + " doesn't exist",
												  		"Error", JOptionPane.PLAIN_MESSAGE);	
					
				}
				else {
					Student stu = getStudent(studentName.getText());
					CampusCourse c = getCampusCourse(departmentName.getText(), courseNumber.getText());
					ByteArrayOutputStream bstream = new ByteArrayOutputStream();
					PrintStream myPS = new PrintStream(bstream);
					
					// If the course is a campus course
					if (c != null) {
						
						// If there is a course conflict
						if (stu.detectConflictGUI(c, myPS)) {
							String msg = new String(bstream.toByteArray());
							JOptionPane.showMessageDialog(null, msg, 
									"Error", JOptionPane.PLAIN_MESSAGE);
							
						}
						
						// If the course has had enough students
						else if(!c.availableTo(stu)) {
							JOptionPane.showMessageDialog(null, stu.getName() + 
									" can't add Campus Course " + 
									c.getDepartment().getDepartmentName() + c.getCourseNumber() + " " + c.getName() + 
									". Because this Campus course has enough students.",
									"Error", JOptionPane.PLAIN_MESSAGE);	
						}
						
						// If there is no course conflict and the course is available
						else {
							stu.addCourse(c);
							JOptionPane.showMessageDialog(null, "Success you have added " + 
									c.getName(),
									"Success", JOptionPane.PLAIN_MESSAGE);
							
						}	
					}
					
					// The course is an online course
					else {
						OnlineCourse o = getOnlineCourse(departmentName.getText(), courseNumber.getText());
						
						// If the course is not available due to insufficient on-campus credits
						if (!o.availableToGUI(stu, myPS)) {
							String msg = new String(bstream.toByteArray());
							msg += stu.getName() + " can't add online Course " + 
									   o.getDepartment().getDepartmentName() + o.getCourseNumber() + " " + o.getName() + 
									   ". Because this student doesn't have enough Campus course credit.";
							JOptionPane.showMessageDialog(null, msg,
									   "Error", JOptionPane.PLAIN_MESSAGE);	
						}
						else {
							stu.addCourse(o);
							JOptionPane.showMessageDialog(null, "Success you have added " + 
									o.getName(),
									"Success", JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			}
		}

		
		
		//----------------------------------------------- FILE MENU --------------------------------------------------------//
		
		private void handleFileExit() {
			System.exit(0);
			setVisible(false);
			dispose();
		}
		
		private void handleFileLoad() {
			univ = University.loadData();
		}

		private void handleFileSave() {
			University.saveData(univ);
		}
		
		
		
		//----------------------------------------------- HELPER METHODS ----------------------------------------------------//
		
		// Check if a department exists
		public boolean isValidDept(String name) {
			for (Department dept: univ.getDepartmentList()) {
				if (dept.getDepartmentName().equals(name))
					return true;
			}
			return false;
		}
		
		// Check if a classroom exists
		public boolean isValidRoom(String name) {
			for (Classroom cr: univ.getClassroomList()) {
				if (cr.getRoomNumber().equals(name)) 
					return true;
			}
			
			return false;
		}
		
		// Check if a student exists in the university
		public boolean isValidStudent(String name) {
			for (Department dept: univ.getDepartmentList()) { 
				for (Student stu: dept.getStudentList()) {
					if (stu.getName().equals(name)) {
						return true;
					}
				}
			}
			return false;
		}
		
		// Retrieve a classroom from the string entered
		public Classroom getClassroom(String roomNumber) {
			for (Classroom cr: univ.getClassroomList()) {
				if (cr.getRoomNumber().equals(roomNumber))
					return cr;
			}
			return null;
		}
		
		// Retrieve a campus course from the strings entered
		public CampusCourse getCampusCourse(String deptName, String number) {
			for (Department dept: univ.getDepartmentList()) {
				if (dept.getDepartmentName().equals(deptName)) {
					for (CampusCourse c: dept.getCampusCourseList()) {
						if (c.getCourseNumber() == Integer.valueOf(number)) {
							return c;
						}
					}
				}
			}
			return null;
		}
		
		// Retrieve an online course from the strings entered
		public OnlineCourse getOnlineCourse(String deptName, String number) {
			for (Department dept: univ.getDepartmentList()) {
				if (dept.getDepartmentName().equals(deptName)) {
					for (OnlineCourse c: dept.getOnlineCourseList()) {
						if (c.getCourseNumber() == Integer.valueOf(number)) {
							return c;
						}
					}
				}
			}
			return null;			
		}
		
		// Retrieve a student from the string entered
		public Student getStudent(String name) {
			for (Department dept: univ.getDepartmentList()) {
				for (Student stu: dept.getStudentList()) {
					if (stu.getName().equals(name))
						return stu;
				}
			}
			
			return null;
		}
	}
}
