package com.example.flashnote.data;

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
    private List<Tag> tagList;
    
    public Card(String username, String question, String answer, List<Tag> tagList) {
        this.username = username;
        this.question = question;
        this.answer = answer;
        this.tagList = tagList;
        this.tags = "";
        for (Tag t : tagList) {
            this.tags += t.getName() + ":" + t.getColour() + ",";
        }
        this.tags = this.tags.substring(0, this.tags.length()-1);
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
    
    public List<Tag> getTagList() {
        if (this.tagList == null) {
            String[] mockTagList = tags.split(",");
            for (String s : mockTagList) {
                String[] tag = s.split(":");
                this.tagList.add(new Tag(tag[0], DataState.getLocalUsername(), tag[1]));
            }
        }
        return this.tagList;
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
    
    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
        this.tags = "";
        if (tagList == null) return;
        for (Tag t : tagList) {
            this.tags += t.getName() + ":" +t.getColour() + ",";
        }
        this.tags = this.tags.substring(0, this.tags.length()-1);
    }
    
    public String toString() {
        return this.username + " Q: " + this.question + " A: " + this.answer + "\n with tags: " + this.tags;
        // TODO: return tags
    }
}
