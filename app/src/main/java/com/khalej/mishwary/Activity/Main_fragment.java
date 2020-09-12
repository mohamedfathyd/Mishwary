package com.khalej.mishwary.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.khalej.mishwary.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.mishwary.Adapter.RecyclerAdapter_first_annonce_banner;
import com.khalej.mishwary.Adapter.RecyclerAdapter_sub_annonce;
import com.khalej.mishwary.R;
import com.khalej.mishwary.model.Apiclient_home;
import com.khalej.mishwary.model.apiinterface_home;
import com.khalej.mishwary.model.contact_annonce;
import com.khalej.mishwary.model.contact_category;
import com.khalej.mishwary.model.contact_sub_category;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_fragment extends Fragment {
    int categoryId;
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
    private RecyclerAdapter_first_annonce_banner recyclerAdapter_annonce;
    private  RecyclerAdapter_first_annonce recyclerAdapter_first_annonce;
    TextView novalue;
    private List<contact_category> contactList = new ArrayList<>();
    private List<contact_sub_category> contactList_sub = new ArrayList<>();
    private RecyclerAdapter_sub_annonce recyclerAdapter_first_annonce_sub;

    private apiinterface_home apiinterface;
    private List<contact_annonce> contactList_annonce = new ArrayList<>();

    int x = 0;
    int y = 0;
    Switch swtch;
    int id;
    ProgressBar progressBar;
    SearchView searchView;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main_fragment, container, false);
        id = getArguments().getInt("id");
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerview2);
        recyclerView=view.findViewById(R.id.recyclerview);
        recyclerView3=view.findViewById(R.id.recyclerview3);
        searchView=view.findViewById(R.id.search_bar);
        layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView2.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getContext(), 2);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        novalue=view.findViewById(R.id.novalue);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setHasFixedSize(true);
        recyclerView3.setLayoutManager(staggeredGridLayoutManager);
        recyclerView3.setHasFixedSize(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               fetchInfo_search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        fetchInfo_annonce();

        try {


            final int counter = 100 * 8000;

            countDownTimer = new CountDownTimer(counter, 8000) {

                public void onTick(long millisUntilFinished) {
                    // Toast.makeText(MainActivity.this , ""+(millisUntilFinished / 1000),Toast.LENGTH_LONG).show();
                    recyclerView2.smoothScrollToPosition(y);
                    y++;
                    if (y > x) {
                        y = 0;
                    }
                }

                public void onFinish() {
                }

            }.start();
        } catch (Exception e) {
        }

        return view;
    }

    public void fetchInfo_annonce() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_annonce>> call = apiinterface.getcontacts_annonce__();
        call.enqueue(new Callback<List<contact_annonce>>() {
            @Override
            public void onResponse(Call<List<contact_annonce>> call, Response<List<contact_annonce>> response) {
                contactList_annonce = response.body();
                try {
                    if (contactList_annonce.size() != 0 || !(contactList_annonce.isEmpty())) {
                        x = contactList_annonce.size();
                        recyclerAdapter_annonce = new RecyclerAdapter_first_annonce_banner(getActivity(), contactList_annonce, recyclerView2);
                        recyclerView2.setAdapter(recyclerAdapter_annonce);
                        fetchInfo_catrgory();
                    }

                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<List<contact_annonce>> call, Throwable t) {

            }
        });
    }
    public void fetchInfo_catrgory() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_category>> call = apiinterface.getcontacts_annonce();
        call.enqueue(new Callback<List<contact_category>>() {
            @Override
            public void onResponse(Call<List<contact_category>> call, Response<List<contact_category>> response) {
                progressBar.setVisibility(View.GONE);
                contactList = response.body();
                try {
                    if (contactList.size() != 0 || !(contactList.isEmpty())) {
                        categoryId=contactList.get(0).getId();
                        recyclerAdapter_first_annonce= new RecyclerAdapter_first_annonce(getActivity(), contactList,Main_fragment.this);
                        recyclerView.setAdapter(recyclerAdapter_first_annonce);
                        fetchInfo_sub_catrgory(categoryId);


                    }

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<List<contact_category>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
    public void fetchInfo_sub_catrgory(final int categoryIdd) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView3.setVisibility(View.VISIBLE);
        novalue.setVisibility(View.GONE);
        recyclerView3.setAdapter(null);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);

        recyclerView3.setLayoutManager(staggeredGridLayoutManager);
        recyclerView3.setHasFixedSize(true);
      //  Toast.makeText(getContext(),categoryIdd+"",Toast.LENGTH_LONG).show();
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_sub_category>> call = apiinterface.getsubCategory(categoryIdd);
        call.enqueue(new Callback<List<contact_sub_category>>() {
            @Override
            public void onResponse(Call<List<contact_sub_category>> call, Response<List<contact_sub_category>> response) {
                progressBar.setVisibility(View.GONE);
                          contactList_sub = response.body();
              //  Toast.makeText(getContext(),contactList.get(0).getAr_title()+"",Toast.LENGTH_LONG).show();
                try {
                    if (contactList_sub.size() != 0 || !(contactList_sub.isEmpty())) {
                        recyclerAdapter_first_annonce_sub= new RecyclerAdapter_sub_annonce(getActivity(), contactList_sub);
                        recyclerView3.setAdapter(recyclerAdapter_first_annonce_sub);


                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        novalue.setVisibility(View.VISIBLE);
                        contactList_sub.clear();
                        recyclerView3.removeAllViews();
                        recyclerView3.removeAllViewsInLayout();
                        recyclerView3.setVisibility(View.GONE);
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<contact_sub_category>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
    public void fetchInfo_search(String text) {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView3.setVisibility(View.VISIBLE);
        novalue.setVisibility(View.GONE);
        recyclerView3.setAdapter(null);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);

        recyclerView3.setLayoutManager(staggeredGridLayoutManager);
        recyclerView3.setHasFixedSize(true);
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<List<contact_sub_category>> call = apiinterface.getsubCategorySearch(text);
        call.enqueue(new Callback<List<contact_sub_category>>() {
            @Override
            public void onResponse(Call<List<contact_sub_category>> call, Response<List<contact_sub_category>> response) {
                progressBar.setVisibility(View.GONE);
                contactList_sub = response.body();
                //  Toast.makeText(getContext(),contactList.get(0).getAr_title()+"",Toast.LENGTH_LONG).show();
                try {
                    if (contactList_sub.size() != 0 || !(contactList_sub.isEmpty())) {
                        recyclerAdapter_first_annonce_sub= new RecyclerAdapter_sub_annonce(getActivity(), contactList_sub);
                        recyclerView3.setAdapter(recyclerAdapter_first_annonce_sub);


                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        novalue.setVisibility(View.VISIBLE);
                        contactList_sub.clear();
                        recyclerView3.removeAllViews();
                        recyclerView3.removeAllViewsInLayout();
                        recyclerView3.setVisibility(View.GONE);
                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<List<contact_sub_category>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

            }
        });
    }
}
