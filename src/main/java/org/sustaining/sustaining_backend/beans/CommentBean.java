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
import org.sustaining.sustaining_backend.entities.Rating;

/**
 *
 * @author Silas
 */
@Stateless
public class CommentBean {

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

    public Comment postComment(Comment comment) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO comment (user_id, text, date) VALUES(?, ?, ?)");
            stmt.setInt(1, comment.getUserID());
            stmt.setString(2, comment.getText());
            stmt.setDate(3, comment.getDate());
            if (stmt.executeUpdate() > 0) {
                String sql = "SELECT LATEST_INSERT_ID()";
                ResultSet data = stmt.executeQuery(sql);
                if (data.next()) {
                    comment.setId(data.getInt(1));
                    return comment;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in CommentBean.postComment: " + e.getMessage());
        }
        return null;
    }
}
