package com.local.sample.extra.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.local.sample.R;

import fishy.support.view.PointImageView;

/**
 * Created by DN2017030300 on 2018/5/25.
 */

public class PointImageViewSample extends AppCompatActivity{
    PointImageView pointImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_point_img);
        pointImg=findViewById(R.id.pointImg);
    }
}
