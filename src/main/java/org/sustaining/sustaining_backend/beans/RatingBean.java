package org.sustaining.sustaining_backend.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.sustaining.sustaining_backend.ConnectionFactory;
import org.sustaining.sustaining_backend.entities.Rating;

/**
 * This class handles all the logic for ratings of an Image.
 * @author Adrian
 */
@Stateless
public class RatingBean {

    @EJB
    UserBean userBean;
    
    /**
     * Gets all the ratings for a specific image.
     * @param imageID The id of the image to retrieve ratings from.
     * @return A list of the ratings for the image.
     */
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

    /**
     * Posts a rating to an image.
     * @param imageID The id of the image to rate.
     * @param rating The rating to post.
     * @param token The token of the user posting the comment.
     * @return True if success, false if something went wrong.
     */
    public boolean postRating(int imageID, Rating rating, String token) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO image_user_rating (user_id, image_id, score) VALUES (?, ?, ?)");
            int userId = userBean.getUserId(token);
            stmt.setInt(1, userId);
            stmt.setInt(2, imageID);
            stmt.setInt(3, rating.getRating());
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected == 1;
        } catch (Exception ex) {
            System.out.println("RatingBean.postRating: " + ex.getMessage());
            return false;
        }
    }
}
