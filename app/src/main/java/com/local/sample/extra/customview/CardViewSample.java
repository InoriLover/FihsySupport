package com.local.sample.extra.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.local.sample.R;

/**
 * Created by DN2017030300 on 2018/10/25.
 */

public class CardViewSample extends AppCompatActivity {
    CardView cardView;
    TextView tvElevation;
    SeekBar seekbar;
    int minDp = 0;
    int maxDp = 30;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_cardview);
        cardView = findViewById(R.id.cardView);
        tvElevation = findViewById(R.id.tvElevation);
        seekbar = findViewById(R.id.seekbar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int dp = (int) (progress * (maxDp - minDp) * 1.0f / 100 + minDp + 0.5f);
                tvElevation.setText(dp + "dp");
                cardView.setCardElevation(dp);
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
