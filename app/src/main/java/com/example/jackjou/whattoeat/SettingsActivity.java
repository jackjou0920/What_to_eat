package com.example.jackjou.whattoeat;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<FoodList> list = new ArrayList<>();
    //數據適配器
    private MainAdapter mainAdapter;
    //滑動拖拽的幫助類
    //private DefaultItemTouchHelper itemTouchHelper;

    EditText nameText;
    EditText noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //add item
        FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFood();
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 必須要设置一个佈局管理器
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mainAdapter = new MainAdapter(this, list);
//        mainAdapter.setOnItemClickListener(onItemClickListener);

//        recyclerView.setAdapter(mainAdapter);


//        itemTouchHelper = new DefaultItemTouchHelper(onItemTouchCallbackListener);
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//
//        mainAdapter.setItemTouchHelper(itemTouchHelper);
//
//        itemTouchHelper.setDragEnable(true);
//        itemTouchHelper.setSwipeEnable(true);

//        deletText = (ImageButton)findViewById(R.id.delete);

//        deletText.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        retrieve();
    }

//    private DefaultItemTouchHelpCallback.OnItemTouchCallbackListener onItemTouchCallbackListener = new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
//        @Override
//        public void onSwiped(int adapterPosition) {
//            if (list != null) {
//                list.remove(adapterPosition);
//                mainAdapter.notifyItemRemoved(adapterPosition);
//            }
//        }
//
//        @Override
//        public boolean onMove(int srcPosition, int targetPosition) {
//            if (list != null) {
//                // 更換數源中的數據Item的位置
//                Collections.swap(list, srcPosition, targetPosition);
//
//                // 更新UI中的Item的位置，主要是给用户看到交互效果
//                mainAdapter.notifyItemMoved(srcPosition, targetPosition);
//                return true;
//            }
//            return false;
//        }
//    };

    /*RecyclerView的Item點擊監聽*/
//    private MainAdapter.OnItemClickListener onItemClickListener = new MainAdapter.OnItemClickListener() {
//        @Override
//        public void onItemClick(View view, int position) {
//            Toast.makeText(SettingsActivity.this, "第" + position + "被點擊", Toast.LENGTH_SHORT).show();
//        }
//    };


    private void addFood(){

        final View item = LayoutInflater.from(SettingsActivity.this).inflate(R.layout.custom_layout, null);
        new AlertDialog.Builder(SettingsActivity.this)
                .setView(item)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nameText = (EditText) item.findViewById(R.id.nameEdit);
                        noteText = (EditText) item.findViewById(R.id.noteEdit);

                        save(nameText.getText().toString(), noteText.getText().toString());
                    }
                })
                .show();
    }

    private void save(String name,String note){
        MyDatabase db = new MyDatabase(this);

        //OPEN DB
        db.openDB();

        //COMMIT
        long result = db.addDB(name,note);

        if(result > 0){
            nameText.setText("");
            noteText.setText("");
        }
        else{
            Snackbar.make(nameText,"Unable To Save",Snackbar.LENGTH_SHORT).show();
        }

        db.closeDB();

        //REFRESH
        retrieve();
    }

    //RETREIEV
    private void retrieve(){
        list.clear();

        MyDatabase db = new MyDatabase(this);
        db.openDB();

        //RETRIEVE
        Cursor c = db.getAll();

        //LOOP AND ADD TO ARRAYLIST
        while (c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            String note = c.getString(2);

            FoodList p = new FoodList(id, name, note);

            //ADD TO ARRAYLIS
            list.add(p);
        }

        //CHECK IF ARRAYLIST ISNT EMPTY
        if(!(list.size()<1)){
            recyclerView.setAdapter(mainAdapter);
        }

        db.closeDB();
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieve();
    }

}
