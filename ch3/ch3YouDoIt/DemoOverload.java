//Austin Radloff
//p.106
public class DemoOverload {
    public static void main(String[] args) {
        int month = 6;
        int day = 24;
        int year = 2026;

        displayDate(month);
        displayDate(month, day);
        displayDate(month, day, year);
    }

    public static void displayDate(int month) {
        int day = 1;
        int year = 2026;

        System.out.println(month + "/" + day + "/" + year);
    }

    public static void displayDate(int month, int day) {
        int year = 2026;

        System.out.println(month + "/" + day + "/" + year);
    }

    public static void displayDate(int month, int day, int year) {
        System.out.println(month + "/" + day + "/" + year);
    }
}