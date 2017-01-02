package com.example.jackjou.whattoeat;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    private ArrayList<String> PickerData = new ArrayList<>();
    private WheelPicker wheelPicker;
    private Button actionBtn;
    private int count;
    private int position;
    private ScheduledExecutorService scheduler;
    private Runnable runnable;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        //add item
//        FloatingActionButton add= (FloatingActionButton) findViewById(R.id.add);
//        add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        WheelPicker wheelCenter = (WheelPicker) findViewById(R.id.main_wheel_center);
//        wheelCenter.setOnItemSelectedListener(this);




        wheelPicker = (WheelPicker) findViewById(R.id.wheelPicker);
        actionBtn = (Button) findViewById(R.id.start);
        actionBtn.setOnClickListener(new OnActionClickListener());

        isRunning = false;
        position = 0;
        scheduler = Executors.newScheduledThreadPool((Runtime.getRuntime().availableProcessors()));

        initWheelPicker();

        runnable = new Runnable() {
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
        };

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

        } else if (id == R.id.nav_home) {

        } else if (id == R.id.nav_station) {

        } else if (id == R.id.nav_ximending) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    @Override
//    public void onItemSelected(WheelPicker picker, Object data, int position) {
//        //String text = "";
//        //switch (picker.getId()) {
//        //case R.id.main_wheel_left:
//        //    text = "Left:";
//        //    break;
//        //   case R.id.main_wheel_center:
//        //    text = "Center:";
//        //       break;
//        //case R.id.main_wheel_right:
//        //    text = "Right:";
//        //    break;
//        //}
//        Toast.makeText(this,  String.valueOf(data), Toast.LENGTH_SHORT).show();
//    }


    public void setPickerData(WheelPicker wheelCenter){

        MyDatabase db = new MyDatabase(this);
        db.openDB();

        //RETRIEVE
        Cursor c = db.getAll();
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
    }

    private void initWheelPicker(){
        List<String> data = new ArrayList<>();
        data.add("Hello");
        data.add("Hey");
        data.add("Hi");
        count = data.size();

        wheelPicker.setData(data);
        wheelPicker.setCyclic(true);
        wheelPicker.setSelectedItemTextColor(ContextCompat.getColor(this, R.color.red));
        wheelPicker.setIndicator(true);
        wheelPicker.setSelectedItemPosition(position);
    }

    private class OnActionClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(isRunning){
                scheduler.shutdownNow();
                scheduler = new ScheduledThreadPoolExecutor((Runtime.getRuntime().availableProcessors()));
                actionBtn.setText("Start");
            }
            else{
                scheduler.scheduleWithFixedDelay(runnable, 0, 40, TimeUnit.MILLISECONDS);
                actionBtn.setText("Stop");
            }
            isRunning = !isRunning;
        }
    }

    private void initWheelPicker(){
        List<String> data = new ArrayList<>();
        data.add("Hello");
        data.add("Hey");
        data.add("Hi");
        count = data.size();

        wheelPicker.setData(data);
        wheelPicker.setCyclic(true);
        wheelPicker.setSelectedItemTextColor(ContextCompat.getColor(this, R.color.red));
        wheelPicker.setIndicator(true);
        wheelPicker.setSelectedItemPosition(position);
    }

    private class OnActionClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(isRunning){
                scheduler.shutdownNow();
                scheduler = new ScheduledThreadPoolExecutor((Runtime.getRuntime().availableProcessors()));
                actionBtn.setText("Start");
            }
            else{
                scheduler.scheduleWithFixedDelay(runnable, 0, 40, TimeUnit.MILLISECONDS);
                actionBtn.setText("Stop");
            }
            isRunning = !isRunning;
        }
    }
}
