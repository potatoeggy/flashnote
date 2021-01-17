package com.example.flashnote.data;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PipelineInterface {
    @POST("/{pipeline}/generate_presigned_url")
    Call<DropbaseUploadJob> generatePresignedUrl(@Path("pipeline") String pipeline, @Body DropbaseUploadJob token);
    @GET("/{pipeline}/run_pipeline")
    Call<DropbaseUploadStatus> getJob(@Path("pipeline") String pipeline, @Body DropbaseUploadStatus jobId);
}
