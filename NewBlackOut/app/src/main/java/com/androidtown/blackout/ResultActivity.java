package com.androidtown.blackout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private ToolBar toolbar;

    private BottomNavigationView bottomView;

    private GpsFragment gpsFrag;
    private TimeFragment timeFrag;
    private TestFragment testFrag;

    public static Context mContext;

    public ArrayList<String> listLat;
    public ArrayList<String> listLng;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        listLng = new ArrayList<>();
        listLat = new ArrayList<>();

        Intent recive = getIntent();
        listLat = recive.getStringArrayListExtra("lat");
        listLng = recive.getStringArrayListExtra("lng");
        Log.e("@@@Result/lat[0]@@@", listLat.get(0));

        bottomView = findViewById(R.id.bottom_navigation);

        gpsFrag = new GpsFragment();
        timeFrag = new TimeFragment();
        testFrag = new TestFragment();
        mContext = this;

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("lat", listLat);
        bundle.putStringArrayList("lng", listLng);
        gpsFrag.setArguments(bundle);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 3);


        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_map:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, gpsFrag).commit();
                        getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        getSupportFragmentManager().beginTransaction().commit();
                        break;

                    case R.id.action_home:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, testFrag).commit();
                        getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        getSupportFragmentManager().beginTransaction().commit();
                        break;

                    case R.id.action_timeline:

                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, timeFrag).commit();
                        getSupportFragmentManager().beginTransaction().addToBackStack(null);
                        getSupportFragmentManager().beginTransaction().commit();
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
