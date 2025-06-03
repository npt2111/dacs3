package com.example.dacs3.model;

public class Users {
    String hovaten, birthday, username, email, password;
    public  Users(){

    }



    public Users(String hovaten, String birthday, String username, String email, String password) {
        this.hovaten = hovaten;
        this.birthday = birthday;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getHovaten() {
        return hovaten;
    }

    public void setHovaten(String hovaten) {
        this.hovaten = hovaten;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
