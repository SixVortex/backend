package org.sustaining.sustaining_backend.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import org.sustaining.sustaining_backend.ConnectionFactory;
import org.sustaining.sustaining_backend.entities.Rating;

/**
 *
 * @author
 */
@Stateless
public class RatingBean {

    public List<Rating> getRatings(int imageID) {
        try ( Connection connection = ConnectionFactory.getConnection()) {

            String sql = "SELECT * FROM image_user_rating WHERE image_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, imageID);

            ResultSet result = stmt.executeQuery();
            List<Rating> ratings = new ArrayList();
            while (result.next()) {
                int score = result.getInt("score");
                int userID = result.getInt("user_id");
                ratings.add(new Rating(score, userID, imageID));
            }
            return ratings;
        } catch (Exception ex) {
            System.out.println("Error in RatingBean.getRatings: " + ex.getMessage());
        }
        return new ArrayList();
    }

    public boolean postRating(Rating rating) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            String sql = "INSERT INTO image_user_rating (user_id, image_id, score) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, rating.getUserID());
            stmt.setInt(2, rating.getImageID());
            stmt.setInt(3, rating.getRating());
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected == 1;
        } catch (Exception ex) {
            System.out.println("RatingBean.postRating: " + ex.getMessage());
            return false;
        }
    }
}
