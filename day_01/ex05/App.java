import java.util.Scanner;
import java.util.UUID;

public class App {
    public static void main(String[] av) {
        if (av.length != 1) {
            System.err.println("Error: wrong number of arguments");
            System.err.println("Please provide 1 argument --profile=dev or --profile=production");
        }
        String mode = parseMode(av);
        if (mode == null) {
            System.err.println("Error: Wrong argument. Please provide one argument: --profile=dev or --profile=production");
        }
        boolean isDevMode = mode.equals("dev");
        App app = new App(isDevMode);
        app.run();
    }
    public App(boolean isDevMode) {
        this.isDevMode = isDevMode;
        transactionsService = new TransactionsService();
        menu = new Menu(new Scanner(System.in));
    }

    public void run() {
        while (true) {
            String topMenuString = composeTopMenuString();
            menu.printMessage(topMenuString);
            Integer choice = menu.readIntAnswer();
            if (choice == null) {
                menu.printError("Error: number is expected");
                break ;
            }
            switch (choice) {
                case 1: performAddUserOperation();
                        break ;
                case 2: performViewUserBalancesOperation();
                        break ;
                case 3: performTransfer();
                        break ;
                case 4: performViewAllTransactionsOperation();
                        break ;
                case 5: performRemovingOfTransfer();
                        break ;
                case 6: performCheckTransferValidity();
                        break;
                case 7:
                    menu.printMessage("See you!");
                    System.exit(0);
                default:
                    menu.printError("Error: your choice is out of available choice range");
            }
        }
    }
    private String composeTopMenuString() {
        String line1 = "1. Add a user\n";
        String line2 = "2. View user balances\n";
        String line3 = "3. Perform a transfer\n";
        String line4 = "4. View all transactions for a specific user\n";
        String line5 = "5. DEV - remove a transfer by ID\n";
        String line6 = "6. DEV - check transfer validity\n";
        String line7 = "7. Finish execution";
        return line1 + line2 + line3 + line4 + line5 + line6 + line7;
    }

    private void performAddUserOperation() {
        menu.printMessage("Enter a user name and a balance");
        String name = menu.readStringAnswer();
        Integer balance = menu.readIntAnswer();
        if (balance == null) {
            menu.printError("Error: number is expected");
            return ;
        }
        try {
            User u = new User(name, balance);
            transactionsService.addUser(u);
            menu.printMessage("User with id = " + u.getId() + " is added");
        } catch (IllegalUserBalanceException ex) {
            menu.printError(ex.getMessage());
        }
    }
    private void performViewUserBalancesOperation() {
        menu.printMessage("Enter a user ID");
        Integer id = menu.readIntAnswer();
        if (id == null) {
            menu.printError("Error: number is expected as user id");
            return ;
        }
        try {
            String name = transactionsService.getUserName(id);
            int balance = transactionsService.getUserBalance(id);
            menu.printMessage(name + " - " + balance);
        } catch (UserNotFoundException ex) {
            menu.printError(ex.getMessage());
        }
    }

    private void performTransfer() {
        menu.printMessage("Enter a sender ID, a recipient ID, and a transfer amount");
        Integer senderId = menu.readIntAnswer();
        Integer recipientId = menu.readIntAnswer();
        Integer transferAmount = menu.readIntAnswer();
        if (senderId == null || recipientId == null || transferAmount == null) {
            menu.printError("Error: expected to receive 3 numbers. E.g. 1 2 10");
            return ;
        }
        try {
            transactionsService.performTransferTransaction(senderId, recipientId, transferAmount);
            menu.printMessage("The transfer is completed");
        } catch (NegativeTransferAmountException ex) {
            menu.printError("Error: Transaction amount can't be negative");
        } catch (UserNotFoundException ex) {
            menu.printError("Error: can't find user, try another id");
        } catch (IllegalTransactionException ex) {
            menu.printError("Error: sender doesn't have enough money");
        }
    }
    private void performViewAllTransactionsOperation() {
        menu.printMessage("Enter a user ID");
        Integer id = menu.readIntAnswer();
        if (id == null) {
            menu.printError("Error: number is expected");
            return ;
        }
        try {
            Transaction[] transactions = transactionsService.getTransactionsOfUser(id);
            for (Transaction t : transactions) {
                String transactionString = composeTransactionString(t);
                menu.printMessage(transactionString);
            }
        } catch (UserNotFoundException ex) {
            menu.printError(ex.getMessage());
        }
    }
    private void performRemovingOfTransfer() {
        if (!isDevMode) {
            menu.printError("Error: you are not in dev mode");
            menu.printError("Please start app in with --profile=dev to select this option");
            return ;
        }
        menu.printMessage("Enter a user ID and a transfer ID");
        Integer userId = menu.readIntAnswer();
        if (userId == null) {
            menu.printError("Error: number is expected");
            menu.skipUserInput();
            return ;
        }
        String transactionIdStr = menu.readStringAnswer();
        try {
            UUID transactionId = UUID.fromString(transactionIdStr);
            Transaction t = transactionsService.getTransaction(transactionId, userId);
            if (t == null) {
                throw new TransactionNotFoundException("");
            }
            String removeTransactionMessage = composeTransactionRemovedString(t);
            transactionsService.removeTransactionFromUser(transactionId, userId);
            menu.printMessage(removeTransactionMessage);
        } catch (IllegalArgumentException ex) {
            menu.printError("Error: transaction id format is incorrect");
        } catch (UserNotFoundException ex) {
            menu.printError("Error: user is not found");
        } catch (TransactionNotFoundException ex) {
            menu.printError("Error: transaction is not found");
        }
    }
    private void performCheckTransferValidity() {
        menu.printMessage("Check results:");
        Transaction[] unpairedTransactions = transactionsService.getUnpairedTransactions();
        if (unpairedTransactions.length == 0) {
            menu.printMessage("There are no unpaired transactions");
            return ;
        }
        for (Transaction t : unpairedTransactions) {
            String unpairedTransactionDescription = composeUnpairedTransactionMessage(t);
            menu.printMessage(unpairedTransactionDescription);
        }
    }
    private String composeUnpairedTransactionMessage(Transaction t) {
        int fromUserId = t.getSender().getId();
        int toUserId = t.getRecipient().getId();
        String fromUserName = t.getSender().getName();
        String toUserName = t.getSender().getName();
        String transactionId = t.getId().toString();
        int amount = t.getTransferAmount();
        String str = fromUserName + "(id = " + fromUserId + ") has an unacknowledged transfer id = ";
        str += transactionId + " from " + toUserName + "(id = " + toUserId + ") for " + amount;
        return str;
    }

    private String composeTransactionString(Transaction t) {
        String recipientName = t.getRecipient().getName();
        int recipientId = t.getRecipient().getId();
        int amount = t.getTransferAmount();
        String transactionId = t.getId().toString();
        String line1 = "To " + recipientName + "(id=" + recipientId + ")";
        String line2 = + amount + " with id = " + transactionId;
        return  line1 + line2;
    }
    private String composeTransactionRemovedString(Transaction t) {
        String userName = t.getSender().getName();
        int amount = t.getTransferAmount();
        return "Transfer To " + userName + "(id = " + userName + ")" +  amount + " removed";
    }

    private static String parseMode(String[] av) {
        if (av[0].equals("--profile=dev")) {
            return "dev";
        } else if (av[0].equals("--profile=production")) {
            return "prod";
        } else {
            return null;
        }
    }
    private final boolean isDevMode;
    private final TransactionsService transactionsService;
    private final Menu menu;
}
