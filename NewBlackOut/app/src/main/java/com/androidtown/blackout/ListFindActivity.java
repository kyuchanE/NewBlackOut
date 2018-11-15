package com.androidtown.blackout;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListFindActivity extends AppCompatActivity {

    ListDBHelper listDBHelper;

    RecyclerView rvList;
    RecyclerView.LayoutManager rvLayoutManager;

    ListAdapter listAdapter;

    Cursor cursor;

    private Context context;

    ArrayList<ItemList> dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_find);


        listDBHelper = new ListDBHelper(this);

        dateList = new ArrayList<>();

        context = this;

        setRecyclerView();

        ImageView ivBack = findViewById(R.id.ivBack);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListFindActivity.this, ListActivity.class);
                startActivity(intent);

                finish();
            }
        });



    }

    public void itemDelete(String date){

        SQLiteDatabase sql = listDBHelper.getWritableDatabase();
        sql.execSQL("DELETE FROM resultList WHERE Title = '" + date + "';");

        sql.close();



    }

    public void setRecyclerView(){

        SQLiteDatabase sql = listDBHelper.getReadableDatabase();

        cursor = sql.rawQuery("SELECT * FROM resultList", null);

        for (int i = 0; i <= cursor.getColumnCount(); i++) {

            while (cursor.moveToNext()) {
                dateList.add(new ItemList(cursor.getString(0), cursor.getString(1), cursor.getString(2)));

            }

        }

        cursor.close();
        sql.close();

        rvList = findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);
        rvLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(rvLayoutManager);

        listAdapter = new ListAdapter(dateList, context);

        rvList.setAdapter(listAdapter);

    }

}
