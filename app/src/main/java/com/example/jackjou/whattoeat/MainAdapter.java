package com.example.jackjou.whattoeat;

import android.content.Context;
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

        //CLICKED
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Snackbar.make(v,list.get(pos).getName(),Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }





//    /*Item點擊監聽*/
//    private OnItemClickListener mItemOnClickListener;
//    /*數據*/
//    private List<FoodList> foodLists = null;
//    /*Item拖拽滑動幫助*/
//    private ItemTouchHelper itemTouchHelper;
//
//    public MainAdapter(List<FoodList> foodLists) {
//        this.foodLists = foodLists;
//    }
//
//    public void notifyDataSetChanged(List<FoodList> foodLists) {
//        this.foodLists = foodLists;
//        super.notifyDataSetChanged();
//    }
//
//    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
//        this.itemTouchHelper = itemTouchHelper;
//    }
//
//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.mItemOnClickListener = onItemClickListener;
//    }
//
//    @Override
//    public MainContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new MainContentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(MainContentViewHolder holder, int position) {
//        holder.setData();
//    }
//
//    @Override
//    public int getItemCount() {
//        return foodLists == null ? 0 : foodLists.size();
//    }
//
//    public FoodList getData(int position) {
//        return foodLists.get(position);
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }


//    class MainContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {
//        /*名字和性别*/
//        private TextView mName, mNote;
//        /*觸摸就可以拖拽*/
//        private ImageView mIvTouch;
//
//        public MainContentViewHolder(View itemView) {
//            super(itemView);
//            itemView.setOnClickListener(this);
//            mName = (TextView) itemView.findViewById(R.id.name);
//            mNote = (TextView) itemView.findViewById(R.id.note);
//            mIvTouch = (ImageView) itemView.findViewById(R.id.iv_touch);
//            mIvTouch.setOnTouchListener(this);
//        }
//
//        /*给這個Item设置數據*/
//        public void setData() {
//            FoodList foodLists = getData(getAdapterPosition());
//            mName.setText(foodLists.getName());
//            mNote.setText(foodLists.getNote());
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (view == itemView && itemTouchHelper != null) {
//                mItemOnClickListener.onItemClick(view, getAdapterPosition());
//            }
//        }
//
//        @Override
//        public boolean onTouch(View view, MotionEvent event) {
//            if (view == mIvTouch)
//                itemTouchHelper.startDrag(this);
//            return false;
//        }
//    }

}
