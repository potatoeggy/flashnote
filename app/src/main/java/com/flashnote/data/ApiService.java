package com.flashnote.data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// what everything calls
public class ApiService {
    private static ApiInterface service = ApiAdapter.getQueryRetrofit().create(ApiInterface.class);
    // TODO: remove api keys there should not be api keys
    private final static String DROPBASE_DB = "bWTRB7iSwazzub4P7HZnQS";
    private final static String DROPBASE_PIPELINE = "v1/pipeline/run_pipeline";
    private final static String DROPBASE_AUTH = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhYmFzZUlkIjoiYldUUkI3aVN3YXp6dWI0UDdIWm5RUyIsImFjY2Vzc1Blcm0iOiJmdWxsIiwidG9rZW5JZCI6IjlsQTJaaFBZOWhkWFl6WXFtWUxPdWp2RWhIUWU3QnJBcW5LRTE0YWd0dHJCTXV2ZEt0NkNzQXB5VmYxcmNMbGEiLCJpYXQiOjE2MTA3NzUzNDIsImV4cCI6MTYxMTcyNTc0MiwiaXNzIjoiZHJvcGJhc2UuaW8iLCJzdWIiOiJLemJEeGpGVWZ0eDJtOFV5Vld6SkJrIn0.YvlWqXNZq5NtwAMOzrijwhsi0z1Joc74BsKH_5_PW7g";
    
    public static void getCardById(Callback<Card> callback) {

    }

    public static void main(String[] args) {
        /* samples
        Call<List<Card>> asyncCall = service.getAllCards(DROPBASE_DB, DROPBASE_AUTH);
        
        asyncCall.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                } else {
                    System.out.println("ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) {
                System.out.println("ERROR FAIL: " + t.getLocalizedMessage());
            }
        });
        */
    }
}
