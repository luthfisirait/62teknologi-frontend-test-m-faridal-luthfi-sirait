package com.example.mytest.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.mytest.DetailActivity;
import com.example.mytest.R;
import com.example.mytest.dataApi.BusinessesItem;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class AdapterBisnis extends RecyclerView.Adapter<AdapterBisnis.MyViewHolder> {
    private static int row_index=-1 ;
    Context context;


    List<BusinessesItem> businessesItemList;

    public AdapterBisnis(Context context, List<BusinessesItem> businessesItemList) {
        this.businessesItemList= businessesItemList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView =inflater.inflate(R.layout.item_bisnis,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BusinessesItem businessesItem = businessesItemList.get(position);

        holder.nama_bisnis.setText(businessesItem.getName());
        holder.tv_rating.setText(String.valueOf(businessesItem.getRating()));
        holder.tv_countreview.setText("| "+String.valueOf(businessesItem.getReviewCount()));


        holder.tv_jarak.setText(String.valueOf(businessesItem.getJarak())+" Km");
        String url = businessesItem.getUrl();
        String[] list1url = url.split(",");
        String url1 = list1url[0];
        Glide.with(context)
                .load(url1)
                .into(holder.foto_bisnis);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("url",url);
                intent.putExtra("nama",businessesItem.getName());
                intent.putExtra("alamat",businessesItem.getAddress());
                intent.putExtra("notelp",businessesItem.getPhone());
                intent.putExtra("latitude",String.valueOf(businessesItem.getLatitude()));
                intent.putExtra("longitude",String.valueOf(businessesItem.getLongitude()));
                context.startActivity(intent);

            }
        });

// Hitung jarak antara kedua lokasi dalam meter



    }

    @Override
    public int getItemCount() {
        return businessesItemList.size();
    }

    public void setData(List<BusinessesItem> businessesItemList) {
        this.businessesItemList= businessesItemList;
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView foto_bisnis;
        TextView nama_bisnis,tv_rating,tv_countreview,tv_jarak;
        MaterialCardView card;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            foto_bisnis = itemView.findViewById(R.id.foto_bisnis);
            nama_bisnis = itemView.findViewById(R.id.nama_bisnis);
            tv_countreview = itemView.findViewById(R.id.tv_countreview);
            tv_rating = itemView.findViewById(R.id.tv_rating);
            tv_jarak = itemView.findViewById(R.id.tv_jarak);
            card = itemView.findViewById(R.id.cardView);


        }
    }
    public void filteredlist(ArrayList<BusinessesItem>filterlist){
        businessesItemList=filterlist;
        notifyDataSetChanged();
    }
}
