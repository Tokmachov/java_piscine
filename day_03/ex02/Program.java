public class Program {
    public static void main(String[] av) {
        if (av.length != 2)
        {
            System.err.println("Error: program need two arguments of kind  --arraySize=13 --threadsCount=3");
            System.exit(-1);
        }
        int arraySize = ArgumentsParser.parseArg(av[0], "--arraySize");
        int threadsCount = ArgumentsParser.parseArg(av[1], "--threadsCount");
        if (areArgumentsValid(arraySize, threadsCount) == false)
        {
            System.err.println("Error: wrong args values");
            System.exit(-1);
        }
        int[] randomIntArray = RandomIntsArrayGenerator.generate(-1000, 1000, arraySize);
        IntArraySumCalculator calc = new IntArraySumCalculator(randomIntArray);
        calc.calculateOnSingleThread();
        int singleThreadResult = calc.getResult();
        calc.calculateOnMultipleThreads(threadsCount);
        int multipleThreadsResult = calc.getResult();
        System.out.println("Sum: " + singleThreadResult);
        System.out.println("Sum by threads: " + multipleThreadsResult);
    }
    private static boolean areArgumentsValid(int arrSize, int threadsCount) {
        if (arrSize <= 0 || arrSize > 2_000_000)
            return false;
        if (threadsCount <= 0 || threadsCount > arrSize)
            return false;
        return true;
    }
}