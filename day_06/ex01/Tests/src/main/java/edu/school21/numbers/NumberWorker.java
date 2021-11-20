package edu.school21.numbers;

public class NumberWorker {
    public boolean isPrime(int number) {
        if (number <= 1) throw new IllegalNumberException();
        for (int divider = 2; divider * divider <= number; divider++)
        {
            if ((number % divider) == 0)
                return false;
        }
        return true;
    }

    public int digitsSum(int number) {
        number = Math.abs(number);
        int sum = 0;
        while (number != 0)
        {
            int lastDigit = number % 10;
            sum += lastDigit;
            number /=10;
        }
        return sum;
    }
}
