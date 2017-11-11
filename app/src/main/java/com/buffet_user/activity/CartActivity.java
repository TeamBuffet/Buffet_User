package com.buffet_user.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.adapter.CustomAdapterCart;
import com.buffet_user.global.UserSharedPreferenceData;
import com.buffet_user.network.ApiClientBase;
import com.buffet_user.network.ApiClientGetFromCart;
import com.buffet_user.network.ApiClientGetMenu;
import com.buffet_user.pojo.getfromcartpojo.CartinfoPojo;
import com.buffet_user.pojo.getmenupojo.MenuPojo;
import com.buffet_user.pojo.getmenupojo.SingleMenuPojo;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    Toolbar tb;
    private ApiClientGetFromCart apiClientGetfromcart;
    private CartinfoPojo cartinfoPojo;
    RecyclerView recyclerView;
    CustomAdapterCart customAdapterCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().setStatusBarColor(Color.DKGRAY);
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        tb.setNavigationIcon(R.mipmap.icon_back_button);
        tb.setTitleTextColor(Color.DKGRAY);
        getSupportActionBar().setTitle("Your Cart");
        initItems();

        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initItems() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        pDialog.show();
        apiClientGetfromcart = ApiClientBase.getApiClient().create(ApiClientGetFromCart.class);
        Call<CartinfoPojo> call= apiClientGetfromcart.getItems(UserSharedPreferenceData.getPrefLoggedinUserPhn(this));
        call.enqueue(new Callback<CartinfoPojo>() {
            @Override
            public void onResponse(Call<CartinfoPojo> call, Response<CartinfoPojo> response) {

                cartinfoPojo=response.body();
           //     System.out.println(cartinfoPojo.getItems().toString());
           //     Toast.makeText(CartActivity.this,cartinfoPojo.getItems().toString(),Toast.LENGTH_SHORT).show();
                recyclerView=(RecyclerView)findViewById(R.id.recyclerViewCart);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
                customAdapterCart=new CustomAdapterCart(CartActivity.this,cartinfoPojo.getItems(),CartActivity.this);
                recyclerView.setAdapter(customAdapterCart);
                tb.setTitle("Your Cart ("+ cartinfoPojo.getItems().size()+")");
                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CartinfoPojo> call, Throwable t) {
                pDialog.hide();

                Toast.makeText(CartActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                System.out.println("failure"+"+ : "+t.getMessage());
                System.out.println("failure"+"+ : "+t.getCause());
                System.out.println("failure"+"+ : "+t.toString());
            }
        });

    }
}
