package com.khalej.mishwary.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


import com.khalej.mishwary.R;
import com.khalej.mishwary.model.AboutUs;
import com.khalej.mishwary.model.Apiclient_home;
import com.khalej.mishwary.model.apiinterface_home;

import androidx.appcompat.app.AppCompatActivity;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class whous extends AppCompatActivity {
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    AboutUs respons =new AboutUs();
    String conent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        fetchInfo();

    }
    public void fetchInfo() {

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<AboutUs> call = apiinterface.AboutUS_ar();
        call.enqueue(new Callback<AboutUs>() {
            @Override
            public void onResponse(Call<AboutUs> call, Response<AboutUs> response) {
                try {
                    respons=response.body();
                    sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
                    edt = sharedpref.edit();
                    Element versionElement = new Element();
                    versionElement.setTitle("Version 1.0");

                    Element adsElement = new Element();
                    adsElement.setTitle("Advertise with us");
                    try{
                        if(sharedpref.getString("language","").trim().equals("ar")){
                            conent=respons.getContent();}
                        else{
                            conent=respons.getContent();
                        }}
                    catch (Exception e){}
                    View aboutPage = new AboutPage(whous.this)
                            .isRTL(false)
                            .addItem(versionElement)
                            .addItem(adsElement)
                            .setDescription(conent)
                            .addGroup("Connect with us")
                            .addEmail(respons.getLink())
                            .create();

                    setContentView(aboutPage);
                }
                catch (Exception e){}


            }

            @Override
            public void onFailure(Call<AboutUs> call, Throwable t) {
                //  Toast.makeText(whous.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
