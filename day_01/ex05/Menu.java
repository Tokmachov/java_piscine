import java.util.Scanner;

public class Menu {
    public Menu(Scanner sc) { this.sc = sc; }

    public int readMenuChoice() {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) {
            System.err.println("Error: number input expected");
            return -1;
        } else {
            return sc.nextInt();
        }
    }
    public String readStringAnswer() {
        return sc.next();
    }
    public Integer readIntAnswer() {
        if (sc.hasNextInt()) {
            return sc.nextInt();
        } else {
            sc.next();
            return null;
        }
    }
    public void skipUserInput() {
        sc.next();
    }
    public void printError(String err) {
        System.err.println(err);
    }
    public void printMessage(String msg) {
        System.out.println(msg);
    }
    public void clearBuffer() {
            sc.nextLine();
    }
    private Scanner sc;
}
