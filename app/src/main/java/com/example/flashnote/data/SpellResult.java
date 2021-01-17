package com.example.flashnote.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpellResult {

    @SerializedName("_type")
    @Expose
    private String type;
    @SerializedName("flaggedTokens")
    @Expose
    private List<FlaggedToken> flaggedTokens = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FlaggedToken> getFlaggedTokens() {
        return flaggedTokens;
    }

    public void setFlaggedTokens(List<FlaggedToken> flaggedTokens) {
        this.flaggedTokens = flaggedTokens;
    }

}