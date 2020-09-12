package com.khalej.mishwary.Activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
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
import com.khalej.mishwary.R;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
   int x=0;
    private GoogleMap mMap;
    String lati, longg, subDetails;
    Intent intent;
    Handler mHandler;
    String detail, addressTo;
    double lat, latTo;
    double lng, lngTo;
    int id;
    String name, price, date, type;
   Long datelong;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    int getCategory_id, gender_id;
    String image;
    TextView toolbar_title;
    Button cunti;
    LatLng latLngValue = null;
    TextView address;
SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        toolbar_title=findViewById(R.id.toolbar_title);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        address = findViewById(R.id.address);
        intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        lat = intent.getDoubleExtra("lat",0);
        lng = intent.getDoubleExtra("lng",0);
        image=intent.getStringExtra("image");
        toolbar_title.setText(name);
       datelong= intent.getLongExtra("datelong",0);
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

        cunti = findViewById(R.id.cunti);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
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
        int height = 105;
        int width = 105;
        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.logo);
        Bitmap b = bitmapdraw.getBitmap();
        final Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera

            sydney = new LatLng(lat, lng);
        final CameraUpdate location= CameraUpdateFactory.newLatLngZoom(sydney,18);
        Marker marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(name)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).visible(true));
        marker.showInfoWindow();
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if(x!=0){
                    mMap.clear();
                    Marker marker =  mMap.addMarker(new MarkerOptions().position(new LatLng(lat,lng)).title(name)
                            .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)).visible(true));
                    marker.showInfoWindow();
               }
                mMap.addMarker(new MarkerOptions().position(latLng).title("Mishwary").icon(BitmapDescriptorFactory.fromResource(R.drawable.locationn)));
                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }
                address.setText(addresses.get(0).getAddressLine(0));
                latLngValue=latLng;

            }
        });
        mMap.animateCamera(location);
        cunti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latLngValue!=null){
                    mylocation(latLngValue);}
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void mylocation(final LatLng latLng){

        new AlertDialog.Builder(MapsActivity.this)
                .setTitle("Mishwary")
                .setMessage("هل هذا هو الموقع الذى تريد توصيل الطلب فيه  ؟")
                .setIcon(R.drawable.logo)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Geocoder geocoder;
                        List<Address> addresses = null;
                        geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent intent3=new Intent(MapsActivity.this,Add_Order.class);
                        intent3.putExtra("address",addresses.get(0).getAddressLine(0));
                        intent3.putExtra("latClient",latLng.latitude);
                        intent3.putExtra("lngClient",latLng.longitude);
                        intent3.putExtra("latOrder",lat);
                        intent3.putExtra("lngOrder",lng);
                        intent3.putExtra("name",name);

                        startActivity(intent3);
                        finish();


                    }})
                .setNegativeButton(android.R.string.no,  new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mMap.clear();
                    }}).show();

    }
}

