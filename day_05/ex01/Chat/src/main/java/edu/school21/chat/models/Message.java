package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {

    public Message(Long id, User author, Chatroom chatroom, String text, Timestamp timeStamp) {
        this.id = id;
        this.author = author;
        this.chatroom = chatroom;
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

    public Chatroom getChatroom() {
        return this.chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
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
        return Objects.hash(id, author, chatroom, text, timeStamp);
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
    private Chatroom chatroom;
    private String text; 
    private Timestamp timeStamp;
}