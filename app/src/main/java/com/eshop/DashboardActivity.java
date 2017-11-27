package com.eshop;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.eshop.activeandroid.CheckList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    de.hdodenhof.circleimageview.CircleImageView profile_image;
    LinearLayout linear_moveprofile;
    TextView txt_username;
    TextView cart_count;
    public static int cart = 0;
    Handler handler = new Handler();
    ImageView ic_action_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        UIinitialization();
        //count();


    }

    private void UIinitialization() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);

        //circular imageview

        profile_image = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.profile_image);
        linear_moveprofile = (LinearLayout) headerview.findViewById(R.id.linear_moveprofile);
        txt_username = (TextView) findViewById(R.id.txt_username);


        displaySelectedScreen(R.id.nav_home);


        //onclick

        linear_moveprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent profile_image = new Intent(DashboardActivity.this, ProfileEditActivity.class);
                startActivity(profile_image);
                finish();
            }
        });


        txt_username = headerview.findViewById(R.id.txt_username);

        if (Pref.getname() != null) {
            txt_username.setText(Pref.getname());
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        displaySelectedScreen(item.getItemId());

        return true;
    }

    private void displaySelectedScreen(int itemId) {


        Fragment fragment = null;

        switch (itemId)

        {
            case R.id.nav_home:
                fragment = new BlankFragment();
                break;
            case R.id.nav_order:
                fragment = new OrderFragment();
                break;
            case R.id.nav_about:
                fragment = new AboutFragment();
                break;
            case R.id.nav_logout:
                Pref.logoutUser();
                new Delete().from(CheckList.class).execute();
                Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
        }


        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    public static int setNotifCountinc(int number) {

        cart = cart + number;
        Log.v(" main_cart", " " + number);
        return cart;
    }


    public int count() {
        int count = new Select().from(CheckList.class).count();
        cart_count.setText(" " + count + " ");

        if (!cart_count.getText().toString().trim().equals("0")) {
            cart_count.setVisibility(View.VISIBLE);
            ic_action_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), CheckListActivity.class);
                    startActivity(i);


                }
            });
        } else {
            cart_count.setVisibility(View.GONE);
        }


        return count;
    }

    @Override
    protected void onResume() {
        callAsynchronousTask();
        if (Pref.getname() != null) {
            txt_username.setText(Pref.getname());
        }
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



}
