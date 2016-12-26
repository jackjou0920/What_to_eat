package com.example.jackjou.whattoeat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by JackJou on 2016/12/25.
 */

public class MyDatabase {

    private SQLiteDatabase db = null;
    static final String DB_NAME="d_DB";
    static final String TB_NAME="d_TB";
    static final String ID="id";
    static final String NAME = "name";
    static final String NOTE = "note";

    static final String CREATE_TB="CREATE TABLE d_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
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
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //CLOSE DATABASE
    public void closeDB(){
        db.close();
    }

    //INSERT
    public long addDB(String name, String note){
        ContentValues cv=new ContentValues();
        cv.put(NAME, name);
        cv.put(NOTE, note);

        return db.insert(TB_NAME, ID, cv);
    }

    //RETRIEVE
    public Cursor getAll() {
        String[] columns={ID, NAME, NOTE};

        return db.query(TB_NAME,columns,null,null,null,null,null);
    }


}
