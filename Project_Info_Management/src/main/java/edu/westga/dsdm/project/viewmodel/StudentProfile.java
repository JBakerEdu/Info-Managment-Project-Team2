package edu.westga.dsdm.project.viewmodel;

import javafx.beans.property.StringProperty;

public class StudentProfile {

	private StringProperty firstNameProperty;
	private StringProperty lastNameProperty;
	private StringProperty emailProperty;
	private StringProperty passwordProperty;
	private StringProperty confirmPasswordProperty;
	
	public StudentProfile() {
		
	}
	
	public StringProperty firstNameProperty() {
		return this.firstNameProperty;
	}
	
	public StringProperty lastNameProperty() {
		return this.lastNameProperty;
	}
	
	public StringProperty emailProperty() {
		return this.emailProperty;
	}
	
	public StringProperty passwordProperty() {
		return this.passwordProperty;
	}
	
	public StringProperty confirmPasswordProperty() {
		return this.confirmPasswordProperty;
	}
	
	public void printInformation() {
		System.out.println(this.firstNameProperty.getValue() + " " + this.lastNameProperty.getValue());
		System.out.println(this.emailProperty.getValue());
		System.out.println(this.passwordProperty.getValue());
		System.out.println(this.confirmPasswordProperty.getValue());
	}
}
