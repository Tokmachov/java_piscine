package school21.spring.service.models;

import java.util.Objects;

public class User {

    private Long identifier;
    private String email;
    private String password;

    public User(Long identifier, String email, String password) {
        this.identifier = identifier;
        this.email = email;
        this.password = password;
    }

    public User() {
        this(0l, "", "");
    }

    public User(String email) {
        this(0l, email, "");
    }

    public User(Long id, String email) {
        this(id, email, "");
    }

    public User(String email, String password) {
        this(0l, email, password);
    }

    public Long getIdentifier() {
        return identifier;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return identifier.equals(user.identifier) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return "User{" +
                "Identifier=" + identifier +
                ", Email='" + email + '\'' +
                '}';
    }
}
