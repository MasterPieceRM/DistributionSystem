package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Secretariat {
    private ArrayList<Student> studenti;
    private ArrayList<Curs<?>> cursuri;
    public Secretariat() {
        this.studenti = new ArrayList<>();
        this.cursuri = new ArrayList<>();
    }
    public boolean isDuplicated(String name) {
        return getStudent(name) != null;
    }
    public Student getStudent(String name) {
        for (Student student : studenti) {
            if (student.getNume().equals(name))
                return student;
        }
        return null;
    }
    public Curs<?> getCourse(String name) {
        for (Curs<?> curs : cursuri) {
            if (curs.getDenumire().equals(name))
                return curs;
        }
        return null;
    }
    public ArrayList<Curs<?>> getAllCourses() {
        return cursuri;
    }
    public String getCourseFromStudent(Student student) {
        for (Curs<?> curs : cursuri) {
            if (curs.getStudenti().contains(student))
                return curs.getDenumire();
        }
        return null;
    }
    public void afiseazaStudent(String name, String outputFile) {
        Student student = getStudent(name);
        if (student != null) {
            try {
                FileWriter myWriter = new FileWriter(outputFile, true);
                myWriter.write("***" + System.lineSeparator());
                myWriter.write(student.posteazaStudent(getCourseFromStudent(student))
                        + System.lineSeparator());
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
    public void adaugaStudent(Student student) {
        this.studenti.add(student);
    }
    public void citesteMediileDinDirector(String director) {
        File folder = new File(director);
        File[] listaFisiere = folder.listFiles();

        if (listaFisiere != null) {
            for (File fisier : listaFisiere) {
                if (fisier.isFile() && fisier.getName().startsWith("note_")) {
                    citesteMediileDinFisier(fisier);
                }
            }
        }
    }
    private void citesteMediileDinFisier(File fisier) {
        try (Scanner scanner = new Scanner(fisier)) {
            while (scanner.hasNextLine()) {
                String linie = scanner.nextLine();
                String[] informatiiStudent = linie.split(" - ");
                if (informatiiStudent.length == 2) {
                    String nume = informatiiStudent[0].trim();
                    double medieGenerala = Double.parseDouble(informatiiStudent[1].trim());
                    Student student = getStudent(nume);
                    student.setMedie(medieGenerala);
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }
    public void contestatie(String name, Double medie) {
        Student student = getStudent(name);
        if (student != null) {
            student.setMedie(medie);
        }
    }
    public void posteazaMediile(String outputFile) {
        studenti.sort((stud1, stud2) -> {
            int gradeComparison = stud2.getMedie().compareTo(stud1.getMedie());
            if (gradeComparison == 0) {
                return stud1.getNume().compareTo(stud2.getNume());
            }
            return gradeComparison;
        });
        try {
            FileWriter myWriter = new FileWriter(outputFile, true);
            myWriter.write("***" + System.lineSeparator());
            for (Student student : studenti)
                myWriter.write(student.getNume() + " - " +
                        student.getMedie() + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void adaugaPreferinte(String[] command) {
        Student student = getStudent(command[1]);
        if (student != null)
            for (int i = 2; i < command.length; i++)
                student.getPreferences().add(command[i]);
    }
    public void posteazaCurs(String denumire, String outputFile) {
        Curs<?> curs = getCourse(denumire);
        try {
            FileWriter myWriter = new FileWriter(outputFile, true);
            myWriter.write("***" + System.lineSeparator());
            myWriter.write(curs.getDenumire() + " (" + curs.getCapacitate() + ")" + System.lineSeparator());
            curs.getStudenti().sort(Comparator.comparing(Student::getNume));
            for (Student student : curs.getStudenti())
                myWriter.write(student.getNume() + " - " +
                        student.getMedie() + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void repartizeazaStudenti() {
        studenti.sort((stud1, stud2) -> {
            int gradeComparison = stud2.getMedie().compareTo(stud1.getMedie());
            if (gradeComparison == 0) {
                return stud1.getNume().compareTo(stud2.getNume());
            }
            return gradeComparison;
        });
        for (Student student : studenti) {
            for (int i = 0; i < student.getPreferences().size(); i++) {
                Curs<?> curs = getCourse(student.getPreferences().get(i));
                if (((student.getMedie().equals(curs.getMinGrade()) && curs.isFull()) ||
                        !curs.isFull()) && !student.isAssigned()) {
                    if (student instanceof StudentMaster)
                        ((Curs<StudentMaster>) curs).adaugaStudent((StudentMaster) student);
                    else
                        ((Curs<StudentLicenta>) curs).adaugaStudent((StudentLicenta) student);
                    student.assign();
                    curs.setMinGrade(student.getMedie());
                }
            }
        }
    }
}
