package edu.school21.moneyTransferApp.model.user;

public interface UsersList {
    void addUser(User u);
    User retrieveUserById(int id);
    User retrieveUserByIndex(int idx);
    int retrieveNumberOfUsers();
}