package com.buffet_user.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buffet_user.R;
import com.buffet_user.pojo.MenuPojo;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.util.ArrayList;

/**
 * Created by akshaybmsa96 on 31/10/17.
 */

public class CustomAdapterDashboardCategory extends RecyclerView.Adapter<CustomAdapterDashboardCategory.ViewHolder> {

    private Context context;
    ArrayList<String> category;
    Toolbar tb;
    MenuPojo menuPojo=new MenuPojo();
    CustomAdapterDashboardMenu customAdapterDashboardMenu;
    RecyclerView recyclerView;
    StickyRecyclerHeadersDecoration headersDecor;

    public CustomAdapterDashboardCategory(Context context, ArrayList<String> category, Toolbar tb, MenuPojo menuPojo, RecyclerView recyclerView, StickyRecyclerHeadersDecoration headersDecor) {

        this.context = context;
        this.category=category;
        this.tb=tb;
        this.menuPojo=menuPojo;
        this.headersDecor=headersDecor;
        this.recyclerView=recyclerView;

    }


    @Override
    public CustomAdapterDashboardCategory.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dashboard_list_category_layout, parent, false);
        CustomAdapterDashboardCategory.ViewHolder holder = new CustomAdapterDashboardCategory.ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(CustomAdapterDashboardCategory.ViewHolder holder, int position) {
       holder.textViewItemName.setText(category.get(position));



    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {


        TextView textViewItemName;



        public ViewHolder(View view) {
            super(view);
            textViewItemName=(TextView)view.findViewById(R.id.textView);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {

            tb.setTitle(category.get(getPosition()));
            if(getPosition()==1) {

                customAdapterDashboardMenu = new CustomAdapterDashboardMenu(context, menuPojo.getMessage().getPizza());
                recyclerView.setAdapter(customAdapterDashboardMenu);
                recyclerView.removeItemDecoration(headersDecor);
                headersDecor = new StickyRecyclerHeadersDecoration(customAdapterDashboardMenu);
                recyclerView.addItemDecoration(headersDecor);
                customAdapterDashboardMenu.notifyDataSetChanged();
            }
            else if(getPosition()==2) {

                customAdapterDashboardMenu = new CustomAdapterDashboardMenu(context, menuPojo.getMessage().getSides());
                recyclerView.setAdapter(customAdapterDashboardMenu);
                recyclerView.removeItemDecoration(headersDecor);
                headersDecor = new StickyRecyclerHeadersDecoration(customAdapterDashboardMenu);
                recyclerView.addItemDecoration(headersDecor);
                customAdapterDashboardMenu.notifyDataSetChanged();

            }
        }

        @Override
        public boolean onLongClick(View view) {
            //   Toast.makeText(context, "Item will be Delete", Toast.LENGTH_SHORT).show();
            return true;
        }

    }


    @Override
    public int getItemCount() {

        return category.size();
    }


}

