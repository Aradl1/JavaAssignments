//Austin Radloff
//p.71 Aug. 31
import java.util.Scanner;

public class ArithmeticDemo2
{
    public static void main(String[] args)
    {
        double firstNumber;
        double secondNumber;
        double sum;
        double difference;
        double average;

        Scanner input = new Scanner(System.in);

        System.out.print("Enter a double value >> ");
        firstNumber = input.nextDouble();

        System.out.print("Enter another double value >> ");
        secondNumber = input.nextDouble();

        sum = firstNumber + secondNumber;
        difference = firstNumber - secondNumber;
        average = (firstNumber + secondNumber) / 2;

        System.out.println("The sum is " + sum);
        System.out.println("The difference is " + difference);
        System.out.println("The average is " + average);
    }
}