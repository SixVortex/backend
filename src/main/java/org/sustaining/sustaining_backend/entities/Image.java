package org.sustaining.sustaining_backend.entities;

import java.sql.Date;

public class Image {
    private int id;
    private int userID;
    private Date date;
    private String location;
    private String image;
    private String title;

    public Image(int id, int userID, Date date, String location, String image, String title) {
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.location = location;
        this.image = image;
        this.title = title;
    }

    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
