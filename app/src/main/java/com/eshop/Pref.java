package com.eshop;

import android.content.SharedPreferences;


public class Pref {

    private static final String PRE_ACCOUNT_ID = "account_id";
    private static final String PRE_ACCOUNT_USERCODE = "account_usercode";
    private static final String PRE_ACCOUNT_NAME = "account_name";
    private static final String PRE_FIREBASE_TOKEN = "furebase_id";
    private static SharedPreferences preferences = AppController.sharedpreferences;
    private static SharedPreferences.Editor editor;

    private static final String PRE_ACCOUNT_EMAIL = "account_email";


   public static void setAccountId(int account_id) {
        editor = preferences.edit();
        editor.putInt(PRE_ACCOUNT_ID, account_id);
        editor.commit();
    }

   public static int getAccountId()  {
        return preferences.getInt(PRE_ACCOUNT_ID, 0);
    }

    static String getPreAccountUsercode() {
        return preferences.getString(PRE_ACCOUNT_USERCODE, null);
    }

    public static void logoutUser() {
        editor = preferences.edit();
        editor.clear();
        editor.commit();

    }


    public static void setdevicetoken(String token) {

        editor = preferences.edit();
        editor.putString(PRE_FIREBASE_TOKEN, token);
        editor.commit();

    }

    static String getfirbasetoken() {

        return preferences.getString(PRE_FIREBASE_TOKEN, null);

    }


    public static void setemail(String email) {
        editor = preferences.edit();
        editor.putString(PRE_ACCOUNT_EMAIL, email);
        editor.commit();
    }

    public static String getemail()  {
        return preferences.getString(PRE_ACCOUNT_EMAIL, null);
    }


    public static void setname(String name) {
        editor = preferences.edit();
        editor.putString(PRE_ACCOUNT_NAME, name);
        editor.commit();
    }

    public static String getname()  {
        return preferences.getString(PRE_ACCOUNT_NAME, null);
    }
}
