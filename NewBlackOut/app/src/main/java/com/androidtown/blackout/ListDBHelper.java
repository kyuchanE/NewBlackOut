package com.androidtown.blackout;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ListDBHelper extends SQLiteOpenHelper {

    SQLiteDatabase sqLiteDatabase;

    //이전 기록을 불러올때 사용할 이전 기록 DB (  )
    //TODO 이전기록 ListDB CREATE
    public ListDBHelper(Context context) {
        super(context, "resultList", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE resultList (Title TEXT, lat TEXT, lng TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void onSave(String title, String lat, String lng){

        try{
            sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.execSQL("INSERT INTO resultList VALUES('"+ title +"', '"+ lat +"', '"+ lng +"');");

        }catch (SQLException e){

        }

    }
}
