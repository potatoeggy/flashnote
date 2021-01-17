package com.example.flashnote;

import com.example.flashnote.data.Card;

import java.util.ArrayList;

public class State {
    static ArrayList<Card> playing;
    static int playingIndex;
    static String user;
    static String viewingUser;
    static boolean justRegistered;
    static boolean justLoggedIn;
    static boolean justLoggedOut;
    static ArrayList<Card> newCards;
}
