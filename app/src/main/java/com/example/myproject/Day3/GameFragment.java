package com.example.myproject.Day3;

import android.os.Bundle;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.Day1.MainActivity;
import com.example.myproject.MessageEvent;
import com.example.myproject.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFragment extends Fragment {

    private Game[] games = {new Game("茶杯头",R.drawable.game_cuphead),
                            new Game("四海兄弟",R.drawable.game_mafia),
                            new Game("武士零",R.drawable.game_katanazero),
                            new Game("死亡细胞",R.drawable.game_deadcells)};

    private List<Game> gameList = new ArrayList<>();

    private GameAdapter gameAdapter;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.game_fragment, container, false);

        //初始化game图片
        initGameImage();


        //加载RV和Glide布局相关
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.game_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        gameAdapter = new GameAdapter(gameList);
        recyclerView.setAdapter(gameAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        EventBus.getDefault().postSticky(new MessageEvent(R.menu.game_fragment_menu));
    }

    //随机加载50个图片
    private void initGameImage(){
        gameList.clear();
        for (int i = 0; i <= 50; i++){
            Random random = new Random();
            int index = random.nextInt(games.length);
            gameList.add(games[index]);
        }
    }

    public void onDestroyView(){
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    //menu.clear()将Activity里的menu清除，不然会导致menu错乱
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.game_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //需要onCreate里setHasOptionsMenu为true,并且父类的此方法默认返回false，不然会消耗点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.some_game) {
            Toast.makeText(getActivity(),"You Clicked game",Toast.LENGTH_SHORT).show();
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


