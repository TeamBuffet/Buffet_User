package com.buffet_user.activity.dashboard;

/**
 * Created by Ankit on 08/11/17.
 */

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.buffet_user.activity.login.IntroActivity;
import com.buffet_user.activity.login.LoginChooserActivity;
import com.buffet_user.activity.review.BlogHomeActivity;
import com.buffet_user.activity.review.PostFeedActivity;
import com.buffet_user.adapter.CustomAdapterDashboardCategory;
import com.buffet_user.adapter.CustomAdapterDashboardMenu;
import com.buffet_user.pojo.LoginDataPOJO;
import com.buffet_user.pojo.LoginPojo;
import com.buffet_user.pojo.LoginSuperPojo;
import com.buffet_user.pojo.MenuPojo;
import com.buffet_user.pojo.PostFeedPOJO;
import com.buffet_user.pojo.SingleMenuPojo;
import com.buffet_user.retrofit.ApiUtils;
import com.buffet_user.retrofit.SOService;
import com.google.android.gms.maps.model.Dash;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends BaseActivity {

    private RecyclerView recyclerViewMenu, recyclerViewCategory;
    private MenuPojo menuPojo;
    private CustomAdapterDashboardMenu customAdapterDashboardMenu;
    private RecyclerView.LayoutManager layoutManager;
    private SOService mService;
    StickyRecyclerHeadersDecoration headersDecor;
    Toolbar tb;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    MenuItem prevItem;
    TextView txtToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().setStatusBarColor(Color.DKGRAY);
        mService = ApiUtils.getSOService();
        recyclerViewMenu = (RecyclerView) findViewById(R.id.recyclerViewMenu);
        recyclerViewCategory = (RecyclerView) findViewById(R.id.recyclerViewCategory);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewMenu.setLayoutManager(layoutManager);
        recyclerViewMenu.setHasFixedSize(true);
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setTitleTextColor(Color.DKGRAY);
        getSupportActionBar().setTitle("");
        txtToolbar = (TextView) tb.findViewById(R.id.toolbar_title);
        txtToolbar.setText("Recommended");
        //Auth User
        CheckAuth(sharedPreferences.getString("phonenumber",""));

        //drawer code

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigator);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (prevItem != null)
                    prevItem.setChecked(false);

                menuItem.setCheckable(true);
                menuItem.setChecked(true);

                prevItem = menuItem;

                drawer.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.profile:
                        //  Intent intent = new Intent(DashboardActivity.this, ProfileActivity.class);
                        //  startActivity(intent);
                        return true;

                    case R.id.blog:
                        startActivity(openActivity(DashboardActivity.this, BlogHomeActivity.class));
                        return true;

                    case R.id.rateus:
                        Uri uri = Uri.parse("market://details?id=" + "edu.smartbox");
                        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        try {
                            startActivity(goToMarket);
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + "edu.smartbox")));
                        }
                        return true;
                    case R.id.contact_us:
                    /*    Intent inte = new Intent(DashboardActivity.this, ContactUs.class);
                        startActivity(inte);*/
                        return true;
                    case R.id.privacy:
                        Intent i1 = new Intent(DashboardActivity.this, PrivacyPolicyActivity.class);
                        startActivity(i1);

                        return true;

                    case R.id.logout:

                        Intent intent = new Intent(DashboardActivity.this, IntroActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        toggle = new ActionBarDrawerToggle(this, drawer, tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.closeDrawers();


        initMenu();


    }

    private void CheckAuth(String phone) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        LoginDataPOJO loginPojo = new LoginDataPOJO();
        loginPojo.setTimestamp(formattedDate);
        loginPojo.setUserid(phone);


        mService.getLogin(loginPojo).enqueue(new Callback<LoginSuperPojo>() {
            @Override
            public void onResponse(Call<LoginSuperPojo> call, Response<LoginSuperPojo> response) {

                if (response.isSuccessful() && response.body().getError().equals("false")) {
                    Log.d("MainActivity", "Authenticated from API");
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                    Toast.makeText(DashboardActivity.this, "Sorry, Login Again", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DashboardActivity.this, LoginChooserActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    Toast.makeText(DashboardActivity.this, "Error in hit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginSuperPojo> call, Throwable t) {
                Log.d("MainActivity", "error loading from API");

            }
        });


    }

    private void initMenu() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        pDialog.show();
        mService.getallmenu().enqueue(new Callback<MenuPojo>() {
            @Override
            public void onResponse(Call<MenuPojo> call, Response<MenuPojo> response) {

                menuPojo = response.body();
                ArrayList<SingleMenuPojo> l1 = new ArrayList<SingleMenuPojo>();
                l1.addAll(menuPojo.getMessage().getPizza());
                l1.addAll(menuPojo.getMessage().getSides());
                loaddata(l1);
                initCategoryList();
                pDialog.hide();

            }

            @Override
            public void onFailure(Call<MenuPojo> call, Throwable t) {
                pDialog.hide();

                Toast.makeText(DashboardActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void loaddata(ArrayList<SingleMenuPojo> singlemenupojo) {
        customAdapterDashboardMenu = new CustomAdapterDashboardMenu(DashboardActivity.this, singlemenupojo);
        recyclerViewMenu.setAdapter(customAdapterDashboardMenu);
        headersDecor = new StickyRecyclerHeadersDecoration(customAdapterDashboardMenu);
        recyclerViewMenu.addItemDecoration(headersDecor);
        Toast.makeText(DashboardActivity.this, "Success !!!!", Toast.LENGTH_SHORT).show();
    }

    private void initCategoryList() {
        ArrayList<String> category = new ArrayList<>(Arrays.asList(" Recommended ", " Pizza Zone", " Sides Picks ", " Offers "));
        CustomAdapterDashboardCategory customAdapterDashboardCategory = new CustomAdapterDashboardCategory(this, category, txtToolbar, menuPojo, recyclerViewMenu, headersDecor);
        recyclerViewCategory.setHasFixedSize(true);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setAdapter(customAdapterDashboardCategory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.cart) {
            //Cart Start
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}