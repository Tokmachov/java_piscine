package edu.school21.moneyTransferApp.model.transaction;

import edu.school21.moneyTransferApp.model.exceptions.TransactionNotFoundException;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    public TransactionsLinkedList() {
        pastTheEndNode = new Node();
        pastTheEndNode.next = pastTheEndNode;
        pastTheEndNode.prev = pastTheEndNode;
        count = 0;
    }
    public void addATransaction(Transaction t) {
        Node nodeToIstert = new Node();
        nodeToIstert.data = t;
        Node lastNode = pastTheEndNode.prev;
        linkNodes(lastNode, nodeToIstert);
        linkNodes(nodeToIstert, pastTheEndNode);
        count++;
    }
    public void removeATransactionBy(UUID id) {
        Node current = pastTheEndNode.next;
        while (current != pastTheEndNode)
        {
            if (current.data.getId().equals(id))
            {
                linkNodes(current.prev, current.next);
                count--;
                return;
            }
            current = current.next;
        }
        throw new TransactionNotFoundException("Error: can not retreive transaction for id");
    }
    public Transaction[] toArray() {
        Transaction[] transactionArray = new Transaction[count];
        Node current = pastTheEndNode.next;
        int arrayIndex = 0;
        while (current != pastTheEndNode)
        {
            transactionArray[arrayIndex] = current.data;
            arrayIndex++;
            current = current.next;
        }
        return transactionArray;
    }
    @Override
    public String toString() {
        String lineOne = "---TransactionLinkedList object---\n";
        String lineTwo = "Transactions count: " + count + "\n";
        String lineThree = makeAllTransactionsDescriptionsString();
        return lineOne + lineTwo + lineThree;
    }
    private String makeAllTransactionsDescriptionsString() {
        String description = "";
        for (Transaction t : toArray()) {
            description += t.toString();
        }
        return  description;
    }
    private Node pastTheEndNode;
    private int count;
    private void linkNodes(Node prev, Node next) {
        prev.next = next;
        next.prev = prev;
    }
}