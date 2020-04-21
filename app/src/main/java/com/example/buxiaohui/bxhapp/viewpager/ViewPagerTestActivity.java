package com.example.buxiaohui.bxhapp.viewpager;

import com.example.buxiaohui.bxhapp.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewPagerTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        ViewPager viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 10; // test
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(View container, int position, Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(View container, int position) {
                View page = LayoutInflater.from(container.getContext()).inflate(R.layout.item_movie_banner, null);
                ImageView image = page.findViewById(R.id.img);

                image.setScaleType(ImageView.ScaleType.FIT_XY); //  test

                ViewGroup.LayoutParams layoutParams =
                        new ViewGroup.LayoutParams(200, 400);
                page.setLayoutParams(layoutParams);

                ((ViewPager) container).addView(page);
                Log.d("instantiateItem", "position:" + position);
                return page;
            }
        });
    }
}
