package com.local.sample.extra.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.local.sample.R;

import fishy.support.view.IndicatorProgressBar;

/**
 * Created by DN2017030300 on 2018/5/10.
 */

public class ColorIndicatorBarSample extends AppCompatActivity {
    IndicatorProgressBar mColorIndicatorProgressBar;
    SeekBar seekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_widget_color_indicatorbar);
        mColorIndicatorProgressBar = findViewById(R.id.colorIndicator);
        seekBar = findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mColorIndicatorProgressBar.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //配置indicator属性
        mColorIndicatorProgressBar.setShowAxis(true);
        //指标针往内部嵌入的off
        mColorIndicatorProgressBar.setRingAxisOff(6);
        mColorIndicatorProgressBar.setIndicatorWidth(32);
        mColorIndicatorProgressBar.setLowTv("低");
        mColorIndicatorProgressBar.setNormalTv("一般");
        mColorIndicatorProgressBar.setHighTv("高");
        mColorIndicatorProgressBar.setSubDesTv("健康宝宝几率");
        mColorIndicatorProgressBar.setTvUnit("%");
        mColorIndicatorProgressBar.setValueYoff(4);
        mColorIndicatorProgressBar.setValueDesYoff(4);
        mColorIndicatorProgressBar.setRingValueColor(getResources().getColor(R.color.gray));
        mColorIndicatorProgressBar.setRingValueSize(14);
        mColorIndicatorProgressBar.setRangeColor(getResources().getColor(R.color.colorPrimary));
        mColorIndicatorProgressBar.setRangeSize(14);
        mColorIndicatorProgressBar.setCenterTextColor(getResources().getColor(R.color.black));
        mColorIndicatorProgressBar.setCenterTextSize(24);
        mColorIndicatorProgressBar.setCenterSubColor(getResources().getColor(R.color.soft_gray));
        mColorIndicatorProgressBar.setCenterSubSize(12);


        mColorIndicatorProgressBar.refresh();
    }
}
