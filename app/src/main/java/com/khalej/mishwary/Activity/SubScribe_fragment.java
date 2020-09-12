package com.khalej.mishwary.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.khalej.mishwary.Adapter.RecyclerAdapter_notification;
import com.khalej.mishwary.R;
import com.khalej.mishwary.model.Apiclient_home;
import com.khalej.mishwary.model.apiinterface_home;
import com.khalej.mishwary.model.notificationData;


import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubScribe_fragment extends Fragment {
    private apiinterface_home apiinterface;
    RecyclerView recyclerView;
    EditText message;
    ImageView send;
    ProgressBar progressBar;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter_notification recyclerAdapter;
    private List<notificationData> contactList = new ArrayList<>();
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_subscribe, container, false);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        recyclerView=view.findViewById(R.id.recyclerview);
        layoutManager = new GridLayoutManager(getContext(), 1);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);

        fetchInfo();
      return  view;
    }
    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<notificationData>> call = apiinterface.getcontacts_Notification(sharedpref.getInt("id",0));
        call.enqueue(new Callback<List<notificationData>>() {
            @Override
            public void onResponse(Call<List<notificationData>> call, Response<List<notificationData>> response) {
                progressBar.setVisibility(View.GONE);

                try {


                    contactList = response.body();
                    if (response.code() == 404) {
                        contactList=new ArrayList<>();
                        return;
                    }
                    if(contactList.isEmpty()){
                        contactList=new ArrayList<>();
                    }
                    else {
                        //  Toast.makeText(ChatActivity.this, "22", Toast.LENGTH_LONG).show();
                        recyclerAdapter = new RecyclerAdapter_notification(getContext(), contactList);
                        recyclerView.setAdapter(recyclerAdapter);
                        recyclerView.scrollToPosition(contactList.size() - 1);
                    }
                }
                catch (Exception e){
                    //  Toast.makeText(ChatActivity.this,e+"",Toast.LENGTH_LONG).show();
                    contactList=new ArrayList<>();
                }

            }

            @Override
            public void onFailure(Call<List<notificationData>> call, Throwable t) {
                contactList=new ArrayList<>();
                progressBar.setVisibility(View.GONE);

            }
        });
    }

}
