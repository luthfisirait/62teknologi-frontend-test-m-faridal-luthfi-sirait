package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.mytest.Adapter.AdapterBisnis;
import com.example.mytest.api.ApiClientDjango;
import com.example.mytest.api.ApiInterfaceDjango;
import com.example.mytest.dataApi.BusinessesItem;
import com.example.mytest.dataApi.ResponseApi;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 10;
    LocationManager locationManager;
    EditText search;
    ImageView btn_search;
    double mylatitude,mylongitude;
    List<BusinessesItem> responseApiList;
    AdapterBisnis adapter;
    RecyclerView recyclerView;
    SharedPreferences sharedPreferences;
    RadioButton filterpopuler, filterrating, filterterdekat;
    private FusedLocationProviderClient fusedLocationProviderClient;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new AdapterBisnis(MainActivity.this,responseApiList);
        search = findViewById(R.id.et_search);
        btn_search = findViewById(R.id.btn_search);
        recyclerView =findViewById(R.id.recyclerview);
        filterpopuler = findViewById(R.id.filter_button_1);
        filterrating = findViewById(R.id.filter_button_2);
        filterterdekat = findViewById(R.id.filter_button_3);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        sharedPreferences = this.getSharedPreferences("Lokasi", Context.MODE_PRIVATE);

        getlokasi();
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_search = search.getText().toString();

                if(!str_search.isEmpty()){
                    getAPI(str_search);
                    filterpopuler.setChecked(true);
                }

                getlokasi();
            }
        });
        filterpopuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(responseApiList!=null) {
                    Collections.sort(responseApiList, new Comparator<BusinessesItem>() {
                        @Override
                        public int compare(BusinessesItem o1, BusinessesItem o2) {
                            return Integer.compare(o1.getReviewCount(), o2.getReviewCount());
                        }
                    });
                    Collections.reverse(responseApiList);

                    adapter.setData(responseApiList);
                    recyclerView.setAdapter(adapter);
                }
            }
        });
        filterrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(responseApiList!=null){
                Collections.sort(responseApiList, new Comparator<BusinessesItem>() {
                    @Override
                    public int compare(BusinessesItem o1, BusinessesItem o2) {
                        return Double.compare(o1.getRating(), o2.getRating());
                    }
                });
                Collections.reverse(responseApiList);

                adapter.setData(responseApiList);
                recyclerView.setAdapter(adapter);

            }}
        });
        filterterdekat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(responseApiList!=null){
                Collections.sort(responseApiList, new Comparator<BusinessesItem>() {
                    @Override
                    public int compare(BusinessesItem o1, BusinessesItem o2) {
                        return Double.compare(o1.getJarak(), o2.getJarak());
                    }
                });


                adapter.setData(responseApiList);
                recyclerView.setAdapter(adapter);
            }}
        });
    }
    private void getlokasi(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Mendapatkan lokasi terakhir yang diketahui
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocation != null) {
                mylatitude= lastKnownLocation.getLatitude();
                mylongitude = lastKnownLocation.getLongitude();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("latitude",String.valueOf(mylatitude));
                editor.putString("longitude",String.valueOf(mylongitude));
                editor.apply();
                // Gunakan data lokasi ini
            } else {
                // Lokasi terakhir tidak diketahui, perlu memperbarui lokasi saat ini
                // Metode lain dari LocationListener yang perlu diimplementasikan
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, location -> {
                    mylatitude = location.getLatitude();
                    mylongitude = location.getLongitude();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("latitude",String.valueOf(mylatitude));
                    editor.putString("longitude",String.valueOf(mylongitude));
                    editor.apply();

                    // Gunakan data lokasi ini
                });
            }

        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    private void getAPI(String pencarian) {


        ApiInterfaceDjango apiInterface = ApiClientDjango.getClient().create(ApiInterfaceDjango.class);
        Call<ResponseApi> responseApiCall = apiInterface.homeresponse(pencarian);
        responseApiCall.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
                responseApiList = response.body().getBusinesses();
                for(BusinessesItem item:responseApiList){
                    Location locationA = new Location("point A");
                    locationA.setLatitude(item.getLatitude());
                    locationA.setLongitude(item.getLongitude());
                    Location locationB = new Location("point B");
                    double mylatitude = Double.valueOf(sharedPreferences.getString("latitude",""));
                    double mylongitude = Double.valueOf(sharedPreferences.getString("longitude",""));
                    locationB.setLatitude(mylatitude);
                    locationB.setLongitude(mylongitude);
                    float distance = locationA.distanceTo(locationB);
                    double jarak = Math.round(distance * 0.001 * 10.0) / 10.0;
                    item.setJarak(jarak);
                }

                adapter.setData(responseApiList);
                recyclerView.setAdapter(adapter);



            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {

            }
        });

    }

}