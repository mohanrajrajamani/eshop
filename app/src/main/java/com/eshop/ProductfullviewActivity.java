package com.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.eshop.activeandroid.CheckList;
import com.squareup.picasso.Picasso;
import com.travijuu.numberpicker.library.NumberPicker;

public class ProductfullviewActivity extends AppCompatActivity {

    Button button;
    NumberPicker numberPicker;
    String product_id;
    String product_name;
    String product_description;
    String product_price;
    String product_image;
    ImageView im_product;
    TextView tv_description;
    TextView tv_name;
    TextView tv_price;

    TextView cart_count;
    public static int cart = 0;
    Handler handler = new Handler();
    ImageView ic_action_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productfullview);


        UIinitialization();
    }

    private void UIinitialization() {

        //toolbar

        Toolbar toolbar_productview = (Toolbar) findViewById(R.id.toolbar_productview);
        toolbar_productview.setTitle("ESHOP");
        setSupportActionBar(toolbar_productview);

        toolbar_productview.setTitleTextColor(0xFFFFFFFF);


        final ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowTitleEnabled(true);

        button = findViewById(R.id.bt_add_cart);
        numberPicker = findViewById(R.id.number_picker);
        im_product = findViewById(R.id.im_product);
        tv_description = findViewById(R.id.tv_description);
        tv_name = findViewById(R.id.tv_name);
        tv_price = findViewById(R.id.tv_price);

        if (getIntent() != null) {

            product_id = getIntent().getStringExtra("product_id");
            product_name = getIntent().getStringExtra("product_name");
            product_description = getIntent().getStringExtra("product_description");
            product_price = getIntent().getStringExtra("product_price");
            product_image = getIntent().getStringExtra("product_image");

            tv_price.setText(product_price);
            tv_name.setText(product_name);
            tv_description.setText(product_description);
            tv_price.setText("₹" + " " + product_price);


            Picasso.with(ProductfullviewActivity.this)
                    .load(product_image)
                    .placeholder(R.drawable.casualshoe)   // optional
                    .error(R.drawable.casualshoe)      // optiona
                    .into(im_product);


        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberPicker.getValue() != 0) {

                    CheckList checkList;

                    checkList = new Select()
                            .from(CheckList.class)
                            .where("product_id= ?", product_id)
                            .executeSingle();

                    if (checkList != null) {


                        String CR_updateSet = " product_name = ? ," +
                                " product_description = ?," + " price = ? ," + " image = ? ," + " product_count = ? ";


                        new Update(CheckList.class)
                                .set(CR_updateSet, product_name, product_description, product_price, product_image, String.valueOf(numberPicker.getValue()))
                                .where("product_id = ? ", product_id)
                                .execute();
                        Log.v("emfenfefne", "fefe");

                    } else {
                        checkList = new CheckList();
                        checkList.product_id = product_id;
                        checkList.product_name = product_name;
                        checkList.product_description = product_description;
                        checkList.price = product_price;
                        checkList.image = product_image;
                        checkList.product_count = String.valueOf(numberPicker.getValue());

                        checkList.save();


                        Log.v("emfenfefne", "eudeydbyedyed");

                    }


                } else {
                    Toast.makeText(ProductfullviewActivity.this, "Please choose the product", Toast.LENGTH_SHORT).show();
                }


            }
        });


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
