
//Austin Radloff
//p.81 Ex8a Sept. 2
import java.util.Scanner;

public class ChiliToGo {
    public static void main(String[] args) {
        int noAdultMeals;
        int noChildMeals;
        double priceAdult = 7.0;
        double priceChild = 4.0;
        double totalChild = 0.0;
        double totalAdult = 0.0;
        double totalAll = 0.0;
        Scanner input = new Scanner(System.in);
        System.out.print("please enter the number of children's meals >> ");
        noChildMeals = input.nextInt();
        System.out.print("Please enter the number of adult's meals >>");
        noAdultMeals = input.nextInt();
        totalChild = priceChild * noChildMeals;
        totalAdult = priceAdult * noAdultMeals;
        totalAll = totalChild + totalAdult;
        System.out.printf("The total amount of money collected for children's meals is $%.2f\n", totalChild);
        System.out.printf("The total amount of money collected for adult's meals is $%.2f\n", totalAdult);
        System.out.printf("The total amount of money collected for all meals is $%.2f\n", totalAll);
        input.close();
    }
}
