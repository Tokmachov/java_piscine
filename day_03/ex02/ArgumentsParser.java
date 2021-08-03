public class ArgumentsParser {
    public static int parseArg(String arg, String flagType) {
        try {
            int idx = arg.indexOf("=");
            String flag = arg.substring(0, idx);
            if (flag.equals(flagType) == false) {
                System.err.println("Error: wrong flag format. Needed of kind " + flag);
                return -1;
            }
            String num = arg.substring(idx + 1);
            int parsedNum = Integer.parseInt(num);
            return parsedNum;
        }
        catch (IndexOutOfBoundsException ex) {
            System.err.println(ex);
            return -1;
        } 
        catch (NumberFormatException ex) {
            System.err.println(ex);
            return -1;
        }
    }
}