package com.audieni.models;

import java.util.Objects;

public class User {
    private Integer id;
    private String email;
    private String password;
    private boolean manager;

    public User() {}

    public User(Integer id, String email, String password, boolean manager) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.manager = manager;
    }

    public User(String email, String password, boolean manager) {
        this.email = email;
        this.password = password;
        this.manager = manager;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return manager;
    }

    public void setManager(boolean manager) {
        this.manager = manager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return manager == user.manager && Objects.equals(id, user.id) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, password, manager);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", manager=" + manager +
                '}';
    }
}
