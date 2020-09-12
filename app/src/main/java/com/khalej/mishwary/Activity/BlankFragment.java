package com.khalej.mishwary.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.khalej.mishwary.Adapter.RecyclerAdapter_morder;
import com.khalej.mishwary.R;
import com.khalej.mishwary.model.Apiclient_home;
import com.khalej.mishwary.model.Myorder;
import com.khalej.mishwary.model.apiinterface_home;

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


public class BlankFragment extends Fragment {
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter_morder recyclerAdapter;
    private List<Myorder> contactList=new ArrayList<>();
    private apiinterface_home apiinterface;
    ImageView notification;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        layoutManager = new GridLayoutManager(getContext(), 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setHasFixedSize(true);

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        fetchInfo();
        return view;
    }
    public void fetchInfo(){
        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<Myorder>> call = apiinterface.getcontacts_MyOrders(sharedpref.getInt("id",0),0);
        call.enqueue(new Callback<List<Myorder>>() {
            @Override
            public void onResponse(Call<List<Myorder>> call, Response<List<Myorder>> response) {
                progressBar.setVisibility(View.GONE);

                contactList = response.body();
                try{
                    if(contactList.size()!=0||!(contactList.isEmpty())) {
                        progressBar.setVisibility(View.GONE);
                        recyclerAdapter=new RecyclerAdapter_morder(getActivity(),contactList,sharedpref.getInt("id",0));
                        recyclerView.setAdapter(recyclerAdapter);}}
                catch (Exception e){
                    progressBar.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<List<Myorder>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }

}
