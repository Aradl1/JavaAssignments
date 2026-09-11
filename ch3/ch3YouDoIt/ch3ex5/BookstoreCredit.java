package ch3ex5;

//Austin Radloff
//p. 112
import java.util.Scanner;

public class BookstoreCredit {
    public static void main(String[] args) {
        String name;
        double gpa;
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the name of the student >> ");
        name = keyboard.nextLine();
        System.out.print("What is this student's gpa? >> ");
        gpa = keyboard.nextDouble();
        computeCredit(name, gpa);
        keyboard.close();

    }

    public static void computeCredit(String name, double gpa) {
        double credit;
        credit = gpa * 10;
        System.out.println();
        System.out.println("Hermosa High School Grade Reward");
        System.out.println("This student's name is: " + name);
        System.out.println("This student's GPA is " + gpa);
        System.out.println("Therefore, your bookstore credit for your GPA is $" + credit);
        System.out.println();
    }

}