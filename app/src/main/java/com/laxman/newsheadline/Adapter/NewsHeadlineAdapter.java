package com.laxman.newsheadline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.laxman.newsheadline.Model.TopHeadlineModelClass;
import com.laxman.newsheadline.R;

import java.util.List;

public class NewsHeadlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<TopHeadlineModelClass> topHeadlineModelClasses;
    Context context;


    public NewsHeadlineAdapter(Context context, List<TopHeadlineModelClass> topHeadlineModelClass) {
        this.context = context;
        this.topHeadlineModelClasses = topHeadlineModelClass;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_newsheadline, parent, false);
        HeadLineHolder mh = new HeadLineHolder(v);
        return mh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final HeadLineHolder headLineHolder = (HeadLineHolder) holder;
        headLineHolder.tvTitle.setText(topHeadlineModelClasses.get(position).getTitle());
        headLineHolder.tvPublishedAt.setText(topHeadlineModelClasses.get(position).getPublishedAt());
        headLineHolder.tvDescription.setText(topHeadlineModelClasses.get(position).getDescription());
        Glide.with(context).load(topHeadlineModelClasses.get(position).getUrlToImage()).into(((HeadLineHolder) holder).ivNwsHdl);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return topHeadlineModelClasses.size();
    }

    public class HeadLineHolder extends RecyclerView.ViewHolder {

        ImageView ivNwsHdl;
        TextView tvTitle, tvPublishedAt, tvDescription;

        public HeadLineHolder(View v) {
            super(v);
            tvDescription = (TextView) v.findViewById(R.id.tvDescription);
            tvTitle = (TextView) v.findViewById(R.id.tvTitle);
            tvPublishedAt = (TextView) v.findViewById(R.id.tvPublishedAt);
            ivNwsHdl = (ImageView) v.findViewById(R.id.ivNwsHdl);


        }
    }


}
