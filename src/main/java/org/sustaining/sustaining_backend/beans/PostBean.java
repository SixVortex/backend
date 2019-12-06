/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import org.sustaining.sustaining_backend.ConnectionFactory;
import org.sustaining.sustaining_backend.entities.Comment;
import org.sustaining.sustaining_backend.entities.Image;
import org.sustaining.sustaining_backend.entities.Post;

/**
 * This class handles all the logic for posts on the website
 *
 * @author Adrian
 */
@Stateless
public class PostBean {

    @EJB
    private CommentBean commentBean;

    /**
     * Retrieves the data neccessary to form a Post instance from the database
     * and packs it into a Response instance.
     *
     * @param numberOfPosts This is how many posts you want to retrieve from the
     * database.
     * @return The response to send back to the frontend.
     */
    public Response getPosts(int numberOfPosts) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM full_image_info LIMIT ?");
            stmt.setInt(1, numberOfPosts);
            ResultSet data = stmt.executeQuery();

            List<Post> posts = new ArrayList();

            while (data.next()) {
                int imageID = data.getInt("image_id");
                int userID = data.getInt("user_id");
                String user = data.getString("user");
                Date date = data.getDate("date");
                String title = data.getString("title");
                String location = data.getString("location");
                String imageData = data.getString("image");
                int rating = data.getInt("rating");
                int fameCount = data.getInt("fame_count");
                int shameCount = data.getInt("shame_count");

                List<Comment> comments = commentBean.getComments(imageID);

                Image postImage = new Image(imageID, userID, date, location, imageData, title, rating, user, fameCount, shameCount);

                posts.add(new Post(postImage, comments));
            }

            return Response.status(Response.Status.OK).entity(posts).build();
        } catch (Exception ex) {
            System.out.println("PostBean.getPosts: " + ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Gets the next post based on the last image you received.
     * @param lastImageID The id of the last image you receieved.
     * @return The next image.
     */
    public Response getNextPost(int lastImageID) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM full_image_info WHERE image_id > ? LIMIT 1");
            stmt.setInt(1, lastImageID);
            ResultSet data = stmt.executeQuery();
            data.next();

            int imageID = data.getInt("image_id");
            int userID = data.getInt("user_id");
            String user = data.getString("user");
            Date date = data.getDate("date");
            String title = data.getString("title");
            String location = data.getString("location");
            String imageData = data.getString("image");
            int rating = data.getInt("rating");
            int fameCount = data.getInt("fame_count");
            int shameCount = data.getInt("shame_count");

            List<Comment> comments = commentBean.getComments(lastImageID);
            
            Image postImage = new Image(imageID, userID, date, location, imageData, title, rating, user, fameCount, shameCount);
            Post post = new Post(postImage, comments);
            
            return Response.status(Response.Status.OK).entity(post).build();

        } catch (Exception e) {
            System.out.println("PostBean.getPost: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
