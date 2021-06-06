package com.example.myproject.Day2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.myproject.R;

import java.util.List;

public class NewsActivity extends Activity {

    //定义一个变量
    private WebView mWebView;
    //定义一个等待显示浮动窗口
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局文件
        setContentView(R.layout.activity_news);

        //先取出Intent，再取出Intent里面的数据
        Intent intent = getIntent();

        String url = intent.getStringExtra("url");

        //设置等待显示浮动窗口的内容
        mDialog = new ProgressDialog(NewsActivity.this);
        mDialog.setTitle("今日头条");
        mDialog.setMessage("正在加载中...");


        mWebView = (WebView) findViewById(R.id.web_view);
        //让网络浏览的客户端设定在当前的WebView控件上（防止跳转到原生浏览器）
        mWebView.setWebViewClient(new WebViewClient(){

            //开始加载网页的回调方法
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //弹出等待窗口
                mDialog.show();
            }

            //网页加载结束的回调方法
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //如果等待窗口还在显示，那么关闭
                if(mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }

        });
        //开启JavaScript
        mWebView.getSettings().setJavaScriptEnabled(true);
        //加载网页
        mWebView.loadUrl(url);
    }

}
