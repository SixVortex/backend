package org.sustaining.sustaining_backend.beans;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.Stateless;
import java.util.Collections;
import org.sustaining.sustaining_backend.ConnectionFactory;
import org.sustaining.sustaining_backend.entities.User;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class UserBean {

    private static final String CLIENT_ID = "460081610512-2oj0qq8g65u4gk91obanganpq63tt3m2.apps.googleusercontent.com";

    public boolean verify(String token) {
        try {
            JsonFactory jsonFactory = new JacksonFactory();
            HttpTransport transport = new NetHttpTransport();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            // (Receive idTokenString by HTTPS POST)
            System.out.println(token);
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                String name = (String) payload.get("name");
                System.out.println("Name: " + name);

                return true;
            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (Exception e) {
            System.out.println("Error in UserBean.verify: " + e.getMessage());
        }
        return false;
    }

    public int getUserId(String token) throws Exception {
        try {
            JsonFactory jsonFactory = new JacksonFactory();
            HttpTransport transport = new NetHttpTransport();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    .build();

            System.out.println(token);
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                String oauthId = payload.getSubject();
                String name = (String) payload.get("name");

                String sql = "SELECT getUserId(?, ?)";
                PreparedStatement stmt = ConnectionFactory.getConnection().prepareStatement(sql);
                stmt.setString(1, oauthId);
                stmt.setString(2, name);
                ResultSet data = stmt.executeQuery();

                if (data.first()) {
                    return data.getInt(1);
                }

            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (Exception ex) {
            System.out.println("UserBean.getUserId: " + ex.getMessage());
        }
        throw new Exception("Failed to get userId");
    }

    public String getUsername(int userID) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT username from sustain.user WHERE(id = ?);");
            stmt.setInt(1, userID);
            ResultSet data = stmt.executeQuery();
            data.next();
            return data.getString("username");
        } catch (Exception e) {
            System.out.println("UserBean.getUsername: " + e.getMessage());
        }
        return "";
    }
    
    public String getRank(String token){
        try {
            int userID = getUserId(token);
            
            try (Connection connection = ConnectionFactory.getConnection()) {
                PreparedStatement stmt = connection.prepareStatement("SELECT rank from sustain.user WHERE(id = ?);");
                stmt.setInt(1, userID);
                ResultSet data = stmt.executeQuery();
                data.next();
                return data.getString("rank");
            } catch (Exception e) {
                System.out.println("UserBean.getRank: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("UserBean.getRank: " + e.getMessage());
        }
        return "user";
    }
    
    public void deleteUser(int userID){
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE * from sustain.user WHERE(id = ?);");
            stmt.setInt(1, userID);
            ResultSet result = stmt.executeQuery();
        } catch (Exception e) {
            System.out.println("UserBean.getRank: " + e.getMessage());
        }
    }
}
