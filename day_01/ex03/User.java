
public class User {
    public User(String name, int initialBalance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        if (initialBalance < 0)
            balance = 0;
        else
            balance = initialBalance;
    }
    public void setBalance(int newBalance) {
        if (newBalance < 0)
            return;
        balance = newBalance;
    }
    public void setTransactionsList(TransactionsList tl) {
        this.transactionsList = tl;
    }
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
        String lineOne = "User name: " + this.name + "\n";
        String lineTwo = "User id: " + this.identifier + "\n";
        return lineOne + lineTwo;
    }
    private TransactionsList transactionsList;
    private final int identifier;
    private String name;
    private int balance;
    
}