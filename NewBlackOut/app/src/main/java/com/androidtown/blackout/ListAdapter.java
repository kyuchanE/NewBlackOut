package com.androidtown.blackout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    ArrayList<ItemList> dateList;
    Context context;


    public ListAdapter(ArrayList<ItemList> dateList, Context context) {
        this.dateList = dateList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ListAdapter.MyViewHolder myViewHolder, int i) {
        final int position = i;
        myViewHolder.tvDate.setText(dateList.get(position).getDate());

        myViewHolder.llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListActivity.class);
                intent.putExtra("lat", dateList.get(position).getLat());
                intent.putExtra("lng", dateList.get(position).getLng());
                intent.putExtra("date", dateList.get(position).getDate());
                context.startActivity(intent);

                ((Activity)context).finish();

            }
        });


        myViewHolder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((ListFindActivity)context).itemDelete(dateList.get(position).getDate());

                dateList.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvDate;
        LinearLayout llDate;
        ImageView ivDelete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            llDate = itemView.findViewById(R.id.llDate);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
