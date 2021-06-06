package com.example.myproject.Day2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myproject.Day1.MainActivity;
import com.example.myproject.MessageEvent;
import com.example.myproject.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class HomeFragment extends Fragment {
    //循环banner轮播图
    ViewPager viewPager;
    ArrayList<ImageView> points = new ArrayList<>();

    //首页新闻
    // Model:模型层，使用ArrayList保存数据源
    private List<News.DataBean> mNewsListTitle; // 新闻标题
    private List<String> mUrlList; // 新闻连接

    // View：视图层
    private RecyclerView recyclerViewForNews;

    // Presenter：表示层
    private NewsAdapter newsAdapter;

    //TabLayout选项卡和ViewPager
    private TabLayout tabLayout;
    private ViewPager viewPagerForTabLayout;
    private TextView textView;

    //progressBar
    private ProgressBar progressBar;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = (ViewPager)view.findViewById(R.id.viewPagerForBanner);
        progressBar = (ProgressBar)view.findViewById(R.id.home_progress);


        //设置适配器，调整当前所在page，加载进度点图像
        viewPager.setAdapter(new BannerAdapter(getImages()));

        viewPager.setCurrentItem(2);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            //根据选中图片的位置返回索引
            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    viewPager.setCurrentItem(getImages().size() - 2,false);
                }
                if(position == getImages().size() - 1){
                    viewPager.setCurrentItem(1,false);
                }
                setPointsImages();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //添加点图像
        LinearLayout pointLinearLayout = (LinearLayout)view.findViewById(R.id.point_layout);

        for(int i = 0; i < getImages().size() - 2; i++){
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30,30);
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            imageView.setLayoutParams(layoutParams);
            points.add(imageView);
            pointLinearLayout.addView(imageView);
        }

        // 实例化数据源
        mNewsListTitle = new ArrayList<>();
        // 对应的新闻连接
        mUrlList = new ArrayList<>();



        // 找控件
        recyclerViewForNews = (RecyclerView)view.findViewById(R.id.news_recycler_view);

        //设置不可滑动
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext()){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerViewForNews.setLayoutManager(layoutManager);
//        recyclerViewForNews.setNestedScrollingEnabled(false);

        // 实例化适配器
        newsAdapter = new NewsAdapter(getContext(),mNewsListTitle);

        // 发现视图层View没有跟表示层Presenter建立关系
        recyclerViewForNews.setAdapter(newsAdapter);

        //设置下划线
        recyclerViewForNews.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        //请求网络
        sendRequestWithOkHttp();

        //RV的点击事件
        RecyclerViewItemClick();

        //订阅
        EventBus.getDefault().postSticky(new MessageEvent( R.menu.menu));



//        new Thread() {
//
//            public void run() {
//                // 准备一个要访问的链接地址
//
////                String site = "http://v.juhe.cn/toutiao/index?type=youxi&page=1&page_size=25&key=2cb4dfb90827deb9158e0a1f4e4f1e45";
//                try {
//                    // 转换字符串为URL对象
//                    URL url = new URL(site);
//                    // 获得网络请求对象
//                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                    // 设置请求模式为GET
//                    conn.setRequestMethod("GET");
//                    // 获得网络请求状态码
//                    int httpCode = conn.getResponseCode();
//                    // 如果正常
//                    if (httpCode == 200) {
//                        // 获得字节输入流
//                        InputStream is = conn.getInputStream();
//                        // 字节流转字符流
//                        InputStreamReader isr = new InputStreamReader(is);
//                        // 套一层缓冲流提高效率(字符输入缓冲流)
//                        BufferedReader br = new BufferedReader(isr);
//
//                        // 用于拼接服务器返回的字符数据的字符串对象
//                        String data = new String();
//
//                        // 每次循环读取的buffer
//                        String buf;
//
//                        // 循环读取
//                        while ((buf = br.readLine()) != null) {
//                            // 拼接结果
//                            data += buf;
//                        }
//
//                        // 关闭流
//                        br.close();
//
//                        // 输出接收到的服务器的数据
//                        Log.d("HomeFragment", data);
//
//                        // 解析JSON数据（一层一层解析）
//                        // 最外层是一个JSONObject对象
//                        JSONObject object = new JSONObject(data);
//                        // 通过“result”键取得对应的值：JSONObject对象
//                        JSONObject result = object.getJSONObject("result");
//                        // 拿到“data”键对应的值：JSONArray（JSONObject数组）
//                        JSONArray dataArray = result.getJSONArray("data");
//                        // 遍历数组
//                        for (int i = 0; i < dataArray.length(); i++) {
//                            //每一条新闻的JSONObject对象
//                            JSONObject jsonObjectNews = dataArray.getJSONObject(i);
//                            //拿到新闻中需要需要的数据
//                            String name = jsonObjectNews.getString("title");
//                            String url2 = jsonObjectNews.getString("url");
//                            String image = jsonObjectNews.getString("thumbnail_pic_s");
//                            //添加到数据源中
//                            News news = new News(name,image);
//                            mNewsList.add(news);
//                            mUrlList.add(url2);
//                        }
//
//                        //切换到主线程刷新UI
//                        getActivity().runOnUiThread(new Runnable() {
//
//                            @Override
//                            public void run() {
//                                //主线程代码
//                                newsAdapter.upDateItem(mNewsList);
//                            }
//                        });
//
//                    } else {
//                        Log.d("MainActivity", "http请求失败，状态码：" + httpCode);
//                    }
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }.start();


    }





    //---------------------------------------------------------------------------------------------------------
    private void RecyclerViewItemClick(){
        //添加点击事件
        recyclerViewForNews.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerViewForNews){
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh,int position) {
                //item点击事件
                String url = mUrlList.get(position);
                // 跳转传值
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);

            }

            public void onItemDoubleClick(RecyclerView.ViewHolder vh, int position){
                //item双击事件
                String url = mUrlList.get(position);
                // 跳转传值
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }

            public void onItemLongClick(RecyclerView.ViewHolder vh, int position){
                //item长按事件
                String url = mUrlList.get(position);
                // 跳转传值
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }

    //---------------------------------------------------------------------------------------------------------


    //网络请求OkHttp
    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder requestBuilder = new Request.Builder().url("http://v.juhe.cn/toutiao/index?type=youxi&page=1&page_size=25&key=2cb4dfb90827deb9158e0a1f4e4f1e45");
                requestBuilder.method("GET",null);
                Request request = requestBuilder.build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    //此方法在主线程中回调，因此不能做耗时操作，要么重开子线程，要么改为同步方法直接接收Response
                    @Override
                    public void onResponse(Call call,  Response response) throws IOException {
                        final String responseData = response.body().string();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                parseJSONWIthGson(responseData);
                                showResponse(responseData);
                            }
                        }).start();

                    }
                });
            }
        }).start();
    }


    //解析GSON
    private void parseJSONWIthGson(String jsonData) {
        Gson gson = new Gson();

        News news = gson.fromJson(jsonData,News.class);
        List<News.DataBean> newsDataBean = news.getResult().getData();

        for (News.DataBean ndb : newsDataBean) {
            mNewsListTitle.add(ndb);
            mUrlList.add(ndb.getUrl());
        }
    }


    //更新界面
    private void showResponse(final String response) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                //主线程代码
                progressBar.setVisibility(View.GONE);
                newsAdapter.upDateItem(mNewsListTitle);
            }
        });
    }

    //---------------------------------------------------------------------------------------------------------


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_fragment, container, false);

        return view;
    }

    //---------------------------------------------------------------------------------------------------------

    //设置点图片
    public void setPointsImages(){
        for(int i = 0; i < points.size(); i++){
            if(i == viewPager.getCurrentItem() - 1 ){
                points.get(i).setBackgroundResource(R.drawable.icon_point1);
            }else {
                points.get(i).setBackgroundResource(R.drawable.icon_point2);
            }
        }
    }


    //返回一个banner图片的集合
    private ArrayList<Integer> getImages(){
        ArrayList<Integer> imagesList = new ArrayList<>();
        imagesList.add(R.drawable.timg26);
        imagesList.add(R.drawable.timg22);
        imagesList.add(R.drawable.timg24);
        imagesList.add(R.drawable.timg26);
        imagesList.add(R.drawable.timg22);

        return imagesList;
    }

    //内部类
    class BannerAdapter extends PagerAdapter{

        private ArrayList<Integer> bannerImageList;

        public BannerAdapter(ArrayList<Integer> imagesList){
            bannerImageList = imagesList;
        }
        @Override
        public int getCount() {
            return bannerImageList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View)object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setBackgroundResource(bannerImageList.get(position));
            setPointsImages();
            container.addView(imageView);
            return imageView;
        }
    }
//----------------------------------------------------------------------------------------------------------------

    public void onDestroyView(){
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
