package edu.school21.chat.models;

import java.util.*;

public class Room {

    public Room(Long id, String name, User owner, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }
    public Room() {}

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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

    public Room id(Long id) {
        setId(id);
        return this;
    }

    public Room name(String name) {
        setName(name);
        return this;
    }

    public Room owner(User owner) {
        setOwner(owner);
        return this;
    }

    public Room messages(List<Message> messages) {
        setMessages(messages);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Room)) {
            return false;
        }
        Room room = (Room) o;
        return id == room.id && Objects.equals(name, room.name) && Objects.equals(owner, room.owner) && Objects.equals(messages, room.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, owner, messages);
    }

    @Override
    public String toString() {
        return "{" +
            " id=" + getId() +
            ", name=" + getName() +
            ", owner=" + getOwner() +
            ", messages=" + getMessages() +
            "}";
    }
    private Long id;
    private String name;
    private User owner;
    private List<Message> messages;
}