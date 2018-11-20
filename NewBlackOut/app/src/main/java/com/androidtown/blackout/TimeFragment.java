package com.androidtown.blackout;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeFragment extends Fragment {

    RecyclerView rcv;
    LinearLayoutManager llm;
    WrittingAdapter wadapter;
    WrittingAdapterSMS wadapterSMS;
    Cursor c,c1;
    Button btnSMS, btnCall;
    String startTime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        startTime = bundle.getString("start");
    }

    @SuppressLint("MissingPermission")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_time, container, false);
        btnSMS = (Button) view.findViewById(R.id.btn3);
        btnCall = (Button)view.findViewById(R.id.btn2);


        Log.e("@@@@@", "@@@@@");
        Activity activity = (ResultActivity)getActivity();
        ArrayList<ItemForm> list = new ArrayList<>();//ItemFrom에서 받게되는 데이터를 어레이 리스트화 시킨다.
        //ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS},4);


        //*1time위해 추가한것
        rcv = (RecyclerView)view.findViewById(R.id.recycler_test);
        llm = new LinearLayoutManager(activity);//종류는 총 3가지, ListView를 사용하기 위한 사용
        rcv.setHasFixedSize(true);//각 아이템이 보여지는 것을 일정하게
        rcv.setLayoutManager(llm);//앞서 선언한 리싸이클러뷰를 레이아웃메니저에 붙힌다

        Toast.makeText(getContext(), startTime, Toast.LENGTH_SHORT).show();
        //기준 시간 이후에 기록 있는지 없는지 체크해주는 변수
        int chk= 0;



        //퍼미션 여부 체크
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED ||ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
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
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS},4);
        }
        c = ((ResultActivity)ResultActivity.mContext).getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);



        btnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ItemFormSMS> listSms = new ArrayList<>();
                Activity activity = (ResultActivity)getActivity();
                c1 = ((ResultActivity)ResultActivity.mContext).getContentResolver().query(Uri.parse("content://sms"),
                        new String[] { "date", "person", "type" , "address" , "body", "read" }, null, null, "date DESC");

                //기준 시간 이후에 기록 있는지 없는지 체크해주는 변수
                int chk = 0;
                while  (c1.moveToNext()) {
                    Log.e("@@@@@4445555", "@@@@@4445555");


                    //날짜
                    long date = c1.getLong(0);
                    SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
                    String timetamp = dFormat.format(new Date(date));
                    //이름
                    long contactId = c1.getLong(1);
                    String contactId_string = String.valueOf(contactId);
                    //수신, 발신 타입
                    long type = c1.getLong(2);
                    // 전화번호
                    String address = c1.getString(3);
                    String mType;
                    if (type == 1){
                        mType = "수신 : " + address;
                    }else{
                        mType = "발신 : "+  address; }
                    //내용
                    String body = c1.getString(4);
                    //읽음여부
                    long read = c1.getLong(5);

                    //시작 시간 이후의 값만 가져오기 위해 비교
                    SimpleDateFormat chkTime = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");
                    String chkTimee = chkTime.format(new Date(date));
                    int compare = startTime.compareTo(chkTimee);
                    Log.d("SMScompare", ""+ compare);
                    if(compare <= 0){
                        listSms.add(new ItemFormSMS(timetamp,contactId_string,mType,address,body,read , R.drawable.message));
                        chk++;
                    }
                }
                if(chk ==0){
                    listSms.add(new ItemFormSMS("","문자기록없음","","","",0 , 0));
                }
                c1.close();
                wadapterSMS = new WrittingAdapterSMS(activity, listSms);
                wadapterSMS.notifyDataSetChanged();
                rcv.setAdapter(wadapterSMS);
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ItemForm> list = new ArrayList<>();//ItemFrom에서 받게되는 데이터를 어레이 리스트화 시킨다.
                Activity activity = (ResultActivity)getActivity();
                c = ((ResultActivity)ResultActivity.mContext).getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
                //기준 시간 이후에 기록 있는지 없는지 체크해주는 변수
                int chk = 0;

                while (c.moveToNext()) {

                    int imageNumber1 = 0;
                    // 날짜시간
                    long callDate = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
                    SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
                    String date_str = dFormat.format(new Date(callDate));
                    // 연락처 대표 이름
                    String name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    if (name == null){name = "알수없음";}
                    //전화번호
                    String pNumber = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));
                    //전화타입 구분
                    if (c.getInt(c.getColumnIndex(CallLog.Calls.TYPE)) == CallLog.Calls.INCOMING_TYPE) {
                        pNumber =" 수  신 : " + pNumber;
                        imageNumber1 = R.drawable.call_incoming;
                    } else if (c.getInt(c.getColumnIndex(CallLog.Calls.TYPE)) == CallLog.Calls.OUTGOING_TYPE) {
                        pNumber =" 발  신 : " + pNumber;
                        imageNumber1 = R.drawable.call_outgoing;
                    }else if (c.getInt(c.getColumnIndex(CallLog.Calls.TYPE)) == CallLog.Calls.MISSED_TYPE) {
                        pNumber =" 부재중 : " + pNumber;
                        imageNumber1 = R.drawable.call_red;
                    }else{
                        pNumber ="수신거부: " + pNumber;
                        imageNumber1 = R.drawable.call_red;
                    }
                    //통화시간
                    Integer callDur = c.getInt(c.getColumnIndex(CallLog.Calls.DURATION));
                    Integer result;
                    String duration = "";
                    if (callDur != 0) {
                        int i = 0;
                        do {
                            i++;
                            result = callDur % 60;
                            callDur = callDur / 60;
                            if (i == 1) {
                                if (result < 10) {
                                    duration = ":0" + result;
                                }else
                                    duration = ":"+result;
                            }else
                            if (i == 2) {
                                if (result < 10) {
                                    duration = ":0" + result + duration;
                                }else
                                    duration = ":"+result + duration;
                            }else
                            if (i == 3) {
                                if (result < 10) {
                                    duration = "0" + result + duration;
                                }else
                                    duration =result + duration;
                            }
                        } while (i <= 3);
                    }else {
                        duration = "00:00:00";
                    }
                    Log.d("DURATION","HHHHHHHHHHHHHHHHHH"+duration+"    " +callDur);

                    //시작 시간 이후의 값만 가져오기 위해 비교
                    SimpleDateFormat chkTime = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");
                    String chkTimee = chkTime.format(new Date(callDate));
                    int compare = startTime.compareTo(chkTimee);
                    Log.d("CALLcompare", ""+ compare);
                    if(compare <= 0) {
                        list.add(new ItemForm(date_str, imageNumber1, name, pNumber, duration));
                        chk++;
                    }
                }// end while
                if(chk==0){
                    list.add(new ItemForm("", 0, "통화기록없음", "", ""));
                }
                c.close();
                wadapter = new WrittingAdapter(activity, list);
                wadapter.notifyDataSetChanged();
                rcv.setAdapter(wadapter);
            }
        });




        //프래그먼트 최초 노출시 기본으로 한번 띄워주기위함(통화목록)
        while (c.moveToNext()) {

            int imageNumber1 = 0;
            // 날짜시간
            long callDate = c.getLong(c.getColumnIndex(CallLog.Calls.DATE));
            SimpleDateFormat dFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
            String date_str = dFormat.format(new Date(callDate));
            Log.d("DATE","DDDDDDDDDDDDDDDDDDD"+date_str +"    " +callDate);
            // 연락처 대표 이름
            String name = c.getString(c.getColumnIndex(CallLog.Calls.CACHED_NAME));
            if (name == null){name = "알수없음";}
            //전화번호
            String pNumber = c.getString(c.getColumnIndex(CallLog.Calls.NUMBER));

            //전화타입 구분
            if (c.getInt(c.getColumnIndex(CallLog.Calls.TYPE)) == CallLog.Calls.INCOMING_TYPE) {
                pNumber =" 수  신 : " + pNumber;
                imageNumber1 = R.drawable.call_incoming;
            } else if (c.getInt(c.getColumnIndex(CallLog.Calls.TYPE)) == CallLog.Calls.OUTGOING_TYPE) {
                pNumber =" 발  신 : " + pNumber;
                imageNumber1 = R.drawable.call_outgoing;
            }else if (c.getInt(c.getColumnIndex(CallLog.Calls.TYPE)) == CallLog.Calls.MISSED_TYPE) {
                pNumber =" 부재중 : " + pNumber;
                imageNumber1 = R.drawable.call_red;
            }else {
                pNumber ="수신거부: " + pNumber;
                imageNumber1 = R.drawable.call_red;
            }
            //통화시간
            Integer callDur = c.getInt(c.getColumnIndex(CallLog.Calls.DURATION));
            Integer result;
            String duration = "";
            if (callDur != 0) {
                int i = 0;
                do {
                    i++;
                    result = callDur % 60;
                    callDur = callDur / 60;
                    if (i == 1) {
                        if (result < 10) {
                            duration = ":0" + result;
                        }else
                            duration = ":"+result;
                    }else
                    if (i == 2) {
                        if (result < 10) {
                            duration = ":0" + result + duration;
                        }else
                            duration = ":"+result + duration;
                    }else
                    if (i == 3) {
                        if (result < 10) {
                            duration = "0" + result + duration;
                        }else
                            duration =result + duration;
                    }
                } while (i <= 3);
            }else {
                duration = "00:00:00";
            }
            Log.d("DURATION","HHHHHHHHHHHHHHHHHH"+duration+"    " +callDur);

            //시작 시간 이후의 값만 가져오기 위해 비교
            SimpleDateFormat chkTime = new SimpleDateFormat("yyyy-MM-dd, hh:mm:ss a");
            String chkTimee = chkTime.format(new Date(callDate));
            int compare = startTime.compareTo(chkTimee);
            if(compare <= 0) {
                list.add(new ItemForm(date_str, imageNumber1, name, pNumber, duration));
                chk++;
            }
        }// end while
        if(chk==0){
            list.add(new ItemForm("", 0, "통화기록없음", "", ""));
        }
        c.close();Log.e("@@@@@55", "@@@@@55");



        wadapter = new WrittingAdapter(activity, list);
        wadapter.notifyDataSetChanged();
        rcv.setAdapter(wadapter);

        return view;
    }


}