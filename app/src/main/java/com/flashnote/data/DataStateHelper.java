package com.flashnote.data;

import java.util.List;

public class DataStateHelper {
    public static void getHelperLock() {
        while (DataState.getApiLock() || DataState.getReady()); // only when both are false can you get lock
        DataState.setApiLock(true);
    }

    public static void dropHelperLock() {
        DataState.setApiLock(false);
        DataState.setReady(true);
    }

    public static void setTagList(List<Tag> tags) {
        DataState.setTagList(tags);
    }

    public static void setCardList(List<Card> cards) {
        DataState.setCardList(cards);
    }

    public static List<Tag> getClientTagList() {
        while (!DataState.getReady());
        DataState.setReady(false);
        List<Tag> temp = DataState.getTagList();
        DataState.setTagList(null);
        return temp;
    }

    public static List<Card> getClientCardList() {
        while (!DataState.getReady());
        DataState.setReady(false);
        List<Card> temp = DataState.getCardList();
        //DataState.setCardList(null);
        return temp;
    }
}
