package com.example.dell.tourguide2;

public class Profile {
    public Profile(String userName, String userEmail, String uid) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.uid = uid;
    }

    String userName,userEmail,uid;

    Profile(){

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;

    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUid() {
        return uid;
    }
}
