
import java.util.UUID;

public class Transaction implements Cloneable {
    public Transaction(User sender, User recipient, TransferCategory cat, int transferAmount) {
        this.identifier = UUID.randomUUID();
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = cat;
        if (isTransferAmountValid(transferAmount, cat))
            this.transferAmount = transferAmount;
        else
            throw new WrongTransactionAmountException();
    }
    public Transaction(User sender, User recipient, TransferCategory cat, int transferAmount, Transaction sourceOfId) {
        this.identifier = sourceOfId.getId();
        this.sender = sender;
        this.recipient = recipient;
        this.transferCategory = cat;
        if (isTransferAmountValid(transferAmount, cat))
            this.transferAmount = transferAmount;
        else
            throw new WrongTransactionAmountException();
    }
    public void setTransferAmount(int amount) {
        if (isTransferAmountValid(amount, transferCategory)) {
            transferAmount = amount;
        } else {
            throw new WrongTransactionAmountException();
        }
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
        String line1 = "To " + recipient.getName() + "(id=" + sender.getId() + ")";
        String line2 = + transferAmount + " with id = " + identifier;
        return line1 + line2;
    }
    private UUID identifier;
    private User sender;
    private User recipient;
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