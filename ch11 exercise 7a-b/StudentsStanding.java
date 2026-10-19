
//Austin Radloff
//p.483
import java.util.Scanner;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class StudentsStanding {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        try {
            PrintWriter goodStanding = new PrintWriter(
                    new FileWriter("GoodStanding.txt"));
            PrintWriter probation = new PrintWriter(
                    new FileWriter("AcademicProbation.txt"));

            while (true) {
                System.out.print("Enter student ID (or Q to quit): ");
                String id = input.nextLine();

                if (id.equalsIgnoreCase("Q")) {
                    break;
                }

                System.out.print("Enter first name: ");
                String firstName = input.nextLine();

                System.out.print("Enter last name: ");
                String lastName = input.nextLine();

                double gpa;

                while (true) {
                    System.out.print("Enter GPA: ");

                    try {
                        gpa = Double.parseDouble(input.nextLine());

                        if (gpa >= 0.0 && gpa <= 4.0) {
                            break;
                        }

                        System.out.println("GPA must be between 0.0 and 4.0.");
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid GPA.");
                    }
                }

                String record = id + "," + firstName + ","
                        + lastName + "," + gpa;

                if (gpa >= 2.0) {
                    goodStanding.println(record);
                    System.out.println("Student added to good standing.");
                } else {
                    probation.println(record);
                    System.out.println("Student added to academic probation.");
                }

                System.out.println();
            }

            goodStanding.close();
            probation.close();

            System.out.println("Student records have been saved.");

        } catch (IOException e) {
            System.out.println("Error writing student files.");
        }

        input.close();
    }
}