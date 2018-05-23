package com.local.sample.extra.customview;

import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.SeekBar;

import com.local.sample.R;

import fishy.support.view.CountDownProgressBar;

/**
 * Created by DN2017030300 on 2018/5/14.
 */

public class CountDownProgressBarSample extends AppCompatActivity {
    CountDownProgressBar countDownProgressBar;
    SeekBar seekBar;
    Button btnAdd;
    Button btnSub;
    int totalTime = 40;
    Button btnSmooth;
    Button btnStable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_count_down_progressbar);
        countDownProgressBar = findViewById(R.id.countDownProgressBar);
        btnSmooth=findViewById(R.id.btnSmooth);
        btnStable=findViewById(R.id.btnStable);
        btnSmooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimFloat();
            }
        });
        btnStable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimInt();
            }
        });
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

    void startAnimFloat() {
        ValueAnimator mAnimator;
        mAnimator = ValueAnimator.ofObject(new FloatEvaluator(), 0, 100);
        // 设置差值器
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                countDownProgressBar.setProgress(value);
            }
        });

        mAnimator.setDuration((long) (1000*30));
        mAnimator.start();
    }
    void startAnimInt() {
        ValueAnimator mAnimator;
        mAnimator = ValueAnimator.ofObject(new IntEvaluator(), 0, 100);
        // 设置差值器
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                countDownProgressBar.setProgress(value);
            }
        });

        mAnimator.setDuration((long) (1000*30));
        mAnimator.start();
    }
}
