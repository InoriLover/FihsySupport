package com.local.sample.extra.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.local.sample.R;

import java.util.ArrayList;
import java.util.List;

import fishy.support.view.StepPillarView;

/**
 * Created by DN2017030300 on 2018/9/7.
 */

public class StepPillarViewSample extends AppCompatActivity {
    StepPillarView stepPillarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_steppillar);
        stepPillarView = findViewById(R.id.stepPillarView);
        stepPillarView.setData(createData());
        stepPillarView.setCurrentLevel(3);
    }

    List<StepPillarView.ArrowIndicator> createData() {
        List<StepPillarView.ArrowIndicator> list
                = new ArrayList<>();
        list.add(new StepPillarView.ArrowIndicator(getResources().getColor(R.color.indicator_blue_1),
                "放松", R.mipmap.icon_slark));
        list.add(new StepPillarView.ArrowIndicator(getResources().getColor(R.color.indicator_blue_2),
                "轻度", R.mipmap.icon_slark));
        list.add(new StepPillarView.ArrowIndicator(getResources().getColor(R.color.indicator_blue_3),
                "中度", R.mipmap.icon_slark));
        list.add(new StepPillarView.ArrowIndicator(getResources().getColor(R.color.indicator_blue_4),
                "深度", R.mipmap.icon_slark));
        list.add(new StepPillarView.ArrowIndicator(getResources().getColor(R.color.indicator_blue_5),
                "重度", R.mipmap.icon_slark));
        return list;
    }
}
