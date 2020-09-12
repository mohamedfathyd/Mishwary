package com.khalej.mishwary.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khalej.mishwary.Activity.MapsActivity;
import com.khalej.mishwary.R;
import com.khalej.mishwary.model.contact_category;
import com.khalej.mishwary.model.contact_sub_category;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_sub_annonce extends RecyclerView.Adapter<RecyclerAdapter_sub_annonce.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_sub_category> contactslist;
    RecyclerView recyclerView;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;


    public RecyclerAdapter_sub_annonce(Context context, List<contact_sub_category> contactslist){
        this.contactslist=contactslist;
        this.context=context;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_sub_list,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
            edt = sharedpref.edit();
            if(sharedpref.getString("language","").trim().equals("ar")){
                holder.name.setText(contactslist.get(position).getAr_title());

            }else{
                holder.name.setText(contactslist.get(position).getEn_title());

            }
        holder.address.setText(contactslist.get(position).getAddress());

            Glide.with(context).load("http://applicationme.com/maishwary/public/storage/uploads/"+contactslist.get(position).getLogo()).thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL).error(R.drawable.logo).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent =new Intent(context, MapsActivity.class);
             intent.putExtra("lat",contactslist.get(position).getLatitude());
             intent.putExtra("lng",contactslist.get(position).getLongitude());
             intent.putExtra("name",contactslist.get(position).getAr_title());
             intent.putExtra("id",contactslist.get(position).getId());
             intent.putExtra("image","http://applicationme.com/maishwary/public/storage/uploads/"+contactslist.get(position).getLogo());
             context.startActivity(intent);
            }

        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name,address;

        public MyViewHolder(View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.image);
            name=itemView.findViewById(R.id.name);
            address=itemView.findViewById(R.id.details);

        }
    }}