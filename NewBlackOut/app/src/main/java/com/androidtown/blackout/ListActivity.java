package com.androidtown.blackout;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ListActivity extends AppCompatActivity {

    //TODO 이전 기록 불러오기

    private ListGpsFragment listGpsFragment;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ImageView ivBack = findViewById(R.id.ivBack);
        ImageView ivFind = findViewById(R.id.ivFind);

        listGpsFragment = new ListGpsFragment();

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        setIsMap();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListActivity.this, ListFindActivity.class);
                startActivity(intent);

                finish();

            }
        });
    }

    public void setIsMap(){
        String lat, lng, date;

        Intent intent = getIntent();
        if (intent.getStringExtra("lat") != null) {

            lat = intent.getStringExtra("lat");
            lng = intent.getStringExtra("lng");
            date = intent.getStringExtra("date");

            Bundle bundle = new Bundle();
            bundle.putString("lat", lat);
            bundle.putString("lng", lng);
            bundle.putString("date", date);
            listGpsFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.flMap, listGpsFragment).commit();
            getSupportFragmentManager().beginTransaction().addToBackStack(null);
            getSupportFragmentManager().beginTransaction().commit();
        }
    }
}
