import java.util.UUID;

public class User {
    public User(String name, int initialBalance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        if (initialBalance < 0) {
            throw new IllegalUserBalanceException("Attempt to create user with negative balance");
        }
        balance = initialBalance;
        transactionsList = new TransactionsLinkedList();
    }
    public void setBalance(int newBalance) {
        if (newBalance < 0) {
            throw new IllegalUserBalanceException("Attempt to set negative balance for user");
        }
        balance = newBalance;
    }
    public void addTransaction(Transaction t) { transactionsList.addATransaction(t); }
    public int getId() {
        return identifier;
    }
    public String getName() {
        return name;
    }
    public int getBalance() {
        return balance;
    }
    public TransactionsList getTransactionsList() {
        return transactionsList;
    }
    public String toString() {
        String lineOne = "edu.school21.moneyTransferApp.model.user.User name: " + this.name + "\n";
        String lineTwo = "edu.school21.moneyTransferApp.model.user.User id: " + this.identifier;
        return lineOne + lineTwo;
    }
    public boolean hasTransaction(UUID transactionId) {
        Transaction[] transactions = transactionsList.toArray();
        for (Transaction t : transactions) {
            if (t.getId() == transactionId)
                return  true;
        }
        return  false;
    }

    private final int identifier;
    private final String name;
    private int balance;
    private TransactionsList transactionsList;
}