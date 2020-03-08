package org.sustaining.sustaining_backend.entities;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * A representation of an Image.
 * @author Adrian
 */
public class Image {
    private int id;
    private int userID;
    @NotNull
    private Date date;
    @NotNull
    private String location;
    @NotNull
    private String image;
    @NotNull
    private String title;
    private int rating;
    private int fameCount;
    private int shameCount;
    private String username;

    public Image(int id, int userID, Date date, String location, String image, String title, int rating, String username, int fameCount, int shameCount) {
        this.id = id;
        this.userID = userID;
        this.date = date;
        this.location = location;
        this.image = image;
        this.title = title;
        this.rating = rating;
        this.username = username;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFameCount() {
        return fameCount;
    }

    public void setFameCount(int fameCount) {
        this.fameCount = fameCount;
    }

    public int getShameCount() {
        return shameCount;
    }

    public void setShameCount(int shameCount) {
        this.shameCount = shameCount;
    }
}
