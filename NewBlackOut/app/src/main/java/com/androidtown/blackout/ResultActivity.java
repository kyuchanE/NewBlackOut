package com.androidtown.blackout;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class ResultActivity extends AppCompatActivity{

    private ToolBar toolbar;

    private BottomNavigationView bottomView;

    private GpsFragment gpsFrag;
    private TimeFragment timeFrag;

    public static Context mContext;

    GpsInfo gps;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        bottomView = findViewById(R.id.bottom_navigation);

        gpsFrag = new GpsFragment();
        timeFrag = new TimeFragment();
        mContext = this;

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        gps = new GpsInfo(ResultActivity.this);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);

        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_map:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, gpsFrag).commit();
                        break;

                    case R.id.action_home:

                        break;

                    case R.id.action_timeline:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, timeFrag).commit();
                        break;
                }
                return true;
            }
        });

        // 툴바 설정
        toolbar = new ToolBar(ResultActivity.this);
        toolbar.setToolbar();
        toolbar.setHome();
        toolbar.setMenu();
    }

}
