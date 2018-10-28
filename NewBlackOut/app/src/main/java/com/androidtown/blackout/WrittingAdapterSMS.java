package com.androidtown.blackout;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WrittingAdapterSMS extends RecyclerView.Adapter<WrittingAdapterSMS.MyViewholder> {

    private Activity activity;
    private ArrayList<ItemFormSMS> datalist;


    //getItemCount, onCreateViewHolder, MyViewHolder, onBindViewholder 순으로 들어오게 된다.
    // 뷰홀더에서 초기세팅해주고 바인드뷰홀더에서 셋텍스트해주는 값이 최종적으로 화면에 출력되는 값


    @Override
    public WrittingAdapterSMS.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_sms, parent, false);
        MyViewholder viewholder2 = new MyViewholder(view);

        return viewholder2;
    }

    @Override
    public void onBindViewHolder(WrittingAdapterSMS.MyViewholder holder, int position) {
        ItemFormSMS data = datalist.get(position);
        //초기화 해볼려고 넣음 없어도 될 것 같음 다시 해봐야됨
        holder.txt1.setText("");
        holder.txt2.setText("");
        holder.txt3.setText("");
        holder.txt4.setText("");
        holder.txt5.setText("");
        holder.txt6.setText("");
        holder.txt7.setText("");
        holder.txt8.setText("");
        holder.profile.setImageResource(0);


        holder.txt1.setText(data.getMessageId());
        holder.txt2.setText(data.getThreadId());
        holder.txt3.setText(data.getAddress());
        holder.txt4.setText(data.getContactId());
        holder.txt5.setText(data.getContactId_string());
        holder.txt6.setText(data.getTimestamp());
        holder.txt7.setText(data.getBody());
        holder.txt8.setText(data.getRead());
        holder.profile.setImageResource(data.getImageNumber1());


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView txt1, txt2, txt3 ,txt4 ,txt5 ,txt6 ,txt7,txt8;

        public MyViewholder(View itemview) {
            super(itemview);

            txt1 = (TextView) itemview.findViewById(R.id.txt1);
            txt2 = (TextView) itemview.findViewById(R.id.txt2);
            txt3 = (TextView) itemview.findViewById(R.id.txt3);
            txt4 = (TextView) itemview.findViewById(R.id.txt4);
            txt5 = (TextView) itemview.findViewById(R.id.txt5);
            txt6 = (TextView) itemview.findViewById(R.id.txt6);
            txt7 = (TextView) itemview.findViewById(R.id.txt7);
            txt8= (TextView) itemview.findViewById(R.id.txt8);
            profile = (ImageView) itemview.findViewById(R.id.ivIcon);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "터치 받으면 펼쳐지거나 다른 뷰가 추가되서 넓어짐", Toast.LENGTH_LONG).show();
                    ;
                }
            });

        }

    }

    public WrittingAdapterSMS(Activity activity, ArrayList<ItemFormSMS> datalist) {
        this.activity = activity;
        this.datalist = datalist;
        notifyDataSetChanged();

    }
}