package com.flashnote.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DropbaseUploadJob {
    @SerializedName("upload_url")
    @Expose
    private String upload_url;
    @SerializedName("job_id")
    @Expose
    private String job_id;
    @SerializedName("token")
    @Expose
    private String token;
    
    public DropbaseUploadJob(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return this.token;
    }
    
    public String getUploadUrl() {
        return this.upload_url;
    }
    
    public String getJobId() {
        return this.job_id;
    }
}
