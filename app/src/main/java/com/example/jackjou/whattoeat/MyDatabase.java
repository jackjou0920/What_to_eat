package com.example.jackjou.whattoeat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JackJou on 2017/1/6.
 */

public class MyDatabase {

    private SQLiteDatabase db = null;
    static final String DB_NAME="d_DB";
    static final String TB_NAME="d_TB";
    static final String ID="id";
    static final String NAME = "name";
    static final String NOTE = "note";

    static final String CREATE_TB ="create table IF NOT EXISTS d_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,note TEXT NOT NULL);";
    static final String CREATE_TB2 ="create table IF NOT EXISTS d_TB2(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,note TEXT NOT NULL);";
    static final String CREATE_TB3 ="create table IF NOT EXISTS d_TB3(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,note TEXT NOT NULL);";
    static final String CREATE_TB4 ="create table IF NOT EXISTS d_TB4(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "name TEXT NOT NULL,note TEXT NOT NULL);";


    private Context ctx = null;
    public MyDatabase(Context ctx){
        this.ctx = ctx;
    }

    //OPEN DATABASE
    public void openDB(){
        db = ctx.openOrCreateDatabase(DB_NAME, 0, null);
        try {
            db.execSQL(CREATE_TB);
            db.execSQL(CREATE_TB2);
            db.execSQL(CREATE_TB3);
            db.execSQL(CREATE_TB4);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //CLOSE DATABASE
    public void closeDB(){

        db.close();
    }

    //INSERT
    public long addDB(String TBName, String name, String note){
        try {
            ContentValues cv = new ContentValues();
            cv.put(NAME, name);
            cv.put(NOTE, note);

            return db.insert(TBName, ID, cv);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //UPDATE
    public long UPDATE(String TBName,int id,String name,String note){
        try{
            ContentValues cv = new ContentValues();
            cv.put(NAME, name);
            cv.put(NOTE, note);

            return db.update(TBName, cv, ID+" =?", new String[]{String.valueOf(id)});

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //DELETE
    public long Delete(String TBName, int id){
        try{
            return db.delete(TBName, ID+" =?", new String[]{String.valueOf(id)});

        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //RETRIEVE
    public Cursor getAll(String table) {
        String[] columns={ID, NAME, NOTE};

        return db.query(table,columns,null,null,null,null,null);
    }
}
