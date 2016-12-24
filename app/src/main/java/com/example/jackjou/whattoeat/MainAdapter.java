package com.example.jackjou.whattoeat;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JackJou on 2016/12/1.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainContentViewHolder>{

    /*Item點擊監聽*/
    private OnItemClickListener mItemOnClickListener;
    /*數據*/
    private List<FoodList> foodLists = null;
    /*Item拖拽滑動幫助*/
    private ItemTouchHelper itemTouchHelper;

    public MainAdapter(List<FoodList> foodLists) {
        this.foodLists = foodLists;
    }

    public void notifyDataSetChanged(List<FoodList> foodLists) {
        this.foodLists = foodLists;
        super.notifyDataSetChanged();
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemOnClickListener = onItemClickListener;
    }

    @Override
    public MainContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MainContentViewHolder holder, int position) {
        holder.setData();
    }

    @Override
    public int getItemCount() {
        return foodLists == null ? 0 : foodLists.size();
    }

    public FoodList getData(int position) {
        return foodLists.get(position);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


    class MainContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
        /*名字和備註*/
        private TextView name, remark;
        /*觸摸就可以拖拽*/
        private ImageView mIvTouch;

        public MainContentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
            remark = (TextView) itemView.findViewById(R.id.remark);
            mIvTouch = (ImageView) itemView.findViewById(R.id.iv_touch);
            mIvTouch.setOnTouchListener(this);
        }

        /*给這個Item设置數據*/
        public void setData() {
            FoodList foodLists = getData(getAdapterPosition());
            name.setText(foodLists.getName());
            remark.setText(foodLists.getRemark());
        }

        @Override
        public void onClick(View view) {
            if (view == itemView && itemTouchHelper != null) {
                mItemOnClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (view == mIvTouch)
                itemTouchHelper.startDrag(this);
            return false;
        }
    }

}
