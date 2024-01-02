package org.example;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String director = "src/main/resources/" + args[0];
        String inputFile = director + "/" + args[0] + ".in";
        String outputFile = director + "/" + args[0] + ".out";
        String[] command;
        Secretariat secretariat = new Secretariat();
        try {
            File myObj = new File(inputFile);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                command = data.split(" - ");
                switch(command[0]) {
                    case "adauga_student":
                        try {
                            if (command[1].equals("master")) {
                                StudentMaster studentMaster = new StudentMaster(command[2]);
                                if (secretariat.isDuplicated(studentMaster.getNume()))
                                    throw new ExceptionDuplicatedStudent(command[2], outputFile);
                                secretariat.adaugaStudent(studentMaster);
                            } else if (command[1].equals("licenta")) {
                                StudentLicenta studentLicenta = new StudentLicenta(command[2]);
                                if (secretariat.isDuplicated(studentLicenta.getNume()))
                                    throw new ExceptionDuplicatedStudent(command[2], outputFile);
                                secretariat.adaugaStudent(studentLicenta);
                            }
                        } catch (ExceptionDuplicatedStudent e) {
                            e.printStackTrace();
                        }
                        break;
                    case "adauga_curs":
                        if (command[1].equals("licenta"))
                            secretariat.getAllCourses().add(new
                                    Curs<StudentLicenta>(command[2],
                                    Integer.parseInt(command[3])));
                        else
                            secretariat.getAllCourses().add(new
                                    Curs<StudentMaster>(command[2],
                                    Integer.parseInt(command[3])));
                        break;
                    case "posteaza_student":
                        secretariat.afiseazaStudent(command[1], outputFile);
                        break;
                    case "citeste_mediile":
                        secretariat.citesteMediileDinDirector(director);
                        break;
                    case "posteaza_mediile":
                        secretariat.posteazaMediile(outputFile);
                        break;
                    case "adauga_preferinte":
                        secretariat.adaugaPreferinte(command);
                        break;
                    case "contestatie":
                        secretariat.contestatie(command[1],
                                Double.parseDouble(command[2]));
                        break;
                    case "posteaza_curs":
                        secretariat.posteazaCurs(command[1], outputFile);
                        break;
                    case "repartizeaza":
                        secretariat.repartizeazaStudenti();
                        break;
                    default:
                        break;
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
class ExceptionDuplicatedStudent extends Exception {
    public ExceptionDuplicatedStudent(String name, String outputFile) {
        super("Student duplicat: " + name);
        try {
            FileWriter myWriter = new FileWriter(outputFile, true);
            myWriter.write("***" + System.lineSeparator());
            myWriter.write("Student duplicat: " + name + System.lineSeparator());
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}