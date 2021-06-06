package com.example.myproject.Day4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myproject.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MeFragment extends Fragment {

    private TabLayout tabLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.tablayout);
        List<String> titles = new ArrayList<>();

        titles.add("今日");
        titles.add("求职");
        titles.add("内推");

        for(String t : titles){
            tabLayout.addTab(tabLayout.newTab().setText(t));
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.me_fragment,
                container, false);
        return view;
    }
}
