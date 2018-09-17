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

public class WrittingAdapter extends RecyclerView.Adapter<WrittingAdapter.MyViewholder> {

    private Activity activity;
    private ArrayList<ItemForm> datalist;


    //getItemCount, onCreateViewHolder, MyViewHolder, onBindViewholder 순으로 들어오게 된다.
    // 뷰홀더에서 초기세팅해주고 바인드뷰홀더에서 셋텍스트해주는 값이 최종적으로 화면에 출력되는 값


    @Override
    public WrittingAdapter.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        MyViewholder viewholder1 = new MyViewholder(view);

        return viewholder1;
    }

    @Override
    public void onBindViewHolder(WrittingAdapter.MyViewholder holder, int position) {
        ItemForm data = datalist.get(position);
        //초기화 해볼려고 넣음 없어도 될 것 같음 다시 해봐야됨
        holder.date.setText("");
        holder.profile.setImageResource(0);
        holder.name.setText("");
        holder.number.setText("");
        holder.duration.setText("");

        holder.date.setText(data.getDate());
        holder.profile.setImageResource(data.getImageNumber());
        holder.name.setText(data.getName());
        holder.number.setText(data.getNumber());
        holder.duration.setText(data.getDuration());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView name;
        TextView date;
        TextView number;
        TextView duration;

        public MyViewholder(View itemview) {
            super(itemview);

            profile = (ImageView) itemview.findViewById(R.id.ivIcon);
            name = (TextView) itemview.findViewById(R.id.tvName);
            date = (TextView) itemview.findViewById(R.id.tvTime);
            number = (TextView) itemview.findViewById(R.id.tvNumber);
            duration = (TextView) itemview.findViewById(R.id.tvDuration);
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(activity, "터치 받으면 펼쳐지거나 다른 뷰가 추가되서 넓어짐", Toast.LENGTH_LONG).show();
                    ;
                }
            });

        }

    }

    public WrittingAdapter(Activity activity, ArrayList<ItemForm> datalist) {
        this.activity = activity;
        this.datalist = datalist;
        notifyDataSetChanged();

    }
}
