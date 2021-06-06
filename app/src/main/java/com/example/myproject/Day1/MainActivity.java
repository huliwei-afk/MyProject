package com.example.myproject.Day1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myproject.Day5.ChatFragment;
import com.example.myproject.Day4.EventFragment;
import com.example.myproject.Day3.GameFragment;
import com.example.myproject.Day2.HomeFragment;
import com.example.myproject.Day4.MeFragment;
import com.example.myproject.MessageEvent;
import com.example.myproject.R;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener{

    //底部导航栏相关组件实例化和碎片
    List<Fragment> fragmentList = new ArrayList<>();
    MainFragmentPagerAdapter adapter;
    ViewPager viewPager;
    RadioGroup radioGroup;

    public Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //设置自定toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //初始化控件
        initView();

        //绑定RadioButton
        initViewPager();

        //设置NavigationView的点击监听
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        //设置侧滑图标可显示
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                return true;
            }
        });


        //设置侧滑菜单
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_drawable_menu);
        }

        //订阅
        EventBus.getDefault().register(this);
    }

    //初始化控件
    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        radioGroup = findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(this);
        radioGroup.getChildAt(0).performClick();
    }

    //绑定adioButton
    private void initViewPager() {
        //添加碎片
        fragmentList.add(new HomeFragment());
        fragmentList.add(new GameFragment());
        fragmentList.add(new ChatFragment());
        fragmentList.add(new EventFragment());
        fragmentList.add(new MeFragment());

        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        radioGroup.check(R.id.rb_home);
                        break;
                    case 1:
                        radioGroup.check(R.id.rb_game);
                        break;
                    case 2:
                        radioGroup.check(R.id.rb_chat);
                        break;
                    case 3:
                        radioGroup.check(R.id.rb_event);
                        break;
                    case 4:
                        radioGroup.check(R.id.rb_me);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    //fragment切换时点击
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                viewPager.setCurrentItem(0);
                toolbar.setTitle("首页");
                toolbar.setVisibility(View.VISIBLE);
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.menu);
                break;
            case R.id.rb_game:
                viewPager.setCurrentItem(1);
                toolbar.setTitle("游戏");
                toolbar.setVisibility(View.VISIBLE);
                toolbar.getMenu().clear();
                toolbar.inflateMenu(R.menu.game_fragment_menu);
                break;
            case R.id.rb_chat:
                viewPager.setCurrentItem(2);
                toolbar.setTitle("聊天");
                toolbar.getMenu().clear();
                toolbar.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_event:
                viewPager.setCurrentItem(3);
                toolbar.setTitle("动态");
                toolbar.setVisibility(View.GONE);
                break;
            case R.id.rb_me:
                viewPager.setCurrentItem(4);
                toolbar.setTitle("个人");
                toolbar.getMenu().clear();
                toolbar.setVisibility(View.VISIBLE);
                break;

        }
    }



    //设置右上角menu菜单中的图标可见
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    //获取右上角的menu
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //重写方法指定右上角menu的功能
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.create_chat:
                Toast.makeText(this,"You clicked chat",Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_friend:
                Toast.makeText(this,"You clicked add",Toast.LENGTH_SHORT).show();
                break;
            case R.id.scan:
                Toast.makeText(this,"You clicked scan",Toast.LENGTH_SHORT).show();
                break;
            case R.id.pay:
                Toast.makeText(this,"You clicked pay",Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(this,"You clicked help",Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(this,"You clicked search",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                //与xml中的行为start一致
                drawerLayout.openDrawer(GravityCompat.START);
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MessageEvent messageEvent){
        toolbar.getMenu().clear();
        toolbar.inflateMenu(messageEvent.getMenuId());
    }
}


