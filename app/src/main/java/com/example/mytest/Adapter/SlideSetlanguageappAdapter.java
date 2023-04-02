package com.example.mytest.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.mytest.R;

import java.util.List;


public class SlideSetlanguageappAdapter extends PagerAdapter {

    Context  mContext;

    List<String> mListText;

    public SlideSetlanguageappAdapter(Context mContext, List<String> mListText) {
        this.mContext = mContext;

        this.mListText = mListText;
    }

    @Override
    public int getCount() {
        return mListText.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d(TAG, "instantiateItem: instantiate slide screen");
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.slide_screen, null);
        ImageView imgSlide = layoutScreen.findViewById(R.id.img_slide);

        //f
        Glide.with(container)
                .load(mListText.get(position))
                .into(imgSlide);


        container.addView(layoutScreen);
        return layoutScreen;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        container.removeView((View) object);
    }
}
