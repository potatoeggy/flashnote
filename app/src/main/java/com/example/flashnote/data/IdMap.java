package com.example.flashnote.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdMap extends DropbaseData {
    @SerializedName("cardid")
    @Expose
    private String cardid;
    @SerializedName("tagid")
    @Expose
    private String tagid;

    public IdMap(String cardid, String tagid) {
        this.cardid = cardid;
        this.tagid = tagid;
    }

    public String getCardId() {
        return this.cardid;
    }

    public String getTagId() {
        return this.tagid;
    }
}
