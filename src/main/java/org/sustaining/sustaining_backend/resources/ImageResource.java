/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.resources;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.sustaining.sustaining_backend.beans.ImageBean;
import org.sustaining.sustaining_backend.entities.Image;

/**
 *
 * @author Elev
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class ImageResource {
    
    @EJB
    ImageBean imageBean;
    
    @Path("image/{imageID}")
    @GET
    public Response getImage(@PathParam("imageID") int imageID){
        Image image = imageBean.getImage(imageID);
        return Response.status(Response.Status.OK).entity(image).build();
    }
    
    @Path("image")
    @Consumes(MediaType.APPLICATION_JSON)
    @POST
    public Response postImage(Image image){
        Image createdImage = imageBean.postImage(image);
        return Response.status(Response.Status.CREATED).entity(createdImage).build();
    }
}
