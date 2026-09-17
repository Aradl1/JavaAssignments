
//Austin Radloff
//p.232
import java.util.Scanner;

public class EvenEntryLoop {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int number;

        System.out.print("Enter an even number or 999 to stop: ");
        number = input.nextInt();

        while (number != 999) {
            if (number % 2 == 0) {
                System.out.println("Good job!");
            } else {
                System.out.println("Error: You entered an odd number.");
            }

            System.out.print("Enter an even number or 999 to stop: ");
            number = input.nextInt();
        }
    }
}