/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.beans;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.sustaining.sustaining_backend.ConnectionFactory;
import org.sustaining.sustaining_backend.entities.Rating;

/**
 *
 * @author Elev
 */
@Stateless
public class RatingBean {
    
    @EJB
    ConnectionFactory connectionFactory;
    
    public List<Rating> getRatings(int imageID){
        return new ArrayList();
    }
    
    public boolean postRating(Rating rating) throws SQLException{
        String sql = "INSERT INTO image_user_rating (user_id, image_id, score) VALUES (?, ?, ?)";
        PreparedStatement stmt = connectionFactory.getConnection().prepareStatement(sql);
        stmt.setInt(1, rating.getUserID());
        stmt.setInt(2, rating.getImageID());
        stmt.setInt(3, rating.getRating());
        int rowsAffected = stmt.executeUpdate();
        
        return rowsAffected == 1;
    }
}
