/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.entities;

/**
 * A representation of a Rating on an image.
 * @author Adrian
 */
public class Rating {
    private int rating;
    private int userID;
    private int imageID;

    public Rating(int rating, int userID, int imageID) {
        this.rating = rating;
        this.userID = userID;
        this.imageID = imageID;
    }

    public Rating() {
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }
}
