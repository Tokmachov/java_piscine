
public class Program {
    public static void main(String[] av) {
        User john = new User("john", 10);
        User joe = new User("joe", 100);
        User hoe = new User("Hoe", 1000);
        TransactionsService service = new TransactionsService();
        System.out.println("---Add user test---");
        service.addUser(john);
        service.addUser(joe);
        service.addUser(hoe);
        System.out.println(service);
        System.out.println("---get users balance test---");
        System.out.println(john);
        System.out.println("---Balance is: " + service.getUserBalance(john.getId()));
        System.out.println(joe);
        System.out.println("---Balance is: " + service.getUserBalance(joe.getId()));
        System.out.println(hoe);
        System.out.println("---Balance is: " + service.getUserBalance(hoe.getId()));
        System.out.println("---perform a transfer transaction test---");
        System.out.println("John balance(before transaction) is: " + service.getUserBalance(john.getId()));
        System.out.println("Joe balance(before transaction) is: " + service.getUserBalance(joe.getId()));
        service.performTransferTransaction(john.getId(), joe.getId(), 10);
        System.out.println("John balance(after transaction) is: " + service.getUserBalance(john.getId()));
        System.out.println("Joe balance(after transaction) is: " + service.getUserBalance(joe.getId()));
        System.out.println("John's transactions:");
        System.out.println(john.getTransactionsList());
        System.out.println("Jos transactions:");
        System.out.println(joe.getTransactionsList());
        System.out.println("---Make illegal transaction test---");
        System.out.println("---User has not anough money to transfer---");
        try {
            service.performTransferTransaction(john.getId(), joe.getId(), 10);
        } catch (IllegalTransactionException ex) {
            System.out.println("Error: IllegalTransactionException()");
        }
        System.out.println("---perform a transfer transaction test---");
        service.performTransferTransaction(hoe.getId(), joe.getId(), 10);
        service.performTransferTransaction(hoe.getId(), joe.getId(), 11);
        service.performTransferTransaction(hoe.getId(), joe.getId(), 12);
        System.out.println("---getTransactionsOfUser(int userId) test---");
        System.out.println("---Transactions of Hoe---");
        for (Transaction t : service.getTransactionsOfUser(hoe.getId())) {
            System.out.println(t);
        }
        System.out.println("---Transactions of Joe---");
        for (Transaction t : service.getTransactionsOfUser(joe.getId())) {
            System.out.println(t);
        }
        System.out.println("---Remove transactions test---");
        TransactionsList transactionsList = joe.getTransactionsList();
        Transaction t1 = transactionsList.toArray()[0];
        Transaction t2 = transactionsList.toArray()[2];
        System.out.println("Remove transaction: " + t1);
        service.removeTransactionFromUser(t1.getId(), joe.getId());
        System.out.println("Remove transaction: " + t2);
        service.removeTransactionFromUser(t2.getId(), joe.getId());
        System.out.println("---Transactions of Joe after removal---");
        for (Transaction t : service.getTransactionsOfUser(joe.getId())) {
            System.out.println(t);
        }
        System.out.println("---getUnpairedTransactions test---");
        for (Transaction t : service.getUnpairedTransactions()) {
            System.out.println(t);
        }
    }
}
