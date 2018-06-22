package com.local.sample.self.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.local.sample.R;

import java.util.List;

import fishy.support.view.PointImageView;

/**
 * Created by DN2017030300 on 2018/6/22.
 */

public class TestPointImageAdapter extends RecyclerView.Adapter<TestPointImageAdapter.ViewHolder> {
    Context context;
    List<Boolean> data;


    public TestPointImageAdapter(Context context, List<Boolean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_poingimage_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        boolean isShow = data.get(position);
        holder.imgPoint.setImageRes(R.mipmap.ic_launcher);
        if (isShow) {
            holder.imgPoint.openPoint();
        } else {
            holder.imgPoint.closePoint();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        PointImageView imgPoint;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPoint = itemView.findViewById(R.id.imgPoint);
        }
    }
}
