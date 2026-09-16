//Austin Radloff
//p.216
public class DivideEvenly {
    public static void main(String[] args) {
        final int LIMIT = 100;
        int var;

        System.out.println("The numbers that divide evenly into " + LIMIT + " are:");

        for (var = 1; var <= LIMIT; var++) {
            if (LIMIT % var == 0)
                System.out.print(var + " ");
        }

        System.out.println();
    }
}