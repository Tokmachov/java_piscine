public class Program {
    public static void main(String[] av) {
        int num = 479598;
        int sumOfNumDigits = 0;
        sumOfNumDigits += num % 10;
        num /= 10;
        sumOfNumDigits += num % 10;
        num /= 10;
        sumOfNumDigits += num % 10;
        num /= 10;
        sumOfNumDigits += num % 10;
        num /= 10;
        sumOfNumDigits += num % 10;
        num /= 10;
        sumOfNumDigits += num % 10;
        System.out.println(sumOfNumDigits);
    }
}