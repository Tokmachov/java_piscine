import java.util.*;

public class User {

    public User(int id, String password, List<Chatroom> createdRooms, List<Chatroom> socializationRooms) {
        this.id = id;
        this.password = password;
        this.createdRooms = createdRooms;
        this.socializationRooms = socializationRooms;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chatroom> getCreatedRooms() {
        return this.createdRooms;
    }

    public void setCreatedRooms(List<Chatroom> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public List<Chatroom> getSocializationRooms() {
        return this.socializationRooms;
    }

    public void setSocializationRooms(List<Chatroom> socializationRooms) {
        this.socializationRooms = socializationRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(password, user.password) && Objects.equals(createdRooms, user.createdRooms) && Objects.equals(socializationRooms, user.socializationRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, createdRooms, socializationRooms);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", password='" + getPassword() + "'" +
            ", createdRooms='" + getCreatedRooms() + "'" +
            ", socializationRooms='" + getSocializationRooms() + "'" +
            "}";
    }
    private int id;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> socializationRooms;
}