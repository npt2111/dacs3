package com.example.dacs3.model;

public class YeuThichModel {
    String title,location,description,pic,username;
    private Float score;
    public YeuThichModel(){

    }

    public YeuThichModel(String username, String title, String location, String description, String pic, Float score) {
        this.username = username;
        this.title = title;
        this.location = location;
        this.description = description;
        this.pic = pic;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public double getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
