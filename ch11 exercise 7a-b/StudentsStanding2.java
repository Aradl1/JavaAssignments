//Austin Radloff
//p.483

import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class StudentsStanding2 {
    public static void main(String[] args) {
        displayStudents("GoodStanding.txt",
                "STUDENTS IN GOOD STANDING");

        System.out.println();

        displayStudents("AcademicProbation.txt",
                "STUDENTS ON ACADEMIC PROBATION");
    }

    public static void displayStudents(String fileName,
            String heading) {

        System.out.println("==================================");
        System.out.println(heading);
        System.out.println("==================================");

        try {
            Scanner file = new Scanner(new File(fileName));

            while (file.hasNextLine()) {
                String record = file.nextLine();
                String[] data = record.split(",");

                if (data.length != 4) {
                    continue;
                }

                String id = data[0];
                String firstName = data[1];
                String lastName = data[2];
                double gpa = Double.parseDouble(data[3]);

                double difference = Math.abs(gpa - 2.0);

                System.out.println("Student ID: " + id);
                System.out.println("First Name: " + firstName);
                System.out.println("Last Name: " + lastName);
                System.out.printf("GPA: %.2f%n", gpa);

                if (gpa > 2.0) {
                    System.out.printf("Above cutoff by: %.2f%n",
                            difference);
                } else if (gpa < 2.0) {
                    System.out.printf("Below cutoff by: %.2f%n",
                            difference);
                } else {
                    System.out.println("Exactly at the 2.0 cutoff.");
                }

                System.out.println("----------------------------------");
            }

            file.close();

        } catch (IOException e) {
            System.out.println("Could not read " + fileName);
        } catch (NumberFormatException e) {
            System.out.println("Invalid GPA found in file.");
        }
    }
}