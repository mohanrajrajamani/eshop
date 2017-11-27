package com.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.activeandroid.query.Select;
import com.eshop.activeandroid.CheckList;
import com.eshop.adapter.ProductlistAdapter;
import com.eshop.model.BasicResponse;
import com.eshop.model.Product;
import com.eshop.rest.MyCallback;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity {

    CustomPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;

    LinearLayoutManager layoutManager;

    ProductlistAdapter productlistAdapter;


    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 1000;//delay in milliseconds before task is to be executed
    final long PERIOD_MS = 3000; // time in milliseconds between successive task executions


    int[] mResources = {
            R.drawable.home_shoes,
            R.drawable.watch,
            R.drawable.home_shoes,
            R.drawable.watch,
            R.drawable.home_shoes,
            R.drawable.watch,
            R.drawable.home_shoes,
            R.drawable.watch,
            R.drawable.home_shoes,
            R.drawable.watch
    };

    String subcatagorey;
    String catagorey;

    ArrayList<Product>products;

    RecyclerView recycle_productlist;

    ImageView ic_action_name;


    TextView cart_count;
    public static int cart = 0;

    Handler handler = new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        Toolbar toolbar_productview =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar_productview);

        toolbar_productview.setTitleTextColor(0xFFFFFFFF);

        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);


        products=new ArrayList<>();



        mCustomPagerAdapter = new CustomPagerAdapter(HomeActivity.this);

        mViewPager = findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        CircleIndicator indicator =  findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);

        recycle_productlist = findViewById(R.id.recycle_productlist);
        recycle_productlist.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(HomeActivity.this);
        recycle_productlist.setLayoutManager(layoutManager);





        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == mResources.length-1) {
                    currentPage = 0;
                }
                mViewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);


        if(getIntent()!=null){

            subcatagorey= getIntent().getStringExtra("subcatagorey");
            catagorey= getIntent().getStringExtra("catagorey");

        }

        loadCatagories(subcatagorey,catagorey);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);

        MenuItem item = menu.findItem(R.id.badge);
        MenuItemCompat.setActionView(item, R.layout.badge_layout);

        RelativeLayout badgeLayout = (RelativeLayout) MenuItemCompat.getActionView(item);
        ic_action_name = badgeLayout.findViewById(R.id.click);
        cart_count = badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
        cart_count.setText(" " + cart + " ");


        return true;
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



    Call<BasicResponse> load_Catagories;

    public void loadCatagories(String subcatagorey, String catagorey) {

        this.load_Catagories = AppController.apiInterface.loadCatagories(catagorey,subcatagorey);
        this.load_Catagories.enqueue(new MyCallback<BasicResponse>(HomeActivity.this, MyCallback.IGNORE) {
            @Override
            public void onSuccess(Call<BasicResponse> value, Response<BasicResponse> response) {

                BasicResponse basicResponse = response.body();
                if (basicResponse.getSuccess() == 1) {
                    products.addAll(basicResponse.getProduct());
                    productlistAdapter = new ProductlistAdapter(HomeActivity.this,products);
                    recycle_productlist.setAdapter(productlistAdapter);
                } else {
                    Toast.makeText(getBaseContext(), basicResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailed(Call<BasicResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDestroy() {
        if(load_Catagories!=null){
            load_Catagories.cancel();
        }

        super.onDestroy();
    }


    @Override
    protected void onResume() {

        callAsynchronousTask();
        super.onResume();
    }


    public void callAsynchronousTask() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                callAsynchronousTask();

                count();


            }
        }, 500);
    }


    public int count() {
        int count = new Select().from(CheckList.class).count();
        cart_count.setText(" " + count + " ");

        if(!cart_count.getText().toString().trim().equals("0")){
            cart_count.setVisibility(View.VISIBLE);
            ic_action_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), CheckListActivity.class);
                    startActivity(i);


                }
            });
        }else{
            cart_count.setVisibility(View.GONE);
        }



        return count;
    }
}
