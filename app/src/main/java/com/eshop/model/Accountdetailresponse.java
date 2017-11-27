package com.eshop.model;

/**
 * Created by Admin on 07-11-2017.
 */

public class Accountdetailresponse {


    String profilename;
    String profileemail;
    String profilephone;

    public Accountdetailresponse(String profilename, String profileemail, String profilephone) {
        this.profilename = profilename;
        this.profileemail = profileemail;
        this.profilephone = profilephone;
    }

    public String getProfilename() {
        return profilename;
    }

    public void setProfilename(String profilename) {
        this.profilename = profilename;
    }

    public String getProfileemail() {
        return profileemail;
    }

    public void setProfileemail(String profileemail) {
        this.profileemail = profileemail;
    }

    public String getProfilephone() {
        return profilephone;
    }

    public void setProfilephone(String profilephone) {
        this.profilephone = profilephone;
    }




}
