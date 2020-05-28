package org.sustaining.sustaining_backend.resources;

import org.sustaining.sustaining_backend.beans.UserBean;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("")
public class UserResource {

    @EJB
    UserBean userBean;

    @GET
    @Path("verify")
    public Response verify(@HeaderParam("authorization") String token) {
        System.out.println(token);
        if (userBean.verify(token)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @GET
    @Path("user/{userID}")
    public Response getUser(@PathParam("userID") int userID){
		String username = userBean.getUsername(userID);
		if(username != ""){
			return Response.status(Response.Status.OK).entity(username).build();
		}
		return Response.status(Response.Status.NOT_FOUND).build();
    }

}
