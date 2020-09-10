/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.resources;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.sustaining.sustaining_backend.beans.AdminBean;

/**
 * 
 * 
 * @author elias
 */
@Path("admin")
public class AdminResource {
    
    @EJB
    AdminBean adminBean;
    
    @DELETE
    @Path("image/{imageID}")
    public Response deletePost(@PathParam("imageID") int imageID, @HeaderParam("authorization") String token){
        return adminBean.deleteImage(imageID, token);
    }
    
    @DELETE
    @Path("user/{userID}")
    public Response deleteUser(@PathParam("userID") int userID, @HeaderParam("authorization") String token){
        return adminBean.deleteUser(userID, token);
    }
    
    @GET
    @Path("users/{userAmount}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(@PathParam("userAmount") int userAmount){
        return adminBean.getUsers(userAmount);
    }
}
