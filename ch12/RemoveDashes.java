//Austin Radloff
//p. 496

import java.util.Scanner;

public class RemoveDashes {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int x;
        String phone;

        System.out.print("Enter phone number >> ");
        phone = input.nextLine();

        removeDashes(phone);

        System.out.println();

        input.close();
    }

    public static void removeDashes(String s) {
        int dashLoc = s.indexOf('-');

        if (dashLoc == -1) {
            System.out.print(s);
        } else {
            removeDashes(s.substring(0, dashLoc));
            removeDashes(s.substring(dashLoc + 1));
        }
    }
}