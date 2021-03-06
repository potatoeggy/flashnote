package com.example.flashnote.data;

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
public interface RestInterface {
    // I'm sure there's a way to shorten this but I'm not sure how
    @GET("/{db}/users")
    Call<List<User>> getUsersById(@Path("db") String db, @Header("Authorization") String token, @Query(value = "dropbase_id", encoded = true) String ids);
    @GET("/{db}/users")
    Call<List<User>> getUsersByUsername(@Path("db") String db, @Header("Authorization") String token, @Query(value = "username", encoded = true) String ids);
    @GET("/{db}/users")
    Call<List<User>> getAllUsers(@Path("db") String db, @Header("Authorization") String token);

    @GET("/{db}/cards3")
    Call<List<Card>> getCardsById(@Path("db") String db, @Header("Authorization") String token, @Query(value = "dropbase_id", encoded = true) String ids);
    @GET("/{db}/cards3")
    Call<List<Card>> getCardsByUsername(@Path("db") String db, @Header("Authorization") String token, @Query(value = "username", encoded = true) List<String> usernames);
    @GET("/{db}/cards3")
    Call<List<Card>> getCardsBySpecificity(@Path("db") String db, @Header("Authorization") String token, @Query(value = "username", encoded = true) String username, @Query(value = "question", encoded = true) String question, @Query(value = "answer", encoded = true) String answer);

    @GET("/{db}/maps")
    Call<List<IdMap>> getMapByCardId(@Path("db") String db, @Header("Authorization") String token, @Query(value = "cardid", encoded = true) List<String> cardIds);
    @GET("/{db}/maps")
    Call<List<IdMap>> getMapByTagId(@Path("db") String db, @Header("Authorization") String token, @Query(value = "tagid", encoded = true) String tagIds);

    @GET("/{db}/tags")
    Call<List<Tag>> getTagsById(@Path("db") String db, @Header("Authorization") String token, @Query(value = "dropbase_id", encoded = true) String ids);
    @GET("/{db}/tags")
    Call<List<Tag>> getTagsByUsername(@Path("db") String db, @Header("Authorization") String token, @Query(value = "username", encoded = true) String usernames);
    @GET("/{db}/tags")
    Call<List<Tag>> getTagByNameAndUsername(@Path("db") String db, @Header("Authorization") String token, @Query(value = "username", encoded = true) String usernames, @Query(value = "name", encoded = true) String tags);
    @GET("/{db}/tags")
    Call<List<Tag>> getAllTags(@Path("db") String db, @Header("Authorization") String token);
    
    @Headers({"Content-Type: application/x-www-form-url-encoded"})
    @POST("/v7.0/spellcheck")
    Call<SpellResult> getSpellcheck(@Header("Ocp-Apim-Subscription-Key") String key, @Query("mkt") String mkt, @Query("mode") String mode, @Query("text") String text);
}
