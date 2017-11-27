package com.eshop.rest;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.eshop.R;


public class Dialogue {

    static MaterialDialog.Builder builderConnectionError(AppCompatActivity activity) {

        if (activity != null) {
            return new MaterialDialog.Builder(activity)
                    .title(R.string.dialogue_connection_error_title)
                    .content(R.string.dialogue_connection_error_content)
                    .positiveText(R.string.dhalogue_btn_ok)
                    .canceledOnTouchOutside(false);
        } else {
            return null;
        }
    }

    public static MaterialDialog connectionError(AppCompatActivity activity) {

        if (activity != null) {
            return builderConnectionError(activity).build();
        } else {
            return null;
        }
    }

    public static MaterialDialog loading(AppCompatActivity activity) {

        if (activity != null) {
            return new MaterialDialog.Builder(activity)
                    .content(R.string.dhalogue_loading_content)
                    .progress(true, 0)
                    .canceledOnTouchOutside(false)
                    .build();
        } else {
            return null;
        }
    }

    public static MaterialDialog connectionErrorFinish(final AppCompatActivity activity) {

        if (activity != null) {
            return builderConnectionError(activity).dismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    activity.onBackPressed();
                }
            }).build();
        } else {
            return null;
        }
    }

    static MaterialDialog.Builder builderTryAgain(AppCompatActivity activity) {
        return new MaterialDialog.Builder(activity)
                .title(R.string.dialogue_try_again_title)
                .content(R.string.dialogue_try_again_content)
                .positiveText(R.string.dhalogue_btn_try_again)
                .canceledOnTouchOutside(false);
    }

    public static MaterialDialog tryAgain(AppCompatActivity activity) {
        return builderTryAgain(activity).build();
    }

    public static MaterialDialog tryAgainFinish(final AppCompatActivity activity) {
        return builderTryAgain(activity).dismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                activity.onBackPressed();
            }
        }).build();
    }
}



