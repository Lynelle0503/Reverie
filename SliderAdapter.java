package com.example.reverie_themeditationapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }

    //Arrays

    public int[] slide_images = {
            R.drawable.everyday,
            R.drawable.bridge,
            R.drawable.flower,
            R.drawable.bulb,
            R.drawable.hear
    };

    public String[] slide_read = {
            "In the middle of Bustlings",
            "Just 5 minutes a day can lead to a happy lifetime",
            "18M people relieve stress through meditation",
            "Boosting your creativity and increasing mindfulness",
            "Gear on with your headphones"
    };

    @Override
    public int getCount() {
        return slide_read.length;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position){

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.sliderlayout, container,false);

        ImageView slideimageview = (ImageView) view.findViewById(R.id.slideImage);
        TextView slidetext = (TextView) view.findViewById(R.id.slideText);

        slideimageview.setImageResource(slide_images[position]);
        slidetext.setText(slide_read[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){

        container.removeView((RelativeLayout)object);
    }
}
