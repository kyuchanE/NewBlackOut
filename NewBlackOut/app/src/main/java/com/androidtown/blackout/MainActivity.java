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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button btnStart;

    MyDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    public static Context mContext;

    GpsInfo mService;
    Boolean mBound;
    ServiceConnection mConnection;

    int bNum;

    ConstraintLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);


        mContext = this;

        myDBHelper = new MyDBHelper(this);

        btnStart = findViewById(R.id.btnStart);

        main = findViewById(R.id.main);

        changeButton(getButtonDB());

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
                Intent intent = getIntent();
                finish();
                startActivity(intent);

                Date dt = new Date();
                SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd, hh:mm a");
                Log.e("@@@@@DATE@@@@@@", time.format(dt).toString());

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

                        Intent service = new Intent(getApplicationContext(), GpsInfo.class);
                        startService(service);

                        setButtonDB(1);





                    }else{
                        Log.d("test", "결과 화면 버튼");

                        Intent service = new Intent(getApplicationContext(), GpsInfo.class);
                        stopService(service);

                        setButtonDB(0);

                    }
                }


            }
        });

        ToolBar toolbar = new ToolBar(MainActivity.this);
        toolbar.setMenu();
        toolbar.setToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getButtonDB() == 0){
            Drawable drawable = getResources().getDrawable(R.drawable.landscap_1);
            main.setBackground(drawable);

        }else{
            Drawable drawable = getResources().getDrawable(R.drawable.landscap_2);
            main.setBackground(drawable);

        }
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

    public void setButtonDB(int num){
        int cnt;
        cnt = num;
        sqlDB = myDBHelper.getWritableDatabase();
        sqlDB.execSQL("INSERT INTO buttonTBL VALUES (" + cnt + ");");
        sqlDB.close();
    }

    public int getButtonDB(){
        bNum = 0;
        sqlDB = myDBHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM buttonTBL;", null);

        while(cursor.moveToNext()){
            bNum = cursor.getInt(0);
        }

        cursor.close();

        return bNum;
    }

    public void changeButton(int cnt){
        if(cnt == 0){
            btnStart.setText("시작");
        }else{
            btnStart.setText("결과보기");
        }
    }


}
