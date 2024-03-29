package com.example.myproject.Day1;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> mFragmentList;
    public MainFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
        super(fm);
        mFragmentList=list;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }

    @Override
    public int getCount() {
        return mFragmentList!=null?mFragmentList.size():0;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }
}