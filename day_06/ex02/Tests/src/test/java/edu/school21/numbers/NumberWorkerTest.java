package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
    private NumberWorker numberWorker = new NumberWorker();

    @DisplayName("Returns true for prime numbers")
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11})
    public void isPrimeForPrimes(int primeNumber) {
        Assertions.assertTrue(numberWorker.isPrime(primeNumber));
    }

    @DisplayName("Returns false for composite numbers")
    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 14})
    public void isPrimeForNotPrimes(int compositeNumber) {
        Assertions.assertFalse(numberWorker.isPrime(compositeNumber));
    }

    @DisplayName("Should throw IllegalNumberException if numbers are incorrect")
    @ParameterizedTest
    @ValueSource(ints = {-10, -5, -1, 0, 1})
    public void isPrimeForIncorrectNumbers(final int incorrectNumber) {
        Assertions.assertThrows(IllegalNumberException.class, () -> numberWorker.isPrime(incorrectNumber));
    }

    @DisplayName("")
    @ParameterizedTest
    @CsvFileSource(resources = "/digitSumTestValues.csv")
    public void normalOperationTest(int testValue, int expectedResult) {
        Assertions.assertEquals(numberWorker.digitsSum(testValue), expectedResult);
    }
}
