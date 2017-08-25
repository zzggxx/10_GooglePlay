package com.itcast.googleplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.itcast.googleplay.adapter.ImageScaleAdapter;
import com.itcast.googleplay.ui.widght.HackyViewPager;

import java.util.ArrayList;

public class ImageScaleActivity extends AppCompatActivity {

    private HackyViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale);

        //$$$$小心重名的情况,会报空指针
        viewPager = (HackyViewPager) findViewById(R.id.viewPagerr);

        ArrayList<String> urlList = getIntent().getStringArrayListExtra("urlList");
        int currentItem = getIntent().getIntExtra("currentItem", 0);

        viewPager.setAdapter(new ImageScaleAdapter(urlList));

        viewPager.setCurrentItem(currentItem);


    }
}
