package com.example.jackjou.whattoeat;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by JackJou on 2016/12/26.
 */

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView name, note;
    ItemClickListener itemClickListener;

    public MyHolder(View itemView) {
        super(itemView);

        name= (TextView) itemView.findViewById(R.id.name);
        note= (TextView) itemView.findViewById(R.id.note);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }

}
