
import java.lang.RuntimeException;

public class IllegalUserBalanceException extends RuntimeException {
    public IllegalUserBalanceException(String msg) {
        super(msg);
    }
}
