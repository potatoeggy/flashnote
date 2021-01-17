package com.example.flashnote.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tag extends DropbaseData {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("colour")
    @Expose
    private String colour; // TODO: decide how to store colour

    public Tag(String name, String username, String colour) {
        this.name = name;
        this.username = username;
        this.colour = colour;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getColour() {
        return this.colour;
    }
    
    public void setName(String name) { // TODO: handle overwrites (should be a simple update)
        this.name = name;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return this.name + " " + this.username + " " + this.colour;
    }
}
