//Austin Radloff
//p.71 Aug. 31
import java.util.Scanner;
public class ArithmeticDemo 
{
    public static void main(String[] args) 
    {
        double firstNumber;
        double secondNumber;
        double sum;
        double difference;
        double average;
        Scanner input = new Scanner(System.in);
        System.out.print( "please enter a double >> ");
        firstNumber = input.nextDouble();
        System.out.print( "Please enter another double >>");
        secondNumber = input.nextDouble();
        sum = firstNumber + secondNumber;
        difference = firstNumber - secondNumber;
        average = (double) (sum / 2);
        System.out.println(firstNumber + " + " +
        secondNumber + " is " + sum);
        System.out.println(firstNumber + " - " +
        secondNumber + " is " + difference);
        System.out.println("The average of " + firstNumber + " and " + secondNumber + " is " + average);
    }
}
