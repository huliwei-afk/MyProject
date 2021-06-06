package com.example.myproject.Day6;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class GuidePagerAdapter extends PagerAdapter {
    private List<View> views;
    private Context context;

    public GuidePagerAdapter(List<View> views, Context context) {
        this.views = views;
        this.context = context;
    }

    //获得viewpager中有多少个view
    @Override
    public int getCount() {
        return views.size();
    }

    //判断instantiateItem(ViewGroup, int)函数所返回来的Key与一个页面视图是否是 代表的同一个视图
    // (即它俩是否是对应的，对应的表示同一个View),通常我们直接写 return view == object!
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    //①将给定位置的view添加到ViewGroup(容器)中,创建并显示出来 ②返回一个代表新增页面的Object(key),
    // 通常都是直接返回view本身就可以了,当然你也可以 自定义自己的key,但是key和每个view要一一对应的关系
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    //移除一个给定位置的页面
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(position));
    }
}
