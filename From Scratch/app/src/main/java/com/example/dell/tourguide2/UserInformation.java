package com.example.dell.tourguide2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserInformation {

    private String Name, Email , UID , ProfileImageurl;

    public UserInformation(String name, String email, String uid,String profileImageurl) {

        /*FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null)
        {*/
            Name = name;
            Email = email;
            UID =uid;
            ProfileImageurl = profileImageurl;
        //}
    }

    public String getUserName()
    {
        return Name;
    }
    public void setUserName(String Name)
    {
        this.Name = Name;
    }

    public String getUserEmail()
    {
        return Email;
    }
    public void setUserEmail(String Email)
    {
        this.Email = Email;
    }
    public String getUID()
    {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getProfileImageurl() {
        return ProfileImageurl;
    }

    public void setProfileImageurl(String profileImageurl) {
        ProfileImageurl = profileImageurl;
    }
}
