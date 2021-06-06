package com.example.myproject.Day6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myproject.Day1.MainActivity;
import com.example.myproject.Day7.LoginActivity;
import com.example.myproject.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private List<View> views;
    private GuidePagerAdapter pagerAdapter;
    private ImageView dot1,dot2,dot3;
    private Button guideStartButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewPager=findViewById(R.id.guide_view_pager);
        guideStartButton = (Button)findViewById(R.id.guide_start_button);
        guideStartButton.getBackground().setAlpha(0);
        guideStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }

        });
        guideStartButton.setEnabled(false);
        initView();
    }

    private void initView() {
        //每个小点
        dot1=findViewById(R.id.dot_1);
        dot2=findViewById(R.id.dot_2);
        dot3=findViewById(R.id.dot_3);
        //把第一个设置亮其他为暗
        setDot(true,false,false);

        //得到view数据
        views=new ArrayList<>();
        LayoutInflater inflater=getLayoutInflater();
        views.add(inflater.inflate(R.layout.view1,null,false));
        views.add(inflater.inflate(R.layout.view2,null,false));
        views.add(inflater.inflate(R.layout.view3,null,false));
        //创建Adapter
        pagerAdapter=new GuidePagerAdapter(views,this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //当页面滑动时
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //当页面选中时
            @Override
            public void onPageSelected(int position) {

                switch (position){
                    case 0:
                        guideStartButton.setEnabled(false);
                        setDot(true,false,false);
                        break;
                    case 1:
                        guideStartButton.setEnabled(false);
                        setDot(false,true,false);
                        break;
                    case 2:
                        guideStartButton.setEnabled(true);
                        setDot(false,false,true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //小点的变化方法，为true时背景设亮，为false时背景设暗
    private void setDot(boolean a,boolean b,boolean c){
        if (a){
            dot1.setBackgroundResource(R.drawable.icon_point1);
        }else {
            dot1.setBackgroundResource(R.drawable.icon_point2);
        }

        if (b){
            dot2.setBackgroundResource(R.drawable.icon_point1);
        }else {
            dot2.setBackgroundResource(R.drawable.icon_point2);
        }

        if (c){
            dot3.setBackgroundResource(R.drawable.icon_point1);
        }else {
            dot3.setBackgroundResource(R.drawable.icon_point2);
        }
    }
}
