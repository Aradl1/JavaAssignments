
//Austin Radloff
//p 156
import java.util.Scanner;

public class TestSandwich {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Sandwich sandwich = new Sandwich();

        System.out.print("Enter main ingredient: ");
        sandwich.setMainIngredient(input.nextLine());

        System.out.print("Enter bread type: ");
        sandwich.setBreadType(input.nextLine());

        System.out.print("Enter price: ");
        sandwich.setPrice(input.nextDouble());

        System.out.println();
        System.out.println("Sandwich Details");
        System.out.println("Main ingredient: " + sandwich.getMainIngredient());
        System.out.println("Bread type: " + sandwich.getBreadType());
        System.out.println("Price: $" + sandwich.getPrice());

        input.close();
    }
}