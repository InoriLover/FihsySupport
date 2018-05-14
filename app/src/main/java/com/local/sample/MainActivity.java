package com.local.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.local.sample.base.activity.SampleBaseAlphaActivity;
import com.local.sample.base.activity.SampleBaseAlphaActivity2;
import com.local.sample.base.activity.SampleBaseAlphaActivity3;
import com.local.sample.customview.ColorIndicatorBarSample;
import com.local.sample.customview.CountDownProgressBarSample;

import fishy.support.view.ColorIndicatorProgressBar;

public class MainActivity extends AppCompatActivity {

    Button btnNormalColor;
    Button btnImageHead;
    Button btnDrawerLayout;
    Button btnColorIndicatorBar;
    Button btnCountDownProgressBar;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNormalColor:
                    Intent intent = new Intent(MainActivity.this, SampleBaseAlphaActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnImageHead:
                    Intent intent2 = new Intent(MainActivity.this, SampleBaseAlphaActivity2.class);
                    startActivity(intent2);
                    break;
                case R.id.btnDrawerlayout:
                    Intent intent3 = new Intent(MainActivity.this, SampleBaseAlphaActivity3.class);
                    startActivity(intent3);
                    break;
                case R.id.btnColorIndicatorBar:
                    Intent intent4 = new Intent(MainActivity.this, ColorIndicatorBarSample.class);
                    startActivity(intent4);
                    break;
                case R.id.btnCountDownProgressBar:
                    Intent intent5 = new Intent(MainActivity.this, CountDownProgressBarSample.class);
                    startActivity(intent5);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        btnNormalColor = findViewById(R.id.btnNormalColor);
        btnImageHead = findViewById(R.id.btnImageHead);
        btnDrawerLayout = findViewById(R.id.btnDrawerlayout);
        btnColorIndicatorBar = findViewById(R.id.btnColorIndicatorBar);
        btnCountDownProgressBar = findViewById(R.id.btnCountDownProgressBar);
        btnNormalColor.setOnClickListener(onClickListener);
        btnImageHead.setOnClickListener(onClickListener);
        btnDrawerLayout.setOnClickListener(onClickListener);
        btnColorIndicatorBar.setOnClickListener(onClickListener);
        btnCountDownProgressBar.setOnClickListener(onClickListener);
    }
}
