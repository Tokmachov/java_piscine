import java.lang.Integer;
import java.util.LinkedList;

public class IntArraySumCalculator {
    IntArraySumCalculator(int[] array) {
        this.array = array;
    }
    public void calculateOnSingleThread() {
        for (int i = 0; i < array.length; i++)
            result += array[i];
    }
    public void calculateOnMultipleThreads(int numberOfThreads) {
        result = 0;
        int positionsForThread = numOfArrayPositionsForThread(numberOfThreads);
        LinkedList<RunnableIntArraySumCalculator> calcList = new LinkedList<RunnableIntArraySumCalculator>();
        LinkedList<Thread> threadList = new LinkedList<Thread>();
        for (int i = 0; i < numberOfThreads; i++) {
            int idxFrom = i * positionsForThread;
            int idxTo;
            if (i == numberOfThreads - 1)
                idxTo = array.length;
            else
                idxTo = idxFrom + positionsForThread;
            RunnableIntArraySumCalculator calc = new RunnableIntArraySumCalculator(array, idxFrom, idxTo, i + 1);
            Thread thread = new Thread(calc);
            calcList.add(calc);
            threadList.add(thread);
        }
        for (Thread t : threadList)
            t.start();
        for (Thread t : threadList) {
            try {
                t.join();
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
        }
        for (RunnableIntArraySumCalculator calc : calcList)
            result += calc.getResult();
    }
    private int numOfArrayPositionsForThread(int numberOfThreads) {
        return array.length / numberOfThreads;
    }
    public int getResult() {
        return result;
    }
    private int result = 0;
    private int[] array;
}