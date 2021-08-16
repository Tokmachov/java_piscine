import java.lang.Integer;
import java.lang.NumberFormatException;
import java.lang.IndexOutOfBoundsException;

public class Program {
    public static void main(String[] av) {
        if (av.length != 1) {
            System.err.println("Error: program requires exactly one argument of form \"--count=50\"");
            System.exit(-1);
        }
        Integer num = parseNumberOfIterations(av[0]);
        if (num == null)
        {
            System.err.println("Error: failed to parse number of iterations");
            System.exit(-1);
        }
        Program.numberOfIterations = num.intValue() * 2;
        Runnable hen = new Hen();
        Thread henThread = new Thread(hen);
        Runnable egg = new Egg();
        Thread eggThread = new Thread(egg);
        eggThread.start();
        henThread.start();
    }
    public static int numberOfIterations = 50;
    private static Integer parseNumberOfIterations(String arg) {
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