package com.khalej.mishwary.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.mishwary.R;
import com.khalej.mishwary.model.Apiclient_home;
import com.khalej.mishwary.model.Myorder;
import com.khalej.mishwary.model.apiinterface_home;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter_morder extends RecyclerView.Adapter<RecyclerAdapter_morder.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<Myorder> contactslist;
    private apiinterface_home apiinterface;
    TextView toolbar_title;
    ProgressDialog progressDialog;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    TextView name,address,phone,price;
    Button Confirm;
    Realm realm;
    int user_id;
    Dialog dialog;

    public RecyclerAdapter_morder(Context context, List<Myorder> contactslist, int user_id){
        this.contactslist=contactslist;
        this.context=context;
        this.user_id=  user_id;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recervation_car_cancel,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        realm=Realm.getDefaultInstance();

        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();

        holder.Name.setText( "تفاصيل الطلب:"+" " +contactslist.get(position).getDescription());
        holder.Name.setTypeface(myTypeface);
        holder.price.setVisibility(View.GONE);
        holder.date.setText(  "عنوان الطلب :" +" "+contactslist.get(position).getAddress());
        holder.date.setTypeface(myTypeface);
        holder.cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelOrder(contactslist.get(position).getId());
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_details2);
                dialog.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                name=dialog.findViewById(R.id.customerName);
                address=dialog.findViewById(R.id.address);
                phone=dialog.findViewById(R.id.phone);
                price=dialog.findViewById(R.id.price);
                Confirm=dialog.findViewById(R.id.confirm);
                if(sharedpref.getInt("type",0)==1){
                    Confirm.setVisibility(View.GONE);
                }
                name.setText(contactslist.get(position).getDescription());
                phone.setText(contactslist.get(position).getPhone());
                try{
                    if(contactslist.get(position).getPhone().toString().equals("")||contactslist.get(position).getPhone().toString()==null){
                        phone.setText(sharedpref.getString("phone",""));
                    }}catch (Exception e){
                    phone.setText(sharedpref.getString("phone",""));
                }
                address.setText(contactslist.get(position).getAddress());
                price.setText(contactslist.get(position).getTotal()+sharedpref.getString("ar_current",""));
                Confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AcceptOrder(contactslist.get(position).getId());
                    }
                });
                dialog.show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,details,date,price;
        ImageView cancel_button;

        public MyViewHolder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.txt_fish_title);
            date=itemView.findViewById(R.id.txt_title);
            price=itemView.findViewById(R.id.txt_);
            cancel_button=itemView.findViewById(R.id.cancel_button);
        }
    }
    public void CancelOrder(int id){
        progressDialog = ProgressDialog.show(context, "جاري مسح الطلب", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_CancelOrder(id,sharedpref.getInt("id",0));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("تم حذف الطلب بنجاح ");
                dlgAlert.setTitle("Mishwary");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void AcceptOrder(int id){
        progressDialog = ProgressDialog.show(context, "جاري الموافقة على الطلب ", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_AcceptOrder(id,sharedpref.getInt("id",0));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
                dlgAlert.setMessage("تمت الموافقة الطلب بنجاح ");
                dlgAlert.setTitle("Mishwary");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}