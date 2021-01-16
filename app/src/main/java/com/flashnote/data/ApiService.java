package com.flashnote.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// what everything calls
public class ApiService {
    private static RestInterface restService = ApiAdapter.getQueryRetrofit().create(RestInterface.class);
    private static PipelineInterface pipelineService = ApiAdapter.getPipelineRetrofit().create(PipelineInterface.class);
    private static DropbaseAmazonInterface uploadService = ApiAdapter.getEmptyRetrofit().create(DropbaseAmazonInterface.class);
    // TODO: remove api keys there should not be api keys
    private final static String DROPBASE_DB = "bWTRB7iSwazzub4P7HZnQS";
    private final static String DROPBASE_PIPELINE = "v1/pipeline/";
    private final static String DROPBASE_AUTH = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhYmFzZUlkIjoiYldUUkI3aVN3YXp6dWI0UDdIWm5RUyIsImFjY2Vzc1Blcm0iOiJmdWxsIiwidG9rZW5JZCI6IjlsQTJaaFBZOWhkWFl6WXFtWUxPdWp2RWhIUWU3QnJBcW5LRTE0YWd0dHJCTXV2ZEt0NkNzQXB5VmYxcmNMbGEiLCJpYXQiOjE2MTA3NzUzNDIsImV4cCI6MTYxMTcyNTc0MiwiaXNzIjoiZHJvcGJhc2UuaW8iLCJzdWIiOiJLemJEeGpGVWZ0eDJtOFV5Vld6SkJrIn0.YvlWqXNZq5NtwAMOzrijwhsi0z1Joc74BsKH_5_PW7g";
    
    public static Call<List<Card>> getCardsByTag(Tag tag) throws Exception { // hard
        List<Tag> singleTag = new ArrayList<Tag>();
        singleTag.add(tag);
        return null;
    }

    public static Call<List<IdMap>> getCardsByTag(List<Tag> tags) throws Exception {
        List<String> tagIds = new ArrayList<String>();
        for (Tag t : tags) {
            if (t.getId() == null) throw new Exception("i'll fix later please never run into this");
            tagIds.add(t.getId());
        }

        Call<List<IdMap>> idMapCall = restService.getMapByTagId(DROPBASE_DB, DROPBASE_AUTH, tagIds);

        return idMapCall;
    }
    
    public static Call<List<Card>> getCardsByUsername(String username) { // hard (because no tags) — alternatively, modify data structure to include a rudimentary bit to fast process locally
        return null;
    }
    
    public static Call<List<Card>> getAllCards() { // hard
        return null;
    }
    
    public static Call<List<Tag>> getTagsByCard(Card card) { // hard
        return null;
    }
    
    public static Call<List<Tag>> getTagsByUsername(String username) {
        List<String> usernames = new ArrayList<String>();
        usernames.add(username);
        return restService.getTagsByUsername(DROPBASE_DB, DROPBASE_AUTH, usernames);
    }
    
    public static Call<List<Tag>> getTagsByUsername(List<String> usernames) {
        return restService.getTagsByUsername(DROPBASE_DB, DROPBASE_AUTH, usernames);
    }
    
    public static Call<List<User>> getUsersByUsername(String username) {
        List<String> usernames = new ArrayList<String>();
        usernames.add(username);
        return restService.getUsersByUsername(DROPBASE_DB, DROPBASE_AUTH, usernames);
    }

    public static void main(String[] args) {
        List<Tag> tags = new ArrayList<Tag>();
        tags.add(new Tag("bleh", "bleh", "bleh"));
        tags.get(0).setId("06ec495e-c36a-4a62-a41e-aeebe35d9bb2");
        try {
            for (Card c : getCardsByTag(tags)) {
                System.out.println(c);
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            System.out.println(e.fillInStackTrace());
        }
        // demo
        /*
        Call<List<Card>> asyncCall = restService.getAllCards(DROPBASE_DB, DROPBASE_AUTH);
        
        asyncCall.enqueue(new Callback<List<Card>>() {
            @Override
            public void onResponse(Call<List<Card>> call, Response<List<Card>> response) {
                if (response.isSuccessful()) { // if everything worked
                    System.out.println(response.body());
                } else { // if connection right but api problem
                    System.out.println("ERROR: " + response.raw());
                }
            }

            @Override
            public void onFailure(Call<List<Card>> call, Throwable t) { // if network problem
                System.out.println("ERROR FAIL: " + t.getLocalizedMessage());
                t.printStackTrace();
            }
        });
        */

    }
}
