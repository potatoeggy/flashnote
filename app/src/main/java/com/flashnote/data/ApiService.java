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
    
    private static Call<List<Card>> getCardsByIdMap(List<IdMap> idMaps)  { // hard
        String cardIds = "in.(";
        for (IdMap map : idMaps) {
            cardIds += map.getCardId() + ",";
        }
        cardIds = cardIds.substring(0,cardIds.length()-1) + ")";
        
        Call<List<Card>> cardCall = restService.getCardsById(DROPBASE_DB, DROPBASE_AUTH, cardIds);
        return cardCall;
    }
    
    private static Call<List<Tag>> getTagsByIdMap(List<IdMap> idMaps) {
        String tagIds = "in.(";
        for (IdMap map : idMaps) {
            tagIds += map.getTagId() + ",";
        }
        tagIds = tagIds.substring(0, tagIds.length()-1) + ")";
        return restService.getTagsById(DROPBASE_DB, DROPBASE_AUTH, tagIds);
    }

    public static void getCardsByTag(final List<Tag> tags) { // TODO — critical: call getTagsByCard for each one
        new Thread() {
            public void run() {
                DataStateHelper.getHelperLock();
                String tagIds = "in.(";
                for (Tag t : tags) {
                    if (t.getId() == null) System.out.println("ERROR: NULL TAG ID"); // TODO: call the server for tag id
                    tagIds += t.getId() + ",";
                }
                tagIds = tagIds.substring(0, tagIds.length()-1) + ")";
                Call<List<IdMap>> idMapCall = restService.getMapByTagId(DROPBASE_DB, DROPBASE_AUTH, tagIds);
                try {
                    Response<List<IdMap>> mapResponse = idMapCall.execute();
                    if (mapResponse.isSuccessful()) {
                        Response<List<Card>> cardResponse = getCardsByIdMap(mapResponse.body()).execute();
                        if (cardResponse.isSuccessful()) {
                            DataStateHelper.setCardList(cardResponse.body());
                        } else {
                            System.out.println("ERROR: " + cardResponse.toString());
                        }
                    } else {
                        System.out.println("ERROR: " + mapResponse.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataStateHelper.dropHelperLock();
            }
        }.start();
    }
    
    public static void getCardsByUsername(final String username) { // hard (because no tags) — alternatively, modify data structure to include a rudimentary bit to fast process locally
        new Thread() {
            public void run() {
                DataStateHelper.getHelperLock();
                List<String> usernames = new ArrayList<String>();
                usernames.add("eq." + username);
                Call<List<Card>> cardCall = restService.getCardsByUsername(DROPBASE_DB, DROPBASE_AUTH, usernames);
                try {
                    Response<List<Card>> cardResponse = cardCall.execute();
                    if (cardResponse.isSuccessful()) {
                        DataStateHelper.setCardList(cardResponse.body());
                    } else {
                        System.out.println("ERROR: " + cardResponse.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataStateHelper.dropHelperLock();
            }
        }.start();
    }

    public static void getTagsByCard(final Card card) { // hard
        new Thread() {
            public void run() {
                DataStateHelper.getHelperLock();
                if (card.getId() == null) System.out.println("ERROR: NULL CARD ID");
                List<String> cardIds = new ArrayList<String>();
                cardIds.add(card.getId());
                Call<List<IdMap>> idMapCall = restService.getMapByCardId(DROPBASE_DB, DROPBASE_AUTH, cardIds);
                try {
                    Response<List<IdMap>> mapResponse = idMapCall.execute();
                    if (mapResponse.isSuccessful()) {
                        Response<List<Tag>> tagResponse = getTagsByIdMap(mapResponse.body()).execute();
                        if (tagResponse.isSuccessful()) {
                            DataStateHelper.setTagList(tagResponse.body());
                        } else {
                            System.out.println("ERROR: " + tagResponse.toString());
                        }
                    } else {
                        System.out.println("ERROR: " + mapResponse.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataStateHelper.dropHelperLock();
            }
        }.start();
    }
    
    public static void getTagsByUsername(final String username) {
        new Thread() {
            public void run() {
                DataStateHelper.getHelperLock();
                String usernames = "eq." + username;
                Call<List<Tag>> tagCall = restService.getTagsByUsername(DROPBASE_DB, DROPBASE_AUTH, usernames);
                try {
                    Response<List<Tag>> tagResponse = tagCall.execute();
                    if (tagResponse.isSuccessful()) {
                        DataStateHelper.setTagList(tagResponse.body());
                    } else {
                        System.out.println("ERROR: " + tagResponse.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataStateHelper.dropHelperLock();
            }
        }.start();
    }

    public static void getUserByUsername(final String username) {
        new Thread() {
            public void run() {
                DataStateHelper.getHelperLock();
                String usernames = "eq." + username;
                Call<List<User>> userCall = restService.getUser(DROPBASE_DB, DROPBASE_AUTH, usernames);
                try {
                    Response<List<User>> userResponse = userCall.execute();
                    if (userResponse.isSuccessful()) {
                        DataStateHelper.setUser(userResponse.body().get(0));
                    } else {
                        System.out.println("ERROR: " + userResponse.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.out.println("Null pointer as user, probably none found");
                }
                DataStateHelper.dropHelperLock();
            }
        }.start();
    }
/*
    public static void main(String[] args) {
        List<Tag> list = new ArrayList<Tag>();
        list.add(new Tag("chemistry", "potatoeggy", "#FFFFFF"));
        list.get(0).setId("06ec495e-c36a-4a62-a41e-aeebe35d9bb2");
        getTagsByUsername("potatoeggy");
        List<Tag> tags = DataStateHelper.getClientTagList();
        System.out.println(tags.size());
        for (Tag t : tags) System.out.println(t.toString());
    }

 */
}
