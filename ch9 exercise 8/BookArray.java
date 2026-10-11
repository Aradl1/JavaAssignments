//Austin Radloff
//p.387

import java.util.Scanner;

public class BookArray {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Book[] books = new Book[10];

        for (int x = 0; x < books.length; x++) {
            System.out.print("Enter book title: ");
            String title = input.nextLine();

            System.out.print("Enter F for Fiction or N for NonFiction: ");
            String type = input.nextLine();

            while (!type.equalsIgnoreCase("F") &&
                    !type.equalsIgnoreCase("N")) {
                System.out.print("Invalid choice. Enter F or N: ");
                type = input.nextLine();
            }

            if (type.equalsIgnoreCase("F")) {
                books[x] = new Fiction(title);
            } else {
                books[x] = new NonFiction(title);
            }
        }

        System.out.println();

        for (int x = 0; x < books.length; x++) {
            System.out.println("Book " + (x + 1));
            System.out.println("Title: " + books[x].getTitle());
            System.out.println("Price: $" + books[x].getPrice());
            System.out.println();
        }

        input.close();
    }
}