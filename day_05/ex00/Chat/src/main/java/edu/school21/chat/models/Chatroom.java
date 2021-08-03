import java.util.*;

public class Chatroom {

    public Chatroom(int id, String name, User owner, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return this.owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Chatroom id(int id) {
        setId(id);
        return this;
    }

    public Chatroom name(String name) {
        setName(name);
        return this;
    }

    public Chatroom owner(User owner) {
        setOwner(owner);
        return this;
    }

    public Chatroom messages(List<Message> messages) {
        setMessages(messages);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Chatroom)) {
            return false;
        }
        Chatroom chatroom = (Chatroom) o;
        return id == chatroom.id && Objects.equals(name, chatroom.name) && Objects.equals(owner, chatroom.owner) && Objects.equals(messages, chatroom.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, messages);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", owner='" + getOwner() + "'" +
            ", messages='" + getMessages() + "'" +
            "}";
    }
    private int id;
    private String name;
    private User owner;
    private List<Message> messages;
}