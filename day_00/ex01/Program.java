
import java.util.Scanner; 

public class Program {
    public static void main(String[] av) {
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt())
        {
            try {
                int num = sc.nextInt();
                if (num <= 1)
                {
                    System.err.println("Illegal Argument");
                    System.exit(-1);
                } else {
                    printIsPrimeAndPrimeCheckIterations(num);
                }
            } catch (Exception ex) {
                System.err.println("Illegal Argument");
                System.exit(-1);
            }
        }
        else
        {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
    }
    private static void printIsPrimeAndPrimeCheckIterations(int num)
    {
        boolean isPrime = true;
        int numOfIterations = 0;
        if (num == 2)
        {
            numOfIterations = 1;
            System.out.println(isPrime + " " + numOfIterations);
            return ;
        }
        for (int divider = 2; divider * divider <= num; divider++)
        {
            numOfIterations++;
            if ((num % divider) == 0)
            {
                isPrime = false;
                break ;
            }
        }
        System.out.println(isPrime + " " + numOfIterations);
    }
}