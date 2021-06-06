package com.example.myproject.Day5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChatFragment extends Fragment {

    private Chat[] chats = {new Chat("星星",R.drawable.star1),
            new Chat("蝶蝶",R.drawable.star2),
            new Chat("马马",R.drawable.marco1),
            new Chat("可可",R.drawable.marco2)};

    private List<Chat> chatList = new ArrayList<>();

    private ChatAdapter chatAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.chat_fragment, container, false);

        initChatList();

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.chat_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //设置item之间的距离
//        recyclerView.addItemDecoration(new SpacesItemDecoration(SpacesItemDecoration.px2dp(10)));

        //设置下划线
       recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        chatAdapter = new ChatAdapter(chatList);
        recyclerView.setAdapter(chatAdapter);
        return view;
    }

    private void initChatList(){
        chatList.clear();
        for (int i = 0; i <= 50; i++){
            Random random = new Random();
            int index = random.nextInt(chats.length);
            chatList.add(chats[index]);
        }
    }

}
