public class RunnableIntArraySumCalculator implements Runnable {
    RunnableIntArraySumCalculator(int[] arr, int idxFrom, int idxTo, int threadN) {
        this.array = arr;
        this.idxFrom = idxFrom;
        this.idxTo = idxTo;
        this.threadN = threadN;
    }
    public void run() {
        result = 0;
        for (int i = idxFrom; i < idxTo; i++)
             result += array[i];
        idxTo -= 1;
        System.out.println("Thread " + threadN + ": from " + idxFrom + " to " + idxTo + " sum is " + result);
    }
    public int getResult() {
        return result;
    }
    private int result = 0;
    private int[] array;
    private int idxFrom;
    private int idxTo;
    private int threadN;
}