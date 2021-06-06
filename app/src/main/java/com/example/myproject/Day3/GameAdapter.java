package com.example.myproject.Day3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject.R;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.ViewHolder> {
    private Context context;
    private List<Game> gameList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView textView;
        LinearLayout linearLayout;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView)view;
            imageView = (ImageView)view.findViewById(R.id.game_image);
            textView = (TextView)view.findViewById(R.id.game_name);

        }
    }

    public GameAdapter(List<Game> mGameList){
        gameList = mGameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.game_item, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Game game = gameList.get(position);
        holder.textView.setText(game.getName());
        Glide.with(context).load(game.getImageId()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }
}
