import java.util.UUID;

public class Transaction {
    public Transaction(User recipient, User sender, TransferCategory cat, int transferAmount) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = cat;
        if (isTransferAmountValid(transferAmount, cat))
            this.transferAmount = transferAmount;
        else
            this.transferAmount = 0;
    }
    public void setTransferAmount(int amount) {
        if (isTransferAmountValid(amount, transferCategory))
            transferAmount = amount;
    }
    public UUID getId() {
        return identifier;
    }
    public User getRecipient() {
        return recipient;
    }
    public User getSender() {
        return sender;
    }
    public String getCategoryName() {
        if (transferCategory == TransferCategory.DEBITS)
            return "Debits";
        else if (transferCategory == TransferCategory.CREDITS)
            return "Credits";
        else 
            return "undefined";
    }
    public int getTransferAmount() {
        return transferAmount;
    }
    @Override
    public String toString() {
        String lineOne = "---Transaction object--\n";
        String lineTwo = "ID: " + identifier + "\n";
        String lineThree = "Recipient name: " + recipient.getName() + "\n";
        String lineFour = "Sender name: " + sender.getName() + "\n";
        String lineFive = "Category: " + getCategoryName() + "\n";
        String lineSix = "Transfer amount: " + transferAmount + "\n";
        return lineOne + lineTwo + lineThree + lineFour + lineFive + lineSix;
    }
    private UUID identifier;
    private User recipient;
    private User sender;
    private TransferCategory transferCategory;
    private int transferAmount;
    
    private boolean isTransferAmountValid(int amount, TransferCategory cat) {
        if ((cat == TransferCategory.DEBITS) && (amount <= 0))
            return false;
        if ((cat == TransferCategory.CREDITS) && (amount >= 0))
            return false;
        return true;
    }
}

enum TransferCategory {
    DEBITS,
    CREDITS
}