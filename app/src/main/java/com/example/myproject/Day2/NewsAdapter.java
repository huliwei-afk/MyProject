package com.example.myproject.Day2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {
    private Context context;
    private List<News.DataBean> homeNewsList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        LinearLayout linearLayout;
        ImageView imageView;

        public ViewHolder(View view){
            super(view);
            linearLayout = (LinearLayout) view;
            textView = (TextView)view.findViewById(R.id.news_title);
            imageView = (ImageView)view.findViewById(R.id.news_image);
        }
    }

    public NewsAdapter(Context context, List<News.DataBean> homeNewsList){
        this.context = context;
        this.homeNewsList = homeNewsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News.DataBean newsData = homeNewsList.get(position);
        holder.textView.setText(newsData.getTitle());
        Glide.with(context).load(newsData.getThumbnail_pic_s()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return homeNewsList.size();
    }

    public void upDateItem(List<News.DataBean> list){
        homeNewsList = list;
        this.notifyDataSetChanged();
    }
}
