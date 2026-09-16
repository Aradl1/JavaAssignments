//Austin Radloff
//p.222
public class DivideEvenly2 {
    public static void main(String[] args) {
        final int LIMIT = 100;
        int var;
        int number;

        System.out.println("The numbers that divide evenly into each number from 1 through 100 are:");

        for (number = 1; number <= LIMIT; number++) {
            System.out.print(number + ": ");

            for (var = 1; var <= number; var++)
                if (number % var == 0)
                    System.out.print(var + " ");

            System.out.println();
        }
    }
}