package com.flashnote.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdMap extends DropbaseData {
    @SerializedName("cardId")
    @Expose
    private String cardId;
    @SerializedName("tagId")
    @Expose
    private String tagId;
    
    public String getCardId() {
        return this.cardId;
    }

    public String getTagId() {
        return this.tagId;
    }
}
