package com.androidtown.blackout;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class TimeFragment extends Fragment {

    RecyclerView rcv;
    LinearLayoutManager llm;
    WrittingAdapter wadapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time, container, false);


        Activity activity = (MainActivity)getActivity();


        //*1time위해 추가한것

        rcv = (RecyclerView)view.findViewById(R.id.recycler_test);
        llm = new LinearLayoutManager(activity);//종류는 총 3가지, ListView를 사용하기 위한 사용
        rcv.setHasFixedSize(true);//각 아이템이 보여지는 것을 일정하게
        rcv.setLayoutManager(llm);//앞서 선언한 리싸이클러뷰를 레이아웃메니저에 붙힌다


        ArrayList<ItemForm> list = new ArrayList<>();//ItemFrom에서 받게되는 데이터를 어레이 리스트화 시킨다.

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
        list.add(new ItemForm("02:08",R.drawable.kkko2,"페이스북"));


        wadapter = new WrittingAdapter(activity, list);

        wadapter.notifyDataSetChanged();
        rcv.setAdapter(wadapter);


        return view;

    }



}
