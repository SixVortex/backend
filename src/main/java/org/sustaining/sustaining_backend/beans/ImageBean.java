/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import org.sustaining.sustaining_backend.ConnectionFactory;
import org.sustaining.sustaining_backend.entities.Image;

/**
 *
 * @author Lea
 */
@Stateless
public class ImageBean {
    
       public List<Image> getimage ( int imageID) {
           try (Connection connection = ConnectionFactory.getConnection()){
               
               String sql = "SELECT * FROM image WHERE image_id=?";
               PreparedStatement stmt = connection.prepareStatement(sql);
               stmt.setInt(1, imageID);
               
               ResultSet result = stmt.executeQuery();
               List<Image> images = new ArrayList();
               while(result.next()){
                   int date = result.getInt("date");
                   int title = result.getInt("title");
                   int location = result.getInt("location");
                   int image = result.getInt("image");
                    
               }
               return images;
           } catch (Exception ex) {
               return null;
           }
          
       }
    
    public Image getImage(int imageID){
        return null;
    }
    
    public Image postImage(Image image){
        return null;
    }
}
