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
import org.sustaining.sustaining_backend.ConnectionFactory;
import org.sustaining.sustaining_backend.entities.Image;

/**
 *
 * @author Lea
 */
@Stateless
public class ImageBean {

    public List<Image> getImages() {
        try (Connection connection = ConnectionFactory.getConnection()) {

            String sql = "SELECT * FROM image";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet result = stmt.executeQuery();
            List<Image> images = new ArrayList();
            while (result.next()) {
                int id = result.getInt("id");
                int userID = result.getInt("user_id");
                Date date = result.getDate("date");
                String title = result.getString("title");
                int location = result.getInt("location");
                String imageData = result.getString("image");
                
                Image image = new Image(id, userID, date, location, imageData, title);
                images.add(image);
            }
            return images;
        } catch (Exception ex) {
            System.out.println("ImageBean.getImages: " + ex.getMessage());
            return null;
        }

    }

    public Image getImage(int imageID){
        return null;
    }

    public Image postImage(Image image) {
        return null;
    }
}
