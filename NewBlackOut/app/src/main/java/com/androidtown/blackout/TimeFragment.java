package com.androidtown.blackout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeFragment extends Fragment {

    RecyclerView rcv;
    LinearLayoutManager llm;
    WrittingAdapter wadapter;
    Cursor c;

    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);

        Log.e("@@@@@", "@@@@@");
        Activity activity = (ResultActivity)getActivity();


        //*1time위해 추가한것

        rcv = (RecyclerView)view.findViewById(R.id.recycler_test);
        llm = new LinearLayoutManager(activity);//종류는 총 3가지, ListView를 사용하기 위한 사용
        rcv.setHasFixedSize(true);//각 아이템이 보여지는 것을 일정하게
        rcv.setLayoutManager(llm);//앞서 선언한 리싸이클러뷰를 레이아웃메니저에 붙힌다

        ArrayList<ItemForm> list = new ArrayList<>();//ItemFrom에서 받게되는 데이터를 어레이 리스트화 시킨다.

        //
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e("@@@@@22", "@@@@@22");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CALL_LOG}, 3);
            Log.e("@@@@@33", "@@@@@33");
        }
        c = ((ResultActivity)ResultActivity.mContext).getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);



        while (c.moveToNext()) {
            Log.e("@@@@@44", "@@@@@44");

            String type;
            // 날짜시간
            long callDate = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
            SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
            String date_str = dFormat.format(new Date(callDate));
            // 연락처 대표 이름
            String name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));
            //전화번호
            String pNumber = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
            //전화타입 구분
            if (c.getInt(c.getColumnIndex(CallLog.Calls.TYPE)) == CallLog.Calls.INCOMING_TYPE) {
                pNumber =" 수신 : " + pNumber;
            } else {
                pNumber =" 발신 : " + pNumber;
            }
            //통화시간
            String duration = c.getString(c.getColumnIndex(CallLog.Calls.DURATION));

            list.add(new ItemForm(date_str,R.drawable.kkko2,name,pNumber,duration));
        }// end while
        c.close();Log.e("@@@@@55", "@@@@@55");



/*
        list.add(new ItemForm("19:30",R.drawable.kkko2,"카카오톡"));
        list.add(new ItemForm("20:30",R.drawable.kkko2,"페이스북"));
        list.add(new ItemForm("21:30",R.drawable.kkko2,"여자친구"));
        list.add(new ItemForm("22:30",R.drawable.kkko2,"위치정보"));
        list.add(new ItemForm("23:30",R.drawable.kkko2,"별타그램"));
        list.add(new ItemForm("24:30",R.drawable.kkko2,"카카오톡"));
        list.add(new ItemForm("25:30",R.drawable.kkko2,"카카오톡"));
        list.add(new ItemForm("02:00",R.drawable.kkko2,"카카오톡"));
        list.add(new ItemForm("02:01",R.drawable.kkko2,"카카오톡"));
        list.add(new ItemForm("02:02",R.drawable.kkko2,"페이스북"));
        list.add(new ItemForm("02:03",R.drawable.kkko2,"여자친구"));
        list.add(new ItemForm("02:04",R.drawable.kkko2,"위치정보"));
        list.add(new ItemForm("02:05",R.drawable.kkko2,"별타그램"));
        list.add(new ItemForm("02:06",R.drawable.kkko2,"카카오톡"));
        list.add(new ItemForm("02:07",R.drawable.kkko2,"카카오톡"));
        list.add(new ItemForm("02:08",R.drawable.kkko2,"페이스북"));*/


        wadapter = new WrittingAdapter(activity, list);

        wadapter.notifyDataSetChanged();
        rcv.setAdapter(wadapter);


        return view;

    }



}
