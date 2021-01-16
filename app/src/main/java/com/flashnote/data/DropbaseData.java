package com.flashnote.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Timestamp;

public abstract class DropbaseData {
    @SerializedName("dropbase_id")
    @Expose
    private String dropbase_id;
    @SerializedName("dropbase_ts")
    @Expose
    private String dropbase_ts; // TODO: set timestamps to actual timestamp when db is ready
    
    public String getId() {
        return this.dropbase_id;
    }
    
    public String getTimestamp() {
        return this.dropbase_ts;
    }
}
