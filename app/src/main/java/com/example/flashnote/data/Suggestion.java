package com.example.flashnote.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Suggestion {

    @SerializedName("suggestion")
    @Expose
    private String suggestion;
    @SerializedName("score")
    @Expose
    private Double score;

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}