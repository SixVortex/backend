/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.sustaining.sustaining_backend.beans.RatingBean;
import org.sustaining.sustaining_backend.entities.Rating;

/**
 *
 * @author Elev
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class RatingResource {
    
    @EJB
    RatingBean ratingBean;
    
    @Path("rating/{imageID}")
    @GET
    public Response getRatings(@PathParam("imageID") int imageID){
        List<Rating> imageRating = ratingBean.getRatings(imageID);
        return Response.status(Response.Status.OK).entity(imageRating).build();
    }
    
    @Path("rating")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response postRating(Rating rating){
        boolean success = ratingBean.postRating(rating);
        return Response.status(success ? Response.Status.CREATED : Response.Status.BAD_REQUEST).build();
    }
}
