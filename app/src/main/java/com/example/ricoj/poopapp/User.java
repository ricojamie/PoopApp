package com.example.ricoj.poopapp;

/**
 * Created by ricoj on 1/30/2017.
 */

public class User {

    private long UserID;
    private String FirebaseUID;

    public User() {

    }

    public User(long userID, String firebaseUID) {
        this.UserID = userID;
        this.FirebaseUID = firebaseUID;
    }

    public String getFirebaseUID() {
        return FirebaseUID;
    }

    public void setFirebaseUID(String firebaseUID) {
        FirebaseUID = firebaseUID;
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long userID) {
        UserID = userID;
    }

}
