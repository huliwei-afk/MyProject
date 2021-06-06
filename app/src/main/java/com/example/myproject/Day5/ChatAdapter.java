package com.example.myproject.Day5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>  {

    private Context context;
    private List<Chat> chatList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        CircleImageView circleImageView;
        TextView textView;


        public ViewHolder(View view){
            super(view);
            relativeLayout = (RelativeLayout) view;
            circleImageView = (CircleImageView)view.findViewById(R.id.chat_image);
            textView = (TextView)view.findViewById(R.id.chat_name);

        }
    }

    public ChatAdapter(List<Chat> mChatList){
        chatList = mChatList;
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(context == null){
            context = parent.getContext();
        }

        View view = LayoutInflater.from(context).inflate(R.layout.chat_item, parent,false);

        return new ChatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        holder.textView.setText(chat.getName());
        Glide.with(context).load(chat.getImageId()).into(holder.circleImageView);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}

