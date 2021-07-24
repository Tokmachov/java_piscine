import java.util.Scanner;

public class Program {
    public static void main(String[] av) {
        int querryNum;
        int coffyQueriesCount = 0;
        Scanner sc = new Scanner(System.in);
        do {
            querryNum = sc.nextInt();
            if (isPrime(sumOfDigits(querryNum)))
                coffyQueriesCount++;
        } while (querryNum != 42);
        System.out.println("Count of coffee-request - " + coffyQueriesCount);
    }
    private static int sumOfDigits(int num) {
        int sum = 0;
        while (num != 0)
        {
            int lastDigit = num % 10;
            sum += lastDigit;
            num /=10;
        }
        return sum;
    }
    private static boolean isPrime(int num) {
        if (num < 2)
            return false;
        for (int divider = 2; divider * divider <= num; divider++)
        {
            if ((num % divider) == 0)
                return false;
        }
        return true;
    }
}