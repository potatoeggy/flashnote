package com.flashnote.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PipelineInterface {
    @POST("/v1/pipeline/generate_presigned_url")
    Call<DropbaseUploadJob> generatePresignedUrl(@Body DropbaseUploadJob token);
    //@GET("/v1/pipeline/run_pipeline")
    //Call<DropbaseUploadStatus> getJob(Body("job_id") String jobId);
    // above is banned by retrofit so unfortunately now we don't have status checks >:(
}
