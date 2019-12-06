/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.resources;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.sustaining.sustaining_backend.beans.PostBean;

/**
 * This class contains all the resources for posts.
 * @author Adrian
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {
    
    @EJB
    PostBean postBean;
    
    @GET
    @Path("posts/all/{numberOfPosts}")
    /**
     * This function is automatically called by the server when a GET request is sent to
     * the specified Path. It parses the amoun of posts to get and returns them in a Response.
     */
    public Response getPosts(@PathParam("numberOfPosts") int numberOfPosts){
        return postBean.getPosts(numberOfPosts);
    }
    
    @GET
    @Path("posts/fame/{numberOfPosts}")
    public Response getFamePosts(@PathParam("numberOfPosts") int numberOfPosts){
        return postBean.getFamePosts(numberOfPosts);
    }
    
    @GET
    @Path("posts/shame/{numberOfPosts}")
    public Response getShamePosts(@PathParam("numberOfPosts") int numberOfPosts){
        return postBean.getShamePosts(numberOfPosts);
    }
    
    @GET
    @Path("post/{lastImageID}")
    public Response getNextPost(@PathParam("lastImageID") int lastImageID){
        return postBean.getNextPost(lastImageID);
    }
}
