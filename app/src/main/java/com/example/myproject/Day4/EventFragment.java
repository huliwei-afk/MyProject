package com.example.myproject.Day4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.myproject.Day1.MainActivity;
import com.example.myproject.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.lang.reflect.Method;

public class EventFragment extends Fragment {


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.event_fragment, container, false);

        //fragment里的控件获取方式不同
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.collapsing_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar_layout);



        return view;
    }


    //fragment中设置menu的icon可显示
    private void setIconsVisible(Menu menu, boolean flag) {
        //判断menu是否为空
        if (menu != null) {
            try {
                //如果不为空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                //暴力访问该方法
                method.setAccessible(true);
                //调用该方法显示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//加上这句话，menu才会显示出来
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        //add toolbar
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("动态");
//    }

    //menu.clear()将Activity里的menu清除，不然会导致menu错乱
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.event_fragment_menu, menu);
        setIconsVisible(menu,true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //需要onCreate里setHasOptionsMenu为true,并且父类的此方法默认返回false，不然会消耗点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.say_something) {
            Toast.makeText(getActivity(),"You Clicked say",Toast.LENGTH_SHORT).show();
//            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
