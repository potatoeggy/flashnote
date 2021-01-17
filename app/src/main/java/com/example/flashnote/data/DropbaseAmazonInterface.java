package com.example.flashnote.data;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface DropbaseAmazonInterface {
    @PUT()
    @Headers({"Content-Type: text/plain"})
    Call<ResponseBody> addFileToAmazon(@Url String url, @Body RequestBody data);
}
