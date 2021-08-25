
public class UsersArrayList implements UsersList {
    public UsersArrayList() {
        this.usersStorage = new User[10];
        this.usersCount = 0;
    }
    public void addUser(User u) {
        if (usersCount < usersStorage.length)
        {
            usersStorage[usersCount++] = u;
            return;
        }
        User[] newUsersStorage = new User[usersStorage.length + usersStorage.length / 2];
        for (int i = 0; i < usersStorage.length; i++) {
            newUsersStorage[i] = usersStorage[i];
        }
        usersStorage = newUsersStorage;
        usersStorage[usersCount++] = u;
    }
    public User retrieveUserById(int id) {
        for (int i = 0; i < usersCount; i++) {
            if (usersStorage[i].getId() == id)
                return usersStorage[i];
        }
        throw new UserNotFoundException("Error: there is no user with requested id");
    }
    public User retrieveUserByIndex(int idx) {
        return usersStorage[idx];
    }
    public int retrieveNumberOfUsers() {
        return usersCount;
    }
    @Override
    public String toString() {
        String lineOne = "---UsersArrayList---\n";
        String lineTwo = "Has " + this.usersCount + " users\n";
        String lineThree = "Capacity is " + this.usersStorage.length + "\n";
        return lineOne + lineTwo + lineThree;
    }
    private User[] usersStorage;
    int usersCount;
}