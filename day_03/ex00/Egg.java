public class Egg implements Runnable {
    public void run() {
        for (int i = 0; i < Program.numberOfIterations; i++)
            Program.println("Egg");
    }
}