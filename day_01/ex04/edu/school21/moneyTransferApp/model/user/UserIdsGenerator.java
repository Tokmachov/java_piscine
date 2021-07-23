package edu.school21.moneyTransferApp.model.user;

public class UserIdsGenerator {
    public static UserIdsGenerator getInstance() {
        return instance;
    }
    public int generateId() {
        return ++previousId;
    }
    static private UserIdsGenerator instance = new UserIdsGenerator();
    private UserIdsGenerator() {
        this.previousId = 0;
    }
    private int previousId;
}