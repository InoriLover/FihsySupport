package com.local.sample.extra.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.local.sample.R;

import java.util.ArrayList;
import java.util.List;

import fishy.support.view.HrvRing;

/**
 * Created by DN2017030300 on 2018/9/11.
 */

public class HrvRingSample extends AppCompatActivity {
    HrvRing hrvRing;
    SeekBar seekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_hrv_ring);
        hrvRing = findViewById(R.id.hrvRing);
        hrvRing.setData(createData());
        hrvRing.setHrvValue(100);

        seekBar = findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float hrvValue = (hrvRing.getMaxValue() - hrvRing.getMinValue()) * progress / 100 + hrvRing.getMinValue();
                hrvRing.setHrvValue(hrvValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    List<HrvRing.HrvScale> createData() {
        List<HrvRing.HrvScale> data = new ArrayList<>();
        data.add(new HrvRing.HrvScale(20, 60, "不好"));
        data.add(new HrvRing.HrvScale(60, 80, "一般"));
        data.add(new HrvRing.HrvScale(80, 90, "良好"));
        data.add(new HrvRing.HrvScale(90, 100, "优秀"));
        return data;
    }
}
