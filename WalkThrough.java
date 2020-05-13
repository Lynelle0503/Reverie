package com.example.reverie_themeditationapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WalkThrough extends AppCompatActivity {

    private Button NextB;
    private TextView Skip;
    private Button PrevB;
    private int current_page;

    private ViewPager Slide;
    private LinearLayout Dots;
    private TextView[] ndots;

    private SliderAdapter sliderAdapter;

    private long Backpressedtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);

        NextB = (Button) findViewById(R.id.next);
        Skip = (TextView) findViewById(R.id.skiptext);
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });
        PrevB = (Button) findViewById(R.id.prev);

        Slide = (ViewPager) findViewById(R.id.slideviewpager);
        Dots = (LinearLayout) findViewById(R.id.dotslayout);

        sliderAdapter = new SliderAdapter(this);

        Slide.setAdapter(sliderAdapter);

        addDotsIndicator(0);

        Slide.addOnPageChangeListener(viewListener);

    }

    private void addDotsIndicator(int position) {
        ndots = new TextView[5];
        Dots.removeAllViews();

        for (int i = 0; i < ndots.length; i++) {
            ndots[i] = new TextView(this);
            ndots[i].setText(Html.fromHtml("&#8226;"));
            ndots[i].setTextSize(35);
            ndots[i].setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            Dots.addView(ndots[i]);
        }

        if (ndots.length > 0) {
            ndots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void openSignUp() {
        Intent sign = new Intent(WalkThrough.this, LogIn.class);
        startActivity(sign);
    }


    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            current_page = i;

            if (i == 0) {
                NextB.setEnabled(true);
                PrevB.setEnabled(false);
                PrevB.setVisibility(View.INVISIBLE);

                NextB.setText("Next");
                PrevB.setText("");
                NextB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Slide.setCurrentItem(current_page + 1);
                    }
                });

            } else if (i == ndots.length - 1) {
                NextB.setEnabled(true);
                PrevB.setEnabled(true);
                PrevB.setVisibility(View.VISIBLE);

                NextB.setText("Done");
                PrevB.setText("Prev");

                PrevB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Slide.setCurrentItem(current_page - 1);
                    }
                });
                NextB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openSignUp();
                    }
                });
            } else {
                NextB.setEnabled(true);
                PrevB.setEnabled(true);
                PrevB.setVisibility(View.VISIBLE);

                NextB.setText("Next");
                PrevB.setText("Prev");
                NextB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Slide.setCurrentItem(current_page + 1);
                    }
                });
                PrevB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Slide.setCurrentItem(current_page - 1);
                    }
                });
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    //Back press code
    @Override
    public void onBackPressed(){
        if(Backpressedtime + 2000 >System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }
        else{
            Toast.makeText(getBaseContext(), "Press again to exit", Toast.LENGTH_LONG).show();
        }
        Backpressedtime = System.currentTimeMillis();
    }
}