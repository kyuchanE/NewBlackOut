package com.androidtown.blackout;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    int hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnBack, btnSave;
        final TimePicker tpFinshTime;

        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        tpFinshTime = findViewById(R.id.tpFinishTime);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {

            //TODO 종료시간 설정 ( 시간 값을 넘겨주기 )
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    hour = tpFinshTime.getHour();
                    min = tpFinshTime.getMinute();
                } else{
                    hour = tpFinshTime.getCurrentHour();
                    min = tpFinshTime.getCurrentMinute();
                }


                //Toast.makeText(MainActivity.mContext, String.valueOf(hour) + "/" + String.valueOf(min) , Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
