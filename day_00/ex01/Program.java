
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
        int divider = 1;
        do {
            numOfIterations++;
            divider++;
            if ((num % divider) == 0)
            {
                isPrime = false;
                break ;
            }
        } while (divider * divider <= num);
        System.out.println(isPrime + " " + numOfIterations);
    }
}