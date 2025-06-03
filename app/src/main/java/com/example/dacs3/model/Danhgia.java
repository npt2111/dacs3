package com.example.dacs3.model;

public class Danhgia {
    String title,binhluan, username;
    Float score;
    public Danhgia(){

    }

    public Danhgia(String title, String binhluan, String username, Float score) {
        this.title = title;
        this.binhluan = binhluan;
        this.username = username;
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBinhluan() {
        return binhluan;
    }

    public void setBinhluan(String binhluan) {
        this.binhluan = binhluan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }
}
