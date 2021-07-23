package edu.school21.moneyTransferApp.model.transaction;

import edu.school21.moneyTransferApp.model.exceptions.WrongTransactionAmountException;
import edu.school21.moneyTransferApp.model.user.User;

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
        String lineOne = "edu.school21.moneyTransferApp.model.transaction.Transaction: ";
        String lineTwo = "ID: " + identifier + " | ";
        String lineThree = "From: " + sender.getName() + " | ";
        String lineFour = "To : " + recipient.getName() + " | ";
        String lineFive = "Category: " + getCategoryName() + " | ";
        String lineSix = "Amount: " + transferAmount;
        return lineOne + lineTwo + lineThree + lineFour + lineFive + lineSix;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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