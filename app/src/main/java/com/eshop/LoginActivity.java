package com.eshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eshop.model.BasicResponse;
import com.eshop.rest.ApiClient;
import com.eshop.rest.ApiInterface;
import com.eshop.rest.MyCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {


    LinearLayout linear_moveregister;
    EditText edt_email, edt_password;
    CardView card_login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        UIintialization();


    }

    private void UIintialization() {

        //initialization

        card_login = (CardView) findViewById(R.id.card_login);
        linear_moveregister = (LinearLayout) findViewById(R.id.linear_moveregister);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);

        //progressdialog init

        progressDialog = new ProgressDialog(LoginActivity.this);

        //onclick

        linear_moveregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent linear_moveregister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(linear_moveregister);
                finish();
            }
        });


        card_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fieldcheck();
            }
        });

    }

    //field check

    private void fieldcheck() {

        if (edt_email.getText().length() != 0) {
            if (Utils.isValidMail(edt_email.getText().toString().trim())) {
                if (edt_password.getText().length() != 0) {
                    loginprofile();
                } else {
                    Toast.makeText(this, "Email and password are empty", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Email and password are empty", Toast.LENGTH_SHORT).show();
        }
    }


    //Login profile update

    Call<BasicResponse> call;


    private void loginprofile() {


        String email = edt_email.getText().toString().trim();
        String password = edt_password.getText().toString().trim();



        progressDialog.show();

      //  Log.d("sdsadsa", "loginprofile: "  + Pref.getAccountId());

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        call = apiService.login(email, password);

        call.enqueue(new MyCallback<BasicResponse>(LoginActivity.this, MyCallback.IGNORE) {
            @Override
            public void onSuccess(Call<BasicResponse> value, Response<BasicResponse> response) {
                if (response.body().getSuccess() == 1) {

                    //Sharedpref

                    Pref.setAccountId(response.body().getId());
                    Pref.setemail(response.body().getEmail());
                    Pref.setname(response.body().getName());


                    Log.d("sdds", "onResponse: " + response.body().getId());

                    Intent login = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(login);
                    finish();


                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(Call<BasicResponse> call, Throwable t) {

            }
        });





    }
}
