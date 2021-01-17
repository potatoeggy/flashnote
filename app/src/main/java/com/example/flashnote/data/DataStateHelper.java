package com.flashnote.data;

import android.provider.ContactsContract;

import java.io.IOException;
import java.util.List;

public class DataStateHelper {
    private static Thread helperTimeout;

    private static void startHelperTimeout() {
        helperTimeout = new Thread() { // timeout just in case programmer error
            public void run() {
                try {
                    Thread.sleep(10000);
                    DataState.setApiLock(false); // this could very much backfire if it interrupts *another* thread
                    DataState.setReady(false);
                } catch (InterruptedException e) {
                    return;
                }
                System.out.println("ERROR: Timeout for API lock reached - resetting lock.");
            }
        };
        helperTimeout.start();
    }
    
    private static void startClientTimeout() {
        Thread clientTimeout = new Thread() {
            public void run() {
                try {
                    Thread.sleep(10000);
                    DataState.setReady(false);
                } catch (InterruptedException e) {
                    return;
                }
                System.out.println("Timeout for user lock reached - resetting lock");
            }
        };
        clientTimeout.start();
        while (!DataState.getReady());
        clientTimeout.interrupt();
        DataState.setReady(false);
    }
    
    public static void getHelperLock() {
        while (DataState.getApiLock() || DataState.getReady()); // only when both are false can you get lock
        DataState.setApiLock(true);
        startHelperTimeout();
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
    
    public static void setUserList(List<User> users) {
        DataState.setUserList(users);
    }
    
    public static void setUploadJob(DropbaseUploadJob job) {
        DataState.setUploadJob(job);
    };

    /*
    public static void setUploadStatus(DropbaseUploadStatus status) {
        DataState.setUploadStatus(status);
    }
     */

    public static List<Tag> getClientTagList() {
        startClientTimeout();
        List<Tag> temp = DataState.getTagList();
        DataState.setTagList(null);
        return temp;
    }

    public static List<Card> getClientCardList() {
        startClientTimeout();
        List<Card> temp = DataState.getCardList();
        DataState.setCardList(null);
        return temp;
    }
    
    public static List<User> getClientUserList() {
        startClientTimeout();
        List<User> temp = DataState.getUserList();
        DataState.setUserList(null);
        return temp;
    }
    
    public static DropbaseUploadJob getClientUploadJob() {
        startClientTimeout();
        DropbaseUploadJob temp = DataState.getUploadJob();
        DataState.setUploadJob(null);
        return temp;
    }
    /*
    public static DropbaseUploadStatus getClientUploadStatus() {
        startClientTimeout();
        DropbaseUploadStatus temp = DataState.getUploadStatus();
        DataState.setUploadStatus(null);
        return temp;
    }
     */
}
