package com.local.sample.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.local.sample.R;

import fishy.support.view.CountDownProgressBar;

/**
 * Created by DN2017030300 on 2018/5/14.
 */

public class CountDownProgressBarSample extends AppCompatActivity {
    CountDownProgressBar countDownProgressBar;
    SeekBar seekBar;
    int totalTime = 40;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_count_down_progressbar);
        countDownProgressBar = findViewById(R.id.countDownProgressBar);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int remainSecond = (int) (totalTime * progress / 100 + 0.5f);
                countDownProgressBar.setRemainSecond(remainSecond);
                if (progress > 1 & progress <= 99) {
                    countDownProgressBar.startCount();
                } else if (progress > 99) {
                    countDownProgressBar.stopCount();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //countDownProgressBar配置
        countDownProgressBar.setTotalTime(totalTime);
        countDownProgressBar.setCountTextColor(getResources().getColor(R.color.white));
        countDownProgressBar.setCountTextSize(22);
        countDownProgressBar.setRingLineColor(getResources().getColor(R.color.transparent_white));
        countDownProgressBar.setProgressLineColor(getResources().getColor(R.color.white));
        countDownProgressBar.setProgressLineWidth(2);
        countDownProgressBar.setIndicatorColorIn(getResources().getColor(R.color.ring_in_color));
        countDownProgressBar.setIndicatorColorOut(getResources().getColor(R.color.white));
        countDownProgressBar.setIndicatorRadius(8);
        countDownProgressBar.setIndicatorRingWidth(4);
    }
}
