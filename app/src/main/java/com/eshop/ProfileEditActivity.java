package com.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.eshop.model.Accountdetailresponse;
import com.eshop.model.Accountlistresponse;
import com.eshop.model.BasicResponse;
import com.eshop.rest.ApiClient;
import com.eshop.rest.ApiInterface;
import com.eshop.rest.MyCallback;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;


public class ProfileEditActivity extends AppCompatActivity {


    private static final String TAG = "ProfileEditActivity";
    LinearLayout linear_profileupdate;
    EditText edt_profileeditphone, edt_profileeditemail, edt_profileeditname;
    CircleImageView img_profile;


    ArrayList<Accountdetailresponse> accountdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);


        UIinitialization();
    }

    private void UIinitialization() {


        //toolbar

        Toolbar toolbar_profile = (Toolbar) findViewById(R.id.toolbar_profile);
        toolbar_profile.setTitle("Profile");
        setSupportActionBar(toolbar_profile);

        toolbar_profile.setTitleTextColor(0xFFFFFFFF);


        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);


        //Initialization


        linear_profileupdate = (LinearLayout) findViewById(R.id.linear_profileupdate);
        edt_profileeditphone = (EditText) findViewById(R.id.edt_profileeditphone);
        edt_profileeditemail = (EditText) findViewById(R.id.edt_profileeditemail);
        edt_profileeditname = (EditText) findViewById(R.id.edt_profileeditname);
        img_profile = (CircleImageView) findViewById(R.id.img_profile);


        //arraylist

        accountdetails = new ArrayList<>();

        account_detail();

        //onclick

        linear_profileupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fieldcheck();

            }
        });


    }

    private void fieldcheck() {

        if (edt_profileeditname.getText().length() != 0) {
            if (Utils.isValidMail(edt_profileeditemail.getText().toString()) || edt_profileeditemail.getText().length() == 0) {
                if (edt_profileeditphone.getText().length() != 0 && Utils.isValidMobile(edt_profileeditphone.getText().toString()))

                {
                    UpdateAccount();

                } else {
                    Toast.makeText(this, "Enter valid phone number", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Enter valid email", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Enter the name", Toast.LENGTH_SHORT).show();
        }

    }


    //Account detail set

    Call<Accountlistresponse> call;

    private void account_detail() {


        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        call = apiService.getaccountdetail(Pref.getAccountId());


        call.enqueue(new MyCallback<Accountlistresponse>(ProfileEditActivity.this, MyCallback.IGNORE) {
            @Override
            public void onSuccess(Call<Accountlistresponse> value, Response<Accountlistresponse> response) {
                if (response.body().getSuccess() == 1) {

                    accountdetails.addAll(response.body().getAccountdetails());


                    if (accountdetails.size() != 0) {


                        edt_profileeditname.setText(response.body().getAccountdetails().get(0).getProfilename());
                        edt_profileeditemail.setText(response.body().getAccountdetails().get(0).getProfileemail());
                        edt_profileeditphone.setText(response.body().getAccountdetails().get(0).getProfilephone());


                    }
                }
            }

            @Override
            public void onFailed(Call<Accountlistresponse> call, Throwable t) {

            }
        });


    }


    //Update account

    Call<BasicResponse> callSubmit;

    private void UpdateAccount() {


        String registername = edt_profileeditname.getText().toString();
        String registeremail = edt_profileeditemail.getText().toString();
        String registerphone = edt_profileeditphone.getText().toString();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


        callSubmit = apiService.updateAccount(registername, registeremail, registerphone, Pref.getAccountId());

        callSubmit.enqueue(new MyCallback<BasicResponse>(ProfileEditActivity.this, MyCallback.IGNORE) {
            @Override
            public void onSuccess(Call<BasicResponse> value, Response<BasicResponse> response) {
                if (response.body().getSuccess() == 1) {

                    Toast.makeText(ProfileEditActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Intent profile_update = new Intent(ProfileEditActivity.this, DashboardActivity.class);
                    startActivity(profile_update);
                    finish();

                }
            }

            @Override
            public void onFailed(Call<BasicResponse> call, Throwable t) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //  onBackPressed();

                Intent backpress = new Intent(ProfileEditActivity.this, DashboardActivity.class);
                startActivity(backpress);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
