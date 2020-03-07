package org.sustaining.sustaining_backend.beans;

import javax.ejb.Stateless;

@Stateless
public class UserBean {
    public String getToken(String code) {
        String token = "";
        return token;
    }

    public boolean verify(String token) {
        return false;
    }
}
