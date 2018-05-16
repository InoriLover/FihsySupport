package com.local.sample.self.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.local.sample.R;
import com.local.sample.self.MainActivity;

import java.util.List;

/**
 * Created by DN2017030300 on 2018/5/16.
 */

public class SimpleGuideAdapter extends RecyclerView.Adapter<SimpleGuideAdapter.ViewHolder> {
    List<GuideItem> items;
    Context context;

    public SimpleGuideAdapter(List<GuideItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_simple_guide, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final GuideItem item = items.get(position);
        holder.tvShow.setText(item.getShowName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, item.getClazz());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvShow;


        public ViewHolder(View itemView) {
            super(itemView);
            tvShow = itemView.findViewById(R.id.tvShow);
        }

    }

    public static class GuideItem {
        String showName;
        Class clazz;

        public GuideItem(String showName, Class clazz) {
            this.showName = showName;
            this.clazz = clazz;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public Class getClazz() {
            return clazz;
        }

        public void setToClass(Class clazz) {
            this.clazz = clazz;
        }
    }
}
