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
        holder.tvTime.setText("");
        holder.tvName.setText("");
        holder.tvNumber.setText("");
        holder.tvBody.setText("");
        holder.ivIcon.setImageResource(0);


        holder.tvTime.setText(data.getTimetamp());
        holder.tvName.setText(data.getContactId_string());
        holder.tvNumber.setText(data.getmType());
        holder.tvBody.setText(data.getBody());
        holder.ivIcon.setImageResource(data.getImageNumber1());


    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTime,tvName,tvNumber,tvBody;

        public MyViewholder(View itemview) {
            super(itemview);

            tvTime = (TextView) itemview.findViewById(R.id.tvTime);
            tvName = (TextView) itemview.findViewById(R.id.tvName);
            tvNumber = (TextView) itemview.findViewById(R.id.tvNumber);
            tvBody = (TextView) itemview.findViewById(R.id.tvBody);
            ivIcon = (ImageView) itemview.findViewById(R.id.ivIcon);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "터치 이벤트", Toast.LENGTH_LONG).show();
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