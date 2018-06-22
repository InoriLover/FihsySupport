package com.local.sample.extra.customview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.local.sample.R;
import com.local.sample.self.adapter.TestPointImageAdapter;

import java.util.ArrayList;
import java.util.List;

import fishy.support.view.PointImageView;

/**
 * Created by DN2017030300 on 2018/5/25.
 */

public class PointImageViewSample extends AppCompatActivity {
    RecyclerView recyclerView;
    TestPointImageAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_point_img);
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new TestPointImageAdapter(this,createFakeData());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private List<Boolean> createFakeData() {
        List<Boolean> data = new ArrayList<>();
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        data.add(true);
        return data;
    }
}
