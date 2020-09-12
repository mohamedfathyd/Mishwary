package com.khalej.mishwary.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.khalej.mishwary.R;
import com.khalej.mishwary.model.Apiclient_home;
import com.khalej.mishwary.model.Reset;
import com.khalej.mishwary.model.apiinterface_home;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {
TextInputEditText textInputEditTextphone;
AppCompatButton appCompatButtonRegisterservcies;
    ProgressDialog progressDialog;
    private apiinterface_home apiinterface;
   Reset reset;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.white));

        setContentView(R.layout.activity_forget_password);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);

        textInputEditTextphone=findViewById(R.id.textInputEditTextphone);
        appCompatButtonRegisterservcies=findViewById(R.id.appCompatButtonRegisterservcies);
        appCompatButtonRegisterservcies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchInfo();
            }
        });

    }

    public void fetchInfo(){
        progressDialog = ProgressDialog.show(ForgetPassword.this,"جاري الإرسال","Please wait...",false,false);
        progressDialog.show();

        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<Reset> call= apiinterface.getcontacts_ResetPassword(
                textInputEditTextphone.getText().toString());
        call.enqueue(new Callback<Reset>() {
            @Override
            public void onResponse(Call<Reset> call, Response<Reset> response) {
                reset=response.body();
                progressDialog.dismiss();
                if(reset.getCan()==1){
                    Toast.makeText(ForgetPassword.this,"تم الطلب",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(ForgetPassword.this,"هذه البيانات غير مسجلة",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Reset> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
