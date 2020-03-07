package org.sustaining.sustaining_backend.resources;

import org.sustaining.sustaining_backend.beans.UserBean;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

public class UserResource {

    @EJB
    UserBean userBean;

    @GET
    @Path("/verify")
    public Response verify(@HeaderParam("authorization") String token) {
        if (userBean.verify(token)) {
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
