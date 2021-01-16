package com.flashnote.data;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DropbaseAmazonInterface {
    @PUT("{path}")
    Call<ResponseBody> pushCardsToPipeline(@Path("path") String path, @Body List<Card> cards);
    
    @PUT("{path}")
    Call<ResponseBody> pushTagsToPipeline(@Path("path") String path, @Body List<Tag> tags);
    
    @PUT("{path}")
    Call<ResponseBody> pushIdMapToPipeline(@Path("path") String path, @Body List<IdMap> idMaps);
}
