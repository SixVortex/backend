package org.sustaining.sustaining_backend.entities;

public class User {
    private int id;
    private int oauthId;
    private String username;

    public User() {

    }

    public User(int id, int oauthId, String username) {
        this.id = id;
        this.oauthId = oauthId;
        this.username = username;
	}
	
	public User(int id, String username){
		this.id = id;
        this.username = username;
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOauthId() {
        return oauthId;
    }

    public void setOauthId(int oauthId) {
        this.oauthId = oauthId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
