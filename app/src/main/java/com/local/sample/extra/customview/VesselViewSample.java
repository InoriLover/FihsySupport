package com.local.sample.extra.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.local.sample.R;

import java.util.ArrayList;
import java.util.List;

import fishy.support.view.VesselView;

/**
 * Created by DN2017030300 on 2018/9/13.
 */

public class VesselViewSample extends AppCompatActivity {
    VesselView vesselView;
    Button btnAdd, btnSub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_vessel_view);
        vesselView = findViewById(R.id.vesselView);
        vesselView.setVesselDes(createVesselDes());
        vesselView.setLevel(3);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vesselView.setLevel(vesselView.getLevel() + 1);
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vesselView.setLevel(vesselView.getLevel() - 1);
            }
        });
    }

    private List<VesselView.VesselDes> createVesselDes() {
        List<VesselView.VesselDes> vesselDes = new ArrayList<>();
        vesselDes.add(new VesselView.VesselDes("第一阶段", "血管柔软"));
        vesselDes.add(new VesselView.VesselDes("第二阶段", "轻度硬化"));
        vesselDes.add(new VesselView.VesselDes("第三阶段", "血管硬化"));
        vesselDes.add(new VesselView.VesselDes("第四阶段", "轻度狭窄"));
        vesselDes.add(new VesselView.VesselDes("第五阶段", "血管狭窄"));
        vesselDes.add(new VesselView.VesselDes("第六阶段", "血管壁钙化"));
        return vesselDes;
    }
}
