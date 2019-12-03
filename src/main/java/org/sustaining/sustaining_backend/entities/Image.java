/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.entities;

import java.sql.Date;

/**
 *
 * @author Elev
 */
public class Image {
    private int id;
    private int userID;
    private Date date;
    private int location;
    private String image;

    public Image(int id, int userID, Date date, int location, String image) {
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.location = location;
        this.image = image;
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

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
