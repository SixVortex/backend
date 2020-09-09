/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;
import org.sustaining.sustaining_backend.ConnectionFactory;

/**
 * This class handles all the logic for admins
 * 
 * @author elias
 */
@Stateless
public class AdminBean {
    
    @EJB
    UserBean userBean;
    
    public Boolean CheckIfAdmin(String token){
        String userRank = userBean.getRank(token);
        return userRank.equals("admin");
    }
    
    public Response deleteImage(int imageID, String token){
        try (Connection connection = ConnectionFactory.getConnection()) {
            if(!CheckIfAdmin(token)){
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            PreparedStatement stmt = connection.prepareStatement("DELETE from sustain.image WHERE(id = ?);");
            stmt.setInt(1, imageID);
            if(stmt.execute()){
                return Response.status(Response.Status.OK).build();
            }
            else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            System.out.println("Error in ImageBean.deleteImage: " + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
    
    public Response deleteUser(int userID, String token){
        try (Connection connection = ConnectionFactory.getConnection()) {
            if(!CheckIfAdmin(token)){
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            PreparedStatement stmt = connection.prepareStatement("DELETE from sustain.user WHERE(id = ?);");
            stmt.setInt(1, userID);
            if(stmt.execute()){
                return Response.status(Response.Status.OK).build();
            }
            else{
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            System.out.println("UserBean.getRank: " + e.getMessage());
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}
