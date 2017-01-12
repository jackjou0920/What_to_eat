package com.example.jackjou.whattoeat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<FoodList> list = new ArrayList<>();
    private String TBName = "d_TB";
    private ArrayList<String> PickerData = new ArrayList<>();
    private WheelPicker wheelPicker;
    private Button actionBtn;
    private int count;
    private int position;
    private ScheduledExecutorService scheduler;
    private Runnable runnable;
    private Runnable runnable2;
    private SharedPreferences sp;
    private static final String data = "DATA";

    private boolean halt;
    Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        wheelPicker = (WheelPicker) findViewById(R.id.wheelPicker);
        actionBtn = (Button) findViewById(R.id.start);
        actionBtn.setOnClickListener(new OnActionClickListener());

        //isRunning = false;
        position = 0;
        scheduler = Executors.newScheduledThreadPool((Runtime.getRuntime().availableProcessors()));

        initWheelPicker();

        halt = true;
        runrun();

        waittime();
        sp = getSharedPreferences(data,0);
        TBName = sp.getString("tb", "");
        //wheelPicker.setOnItemSelectedListener();
        setPickerData(wheelPicker);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent();
            intent.putExtra("TBName",TBName);
            intent.setClass(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_utaipei) {
            TBName = "d_TB";
            sp = getSharedPreferences(data,0);
            sp.edit().putString("tb", TBName).commit();
            //initWheelPicker();
            setPickerData(wheelPicker);
        } else if (id == R.id.nav_home) {
            TBName = "d_TB2";
            sp = getSharedPreferences(data,0);
            sp.edit().putString("tb", TBName).commit();
            //initWheelPicker();
            setPickerData(wheelPicker);
        } else if (id == R.id.nav_station) {
            TBName = "d_TB3";
            sp = getSharedPreferences(data,0);
            sp.edit().putString("tb", TBName).commit();
            setPickerData(wheelPicker);
        } else if (id == R.id.nav_ximending) {
            TBName = "d_TB4";
            sp = getSharedPreferences(data,0);
            sp.edit().putString("tb", TBName).commit();
            setPickerData(wheelPicker);
        }
        retrieve(TBName);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //RETREIEV
    private void retrieve(String TBName){
        list.clear();

        MyDatabase db = new MyDatabase(this);
        db.openDB();

        //RETRIEVE
        Cursor c = db.getAll(TBName);

        //LOOP AND ADD TO ARRAYLIST
        while (c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            String note = c.getString(2);

            FoodList p = new FoodList(id, name, note);

            //ADD TO ARRAYLIS
            list.add(p);
        }

        db.closeDB();
    }

    public void setPickerData(WheelPicker wheelPicker){
        PickerData.clear();
        MyDatabase db = new MyDatabase(this);
        db.openDB();

        //RETRIEVE
        Cursor c = db.getAll(TBName);
        //Cursor cu = db.rawQuery("SELECT * FROM ",null);
        //LOOP AND ADD TO ARRAYLIST
        while (c.moveToNext()){
            //int id = c.getInt(0);
            String name = c.getString(1);
            //String note = c.getString(2);

            //FoodList p = new FoodList(id, name, note);
            PickerData.add(name);

        }
        db.closeDB();

        wheelPicker.setData(PickerData);
        count = PickerData.size();
    }

    private void initWheelPicker(){
//        List<String> data = new ArrayList<>();
//        data.add("Hello");
//        data.add("Hey");
//        data.add("Hi");
//        count = data.size();
//
//        wheelPicker.setData(data);
        wheelPicker.setCyclic(true);
        wheelPicker.setSelectedItemTextColor(ContextCompat.getColor(this, R.color.red));
        wheelPicker.setIndicator(true);
        wheelPicker.setSelectedItemPosition(position);
        wheelPicker.setCurved(true);
        wheelPicker.setAtmospheric(true);
        wheelPicker.setItemTextSize(100);
        wheelPicker.setCurtainColor(ContextCompat.getColor(this, R.color.white));
        //wheelPicker.setCurtain(true);

    }

    private class OnActionClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
//            if(isRunning){
//                scheduler.shutdownNow();
//                scheduler = new ScheduledThreadPoolExecutor((Runtime.getRuntime().availableProcessors()));
//                actionBtn.setText("Start");
//            }
//            else{
            scheduler.scheduleWithFixedDelay(runnable, 0, 40, TimeUnit.MILLISECONDS);
//                actionBtn.setText("Stop");
//            }
//            isRunning = !isRunning;
        }
    }

    private void runrun(){
        thread = new Thread (runnable = new Runnable() {
            @Override
            public void run() {
                if (position < count) {
                    position++;
                } else {
                    position = 0;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        wheelPicker.setSelectedItemPosition(position);
                    }
                });
            }
        });

        thread.start();

    }

    private void waittime(){


        new Thread(runnable2 = new Runnable() {
            int sleepSecond;

            @Override
            public void run() {
                while (halt) {
                    try {
                        sleepSecond = (int) ((Math.random() * 3) + 5);
                        sleepSecond = sleepSecond * 1000;
                        Thread.sleep(sleepSecond);

                        Log.e("sleep", Integer.toString(sleepSecond));

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    scheduler.shutdownNow();
                    scheduler = new ScheduledThreadPoolExecutor((Runtime.getRuntime().availableProcessors()));

                }
            }
        }).start();

        thread.interrupt();


    }
}
