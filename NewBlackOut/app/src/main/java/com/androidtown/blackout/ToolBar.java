package com.androidtown.blackout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ToolBar {
    private AppCompatActivity mActivity;
    private android.support.v7.widget.Toolbar tbMainbar;

    ImageView btnHome;


    public ToolBar(AppCompatActivity activity){
        mActivity = activity;

    }

    //Toolbar Title 설정
    public void setTitle(String title){
        TextView tvTitle = mActivity.findViewById(R.id.tvToolbar);
        tvTitle.setText(title);
    }

    //Toolbar를 Activity에 올리기
    public void setToolbar(){
        tbMainbar = mActivity.findViewById(R.id.tbMainbar);
        mActivity.setSupportActionBar(tbMainbar);

    }

    public void setHome(){
        btnHome = mActivity.findViewById(R.id.btnHome);
        btnHome.setVisibility(View.VISIBLE);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.finish();
            }
        });

    }

    public void setList(){
        btnHome = mActivity.findViewById(R.id.btnHome);
        btnHome.setImageResource(R.drawable.ic_menu);
        btnHome.setVisibility(View.VISIBLE);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, ListActivity.class);
                mActivity.startActivity(intent);
            }
        });


    }



}
