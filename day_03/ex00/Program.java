import java.lang.Integer;
import java.lang.NumberFormatException;
import java.lang.IndexOutOfBoundsException;
import java.lang.Integer;

public class Program {
    public static void main(String[] av) {
        if (av.length != 1) {
            System.err.println("Error: program requires exactly one argument of form \"--count=50\"");
            System.exit(-1);
        }
        Integer numberOfIterations = parseNumbrOfIterations(av[0]);
        if (numberOfIterations == null)
        {
            System.err.println("Error: failed to parse number of iterations");
            System.exit(-1);
        }
        Program.numberOfIterations = numberOfIterations.intValue();
        Runnable hen = new Hen();
        Thread henThread = new Thread(hen);
        Runnable egg = new Egg();
        Thread eggThread = new Thread(egg);
        eggThread.start();
        henThread.start();
        try {
            eggThread.join();
            henThread.join();
        } catch (InterruptedException ex) {
            System.err.println(ex);
        }
        for (int i = 0; i < Program.numberOfIterations; i++)
            System.out.println("Human");
    }
    public static synchronized void println(String str) {
        System.out.println(str);
    }
    public static int numberOfIterations = 50;
    private static Integer parseNumbrOfIterations(String arg) {
        try {
            int idx = arg.indexOf("=");
            String flag = arg.substring(0, idx);
            if (flag.equals("--count") == false) {
                System.err.println("Error: wrong flag format");
                return null;
            }
            String num = arg.substring(idx + 1);
            int parsedNum = Integer.parseInt(num);
            if (parsedNum <= 0)
            {
                System.err.println("Error: please input positive number of iterations");
                return null;
            }
            return new Integer(parsedNum);
        } 
        catch (IndexOutOfBoundsException ex) {
            System.err.println(ex);
            return null;
        } 
        catch (NumberFormatException ex) {
            System.err.println(ex);
            return null;
        }
    }
}