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
import javax.ejb.Stateless;
import org.sustaining.sustaining_backend.entities.Comment;
import org.sustaining.sustaining_backend.entities.Rating;

/**
 *
 * @author Silas
 */
@Stateless
public class CommentBean {
    
    public List<Comment> getComments(int imageID){
        try(Connection connection = ConnectionFactory.getConnection()){
            
            String sql = "SELECT * FROM comment WHERE image_id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, imageID);
            
            ResultSet result = stmt.executeQuery();
            List<Comment> comments = new ArrayList();
            while(result.next()){
                int id = result.getInt("id");
                int userID = result.getInt("user_id");
                String text = result.getString("text");
                Date date = result.getDate("date");
                comments.add(new Comment(id, imageID, userID, text, date));
            }
            
            return comments;
        } catch (Exception ex) {
            
        }
        return new ArrayList();
    }
  }
