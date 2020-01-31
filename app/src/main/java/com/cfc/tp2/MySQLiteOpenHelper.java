package com.cfc.tp2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    //SQL request for create the table
    private String creation = "create table scores (" +
            "id INTEGER PRIMARY KEY," +
            "game INTEGER NOT NULL," +
            "nom TEXT NOT NULL," +
            "score INTEGER NOT NULL," +
            "timerValue INTEGER NOT NULL);";


    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //if database change / doesn't exist
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(creation);
    }


    //if change of verison
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}