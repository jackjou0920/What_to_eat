package com.example.jackjou.whattoeat;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by JackJou on 2016/12/1.
 */

public class MainAdapter extends RecyclerView.Adapter<MyHolder>{
    Context c;
    ArrayList<FoodList> list;

    public MainAdapter(Context c, ArrayList<FoodList> list) {
        this.c = c;
        this.list = list;
    }

    //INITIALIZE VIEWHODER
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //VIEW OBJ
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model_layout,null);

        //HOLDER
        MyHolder holder=new MyHolder(v);

        return holder;
    }

    //BIND VIEW TO DATA
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.note.setText(list.get(position).getNote());

        //HANDLE ITEMCLICKS
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //OPEN DETAIL ACTIVITY
                //PASS DATA

                //CREATE INTENT
                Intent i = new Intent(c, DetailActivity.class);

                //LOAD DATA
                i.putExtra("NAME",list.get(pos).getName());
                i.putExtra("NOTE",list.get(pos).getNote());
                i.putExtra("ID",list.get(pos).getId());

                //START ACTIVITY
                c.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
