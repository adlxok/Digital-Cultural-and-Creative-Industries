package com.example.server_forexam.entity;

public class StudentToChoose {
    String username;
    String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "StudentToChoose{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
