package com.example.kqnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "account.db";
    private static final String TBL_NAME = "AccountTbl";
    private SQLiteDatabase db;
    public DBHelper(Context c){
        super(c,DB_NAME,null,2);
    }


    @Override
    public void onCreate(SQLiteDatabase db){
        this.db = db;
        String CREATE_TBL = "create table IF NOT EXISTS AccountTbl (_id integer primary key autoincrement,name text not null,password text not null)";
        db.execSQL(CREATE_TBL);
        CREATE_TBL = "create table IF NOT EXISTS UserTbl (_id integer primary key autoincrement,name text not null,note text not null)";
        db.execSQL(CREATE_TBL);
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){

    }

}
