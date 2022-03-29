package com.zaaibo.tolet.views.activities;

public class User {

    private int id;
    private String username, email, usertype;

    public User(int id, String username, String email, String usertype) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.usertype = usertype;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return usertype;
    }
}
