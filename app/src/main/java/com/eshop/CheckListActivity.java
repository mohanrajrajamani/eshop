package com.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.eshop.activeandroid.CheckList;
import com.eshop.adapter.CheckListAdapter;
import com.eshop.model.BasicResponse;
import com.eshop.model.BasicSend;
import com.eshop.model.SendCheckOutResponse;
import com.eshop.rest.ApiClient;
import com.eshop.rest.ApiInterface;
import com.eshop.rest.MyCallback;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Admin on 08-11-2017.
 */

public class CheckListActivity extends AppCompatActivity {

    RecyclerView recycler_view;
    CheckListAdapter checkListAdapter;
    List<CheckList> stringList;
    Button bt_checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);

        recycler_view = findViewById(R.id.recycler_view);
        bt_checkout = findViewById(R.id.bt_checkout);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));

        showInventoryList();


        checkListAdapter = new CheckListAdapter(CheckListActivity.this, stringList);
        recycler_view.setAdapter(checkListAdapter);

        bt_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sum = 0;
                for (int i = 0; i < stringList.size(); i++) {

                    sum = sum + Integer.valueOf(stringList.get(i).getPrice()) *
                            Integer.valueOf(stringList.get(i).getProduct_count());

                }

                String con = "TOTAL" + ":" + "₹" + sum;

                if (sum != 0) {
                    new MaterialDialog.Builder(CheckListActivity.this)
                            .title("VERIFY THE AMOUNT")
                            .content(con)
                            .positiveText(android.R.string.ok)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                    ArrayList<SendCheckOutResponse> sendCheckOutResponses = new ArrayList<>();


                                    for (int i = 0; i < stringList.size(); i++) {
                                        sendCheckOutResponses.add(new SendCheckOutResponse(String.valueOf(Pref.getAccountId()),
                                                stringList.get(i).getPrice(),
                                                Pref.getemail(),
                                                stringList.get(i).getProduct_name()));
                                    }

                                    BasicSend basicSend = new BasicSend(sendCheckOutResponses);


                                    checkOut(basicSend);


                                }
                            })
                            .negativeText(android.R.string.cancel)
                            .show();


                } else {

                    Toast.makeText(CheckListActivity.this, "Please order the item", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }


    private List<CheckList> getAll() {
        //Getting all items stored in Inventory table
        return new Select()
                .from(CheckList.class)
                .execute();
    }


    private void showInventoryList() {
        stringList = new ArrayList<>();
        stringList = getAll();


    }

    Call<BasicResponse> call;


    private void checkOut(BasicSend update) {

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        call = apiService.updateSkills(update);

        call.enqueue(new MyCallback<BasicResponse>(CheckListActivity.this, MyCallback.IGNORE) {
            @Override
            public void onSuccess(Call<BasicResponse> value, Response<BasicResponse> response) {
                if (response.body().getSuccess() == 1) {
                    Toast.makeText(CheckListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    new Delete().from(CheckList.class).execute();
                    Intent intent = new Intent(CheckListActivity.this, DashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(CheckListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(Call<BasicResponse> call, Throwable t) {

            }
        });


    }

}