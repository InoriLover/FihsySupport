package com.local.sample.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.local.sample.R;

import fishy.support.view.ColorIndicatorProgressBarCopy;

/**
 * Created by DN2017030300 on 2018/5/10.
 */

public class ColorIndicatorBarSample extends AppCompatActivity {
    ColorIndicatorProgressBarCopy mColorIndicatorProgressBar;
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
                mColorIndicatorProgressBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
