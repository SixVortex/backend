package org.sustaining.sustaining_backend.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
        try ( Connection connection = ConnectionFactory.getConnection()) {

            String sql = "SELECT * FROM image";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet result = stmt.executeQuery();
            List<Image> images = new ArrayList();
            while (result.next()) {
                int id = result.getInt("id");
                int userID = result.getInt("user_id");
                Date date = result.getDate("date");
                String title = result.getString("title");
                String location = result.getString("location");
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

    public Image getImage(int imageID) {
        Image image = null;
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM image WHERE id = ?");
            stmt.setInt(1, imageID);
            ResultSet data = stmt.executeQuery();
            if (data.next()) {
                image = new Image(data.getInt("id"), data.getInt("user_id"), data.getDate("date"), data.getString("location"), data.getString("image"), data.getString("title"));
            }
        } catch (Exception e) {
            System.out.println("Error in ImageBean.getImage: " + e.getMessage());
        }
        return image;
    }

    public Image postImage(Image image) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO image (user_id, date, location, image, title) VALUES(?, ?, ?, ?, ?)");
            stmt.setInt(1, image.getUserID());
            stmt.setDate(2, image.getDate());
            stmt.setString(3, image.getLocation());
            stmt.setString(4, image.getImage());
            stmt.setString(5, image.getTitle());
            if (stmt.executeUpdate() > 0) {
                String sql = "SELECT LAST_INSERT_ID()";
                ResultSet data = stmt.executeQuery(sql);
                if (data.next()) {
                    image.setId(data.getInt(1));
                    return image;
                }
            }
        } catch (Exception e) {
            System.out.println("Error in ImageBean.postImage: " + e.getMessage());
        }
        return null;
    }
}
