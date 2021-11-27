package com.example.medcare;

public class Chat {

        private String profileID;
        int userCode;

    public Chat(String profileID, int userCode) {
        this.profileID = profileID;
        this.userCode = userCode;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }
}


