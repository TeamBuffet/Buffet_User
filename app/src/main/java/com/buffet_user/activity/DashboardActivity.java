package com.buffet_user.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.adapter.CustomAdapterDashboardCategory;
import com.buffet_user.adapter.CustomAdapterDashboardMenu;
import com.buffet_user.network.ApiClientBase;
import com.buffet_user.network.ApiClientGetMenu;
import com.buffet_user.pojo.getmenupojo.MenuPojo;
import com.buffet_user.pojo.getmenupojo.SingleMenuPojo;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMenu,recyclerViewCategory;
    private MenuPojo menuPojo;
    private CustomAdapterDashboardMenu customAdapterDashboardMenu;
    private RecyclerView.LayoutManager layoutManager;
    private ApiClientGetMenu apiClientGetMenu;
    StickyRecyclerHeadersDecoration headersDecor;
    Toolbar tb;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    MenuItem prevItem;
    Boolean check=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        getWindow().setStatusBarColor(Color.DKGRAY);

        recyclerViewMenu =(RecyclerView)findViewById(R.id.recyclerViewMenu);
        recyclerViewCategory=(RecyclerView)findViewById(R.id.recyclerViewCategory);
        layoutManager=new LinearLayoutManager(this);
        recyclerViewMenu.setLayoutManager(layoutManager);
        recyclerViewMenu.setHasFixedSize(true);

        tb=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setTitleTextColor(Color.DKGRAY);
        getSupportActionBar().setTitle("Recommended");

        //drawer code

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigator);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (prevItem != null)
                    prevItem.setChecked(false);

                item.setCheckable(true);
                item.setChecked(true);

                prevItem = item;


                switch (item.getItemId()) {

                    case R.id.home:

                        drawer.closeDrawers();

                        break;



                }
                return false;


            }

        });

        MenuItem item = navigationView.getMenu().findItem(R.id.home);
        item.setCheckable(true);
        item.setChecked(true);
        prevItem=item;


        toggle = new ActionBarDrawerToggle(this, drawer,tb,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.closeDrawers();




        if(check)
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
        apiClientGetMenu = ApiClientBase.getApiClient().create(ApiClientGetMenu.class);
        Call<MenuPojo> call= apiClientGetMenu.getInfo();
        call.enqueue(new Callback<MenuPojo>() {
            @Override
            public void onResponse(Call<MenuPojo> call, Response<MenuPojo> response) {

                menuPojo=response.body();
                ArrayList<SingleMenuPojo> l1=new ArrayList<SingleMenuPojo>();
                l1.addAll(menuPojo.getMessage().getPizza());
                l1.addAll(menuPojo.getMessage().getSides());
                loaddata(l1);
                initCategoryList();
                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<MenuPojo> call, Throwable t) {
                pDialog.hide();

                Toast.makeText(DashboardActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        check=false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu_home, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.cart)
        {
            Intent i=new Intent(this,CartActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }






    public void loaddata(ArrayList <SingleMenuPojo> singlemenupojo)
    {
        customAdapterDashboardMenu =new CustomAdapterDashboardMenu(DashboardActivity.this,singlemenupojo,this);
        recyclerViewMenu.setAdapter(customAdapterDashboardMenu);
        headersDecor = new StickyRecyclerHeadersDecoration(customAdapterDashboardMenu);
        recyclerViewMenu.addItemDecoration(headersDecor);
     //   Toast.makeText(DashboardActivity.this,"Success !!!!",Toast.LENGTH_SHORT).show();
    }

    private void initCategoryList() {
        ArrayList<String> category = new ArrayList<>(Arrays.asList(" Recommended " , " Pizza Zone", " Sides Picks ", " Offers "));
        CustomAdapterDashboardCategory customAdapterDashboardCategory=new CustomAdapterDashboardCategory(this,category,tb,menuPojo,recyclerViewMenu,headersDecor,this);
        recyclerViewCategory.setHasFixedSize(true);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setAdapter(customAdapterDashboardCategory);
    }

}
