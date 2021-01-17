package com.example.flashnote.data;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    private final static String DROPBASE_CARD_PIPELINE = "PVcqRDgBwZ9M2TLeJnyaeW";
    private final static String DROPBASE_MAP_PIPELINE = "eUMJXYAUDo3maRcW6UfRif";
    private final static String DROPBASE_TAG_PIPELINE = "Tdzxn5nMYKXGUWrzwEfmRX";
    private final static String DROPBASE_USER_PIPELINE = "nuTRpPGJsybXYvwcdvE9fs";
    
    private final static String BING_TRANSLATE_KEY = "73506c522ea9455c910eb0511a82da17";
    
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
    
    public static void spellCheck(final String text) {
        new Thread() {
            public void run() {
                DataStateHelper.getHelperLock();
                String mkt = "global", mode = "proof";
                Call<SpellResult> spellCall = restService.getSpellcheck(BING_TRANSLATE_KEY, mkt, mode, text);
                try {
                    Response<SpellResult> spellResponse = spellCall.execute();
                    if (spellResponse.isSuccessful()) {
                        SpellResult result = spellResponse.body();
                        String corrected = "";
                        for (FlaggedToken f : result.getFlaggedTokens()) {
                            if (f.getSuggestions().size() > 0) corrected += f.getSuggestions().get(0);
                            else corrected += f.getToken();
                            corrected += " ";

                        }
                    } else {
                        System.out.println(spellResponse.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                DataStateHelper.dropHelperLock();
            }
        }.start();
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
                } catch (NullPointerException e) {
                    System.out.println("Null pointer as map, probably none found (a lil unusual though)");
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
                } catch (NullPointerException e) {
                    System.out.println("Null pointer as card, probably none found");
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
                } catch (NullPointerException e) {
                    System.out.println("Null pointer as map, probably none found");
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
                } catch (NullPointerException e) {
                    System.out.println("Null pointer as tag, probably none found");
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
                Call<List<User>> userCall = restService.getUsersByUsername(DROPBASE_DB, DROPBASE_AUTH, usernames);
                try {
                    Response<List<User>> userResponse = userCall.execute();
                    if (userResponse.isSuccessful()) {
                        DataStateHelper.setUserList(userResponse.body());
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

    public static void getAllUsers() {
        new Thread() {
            public void run() {
                DataStateHelper.getHelperLock();
                Call<List<User>> userCall = restService.getAllUsers(DROPBASE_DB, DROPBASE_AUTH);
                try {
                    Response<List<User>> userResponse = userCall.execute();
                    if (userResponse.isSuccessful()) {
                        DataStateHelper.setUserList(userResponse.body());
                    } else {
                        System.out.println(userResponse.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    System.out.println("Null pointer as users, probably none found");
                }
            }
        }.start();
    }
    
    private static void getDropbaseUploadJob(final String dest) {
        new Thread() {
            public void run() {
                DataStateHelper.getHelperLock();
                Call<DropbaseUploadJob> jobCall = pipelineService.generatePresignedUrl(new DropbaseUploadJob(dest));
                try {
                    Response<DropbaseUploadJob> jobResponse = jobCall.execute();
                    if (jobResponse.isSuccessful()) {
                        DataStateHelper.setUploadJob(jobResponse.body());
                    } else {
                        System.out.println("ERROR: " + jobResponse.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataStateHelper.dropHelperLock();
            }
        }.start();
    }
    
    private static void uploadDropbase(final DropbaseUploadJob job, final RequestBody body) {
        new Thread() {
            public void run() {
                DataStateHelper.getHelperLock();
                Call<ResponseBody> fileCall = uploadService.addFileToAmazon(job.getUploadUrl(), body);
                try {
                    Response<ResponseBody> fileResponse = fileCall.execute();
                    if (fileResponse.isSuccessful()) {
                        System.out.println("Successful upload to Dropbase (probably, they don't have a GET API that Retrofit supports)");
                        System.out.println(fileResponse.toString());
                    } else {
                        System.out.println(fileResponse.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataStateHelper.dropHelperLock();
            }
        }.start();
    }
    
    public static boolean addUser(User user) {
        getUserByUsername(user.getUsername());
        List<User> users = DataStateHelper.getClientUserList();
        try {
            System.out.println("Checking for " + users.get(0));
            System.out.println(user.getUsername() + " already taken");
        } catch (NullPointerException e) {
            getDropbaseUploadJob(DROPBASE_USER_PIPELINE);
            DropbaseUploadJob userJob = DataStateHelper.getClientUploadJob();
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ApiAdapter.gson.toJson(user).toString());
            uploadDropbase(userJob, body);
            return true;
        }
        return false;
    }
    
    public static void pushCards(final List<Card> cards) {
        getTagsByUsername("eq." + DataState.getLocalUsername());
        List<Tag> internetTags = DataStateHelper.getClientTagList();

        List<Tag> tagsToPush = new ArrayList<Tag>();
        for (Card c : cards) { // slowest code ever
            for (Tag t : c.getTagList()) {
                if (!internetTags.contains(t)) {
                    tagsToPush.add(t);
                }
            }
        }
        getDropbaseUploadJob(DROPBASE_CARD_PIPELINE);
        DropbaseUploadJob cardJob = DataStateHelper.getClientUploadJob();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ApiAdapter.gson.toJson(cards).toString());
        uploadDropbase(cardJob, body);

        getDropbaseUploadJob(DROPBASE_TAG_PIPELINE);
        DropbaseUploadJob tagJob = DataStateHelper.getClientUploadJob();
        body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ApiAdapter.gson.toJson(tagsToPush).toString());
        uploadDropbase(tagJob, body);

        getCardsByUsername("eq." + DataState.getLocalUsername());
        List<Card> internetCards = DataStateHelper.getClientCardList();
        List<IdMap> idMapsToPush = new ArrayList<IdMap>();
        for (Card c : cards) {
            for (Card in : internetCards) {
                if (c.getQuestion().equals(in.getQuestion()) && c.getAnswer().equals(in.getAnswer()) && c.getTagList().size() == in.getTagList().size()) { // good enough
                    c.setId(in.getId());
                    for (Tag t : c.getTagList()) {
                        idMapsToPush.add(new IdMap(c.getId(), t.getId()));
                    }
                }
            }
        }

        getDropbaseUploadJob(DROPBASE_MAP_PIPELINE);
        DropbaseUploadJob mapJob = DataStateHelper.getClientUploadJob();
        body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), ApiAdapter.gson.toJson(idMapsToPush).toString());
        uploadDropbase(mapJob, body);
    }

    /* banned by retrofit
    public static void getDropbaseUploadStatus(final DropbaseUploadJob job) {
        new Thread() {
            public void run() {
                DataStateHelper.getHelperLock();
                Call<DropbaseUploadStatus> statusCall = pipelineService.getJob(job.getJobId());
                try {
                    Response<DropbaseUploadStatus> statusResponse = statusCall.execute();
                    System.out.println(statusResponse.toString());
                    System.out.println(statusResponse.body().getProcessingStage() + statusResponse.body().getStatusCode());
                    if (statusResponse.isSuccessful()) {
                        DataStateHelper.setUploadStatus(statusResponse.body());
                    } else {
                        System.out.println("ERROR: " + statusResponse.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DataStateHelper.dropHelperLock();
            }
        }.start();
    }
     */
}
