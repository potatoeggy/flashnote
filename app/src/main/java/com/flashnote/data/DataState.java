package com.flashnote.data;

import java.util.List;

public class DataState {
    private static String localUsername = "user";
    private static boolean apiLock = false; // if we need multiple at a time you should have multiple openings
    private static boolean ready = false;
    private static List<Tag> tagList;
    private static List<Card> cardList;
    
    public static void setLocalUsername(String username) {
        localUsername = username;
    }
    
    public static String getLocalUsername() {
        return localUsername;
    }

    public static boolean getApiLock() {
        return apiLock;
    }
    
    public static boolean getReady() {
        return ready;
    }
    
    public static List<Tag> getTagList() {
        return tagList;
    }
    
    public static List<Card> getCardList() {
        return cardList;
    }
    
    public static void setApiLock(boolean bool) {
        apiLock = bool;
    }
    
    public static void setReady(boolean bool) {
        ready = bool;
    }
    
    public static void setTagList(List<Tag> tags) {
        tagList = tags;
    }
    
    public static void setCardList(List<Card> cards) {
        cardList = cards;
    }
}
