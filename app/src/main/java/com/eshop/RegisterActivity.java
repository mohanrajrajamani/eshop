package com.eshop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.eshop.model.BasicResponse;
import com.eshop.rest.ApiClient;
import com.eshop.rest.ApiInterface;
import com.eshop.rest.MyCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {

    LinearLayout linear_movelogin;
    TextView txt_login;
    CardView card_register;
    EditText edt_registerpassword;
    EditText edt_registermobile;
    EditText edt_registeremail;
    EditText edt_registerusername;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        UIinitialization();


    }

    private void UIinitialization() {

        //initialization

        linear_movelogin = (LinearLayout) findViewById(R.id.linear_movelogin);
        txt_login = (TextView) findViewById(R.id.txt_login);
        card_register = (CardView) findViewById(R.id.card_register);
        edt_registerpassword = (EditText) findViewById(R.id.edt_registerpassword);
        edt_registermobile = (EditText) findViewById(R.id.edt_registermobile);
        edt_registeremail = (EditText) findViewById(R.id.edt_registeremail);
        edt_registerusername = (EditText) findViewById(R.id.edt_registerusername);

        //progressdialog init
        progressDialog = new ProgressDialog(RegisterActivity.this);

        //onclick
        linear_movelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent linear_movelogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(linear_movelogin);
                finish();
            }
        });

        //card signup

        card_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fieldcheck();
            }
        });

    }

    //fieldcheck

    private void fieldcheck() {

        if (edt_registerusername.getText().length() != 0) {

            if (edt_registeremail.getText().toString().trim().length() != 0) {
                if (Utils.isValidMail(edt_registeremail.getText().toString().trim())) {
                    if (edt_registermobile.getText().length()!=0 && Utils.isValidMobile(edt_registermobile.getText().toString())) {
                        if (edt_registerpassword.getText().length() >= 6) {

                            Updateregister();

                        } else {

                            Toast.makeText(this, "Password should maximum 6 character", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Enter the email", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Enter the username", Toast.LENGTH_SHORT).show();
        }
    }


    //update register

    Call<BasicResponse> call;


    private void Updateregister() {

        String name = edt_registerusername.getText().toString();
        String email = edt_registeremail.getText().toString();
        String mobile = edt_registermobile.getText().toString();
        String password = edt_registerpassword.getText().toString();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        call = apiService.register(name, email, mobile, password);

        call.enqueue(new MyCallback<BasicResponse>(RegisterActivity.this, MyCallback.IGNORE) {
            @Override
            public void onSuccess(Call<BasicResponse> value, Response<BasicResponse> response) {
                if (response.body().getSuccess() == 1) {

                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed(Call<BasicResponse> call, Throwable t) {

            }
        });



    }


}
