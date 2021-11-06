package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {

    public Message(Long id, User author, Room room, String text, Timestamp timeStamp) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.timeStamp = timeStamp;
    }
    public Message() {}

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Room getChatroom() {
        return this.room;
    }

    public void setChatroom(Room room) {
        this.room = room;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Message)) {
            return false;
        }
        Message message = (Message) o;
        return id == message.getId() && Objects.equals(getAuthor(), message.getAuthor()) && Objects.equals(getChatroom(), message.getChatroom()) && Objects.equals(getText(), message.getText()) && Objects.equals(getTimeStamp(), message.getTimeStamp());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, room, text, timeStamp);
    }

    @Override
    public String toString() {
        return "{\n"
            + "   id=" + getId() + ",\n"
            + "   author=" + getAuthor() + ",\n"
            + "   room=" + getChatroom() + ",\n"
            + "   text=" + getText() + ",\n"
            + "   dateTime=" + getTimeStamp() + ",\n"
            + "}";
    }
    private Long id;
    private User author;
    private Room room;
    private String text; 
    private Timestamp timeStamp;
}