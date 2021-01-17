package com.example.flashnote.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FlaggedToken {

    @SerializedName("offset")
    @Expose
    private Integer offset;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("suggestions")
    @Expose
    private List<Suggestion> suggestions = null;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

}