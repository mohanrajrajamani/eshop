package com.eshop;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;


import com.activeandroid.ActiveAndroid;
import com.eshop.rest.ApiClient;
import com.eshop.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

public class AppController extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {

    static List<Activity> activities = new ArrayList<>();

    public static SharedPreferences sharedpreferences;
    public static ProgressDialog progress;
    static AppController instance;
    public static ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    public static AppController getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        instance = this;
        progress = new ProgressDialog(this);
        sharedpreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);

        ActiveAndroid.initialize(this);
        MultiDex.install(this);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.v("#####", "########## onLowMemory ##########");
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        activities.add(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {



    }

//    public static void backToLogin() {
//
//        Log.d("Activity_count", "" + activities.size());
//
//        for (Activity activity : new ArrayList<Activity>(activities)) {
//            if (!(activity instanceof EmergencyContactsActivity)) {
//                activity.finish();
//                activities.remove(activity);
//            }
//        }
//
//    }

    /*public static void finishApp() {

        MyService.mWebSocketClient=null;

        Log.d("Activity_count", "" + activities.size());

        for (Activity activity : new ArrayList<Activity>(activities)) {

            activity.finish();
            activities.remove(activity);
        }

    }*/

}