package com.buffet_user.activity.dashboard;

/**
 * Created by Ankit on 08/11/17.
 */

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.activity.BaseActivity;
import com.buffet_user.activity.cart.CartActivity;
import com.buffet_user.adapter.CustomAdapterDashboardCategory;
import com.buffet_user.adapter.CustomAdapterDashboardMenu;
import com.buffet_user.pojo.MenuPojo;
import com.buffet_user.pojo.SingleMenuPojo;
import com.buffet_user.retrofit.ApiUtils;
import com.buffet_user.retrofit.SOService;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.Arrays;

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
        //drawer code

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigator);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        toggle = new ActionBarDrawerToggle(this, drawer, tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.closeDrawers();


        initMenu();

  /*      DataManager dataManager=new DataManager(this);
        menuPojo = dataManager.getDashboardData();
        ArrayList<SingleMenuPojo> l1=new ArrayList<SingleMenuPojo>();
        l1.addAll(menuPojo.getMessage().getPizza());
        l1.addAll(menuPojo.getMessage().getSides());
        loaddata(l1);
        initCategoryList();
*/

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
            startActivity(openActivity(this, CartActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


}