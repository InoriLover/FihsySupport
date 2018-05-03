package com.local.sample.base.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.local.sample.R;

import fishy.support.base.activity.BaseAlphaActivity;
import fishy.support.util.RandomUtil;

/**
 * Created by Fishy on 2018/5/3.
 */

public class SampleBaseAlphaActivity extends BaseAlphaActivity {
    View toolbar;
    Button btnRandomColor;
    SeekBar seekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_base_alpha_normal);
        setStatusColor(getResources().getColor(R.color.pink));
        initView();
    }

    private void initView() {
        btnRandomColor = findViewById(R.id.btnRandomColor);
        seekBar = findViewById(R.id.seekbar);
        toolbar=findViewById(R.id.toolBar);

        seekBar.setProgress(0);
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
        btnRandomColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int randomColor = RandomUtil.getRandomColor();
                String colorStr = Integer.toHexString(randomColor);
                //简单格式化一下
                colorStr = "#" + colorStr;
                btnRandomColor.setText(colorStr);
                btnRandomColor.setBackgroundColor(randomColor);
                toolbar.setBackgroundColor(randomColor);
                //设置statusbar颜色
                setStatusColor(randomColor);
            }
        });
    }

    @Override
    protected Mode chooseAlphaMode() {
        return Mode.NORMAL_COLOR;
    }
}
