import java.lang.InterruptedException;
public class Hen implements Runnable {
    public void run() {
        int i = 0;
        synchronized (Program.class) {
            try {
                Program.class.wait(100);
            } catch (InterruptedException ex) {
                System.err.println(ex);
            }
            while (Program.numberOfIterations != 0) {
                System.out.println("Hen");
                Program.numberOfIterations--;
                Program.class.notify();
                try {
                    Program.class.wait();
                } catch (InterruptedException ex) {
                    System.err.println(ex);
                }
            }
            Program.class.notify();
        }
    }
}