package com.androidtown.blackout;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;

    public static Context mContext;

    GpsInfo mService;
    Boolean mBound;
    ServiceConnection mConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        btnStart = findViewById(R.id.btnStart);

        mConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                GpsInfo.LocalBinder binder = (GpsInfo.LocalBinder) service;
                mService = binder.getService();
                mBound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                mBound = false;
            }
        };


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(isServiceRunningCheck())){
                    // gps 실행중 화면은 메인 화면
                    Log.d("test", "gps 실행 경로 저장중");
                    btnStart.setText("결과보기");
                    Intent service = new Intent(getApplicationContext(), GpsInfo.class);
                    startService(service);


                }else{
                    Log.d("test", "결과 화면 버튼");
                    btnStart.setText("시작");

                    Intent service = new Intent(getApplicationContext(), GpsInfo.class);
                    stopService(service);


                }

            }
        });

        ToolBar toolbar = new ToolBar(MainActivity.this);
        toolbar.setMenu();
        toolbar.setToolbar();
    }


    public boolean isServiceRunningCheck(){
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.androidtown.blackout.GpsInfo".equals(service.service.getClassName())){
                Log.e("@@@@@@@@@@@@@@@@@@@@@", "true");
                return true;
            }
        }
        /*Intent intent = new Intent(getApplicationContext(), GpsInfo.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);*/
        Log.e("@@@@@@@@@@@@@@@@@@@@@", "false");
        return false;
    }

}
