package com.khalej.mishwary.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.khalej.mishwary.R;
import com.khalej.mishwary.directionhelpers.FetchURL;
import com.khalej.mishwary.directionhelpers.TaskLoadedCallback;
import com.khalej.mishwary.model.Apiclient_home;
import com.khalej.mishwary.model.apiinterface_home;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Add_Order extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private GoogleMap mMap;
    Button cunti;
    Intent intent;
    double latClient,lngClient,latOrder,lngOrder;
    String address,name;
    private MarkerOptions place1, place2;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    LinearLayout addcopoun;
     private Polyline currentPolyline;
     Dialog dialog;
    EditText addcoment;
    Button confirmComment;
    TextInputEditText details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__order);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        this.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dpw);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        details=findViewById(R.id.textInputEditTextphone);
        cunti=findViewById(R.id.cunti);
        cunti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(details.getText().toString().isEmpty()||details.getText().toString()==""){
                    Toast.makeText(Add_Order.this,"من فضلك قم بأدخال تفاصيل طلبك أولا" ,Toast.LENGTH_LONG).show();
                    return;
                }
                if(sharedpref.getString("remember","").equals("yes")){
                    fetchInfo();
                }
                else{
                    Toast.makeText(Add_Order.this,"قم بتسجيل الدخول أولا" ,Toast.LENGTH_LONG).show();
                }
            }
        });
        addcopoun=findViewById(R.id.addcopoun);
        addcopoun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(Add_Order.this);
                dialog.setContentView(R.layout.dialog_comment);
                dialog.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                addcoment=dialog.findViewById(R.id.comment);
                confirmComment=dialog.findViewById(R.id.confirm);
                confirmComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(addcoment.getText().toString()==null||addcoment.getText().toString()==""){
                            Toast.makeText(Add_Order.this,"من فضلك قم بأدخال قيمة الكوبون" ,Toast.LENGTH_LONG).show();
                            return;
                        }
                     dialog.dismiss();
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Add_Order.this);
                        dlgAlert.setMessage("تم اضافة قيمة الكوبون بنجاح سيتم ارفاقه مع طلبك");
                        dlgAlert.setTitle("Mishwary");
                        dlgAlert.setIcon(R.drawable.logo);
                        dlgAlert.setPositiveButton("OK", null);
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                    }
                });
                dialog.show();
            }
        });
    intent=getIntent();
    latClient=intent.getDoubleExtra("latClient",0);
    lngClient=intent.getDoubleExtra("lngClient",0);
    latOrder=intent.getDoubleExtra("latOrder",0);
        lngOrder=intent.getDoubleExtra("lngOrder",0);
        address=intent.getStringExtra("address");
        name=intent.getStringExtra("name");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        place1 = new MarkerOptions().position(new LatLng(latOrder, lngOrder)).title("Location 1");
        place2 = new MarkerOptions().position(new LatLng(latClient, lngOrder)).title("Location 2");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        new FetchURL(Add_Order.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");

    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
        LatLng sydney;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
      //   Add a marker in Sydney and move the camera
        int height = 105;
        int width = 105;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.logo);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
      //  mMap.setMyLocationEnabled(true);
        Marker marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(latOrder,lngOrder)).title(name)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).visible(true));
        marker.showInfoWindow();
        Marker marker2 =  mMap.addMarker(new MarkerOptions().position(new LatLng(latClient,lngClient)).title("موقعك")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.locationn)));
        marker2.showInfoWindow();
        sydney = new LatLng(latOrder, lngOrder);
        mMap.addPolygon(new PolygonOptions().add(new LatLng(latOrder,lngOrder)).add(new LatLng(latClient,lngClient))
                .strokeWidth(6f).fillColor(Color.RED));

        // mMap.addMarker(new MarkerOptions().position(sydney).title("HandMade"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate location= CameraUpdateFactory.newLatLngZoom(sydney,17);
        mMap.animateCamera(location);


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }
    public void fetchInfo(){
        Date c = Calendar.getInstance().getTime();
          String copounn="";
          try {
              copounn=addcoment.getText().toString();
          }catch (Exception e){}

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.add_order(sharedpref.getInt("id",0),lngClient,latClient,address,
                details.getText().toString(),lngOrder,latOrder,copounn);
        progressDialog = ProgressDialog.show(Add_Order.this, "جاري ارسال طلبك", "Please wait...", false, false);
        progressDialog.show();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.code() == 422) {
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //  Toast.makeText(Regester.this,jObjError.toString(),Toast.LENGTH_LONG).show();
                    Toast.makeText(Add_Order.this,"هناك بيانات خاطئة",Toast.LENGTH_LONG).show();
                    Log.d("tag", jObjError.toString());
                    progressDialog.dismiss();
                    return;
                }
                progressDialog.dismiss();

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Add_Order.this);
                dlgAlert.setMessage("تم ارسال طلبك بنجاح سيتم التواصل معك فورا وتوصيل الطلب");
                dlgAlert.setTitle("Mishwary");
                dlgAlert.setIcon(R.drawable.logo);
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("error", t.toString());
                Toast.makeText(Add_Order.this, t+"", Toast.LENGTH_LONG).show();
            }
        });
    }
}
