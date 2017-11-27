package com.eshop.rest;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.afollestad.materialdialogs.MaterialDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public abstract class MyCallback<T> implements Callback<T> {

    AppCompatActivity appCompatActivity;

    public static final int IGNORE = 0;
    public static final int FINISH = 1;

    int action = IGNORE;

    MaterialDialog dialogLoading;
    MaterialDialog dialogConnectionError;

    public MyCallback(AppCompatActivity appCompatActivity, int action) {
        this.appCompatActivity = appCompatActivity;
        this.action = action;
        dialogLoading = Dialogue.loading(appCompatActivity);
        if(dialogLoading!=null)dialogLoading.show();
        if (action == IGNORE) {
            dialogConnectionError = Dialogue.connectionError(appCompatActivity);
        } else {
            dialogConnectionError = Dialogue.connectionErrorFinish(appCompatActivity);
        }

    }

    public abstract void onSuccess(Call<T> value, Response<T> response);

    public abstract void onFailed(Call<T> call, Throwable t);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        if(dialogLoading!=null) dialogLoading.dismiss();

        if (response.errorBody() != null) Log.d("error_body", response.errorBody().toString());

        if (response.isSuccessful()) {

            onSuccess(call, response);

        } else {

            if(dialogConnectionError!=null) dialogConnectionError.show();


            onFailure(call, new Throwable(response.code() + ""));
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (!call.isCanceled()) {

            if(dialogLoading!=null) dialogLoading.dismiss();
            if(dialogConnectionError!=null) dialogConnectionError.show();

            Log.e("error_message", t.getMessage(), t);
            onFailed(call, t);

        }
    }

}