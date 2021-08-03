import java.lang.InterruptedException;

public class Egg implements Runnable {
    public void run() {
        for (int i = 0; i < Program.numberOfIterations; i++)
        {
            synchronized (Lock.getLock()) {
                System.out.println("Egg");
                Lock.getLock().notify();
                if (i < Program.numberOfIterations - 1)
                {
                    try {
                        Lock.getLock().wait();
                    } catch (InterruptedException ex) {
                        System.err.println(ex);
                    }
                }
            }
        }
    }
}