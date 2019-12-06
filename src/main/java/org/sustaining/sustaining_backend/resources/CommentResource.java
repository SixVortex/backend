package org.sustaining.sustaining_backend.resources;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.sustaining.sustaining_backend.beans.CommentBean;
import org.sustaining.sustaining_backend.entities.Comment;

/**
 * This class contains all the resources for comments.
 * @author Adrian
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class CommentResource {
    
    @EJB
    CommentBean commentBean;
    
    @Path("comment/{imageID}")
    @GET
    public Response getComments(@PathParam("imageID") int imageID){
        List<Comment> imageComments = commentBean.getComments(imageID);
        return Response.ok().entity(imageComments).build();
    }
    
    @Path("comment/{imageID}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postComment(@PathParam("imageID") int imageID, Comment comment){
        Comment createdComment = commentBean.postComment(imageID, comment);
        return Response.status(Response.Status.CREATED).entity(createdComment).build();
    }
}
