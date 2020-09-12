package com.khalej.mishwary.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.khalej.mishwary.R;
import com.khalej.mishwary.model.Apiclient_home;
import com.khalej.mishwary.model.Myorder;
import com.khalej.mishwary.model.apiinterface_home;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecyclerAdapter_morder_onging extends RecyclerView.Adapter<RecyclerAdapter_morder_onging.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<Myorder> contactslist;

    Myorder order;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    public RecyclerAdapter_morder_onging(Context context, List<Myorder> contactslist){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recervation_car,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        myTypeface = Typeface.createFromAsset(context.getAssets(), "Nasser.otf");
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
if(sharedpref.getInt("type",0)!=1){
        holder.Name.setText( "صاحب الطلب:"+" " +contactslist.get(position).getName());}
        else{
    holder.Name.setText("الفني:"+" " +contactslist.get(position).getRepresentative_name());
}
        holder.Name.setTypeface(myTypeface);
        holder.price.setTypeface(myTypeface);
         holder.price.setText("رقم هاتف الفني :"+" " +contactslist.get(position).getRepresentative_phone());
        holder.date.setText(  "عنوان الطلب :" +" "+contactslist.get(position).getAddress());
        holder.date.setTypeface(myTypeface);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sharedpref.getInt("type",0)==1) {
                    new AlertDialog.Builder(context)
                            .setTitle("Mishwary")
                            .setMessage("هل قمت بأستلام طلبك ؟")
                            .setIcon(R.drawable.logo)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int whichButton) {
                                    int id = contactslist.get(position).getId();
                                    Finishorder(id,contactslist.get(position).getRepresentative_id());
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name,details,date,price;


        public MyViewHolder(View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.txt_fish_title);
            date=itemView.findViewById(R.id.txt_title);
            price=itemView.findViewById(R.id.txt_);
        }
    }

    public void Finishorder(final int id,final int fani_id){

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.getcontacts_FinishOrder(sharedpref.getInt("id",0),id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context,"تم أنهاء الطلب بنجاح شكرا على تعاملكم معنا ", Toast.LENGTH_LONG).show();
//                Intent intent =new Intent(context, Rate_Fani.class);
//                intent.putExtra("fani_id",fani_id);
//                context.startActivity(intent);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

}
}