package org.sustaining.sustaining_backend.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.sustaining.sustaining_backend.ConnectionFactory;
import org.sustaining.sustaining_backend.entities.Image;

/**
 * This class handles all the logic for images.
 *
 * @author Lea
 */
@Stateless
public class ImageBean {

	@EJB
	UserBean userBean;

	/**
	 * Gets all the images available on the database.
	 *
	 * @return A list of all the images on the database.
	 */
	public List<Image> getImages() {
		try (Connection connection = ConnectionFactory.getConnection()) {

			String sql = "SELECT * FROM full_image_info";
			PreparedStatement stmt = connection.prepareStatement(sql);

			ResultSet result = stmt.executeQuery();
			List<Image> images = new ArrayList();
			while (result.next()) {
				int imageID = result.getInt("image_id");
				int userID = result.getInt("user_id");
				Date date = result.getDate("date");
				String location = result.getString("location");
				String imageData = result.getString("image");
				String title = result.getString("title");
				int rating = result.getInt("rating");
				String username = result.getString("user");
				int fameCount = result.getInt("fame_count");
				int shameCount = result.getInt("shame_count");

				Image image = new Image(imageID, userID, date, location, imageData, title, rating, username, fameCount,
						shameCount);
				images.add(image);
			}
			return images;
		} catch (Exception ex) {
			System.out.println("ImageBean.getImages: " + ex.getMessage());
			return null;
		}

	}

	/**
	 * Gets a specific image from the database.
	 *
	 * @param imageID The id of the image to retrieve.
	 * @return An image instance with all the properties of the image.
	 */
	public Image getImage(int imageID) {
		Image image = null;
		try (Connection connection = ConnectionFactory.getConnection()) {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM full_image_info WHERE id = ?");
			stmt.setInt(1, imageID);
			ResultSet data = stmt.executeQuery();
			if (data.next()) {
				image = new Image(data.getInt("image_id"), data.getInt("user_id"), data.getDate("date"),
						data.getString("location"), data.getString("image"), data.getString("title"),
						data.getInt("rating"), data.getString("user"), data.getInt("fame_count"),
						data.getInt("shame_count"));
			}
		} catch (Exception e) {
			System.out.println("Error in ImageBean.getImage: " + e.getMessage());
		}
		return image;
	}

	/**
	 * Posts an image to the database.
	 *
	 * @param image The image to post.
	 * @param token The token of the user posting the comment.
	 * @return The same image but with the auto-generated id attached to it.
	 */
	public Image postImage(Image image, String token) {

		
		String fileName = "";
        String fileType = "png";
		fileName = getAlphaNumericString(20);

		String imageString = image.getImage();

		String[] imageSplit = imageString.split(",");

		/*
		try{
			
			do {
				
			} while (Files.exists(Paths.get("../Images/"+fileName+"."+fileType).toAbsolutePath().normalize()));
            Files.write(Paths.get("../Images/"+fileName+"."+fileType).toAbsolutePath().normalize(), createBytes(image.getImage()));
        }catch(IOException e){
            System.out.println("Error FileBean.saveOnComp: "+ e.getMessage());
			return null;
        }
        String imgUrl = "C:\\Users\\Tim\\Payara_Server\\glassfish\\domains\\domain1\\config\\Images\\"+fileName+"."+fileType;        
		*/

		try {
			URL url = new URL("ftp://192206_sustain:5EbA5T1aN@ftp.sustaining.te-vxo.se/images/"+ fileName+"."+fileType +";type=i");
			URLConnection urlc = url.openConnection();
			OutputStream os = urlc.getOutputStream(); // To upload
			os.write(createBytes(imageSplit[1]));
		} catch (MalformedURLException e1) {
			System.out.println("Error in ImageBean.postImage: " + e1.getMessage());
			return null;
		} catch (IOException e1) {
			System.out.println("Error in ImageBean.postImage: " + e1.getMessage());
			return null;
		}

		String imgUrl = "http://sustaining.te-vxo.se/images/"+ fileName+"."+fileType;
		image.setImage(imgUrl);
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO image (user_id, date, location, image, title) VALUES(?, ?, ?, ?, ?)");
            
            int userId = userBean.getUserId(token);
            stmt.setInt(1, userId);
            stmt.setDate(2, image.getDate());
            stmt.setString(3, image.getLocation());
            stmt.setString(4, image.getImage());
            stmt.setString(5, image.getTitle());
            if (stmt.executeUpdate() > 0) {
                String sql = "SELECT LAST_INSERT_ID()";
                ResultSet data = stmt.executeQuery(sql);
                if (data.next()) {
                    image.setId(data.getInt(1));
                }
            } else {
                return null;
            }
            stmt = connection.prepareStatement("INSERT INTO image_user_rating (user_id, image_id, score) VALUES (?, ?, ?)");
            stmt.setInt(1, userId);
            stmt.setInt(2, image.getId());
            stmt.setInt(3, image.getRating());
            stmt.executeUpdate();

            return image;
        } catch (Exception e) {
			System.out.println("Error in ImageBean.postImage: " + e.getMessage());
			return null;
        }
	}
	
	public byte[] createBytes(String b64String){
        return Base64.getDecoder().decode(b64String);
	}
	
	static String getAlphaNumericString(int n) 
    { 
  
        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
            .charAt(index)); 
        } 
  
        return sb.toString();
    }
}
