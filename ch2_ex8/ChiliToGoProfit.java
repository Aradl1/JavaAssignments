
//Austin Radloff
//p.81 Ex8b Sept. 2
import java.util.Scanner;

public class ChiliToGoProfit {
    public static void main(String[] args) {
        int noAdultMeals;
        int noChildMeals;
        double priceAdult = 7.0;
        double priceChild = 4.0;
        double costAdult = 4.35;
        double costChild = 3.10;
        double totalChild = 0.0;
        double totalAdult = 0.0;
        double totalProfitAdult = 0.0;
        double totalProfitChild = 0.0;
        double totalProfitAll = 0.0;
        double totalCostChild = 0.0;
        double totalCostAdult = 0.0;
        Scanner input = new Scanner(System.in);
        System.out.print("please enter the number of children's meals >> ");
        noChildMeals = input.nextInt();
        System.out.print("Please enter the number of adult's meals >>");
        noAdultMeals = input.nextInt();
        totalChild = priceChild * noChildMeals;
        totalAdult = priceAdult * noAdultMeals;
        totalCostChild = noChildMeals * costChild;
        totalCostAdult = noAdultMeals * costAdult;
        totalProfitChild = totalChild - totalCostChild;
        totalProfitAdult = totalAdult - totalCostAdult;
        totalProfitAll = totalProfitChild + totalProfitAdult;
        System.out.printf("The total profit made for children's meals is $%.2f\n", totalProfitChild);
        System.out.printf("The total profit made for adult's meals is $%.2f\n", totalProfitAdult);
        System.out.printf("The total profit made for all meals is $%.2f\n", totalProfitAll);
        input.close();
    }
}
