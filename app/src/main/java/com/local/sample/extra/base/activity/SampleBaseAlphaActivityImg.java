package com.local.sample.extra.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.local.sample.R;

import fishy.support.base.activity.BaseAlphaActivity;

/**
 * Created by DN2017030300 on 2018/5/3.
 */

public class SampleBaseAlphaActivityImg extends BaseAlphaActivity{
    SeekBar seekBar;
    ImageView imgMiku;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_base_alpha_image);
        initView();
        setUseLightIcon(true);
    }

    private void initView() {
        seekBar=findViewById(R.id.seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setMaskAlpha(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected Mode chooseAlphaMode() {
        return Mode.IMAGE_TOOLBAR;
    }
}
