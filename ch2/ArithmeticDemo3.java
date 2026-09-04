
//Austin Radloff
//p.71 Aug. 31
import java.util.Scanner;

public class ArithmeticDemo3
{
    public static void main(String[] args)
    {
        int firstNumber;
        int secondNumber;
        int sum;
        int difference;
        double average;

        Scanner input = new Scanner(System.in);

        System.out.print("Enter an integer >> ");
        firstNumber = input.nextInt();

        System.out.print("Enter another integer >> ");
        secondNumber = input.nextInt();

        sum = firstNumber + secondNumber;
        difference = firstNumber - secondNumber;

        average = (double) sum / 2;

        System.out.println("The sum is " + sum);
        System.out.println("The difference is " + difference);
        System.out.println("The average is " + average);
    }
}
