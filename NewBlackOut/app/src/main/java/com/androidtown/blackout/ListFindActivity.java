package com.androidtown.blackout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListFindActivity extends AppCompatActivity {

    ListDBHelper listDBHelper;
    String testText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_find);

        ImageView ivBack = findViewById(R.id.ivBack);
        TextView tvTest = findViewById(R.id.tvTest);

        listDBHelper = new ListDBHelper(this);

        SQLiteDatabase sql = listDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sql.rawQuery("SELECT * FROM resultList", null);
        while(cursor.moveToNext()){
            testText += cursor.getString(0);
            testText += cursor.getString(1);
            testText += cursor.getString(2);

            tvTest.setText(testText);
        }

        cursor.close();
        sql.close();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                finish();
            }
        });


    }
}
