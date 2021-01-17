package com.flashnote.data;

import java.io.IOException;
import java.util.List;

public class DataStateHelper {
    private static Thread helperTimeout = new Thread() { // timeout just in case programmer error
        public void run() {
            try {
                Thread.sleep(5000);
                DataState.setApiLock(false); // this could very much backfire if it interrupts *another* thread
                DataState.setReady(false);
            } catch (InterruptedException e) {
                return;
            }
            System.out.println("ERROR: Timeout for API lock reached - resetting lock.");
        }
    };
    
    private static Thread clientTimeout = new Thread() {
        public void run() {
            try {
                Thread.sleep(5000);
                DataState.setReady(false);
            } catch (InterruptedException e) {
                return;
            }
        }
    };
    
    public static void getHelperLock() {
        while (DataState.getApiLock() || DataState.getReady()); // only when both are false can you get lock
        DataState.setApiLock(true);
        helperTimeout.start();
    }

    public static void dropHelperLock() {
        helperTimeout.interrupt();
        DataState.setApiLock(false);
        DataState.setReady(true);
    }

    public static void setTagList(List<Tag> tags) {
        DataState.setTagList(tags);
    }

    public static void setCardList(List<Card> cards) {
        DataState.setCardList(cards);
    }
    
    public static void setUser(User user) {
        DataState.setUser(user);
    }

    public static List<Tag> getClientTagList() {
        clientTimeout.start();
        while (!DataState.getReady());
        clientTimeout.interrupt();
        DataState.setReady(false);
        List<Tag> temp = DataState.getTagList();
        DataState.setTagList(null);
        return temp;
    }

    public static List<Card> getClientCardList() {
        clientTimeout.start();
        while (!DataState.getReady());
        clientTimeout.interrupt();
        DataState.setReady(false);
        List<Card> temp = DataState.getCardList();
        DataState.setCardList(null);
        return temp;
    }
    
    public static User getClientUser() {
        clientTimeout.start();
        while (!DataState.getReady());
        clientTimeout.interrupt();
        DataState.setReady(false);
        User temp = DataState.getUser();
        DataState.setUser(null);
        return temp;
    }
}
