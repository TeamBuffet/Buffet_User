package com.buffet_user.activity.cart;

/**
 * Created by Ankit on 12/11/17.
 */

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.adapter.CustomAdapterCart;
import com.buffet_user.global.UserSharedPreferenceData;
import com.buffet_user.pojo.CartinfoPojo;
import com.buffet_user.pojo.UserPOJO;
import com.buffet_user.retrofit.ApiUtils;
import com.buffet_user.retrofit.SOService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    Toolbar tb;
    private CartinfoPojo cartinfoPojo;
    RecyclerView recyclerView;
    CustomAdapterCart customAdapterCart;
    private SOService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getWindow().setStatusBarColor(Color.DKGRAY);
        tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setTitleTextColor(Color.DKGRAY);
        getSupportActionBar().setTitle("Your Cart");
        mService = ApiUtils.getSOService();

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
        UserSharedPreferenceData userSharedPreferenceData = new UserSharedPreferenceData();
        UserPOJO userPOJO = new UserPOJO();
        userPOJO.setPhone(userSharedPreferenceData.getPrefLoggedinUserPhn(CartActivity.this));
        mService.getItems(userPOJO).enqueue(new Callback<CartinfoPojo>() {
            @Override
            public void onResponse(Call<CartinfoPojo> call, Response<CartinfoPojo> response) {

                cartinfoPojo = response.body();
                recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCart);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
                customAdapterCart = new CustomAdapterCart(CartActivity.this, cartinfoPojo.getItems(), CartActivity.this);
                recyclerView.setAdapter(customAdapterCart);
                tb.setTitle("Your Cart (" + cartinfoPojo.getItems().size() + ")");
                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<CartinfoPojo> call, Throwable t) {
                pDialog.hide();

                Toast.makeText(CartActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                System.out.println("failure" + "+ : " + t.getMessage());
                System.out.println("failure" + "+ : " + t.getCause());
                System.out.println("failure" + "+ : " + t.toString());
            }
        });

    }
}