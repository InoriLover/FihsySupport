package com.local.sample.extra.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.local.sample.R;

import java.util.ArrayList;
import java.util.List;

import fishy.support.view.SixEdgeArrow;

/**
 * Created by DN2017030300 on 2018/9/6.
 */

public class SixEdgeArrowSample extends AppCompatActivity {
    SixEdgeArrow sixEdgeArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_sixedge_arrow);
        sixEdgeArrow = findViewById(R.id.sixEdgeArrow);
        sixEdgeArrow.setData(createData());
        sixEdgeArrow.setCurrentLevel(2);
    }

    List<SixEdgeArrow.ArrowIndicator> createData() {
        List<SixEdgeArrow.ArrowIndicator> data = new ArrayList<>();
        data.add(new SixEdgeArrow.ArrowIndicator(getResources().getColor(R.color.indicator_red_1), "良好",
                R.mipmap.icon_slark));
        data.add(new SixEdgeArrow.ArrowIndicator(getResources().getColor(R.color.indicator_red_2), "轻度",
                R.mipmap.icon_slark));
        data.add(new SixEdgeArrow.ArrowIndicator(getResources().getColor(R.color.indicator_red_3), "中度",
                R.mipmap.icon_slark));
        data.add(new SixEdgeArrow.ArrowIndicator(getResources().getColor(R.color.indicator_red_4), "深度",
                R.mipmap.icon_slark));
        data.add(new SixEdgeArrow.ArrowIndicator(getResources().getColor(R.color.indicator_red_5), "重度",
                R.mipmap.icon_slark));
        return data;
    }
}
