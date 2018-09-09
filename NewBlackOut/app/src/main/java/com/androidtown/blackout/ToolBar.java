package com.androidtown.blackout;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ToolBar {
    private AppCompatActivity mActivity;
    private android.support.v7.widget.Toolbar tbMainbar;
    private DrawerLayout mDrawerLayout;
    private View mDrawerView;

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
        ImageView btnHome = mActivity.findViewById(R.id.btnHome);
        btnHome.setVisibility(View.VISIBLE);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.finish();
            }
        });

    }

    //Toolbar 버튼 클릭시 기능 설정 Buttom -> (Slide Menu)
    public void setMenu(){

        ImageView btnToolMenu = mActivity.findViewById(R.id.btnToolMenu);


        mDrawerLayout = mActivity.findViewById(R.id.dlMenu);
        mDrawerView = mActivity.findViewById(R.id.drawer);

        //Toolbar의 Menu버튼 클릭 Drawer 열기
        btnToolMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mDrawerView);
                mDrawerLayout.setClickable(true);
            }
        });
    }


}
