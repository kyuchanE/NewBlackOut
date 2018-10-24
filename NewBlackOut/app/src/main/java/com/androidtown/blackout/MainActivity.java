package com.androidtown.blackout;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, 3);

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

                if(Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission((MainActivity.mContext), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission((MainActivity.mContext), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

                    alertDialogBuilder.setTitle("퍼미션 다시 걸기");

                    alertDialogBuilder
                            .setMessage("메시지")
                            .setCancelable(false)
                            .setPositiveButton("퍼미션", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1);
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);


                                }
                            })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();
                                }
                            });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                }else{

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
