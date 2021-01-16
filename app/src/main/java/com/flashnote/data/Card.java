package com.flashnote.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Card extends DropbaseData {
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("tags")
    @Expose
    private String tags; // TODO: set tags to List<Tag> when db is ready
    
    public Card(String username, String question, String answer, String tags) {
        this.username = username;
        this.question = question;
        this.answer = answer;
        this.tags = tags;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getQuestion() {
        return this.question;
    }
    
    public String getAnswer() {
        return this.answer;
    }
    
    public String getTags() {
        return this.tags;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setQuestion(String question) {
        this.question = question;
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }
    
    public String toString() {
        return this.username + " Q: " + this.question + " A: " + this.answer + "\n with tags: ";
        // TODO: return tags
    }
}
