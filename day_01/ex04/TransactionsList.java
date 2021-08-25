
import java.util.UUID;

public interface TransactionsList {
    void addATransaction(Transaction t);
    void removeATransactionBy(UUID id);
    Transaction[] toArray();
}