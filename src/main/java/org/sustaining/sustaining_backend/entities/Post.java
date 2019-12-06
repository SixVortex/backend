/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.entities;

import java.util.List;

/**
 * This is a custom entity based on request from the frontend.
 * @author Adrian
 */
public class Post {
    private Image image;
    private List<Comment> comments;

    public Post(Image image, List<Comment> comments) {
        this.image = image;
        this.comments = comments;
    }

    public Post() {
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
