package com.flashnote.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
// interface for service to utilise
public interface ApiInterface {
    // I'm sure there's a way to shorten this but I'm not sure how
    @GET("/{db}/users")
    Call<List<User>> getUsersById(@Path("db") String db, @Header("Authorization") String token, @Query("dropbase_id") List<String> ids);
    @GET("/{db}/users")
    Call<List<User>> getUsersByUsername(@Path("db") String db, @Header("Authorization") String token, @Query("username") List<String> ids);

    @GET("/{db}/cards")
    Call<List<Card>> getCardsById(@Path("db") String db, @Header("Authorization") String token, @Query("dropbase_id") List<String> ids);
    @GET("/{db}/cards")
    Call<List<Card>> getCardsByUsername(@Path("db") String db, @Header("Authorization") String token, @Query("username") List<String> usernames);
    @GET("/{db}/cards")
    Call<List<Card>> getAllCards(@Path("db") String db, @Header("Authorization") String token);

    @GET("/{db}/maps")
    Call<List<IdMap>> getMapByCardId(@Path("db") String db, @Header("Authorization") String token, @Query("card_id") List<String> cardIds);
    @GET("/{db}/maps")
    Call<List<IdMap>> getMapByTagId(@Path("db") String db, @Header("Authorization") String token, @Query("tag_id") List<String> tagIds);

    @GET("/{db}/tags")
    Call<List<Tag>> getTagsById(@Path("db") String db, @Header("Authorization") String token, @Query("dropbase_id") List<String> ids);
    @GET("/{db}/tags")
    Call<List<Tag>> getTagsByUsername(@Path("db") String db, @Header("Authorization") String token, @Query("username") List<String> usernames);
    @GET("/{db}/tags")
    Call<List<Tag>> getAllTags(@Path("db") String db, @Header("Authorization") String token);
    
    
    //@GET("{pipeline}")
    //TODO: add pipeline running support
}
