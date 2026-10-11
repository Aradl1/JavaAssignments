//Austin Radloff
//p.387

import java.util.Scanner;

public class UseBook {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the title of the fiction book: ");
        String fictionTitle = input.nextLine();

        Fiction fictionBook = new Fiction(fictionTitle);

        System.out.print("Enter the title of the nonfiction book: ");
        String nonFictionTitle = input.nextLine();

        NonFiction nonFictionBook = new NonFiction(nonFictionTitle);

        System.out.println();
        System.out.println("Fiction Book");
        System.out.println("Title: " + fictionBook.getTitle());
        System.out.println("Price: $" + fictionBook.getPrice());

        System.out.println();

        System.out.println("NonFiction Book");
        System.out.println("Title: " + nonFictionBook.getTitle());
        System.out.println("Price: $" + nonFictionBook.getPrice());

        input.close();
    }
}