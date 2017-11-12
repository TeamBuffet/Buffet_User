package com.buffet_user.fragment.review;

/**
 * Created by Ankit on 12/11/17.
 */

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.buffet_user.R;
import com.buffet_user.global.UserSharedPreferenceData;
import com.buffet_user.global.utils.ProcessStringTopping;
import com.buffet_user.pojo.CartAddPojo;
import com.buffet_user.pojo.SingleMenuPojo;
import com.buffet_user.retrofit.ApiUtils;
import com.buffet_user.retrofit.SOService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class CategorizationFragment extends DialogFragment {

    private SingleMenuPojo singleMenuPojo;
    private String gData;
    private Toolbar tb;
    private Button buttonAdd, buttonCancel;
    JSONArray toppings;
    CartAddPojo cartAddPojo;
    String finalTopping = "";
    View view;
    private SOService mService;
    int i = 1;
    int extras = 0;
    int total_price = 0;
    String extraddons = "";
    private ProcessStringTopping processStringTopping;
    ArrayList<String> value;
    TextView textViewSize, textViewBasePrice, textViewToppings, textViewTotalPrice;
    CheckBox checkBoxExtraCheese, checkBoxExtraTopping, checkBoxExtraCheeseBurst, checkBoxExtraWheatCrust, checkBoxExtraItalianCrust, checkBoxExtraFreshPan;

    public CategorizationFragment() {
        // Required empty public constructor
    }

    static public CategorizationFragment newInstance(String data) {

        CategorizationFragment f = new CategorizationFragment();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("data", data);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pick a style based on the num.
        setHasOptionsMenu(true);
        gData = getArguments().getString("data");
        initialize();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categorization, container, false);
        mService = ApiUtils.getSOService();
        tb = (Toolbar) view.findViewById(R.id.toolbarid);
        ((AppCompatActivity) getActivity()).setSupportActionBar(tb);
        tb.setTitleTextColor(Color.DKGRAY);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(singleMenuPojo.getMenu_name());

        cartAddPojo = new CartAddPojo();
        checkBoxExtraTopping = (CheckBox) view.findViewById(R.id.checkBoxExtraToppings);
        checkBoxExtraCheese = (CheckBox) view.findViewById(R.id.checkBoxExtraCheese);
        checkBoxExtraCheeseBurst = (CheckBox) view.findViewById(R.id.checkBoxExtraCheeseBurst);
        checkBoxExtraWheatCrust = (CheckBox) view.findViewById(R.id.checkBoxExtraTWheatThinCrust);
        checkBoxExtraItalianCrust = (CheckBox) view.findViewById(R.id.checkBoxExtraItalianCrust);
        checkBoxExtraFreshPan = (CheckBox) view.findViewById(R.id.checkBoxExtraFreshPanCrust);

        checkBoxExtraTopping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBoxExtraTopping.isChecked()) {
                    extras = extras + Integer.parseInt(singleMenuPojo.getExtra_topping_price());
                    extraddons = extraddons + checkBoxExtraTopping.getText() + " ";
                    viewTotalPrice();
                } else {
                    extras = extras - Integer.parseInt(singleMenuPojo.getExtra_topping_price());
                    extraddons = extraddons.replace(checkBoxExtraTopping.getText() + " ", "");
                    viewTotalPrice();
                }

            }
        });

        checkBoxExtraCheese.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBoxExtraCheese.isChecked()) {
                    extras = extras + Integer.parseInt(singleMenuPojo.getExtra_cheese_price());
                    extraddons = extraddons + checkBoxExtraCheese.getText() + " ";
                    viewTotalPrice();
                } else {
                    extras = extras - Integer.parseInt(singleMenuPojo.getExtra_cheese_price());
                    extraddons = extraddons.replace(checkBoxExtraCheese.getText() + " ", "");
                    viewTotalPrice();
                }
            }
        });

        checkBoxExtraCheeseBurst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBoxExtraCheeseBurst.isChecked()) {
                    extras = extras + Integer.parseInt(singleMenuPojo.getExtra_cheese_burst_price());
                    extraddons = extraddons + checkBoxExtraCheeseBurst.getText() + " ";
                    viewTotalPrice();
                } else {
                    extras = extras - Integer.parseInt(singleMenuPojo.getExtra_cheese_burst_price());
                    extraddons = extraddons.replace(checkBoxExtraCheeseBurst.getText() + " ", "");
                    viewTotalPrice();
                }
            }
        });

        checkBoxExtraItalianCrust.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBoxExtraItalianCrust.isChecked()) {
                    extras = extras + Integer.parseInt(singleMenuPojo.getExtra_italian_crust_price());
                    extraddons = extraddons + checkBoxExtraItalianCrust.getText() + " ";
                    viewTotalPrice();
                } else {
                    extras = extras - Integer.parseInt(singleMenuPojo.getExtra_italian_crust_price());
                    extraddons = extraddons.replace(checkBoxExtraItalianCrust.getText() + " ", "");
                    viewTotalPrice();
                }
            }
        });

        checkBoxExtraWheatCrust.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBoxExtraWheatCrust.isChecked()) {
                    extras = extras + Integer.parseInt(singleMenuPojo.getExtra_wheat_crust_price());
                    extraddons = extraddons + checkBoxExtraWheatCrust.getText() + " ";
                    viewTotalPrice();
                } else {
                    extras = extras - Integer.parseInt(singleMenuPojo.getExtra_wheat_crust_price());
                    extraddons = extraddons.replace(checkBoxExtraWheatCrust.getText() + " ", "");
                    viewTotalPrice();
                }
            }
        });

        checkBoxExtraFreshPan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (checkBoxExtraFreshPan.isChecked()) {
                    extras = extras + Integer.parseInt(singleMenuPojo.getExtra_pan_crush_price());
                    extraddons = extraddons + checkBoxExtraFreshPan.getText() + " ";
                    viewTotalPrice();
                } else {
                    extras = extras - Integer.parseInt(singleMenuPojo.getExtra_pan_crush_price());
                    extraddons = extraddons.replace(checkBoxExtraFreshPan.getText() + " ", "");
                    viewTotalPrice();
                }
            }
        });


        if (singleMenuPojo.getSub_category_name().equals("Veg Singles")) {
            checkBoxExtraTopping.setVisibility(View.GONE);
            checkBoxExtraCheeseBurst.setVisibility(View.GONE);
            checkBoxExtraWheatCrust.setVisibility(View.GONE);
            checkBoxExtraItalianCrust.setVisibility(View.GONE);
            checkBoxExtraFreshPan.setVisibility(View.GONE);
        }

        textViewSize = (TextView) view.findViewById(R.id.textVeiwSize);
        textViewBasePrice = (TextView) view.findViewById(R.id.textVeiwBasePrice);
        textViewToppings = (TextView) view.findViewById(R.id.textViewToppings);
        textViewTotalPrice = (TextView) view.findViewById(R.id.textViewTotalPrice);
        if (singleMenuPojo.getTopping_name().equals("") || singleMenuPojo.getTopping_name() == null) {
            textViewToppings.setVisibility(View.GONE);
            View v = (View) view.findViewById(R.id.view);
            v.setVisibility(View.GONE);
        } else {
            textViewToppings.setText(singleMenuPojo.getTopping_name());
        }
        textViewSize.setText(singleMenuPojo.getSize());
        textViewBasePrice.setText(" ₹ " + singleMenuPojo.getPrice() + " /-");
        viewTotalPrice();

        if (singleMenuPojo.getTopping_count() != null && Integer.parseInt(singleMenuPojo.getTopping_count()) < 4) {
            processStringTopping = new ProcessStringTopping();
            toppings = processStringTopping.process(singleMenuPojo.getTopping_name());
            initGroupToppings(toppings);
        } else if (singleMenuPojo.getTopping_count() != null && Integer.parseInt(singleMenuPojo.getTopping_count()) > 3) {
            initChooseOneToppings(singleMenuPojo.getTopping_name());
        }
        //   Toast.makeText(getContext(),toppings,Toast.LENGTH_SHORT).show();

        buttonAdd = (Button) view.findViewById(R.id.buttonAdd);
        buttonCancel = (Button) view.findViewById(R.id.buttonCancel);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setPOJO();
                Gson gson = new Gson();
                String postData = (gson.toJson(cartAddPojo));
                System.out.println(postData);
                //   Toast.makeText(getContext(),finalTopping+" "+extraddons,Toast.LENGTH_SHORT).show();

                addToDb(postData);
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     getContext().finish();
                getActivity().onBackPressed();
            }
        });


        return view;
    }

    private void addToDb(String postData) {

        final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Adding...");
        pDialog.setCancelable(false);
        pDialog.show();
        // show it
        pDialog.show();
        mService.addCartItems(postData).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {

                int isTrans = response.body();
                if (isTrans == 1) {
                    Toast.makeText(getActivity(), "Item Added To Cart", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                } else {
                    Toast.makeText(getActivity(), "Cannot Add", Toast.LENGTH_SHORT).show();
                }
                pDialog.dismiss();

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                pDialog.hide();

                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
                System.out.println("failure" + "+ : " + t.getMessage());
                System.out.println("failure" + "+ : " + t.getCause());
                System.out.println("failure" + "+ : " + t.toString());
            }
        });
    }

    private void setPOJO() {
        UserSharedPreferenceData userSharedPreferenceData = new UserSharedPreferenceData();
        cartAddPojo.setUser_phn_number(userSharedPreferenceData.getPrefLoggedinUserPhn(getContext()));
        cartAddPojo.setItem_name(singleMenuPojo.getMenu_name());
        cartAddPojo.setItem_size(singleMenuPojo.getSize());
        cartAddPojo.setItem_base_price(singleMenuPojo.getPrice());
        cartAddPojo.setItem_toppings(finalTopping);
        cartAddPojo.setExtra_add_on(extraddons);
        cartAddPojo.setExtra_price(String.valueOf(extras));
        cartAddPojo.setTotal_price(String.valueOf(total_price));

    }

    private void viewTotalPrice() {
        total_price = Integer.parseInt(singleMenuPojo.getPrice()) + extras;
        textViewTotalPrice.setText(" ₹ " + total_price + " /-");
    }

    private void initChooseOneToppings(String toppings) {
        String[] str_chop = toppings.split(",");

        LinearLayout parent = (LinearLayout) view.findViewById(R.id.linearLayoutContainer);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setPadding(10, 10, 10, 10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(getContext());
        textView.setPadding(25, 5, 5, 5);
        textView.setElevation(0.5f);
        textView.setTextSize(12);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("Choice Topping");
        //   textView.setTextColor(getResources().getColor(R.color.topChoice));
        //  textView.setBackground(getResources().getDrawable(R.drawable.recbound));
        parent.setBackground(getResources().getDrawable(R.drawable.recbound));
        parent.addView(textView);
        final int count = str_chop.length;
        final CheckBox c[] = new CheckBox[20];
        for (i = 0; i < str_chop.length; i++) {
            final CheckBox ch = new CheckBox(getActivity());
            c[i] = ch;
            ch.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.toppingcheckboxcolor)));
            ch.setTextSize(12);
            ch.setText(str_chop[i].replace(" ", ""));
            linearLayout.addView(ch);

            ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (ch.isChecked()) {
                        for (int i = 0; i < count; i++) {
                            c[i].setChecked(false);
                        }
                        ch.setChecked(true);
                        finalTopping = ch.getText().toString();
                    }
                }
            });
        }
        c[0].setChecked(true);
        finalTopping = c[0].getText().toString();
        parent.addView(linearLayout);

    }

    private void initGroupToppings(JSONArray toppings) {

        try {

            for (int i = 0; i < toppings.length(); i++) {
                JSONObject js = null;
                js = toppings.getJSONObject(i);
                String value = js.getString("value");
                value = value.replace("[", "");
                value = value.replace("]", "");
                String[] str_chop = value.split(",");
                if (str_chop.length == 3)
                    threeaddviewRadio(str_chop);
                else if (str_chop.length == 2)
                    twoaddviewRadio(str_chop);
                //   Toast.makeText(getContext(),str_chop[0]+" "+str_chop[1],Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initialize() {

        JsonElement jsonElement = new JsonParser().parse(gData);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        singleMenuPojo = (gson.fromJson(jsonElement, SingleMenuPojo.class));
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.cart);
        item.setVisible(false);
    }

    void twoaddviewRadio(String[] str_chop) {
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.linearLayoutContainer);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setPadding(10, 10, 10, 10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(getContext());
        textView.setPadding(25, 5, 5, 5);
        textView.setElevation(0.5f);
        textView.setTextSize(12);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("Choice Topping " + i++);
        //   textView.setTextColor(getResources().getColor(R.color.topChoice));
        //  textView.setBackground(getResources().getDrawable(R.drawable.recbound));
        parent.setBackground(getResources().getDrawable(R.drawable.recbound));
        parent.addView(textView);

        final CheckBox ch = new CheckBox(getActivity());
        ch.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.toppingcheckboxcolor)));
        ch.setTextSize(12);
        ch.setText(str_chop[0].replace(" ", ""));
        ch.setChecked(true);
        finalTopping = finalTopping + ch.getText().toString() + " ";

        linearLayout.addView(ch);

        final CheckBox ch2 = new CheckBox(getActivity());
        ch2.setTextSize(12);
        ch2.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.toppingcheckboxcolor)));
        ch2.setText(str_chop[1].replace(" ", ""));

        linearLayout.addView(ch2);
        parent.addView(linearLayout);

        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ch.isChecked()) {
                    if (finalTopping.contains(ch2.getText())) ;
                    {
                        finalTopping = finalTopping.replace(ch2.getText() + " ", "");
                    }

                    finalTopping = finalTopping + ch.getText().toString() + " ";
                    ch2.setChecked(false);

                }
            }
        });

        ch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ch2.isChecked()) {
                    if (finalTopping.contains(ch.getText())) ;
                    {
                        finalTopping = finalTopping.replace(ch.getText() + " ", "");
                    }

                    finalTopping = finalTopping + ch2.getText().toString() + " ";
                    ch.setChecked(false);

                }
            }
        });

        //     ViewGroup viewGroup = (ViewGroup) view;
        //     viewGroup.addView(parent);

    }


    private void threeaddviewRadio(String[] str_chop) {

        LinearLayout parent = (LinearLayout) view.findViewById(R.id.linearLayoutContainer);
        LinearLayout linearLayout = new LinearLayout(getActivity());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setPadding(10, 10, 10, 10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(getContext());
        textView.setPadding(25, 5, 5, 5);
        textView.setElevation(0.5f);
        textView.setTextSize(12);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText("Choice Topping " + i++);
        //   textView.setTextColor(getResources().getColor(R.color.topChoice));
        //  textView.setBackground(getResources().getDrawable(R.drawable.recbound));
        parent.setBackground(getResources().getDrawable(R.drawable.recbound));
        parent.addView(textView);

        final CheckBox ch = new CheckBox(getActivity());
        ch.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.toppingcheckboxcolor)));
        ch.setTextSize(12);
        ch.setText(str_chop[0].replace(" ", ""));
        ch.setChecked(true);
        finalTopping = finalTopping + ch.getText().toString() + " ";

        linearLayout.addView(ch);

        final CheckBox ch2 = new CheckBox(getActivity());
        ch2.setTextSize(12);
        ch2.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.toppingcheckboxcolor)));
        ch2.setText(str_chop[1].replace(" ", ""));
        linearLayout.addView(ch2);

        final CheckBox ch3 = new CheckBox(getActivity());
        ch3.setTextSize(12);
        ch3.setButtonTintList(ColorStateList.valueOf(getResources().getColor(R.color.toppingcheckboxcolor)));
        ch3.setText(str_chop[2].replace(" ", ""));
        linearLayout.addView(ch3);

        parent.addView(linearLayout);

        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ch.isChecked()) {
                    if (finalTopping.contains(ch2.getText())) ;
                    {
                        finalTopping = finalTopping.replace(ch2.getText() + " ", "");
                    }
                    if (finalTopping.contains(ch3.getText())) ;
                    {
                        finalTopping = finalTopping.replace(ch3.getText() + " ", "");
                    }

                    finalTopping = finalTopping + ch.getText().toString() + " ";
                    ch2.setChecked(false);
                    ch3.setChecked(false);

                }
            }
        });

        ch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ch2.isChecked()) {
                    if (finalTopping.contains(ch.getText())) ;
                    {
                        finalTopping = finalTopping.replace(ch.getText() + " ", "");
                    }

                    if (finalTopping.contains(ch3.getText())) ;
                    {
                        finalTopping = finalTopping.replace(ch3.getText() + " ", "");
                    }

                    finalTopping = finalTopping + ch2.getText().toString() + " ";
                    ch.setChecked(false);
                    ch3.setChecked(false);

                }
            }
        });

        ch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ch3.isChecked()) {
                    if (finalTopping.contains(ch.getText())) ;
                    {
                        finalTopping = finalTopping.replace(ch.getText() + " ", "");
                    }

                    if (finalTopping.contains(ch2.getText())) ;
                    {
                        finalTopping = finalTopping.replace(ch2.getText() + " ", "");
                    }

                    finalTopping = finalTopping + ch3.getText().toString() + " ";
                    ch.setChecked(false);
                    ch2.setChecked(false);

                }
            }
        });
    }


}
