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
 *
 * @author Adrian
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    @EJB
    PostBean postBean;

    /**
     * This function is automatically called by the server when a GET request is sent to
     * the specified Path. It parses the amount of posts to get and returns them in a Response.
     *
     * @param numberOfPosts
     * @return
     */
    @GET
    @Path("posts/all/{numberOfPosts}")
    public Response getPosts(@PathParam("numberOfPosts") int numberOfPosts) {
        return postBean.getPosts(numberOfPosts);
    }

    /**
     * Gets the a number of posts with fame rating
     * @param numberOfPosts Number of posts to get
     * @return
     */
    @GET
    @Path("posts/fame/{numberOfPosts}")
    public Response getFamePosts(@PathParam("numberOfPosts") int numberOfPosts) {
        return postBean.getFamePosts(numberOfPosts);
    }

    /**
     * @param numberOfPosts
     * @return
     */
    @GET
    @Path("posts/shame/{numberOfPosts}")
    public Response getShamePosts(@PathParam("numberOfPosts") int numberOfPosts) {
        return postBean.getShamePosts(numberOfPosts);
    }

    @GET
    @Path("post/{lastImageID}")
    public Response getNextPost(@PathParam("lastImageID") int lastImageID) {
        return postBean.getNextPost(lastImageID);
    }
}
