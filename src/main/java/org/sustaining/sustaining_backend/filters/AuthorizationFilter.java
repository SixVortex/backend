/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sustaining.sustaining_backend.filters;

import java.io.IOException;
import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.sustaining.sustaining_backend.beans.UserBean;

/**
 *
 * @author giman
 */
@Provider
public class AuthorizationFilter implements ContainerRequestFilter {

    @EJB
    UserBean userBean;
    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if(!requestContext.getMethod().equals("GET")){
            if(!requestContext.getHeaders().containsKey("authorization") || !userBean.verify(requestContext.getHeaders().getFirst("authorization"))){
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            }
        }
    }
    
}
