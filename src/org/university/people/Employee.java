package org.university.people;

public abstract class Employee extends Person {
	
//	public Employee() {
//		super();
//	}
//	
//	public Employee(String name) {
//		super(name);
//	}
	
	public abstract double earns();
	
	public abstract void raise(int percent);


}
