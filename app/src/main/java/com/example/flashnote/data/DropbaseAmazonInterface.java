package com.example.flashnote.data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Url;

import java.util.List;

public interface DropbaseAmazonInterface {
    @PUT
    Call<ResponseBody> pushCardsToPipeline(@Url String url, @Body List<Card> cards);
    
    @PUT
    Call<ResponseBody> pushTagsToPipeline(@Url String url, @Body List<Tag> tags);
    
    @PUT
    Call<ResponseBody> pushIdMapToPipeline(@Url String url, @Body List<IdMap> idMaps);
}
