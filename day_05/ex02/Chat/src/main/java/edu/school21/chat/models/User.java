package edu.school21.chat.models;

import java.util.*;

public class User {

    public User(Long id, String login, String password, List<Room> createdRooms, List<Room> rooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.rooms = rooms;
    }
    public User() {}

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Room> getCreatedRooms() {
        return this.createdRooms;
    }

    public void setCreatedRooms(List<Room> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public List<Room> getRooms() {
        return this.rooms;
    }

    public void setRooms(List<Room> socializationRooms) {
        this.rooms = socializationRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return id == user.id && Objects.equals(password, user.password) && Objects.equals(createdRooms, user.createdRooms) && Objects.equals(rooms, user.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, createdRooms, rooms);
    }

    @Override
    public String toString() {
        return "{" +
            " id=" + getId() +
                ", login= " + getLogin() +
            ", password=" + getPassword() +
            ", createdRooms=" + getCreatedRooms() +
            ", rooms='" + getRooms() +
            "}";
    }
    private Long id;
    private String login;
    private String password;
    private List<Room> createdRooms;
    private List<Room> rooms;
}