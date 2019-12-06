package org.sustaining.sustaining_backend.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import org.sustaining.sustaining_backend.ConnectionFactory;
import org.sustaining.sustaining_backend.entities.Comment;

/**
 * This class handles all the logic for comments of an Image.
 * @author Silas
 */
@Stateless
public class CommentBean {

    /**
     * Gets all the comments for a certain image.
     * @param imageID The image you want to retrieve comments from.
     * @return A list of all the comments.
     */
    public List<Comment> getComments(int imageID) {
        try ( Connection connection = ConnectionFactory.getConnection()) {

            String sql = "SELECT * FROM comment WHERE image_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, imageID);

            ResultSet result = stmt.executeQuery();
            List<Comment> comments = new ArrayList();
            while (result.next()) {
                int id = result.getInt("id");
                int userID = result.getInt("user_id");
                String text = result.getString("text");
                Date date = result.getDate("date");
                comments.add(new Comment(id, imageID, userID, text, date));
            }

            return comments;
        } catch (Exception ex) {
            System.out.println("Error in CommentBean.getComments: " + ex.getMessage());
        }
        return new ArrayList();
    }

    /**
     * Posts a comment for a certain image.
     * @param imageID The image to post the comment to.
     * @param comment The comment to post.
     * @return The same comment but with the auto-generated key attached to it.
     */
    public Comment postComment(int imageID, Comment comment) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO comment (image_id, user_id, text, date) VALUES(?, ?, ?, ?)");
            stmt.setInt(1, imageID);
            stmt.setInt(2, comment.getUserID());
            stmt.setString(3, comment.getText());
            stmt.setDate(4, comment.getDate());
            if (stmt.executeUpdate() > 0) {
                String sql = "SELECT LAST_INSERT_ID()";
                ResultSet data = connection.createStatement().executeQuery(sql);
                if (data.next()) {
                    comment.setId(data.getInt(1));
                    comment.setImageID(imageID);
                    return comment;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in CommentBean.postComment: " + e.getMessage());
        }
        return null;
    }
}
