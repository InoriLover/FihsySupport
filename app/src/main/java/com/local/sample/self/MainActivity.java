package com.local.sample.self;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.local.sample.R;
import com.local.sample.extra.base.activity.SampleBaseAlphaActivityColor;
import com.local.sample.extra.base.activity.SampleBaseAlphaActivityImg;
import com.local.sample.extra.base.activity.SampleBaseAlphaActivityDrawer;
import com.local.sample.extra.customview.ColorIndicatorBarSample;
import com.local.sample.extra.customview.CountDownProgressBarSample;
import com.local.sample.extra.customview.GradientBarSample;
import com.local.sample.extra.customview.HrvRingSample;
import com.local.sample.extra.customview.PalletViewSample;
import com.local.sample.extra.customview.PointImageViewSample;
import com.local.sample.extra.customview.SixEdgeArrowSample;
import com.local.sample.extra.customview.StepPillarViewSample;
import com.local.sample.extra.customview.VesselViewSample;
import com.local.sample.self.adapter.SimpleGuideAdapter;

import java.util.ArrayList;
import java.util.List;

import fishy.support.view.HrvRing;
import fishy.support.view.SixEdgeArrow;
import fishy.support.view.VesselView;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SimpleGuideAdapter guideAdapter;

//    Button btnNormalColor;
//    Button btnImageHead;
//    Button btnDrawerLayout;
//    Button btnColorIndicatorBar;
//    Button btnCountDownProgressBar;
//    Button btnGradientBar;

//    View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.btnNormalColor:
//                    Intent intent = new Intent(MainActivity.this, SampleBaseAlphaActivityColor.class);
//                    startActivity(intent);
//                    break;
//                case R.id.btnImageHead:
//                    Intent intent2 = new Intent(MainActivity.this, SampleBaseAlphaActivityImg.class);
//                    startActivity(intent2);
//                    break;
//                case R.id.btnDrawerlayout:
//                    Intent intent3 = new Intent(MainActivity.this, SampleBaseAlphaActivityDrawer.class);
//                    startActivity(intent3);
//                    break;
//                case R.id.btnColorIndicatorBar:
//                    Intent intent4 = new Intent(MainActivity.this, ColorIndicatorBarSample.class);
//                    startActivity(intent4);
//                    break;
//                case R.id.btnCountDownProgressBar:
//                    Intent intent5 = new Intent(MainActivity.this, CountDownProgressBarSample.class);
//                    startActivity(intent5);
//                    break;
//                case R.id.btnGradientBar:
//                    Intent intent6 = new Intent(MainActivity.this, GradientBarSample.class);
//                    startActivity(intent6);
//                    break;
//            }
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
//        btnNormalColor = findViewById(R.id.btnNormalColor);
//        btnImageHead = findViewById(R.id.btnImageHead);
//        btnDrawerLayout = findViewById(R.id.btnDrawerlayout);
//        btnColorIndicatorBar = findViewById(R.id.btnColorIndicatorBar);
//        btnCountDownProgressBar = findViewById(R.id.btnCountDownProgressBar);
//        btnGradientBar = findViewById(R.id.btnGradientBar);
//        btnNormalColor.setOnClickListener(onClickListener);
//        btnImageHead.setOnClickListener(onClickListener);
//        btnDrawerLayout.setOnClickListener(onClickListener);
//        btnColorIndicatorBar.setOnClickListener(onClickListener);
//        btnCountDownProgressBar.setOnClickListener(onClickListener);
//        btnGradientBar.setOnClickListener(onClickListener);

        guideAdapter = new SimpleGuideAdapter(createGuideItems(), this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(guideAdapter);
    }

    List<SimpleGuideAdapter.GuideItem> createGuideItems() {
        List<SimpleGuideAdapter.GuideItem> items = new ArrayList<>();
        items.add(new SimpleGuideAdapter.GuideItem
                ("NormalColorStatusbar", SampleBaseAlphaActivityColor.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "ImageHeaderStatusbar", SampleBaseAlphaActivityImg.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "DrawerlayoutStatusbar", SampleBaseAlphaActivityDrawer.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "ColorIndicatorBar", ColorIndicatorBarSample.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "CountDownProgressBar", CountDownProgressBarSample.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "GradientBar", GradientBarSample.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "PalletView", PalletViewSample.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "PointImageView", PointImageViewSample.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "SixEdgeArrow", SixEdgeArrowSample.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "StepPillarView", StepPillarViewSample.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "HrvRing", HrvRingSample.class));
        items.add(new SimpleGuideAdapter.GuideItem(
                "VesselView", VesselViewSample.class));
        return items;
    }
}
