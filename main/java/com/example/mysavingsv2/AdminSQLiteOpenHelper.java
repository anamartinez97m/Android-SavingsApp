package com.example.mysavingsv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase bbddSavings){
        bbddSavings.execSQL("create table users(username text PRIMARY KEY , email text, password text)");
        bbddSavings.execSQL("create table historial(id INTEGER PRIMARY KEY AUTOINCREMENT, username text, category text, " +
                "nameConcept text, moneyConcept real, dayDate int, monthDate int, yearDate int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase bbddSavings, int oldVersion, int newVersion){
        bbddSavings.execSQL("DROP TABLE IF EXISTS users");
        bbddSavings.execSQL("DROP TABLE IF EXISTS historial");

        if (newVersion > oldVersion) {
            //bbddSavings.execSQL("ALTER TABLE users ADD COLUMN imageColumn text DEFAULT ''");
        }
        onCreate(bbddSavings);
    }
}
