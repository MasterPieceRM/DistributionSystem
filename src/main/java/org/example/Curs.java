package org.example;
import java.util.ArrayList;
public class Curs<T extends Student> {
    private String denumire;
    private int capacitate;
    private boolean full;
    private Double minGrade;
    private ArrayList<T> studenti;
    public Curs(String denumire, int capacitate) {
        this.denumire = denumire;
        this.capacitate = capacitate;
        this.minGrade = -1.0;
        this.full = false;
        this.studenti = new ArrayList<>();
    }
    public String getDenumire() {
        return denumire;
    }
    public int getCapacitate() {
        return capacitate;
    }
    public Double getMinGrade() { return minGrade; }
    public boolean isFull() {
        return studenti.size() == capacitate;
    }
    public ArrayList<T> getStudenti() {
        return studenti;
    }
    public void setMinGrade(Double grade) {
        this.minGrade = grade;
    }
    public void adaugaStudent(T student) {
        studenti.add(student);
    }
}
