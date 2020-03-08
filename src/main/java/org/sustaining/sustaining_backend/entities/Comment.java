package org.sustaining.sustaining_backend.entities;

import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * A representation of a Comment on an image.
 * @author Adrian
 */
public class Comment {
    private int id;
    @NotNull
    private int imageID;
    private int userID;
    @NotNull
    private String text;
    private Date date;

    public Comment(int id, int imageID, int userID, String text, Date date) {
        this.id = id;
        this.imageID = imageID;
        this.userID = userID;
        this.text = text;
        this.date = date;
    }

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
