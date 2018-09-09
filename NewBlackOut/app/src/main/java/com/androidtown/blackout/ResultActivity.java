package com.androidtown.blackout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class ResultActivity extends AppCompatActivity{

    private ToolBar toolbar;

    private BottomNavigationView bottomView;
    private Context context;

    private GpsFragment gpsFrag;
    private TimeFragment timeFrag;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        bottomView = findViewById(R.id.bottom_navigation);

        gpsFrag = new GpsFragment();
        timeFrag = new TimeFragment();
        context = this;

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_map:
                        /*fragmentTransaction.replace(R.id.fragment, gpsFrag);
                        fragmentTransaction.commit();*/
                        getSupportFragmentManager().beginTransaction().replace(R.id.fl, gpsFrag).commit();
                        break;

                    case R.id.action_home:

                        break;

                    case R.id.action_timeline:
                       /* fragmentTransaction.replace(R.id.fragment, timeFrag);
                        fragmentTransaction.commit();*/
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
