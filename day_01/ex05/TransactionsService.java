
import java.util.UUID;

public class TransactionsService {
    public TransactionsService() {
        this.usersList = new UsersArrayList();
    }
    public void addUser(User u) {
        usersList.addUser(u);
    }
    public int getUserBalance(int userId) {
        User user = usersList.retrieveUserById(userId);
        return user.getBalance();
    }
    public String getUserName(int userId) {
        User user = usersList.retrieveUserById(userId);
        return user.getName();
    }
    public void performTransferTransaction(int fromUserId, int toUserId, int amount) {
        if (amount <= 0) {
            throw new NegativeTransferAmountException();
        }
        User fromUser = usersList.retrieveUserById(fromUserId);
        User toUser = usersList.retrieveUserById(toUserId);
        if (fromUser.getBalance() < amount) {
            throw new IllegalTransactionException();
        }
        fromUser.setBalance(fromUser.getBalance() - amount);
        toUser.setBalance(toUser.getBalance() + amount);
        Transaction creditTransaction = new Transaction(fromUser, toUser, TransferCategory.CREDITS, -amount);
        fromUser.addTransaction(creditTransaction);
        Transaction debitTransaction = new Transaction(toUser, fromUser, TransferCategory.DEBITS, amount, creditTransaction);
        toUser.addTransaction(debitTransaction);
    }
    public Transaction[] getTransactionsOfUser(int userId) {
        User u = usersList.retrieveUserById(userId);
        TransactionsList transactionsList = u.getTransactionsList();
        return  transactionsList.toArray();
    }
    public void removeTransactionFromUser(UUID transactionId, int userId) {
        User u = usersList.retrieveUserById(userId);
        TransactionsList transactionsList = u.getTransactionsList();
        transactionsList.removeATransactionBy(transactionId);
    }
    public Transaction[] getUnpairedTransactions() {
        TransactionsList unpairedTransactions = new TransactionsLinkedList();
        for (int idx = 0; idx < usersList.retrieveNumberOfUsers(); idx++) {
            User u = usersList.retrieveUserByIndex(idx);
            TransactionsList userTransactions = u.getTransactionsList();
            for (Transaction checkPairTransaction : userTransactions.toArray()) {
                User transactionPairUser = checkPairTransaction.getRecipient();
                if (!transactionPairUser.hasTransaction(checkPairTransaction.getId())) {
                    unpairedTransactions.addATransaction(checkPairTransaction);
                }
            }
        }
        return unpairedTransactions.toArray();
    }
    public Transaction getTransaction(UUID transId, int userId) {
        User u = usersList.retrieveUserById(userId);
        TransactionsList transactionsList = u.getTransactionsList();
        for (Transaction t : transactionsList.toArray()) {
            if (t.getId().equals(transId)) {
                return t;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        String line1 = "TransactionsService\n";
        String line2 = usersList.toString();
        return line1 + line2;
    }

    private UsersList usersList;
}
