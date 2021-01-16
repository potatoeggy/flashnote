package com.flashnote.data;

public class State {
    private static String localUsername;
    public static void setLocalUsername(String username) {
        localUsername = username;
    }
    
    public static String getLocalUsername() {
        return localUsername;
    }
}
