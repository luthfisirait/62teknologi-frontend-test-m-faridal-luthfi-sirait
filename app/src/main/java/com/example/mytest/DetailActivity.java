package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytest.Adapter.SlideSetlanguageappAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    ViewPager screenPager;
    TabLayout tabIndicator;
    SlideSetlanguageappAdapter slidlanguageadapter;
    int currentPos;
    TextView nama, alamat, nomorhp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String URL = getIntent().getStringExtra("url");
        String str_nama = getIntent().getStringExtra("nama");
        String str_telp = getIntent().getStringExtra("notelp");
        String str_alamat = getIntent().getStringExtra("alamat");
        String str_latitude = getIntent().getStringExtra("latitude");
        String str_longitude = getIntent().getStringExtra("longitude");

        nama = findViewById(R.id.nama_bisnis);
        alamat = findViewById(R.id.alamat);
        nomorhp = findViewById(R.id.no_telp);
        nama.setText(str_nama);
        alamat.setText(str_alamat);
        nomorhp.setText(str_telp);



        final List<String> mListText = new ArrayList<>();
        String[] listurl = URL.split(",");

        for (String item:listurl){

            mListText.add(item);
        }
        String geoUri = "http://maps.google.com/maps?q=loc:" + str_latitude + "," + str_longitude + " (" + "bisnis" + ")";

        findViewById(R.id.map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DetailActivity.this, "clik", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
               startActivity(intent);
            }
        });










        tabIndicator = findViewById(R.id.tab_indicator);

        screenPager = findViewById(R.id.viewpager_setlanguage);
        slidlanguageadapter =  new SlideSetlanguageappAdapter(this,mListText);
        screenPager.setAdapter(slidlanguageadapter);
        tabIndicator.setupWithViewPager(screenPager);

        screenPager.addOnPageChangeListener(changeListener);





        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }



    public static int getSoftButtonsBarSizePort(Activity activity) {
        // getRealMetrics is only available with API 17 and +
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight)
            return realHeight - usableHeight;
        else
            return 0;
    }
    public void next(View view) {
        screenPager.setCurrentItem(currentPos + 1);
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPos = position;



        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }


    };
    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Log.d("language", "setLocale: setlanguageapp"+languageCode);
        Locale.setDefault(locale);
        Resources resources = getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

    }
}