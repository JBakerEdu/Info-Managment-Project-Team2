package edu.westga.dsdm.project.viewmodel;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty ;

public class TeacherProfile {

    private StringProperty firstNameProperty;
    private StringProperty lastNameProperty;
    private StringProperty emailProperty;
    private StringProperty passwordProperty;
    private StringProperty confirmPasswordProperty;

    public TeacherProfile() {
        this.firstNameProperty = new SimpleStringProperty("");
        this.lastNameProperty = new SimpleStringProperty("");
        this.emailProperty = new SimpleStringProperty("");
        this.passwordProperty = new SimpleStringProperty("");
        this.confirmPasswordProperty = new SimpleStringProperty("");
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
//        System.out.println(this.firstNameProperty.getValue() + " " + this.lastNameProperty.getValue());
//        System.out.println(this.emailProperty.getValue());
//        System.out.println(this.passwordProperty.getValue());
//        System.out.println(this.confirmPasswordProperty.getValue());
        System.out.println(this.sendTeacherRegistration());
    }

    private String sendTeacherRegistration() {
        String insertTeacher = "INSERT INTO teachers (" +
                this.firstNameProperty.getValue().trim() + "," +
                this.lastNameProperty.getValue().trim() + "," +
                this.emailProperty.getValue().trim() + "," +
                this.passwordProperty.getValue().trim() + ")";

        return insertTeacher;
    }
}
