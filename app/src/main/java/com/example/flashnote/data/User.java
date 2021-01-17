package com.example.flashnote.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User extends DropbaseData {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    
    public User(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }

    public void setPassword() {
        this.password = password;
    }
}
