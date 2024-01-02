package org.example;

import java.util.ArrayList;

public abstract class Student {
    private String nume;
    private Double medie;
    private ArrayList<String> preferences;
    private boolean isAssigned;

    public Student() {
    }
    public Student(String nume) {
        this.nume = nume;
        this.medie = 0.0;
        this.isAssigned = false;
        this.preferences = new ArrayList<>();
    }
    public String getNume() {
        return nume;
    }
    public Double getMedie() {
        return medie;
    }
    public void setMedie(Double medie) {
        this.medie = medie;
    }
    public boolean isAssigned() {
        return isAssigned;
    }
    public void assign() {
        isAssigned = true;
    }
    public ArrayList<String> getPreferences() {
        return preferences;
    }
    public abstract String posteazaStudent(String Course);
}
class StudentMaster extends Student {
    public StudentMaster(String nume) {
        super(nume);
    }
    public String posteazaStudent(String Course) {
        return "Student Master: " + this.getNume() + " - " + this.getMedie() +
                " - " + Course;
    }
}
class StudentLicenta extends Student {
    public StudentLicenta(String nume) {
        super(nume);
    }
    public String posteazaStudent(String Course) {
        return "Student Licenta: " + this.getNume() + " - " + this.getMedie() +
                " - " + Course;
    }
}