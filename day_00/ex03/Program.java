import java.util.Scanner;
public class Program {
    public static void main(String[] av) {
        Scanner sc = new Scanner(System.in);
        int previousWeekNum = 0;
        int weeksCount = 0;
        long resultsStorage = 0;
        while (!isEndOfInput(sc)) {
            skipWeekWord(sc);
            int weekNum = readWeekNum(sc);
            if (weekNum - previousWeekNum != 1)
                printErrorAndExit();
            previousWeekNum = weekNum;
            weeksCount++;
            int minimalGrade = readMinimalGrade(sc);
            resultsStorage = 10 * resultsStorage + minimalGrade;
        }
        printStatisticsOfAllWeeks(resultsStorage, weeksCount);
    }
    private static boolean isEndOfInput(Scanner sc) {
        if (sc.hasNextInt()) {
            int token = sc.nextInt();
            if (token == 42) {
                return true;
            } else {
                printErrorAndExit();
            }
        }
        return false;
    }
    private static void skipWeekWord(Scanner sc) {
        try {
            String weekWord = sc.next();
            if (!weekWord.equals("Week"))
                printErrorAndExit();
        } catch (RuntimeException ex) {
            printErrorAndExit();
        }
    }
    private static int readWeekNum(Scanner sc) {
        if (!sc.hasNextInt()) {
            printErrorAndExit();
        }
        return sc.nextInt();
    }
    private static int readMinimalGrade(Scanner sc) {
        int minimalGrade = -1;
        for (int i = 0; i < 5; i++) {
            if (sc.hasNextInt()) {
                int grade = sc.nextInt();
                if (!isGradeValid(grade))
                    printErrorAndExit();
                if (minimalGrade == -1 || grade < minimalGrade) {
                    minimalGrade = grade;
                }
            } else {
                printErrorAndExit();
            }
        }
        return minimalGrade;
    }
    private static boolean isGradeValid(int g) {
        return g >= 1 && g <= 9;
    }
    private static void printStatisticsOfAllWeeks(long gradesStorage, int weeksCount) {
        int weekNum = 1;
        while (weeksCount != 0) {
            int divisor = pow(10, weeksCount - 1);
            long grade = gradesStorage / divisor;
            gradesStorage = gradesStorage % divisor;
            weeksCount--;
            printStatisticsOfOneWeek(weekNum, (int)grade);
            weekNum++;
        }
    }
    private static int pow(int base, int pow) {
        if (pow == 0)
            return 1;
        int result = base;
        pow--;
        while (pow != 0) {
            result *= base;
            pow--;
        }
        return result;
    }
    private static void printStatisticsOfOneWeek(int weekNum, int grade) {
        System.out.print("Week " + weekNum + " ");
        for (int i = 0; i < grade; i++) {
            System.out.print("=");
        }
        System.out.println(">");
    }

    private static void printErrorAndExit() {
        System.err.println("IllegalArgument");
        System.exit(-1);
    }
}