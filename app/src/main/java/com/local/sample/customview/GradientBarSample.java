package com.local.sample.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.local.sample.R;

import java.util.ArrayList;
import java.util.List;

import fishy.support.view.GradientScaleBar;

/**
 * Created by DN2017030300 on 2018/5/15.
 */

public class GradientBarSample extends AppCompatActivity {
    GradientScaleBar gradientBar;
    SeekBar seekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_gradient_bar);
        gradientBar = findViewById(R.id.gradientBar);
        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gradientBar.setValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        gradientBar.setValueTvColor(getResources().getColor(R.color.soft_text_gray));
        gradientBar.setDesTvColor(getResources().getColor(R.color.soft_text_gray));
        gradientBar.setBkgBarColor(getResources().getColor(R.color.gradient_bkg));
        gradientBar.setGradientStartColor(getResources().getColor(R.color.gradient_start));
        gradientBar.setGradientEndColor(getResources().getColor(R.color.gradient_end));
        gradientBar.setValueTvSize(12);
        gradientBar.setDesTvSize(12);
        gradientBar.setBarHeight(16);
        gradientBar.setScaleWidth(3);
        gradientBar.setScaleArray(createScaleArray());
        gradientBar.setDesArray(createDesArray());

    }

    List<Integer> createScaleArray() {
        List<Integer> scales = new ArrayList<>();
        scales.add(35);
        scales.add(55);
        scales.add(80);
        return scales;
    }

    List<String> createDesArray() {
        List<String> dess = new ArrayList<>();
        dess.add("区间1");
        dess.add("区间2");
        dess.add("区间3");
        dess.add("区间4");
        return dess;
    }
}
