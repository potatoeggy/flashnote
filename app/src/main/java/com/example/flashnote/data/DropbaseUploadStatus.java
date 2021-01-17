package com.example.flashnote.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DropbaseUploadStatus {
    @SerializedName("job_id")
    @Expose
    private String job_id;
    @SerializedName("str")
    @Expose
    private String str;
    @SerializedName("status_code")
    @Expose
    private String status_code;
    
    public DropbaseUploadStatus(String job_id) {
        this.job_id = job_id;
    }
    
    public String getJobId() {
        return this.job_id;
    }
    
    public String getStatusCode() {
        return this.status_code;
    }
    
    public String getProcessingStage() {
        return this.str;
    }
}
