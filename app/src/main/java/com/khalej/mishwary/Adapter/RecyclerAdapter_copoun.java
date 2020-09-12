package com.khalej.mishwary.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.khalej.mishwary.R;
import com.khalej.mishwary.RoundRectCornerImageView;
import com.khalej.mishwary.model.Apiclient_home;
import com.khalej.mishwary.model.Copoun;
import com.khalej.mishwary.model.apiinterface_home;
import com.khalej.mishwary.model.notificationData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter_copoun extends RecyclerView.Adapter<RecyclerAdapter_copoun.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<Copoun> contactslist;
    private apiinterface_home apiinterface;
    TextView toolbar_title;

    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    public RecyclerAdapter_copoun(Context context, List<Copoun> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationlist,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        if(sharedpref.getString("language","").trim().equals("ar")){
            if(contactslist.get(position).getAr_title()==null){
        holder.Date.setText(contactslist.get(position).getEn_title());}
        else{
                holder.Date.setText(contactslist.get(position).getAr_title());
            }

        }
        else{
            if(contactslist.get(position).getEn_title()==null){
                holder.Date.setText(contactslist.get(position).getAr_title());}
            else{
                holder.Date.setText(contactslist.get(position).getEn_title());
            }

        }
        holder.Name.setTypeface(myTypeface);
        holder.Name.setText(contactslist.get(position).getCoupon_serial());
        holder.Date.setTypeface(myTypeface);
         holder.details.setTypeface(myTypeface);
         holder.details.setText("قيمة الكوبون :" +contactslist.get(position).getCoupon_value());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



//               new AlertDialog.Builder(context)
//                       .setTitle("Bazelt")
//                       .setMessage("هل انت متأكد من ربط هذا الطلب بك وانت من سوف يقوم بتوصيله ؟")
//                       .setIcon(android.R.drawable.ic_dialog_alert)
//                       .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                           public void onClick(DialogInterface dialog, int whichButton) {
//                               fetchdata(contactslist.get(position).getOrder_id());
//                           }})
//                       .setNegativeButton(android.R.string.no, null).show();


            }

        });



    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,details,Date;
        RoundRectCornerImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            Date=itemView.findViewById(R.id.txt_fish_title);
            Name=(TextView)itemView.findViewById(R.id.txt_title);
            details=itemView.findViewById(R.id.txt_);

        }
    }
    public void fetchdata(int id){
        progressDialog = ProgressDialog.show(context, "جاري ربط  الطلب بك", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_AcceptOrder(id,sharedpref.getInt("id",0));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("تم ربط الطلب بنجاح ");
                dlgAlert.setTitle("Bazelt");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                ((Activity)context).finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}